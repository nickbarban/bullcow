package com.vaadinboot.bullcow.ui;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadinboot.bullcow.enums.GameLanguage;
import com.vaadinboot.bullcow.service.GameService;
import javaslang.Tuple2;
import lombok.extern.java.Log;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author Nick Barban.
 */
@Route("")
@Log
public class MainView extends VerticalLayout {

    private static final int NOTIFICATION_DURATION = 3000;

    public static final Integer[] gameComplexityLevels = new Integer[]{5, 6, 7};

    public static String gameCathegory = "noun";

    public static GameLanguage gameLanguage = GameLanguage.ENGLISH;

    public static boolean updateDictionary = false;

    public static int gameComplexityLevel = 5;

    private final transient GameService service;

    private final StartLayout startLayout;

    private GameLayout gameLayout;

    private String secretWord;

    private Label finishGame;

    private List<String> dictionary;

    public MainView(GameService service) {
        this.service = service;

        startLayout = new StartLayout();
        startLayout.getStartButton().addClickListener(event -> startGame());
        add(startLayout);
        setAlignItems(Alignment.CENTER);
        setAlignSelf(Alignment.CENTER);
    }

    private void addRow() {
        CheckRow row = new CheckRow();
        row.getButton().addClickListener(event -> {
            CheckRow lastRow = gameLayout.getLeftDeskLayout().getLastRow();
            TextField wordField = lastRow.getWord();
            String value = wordField.getValue().trim();

            if (wordField.isInvalid() && StringUtils.isBlank(value)) {
                String errorMessage = StringUtils.isNotEmpty(wordField.getErrorMessage()) ? wordField.getErrorMessage() : "no message";
                Notification.show(String.format("Input is invalid: should has length=%s. Message: %s", gameComplexityLevel, errorMessage), NOTIFICATION_DURATION, Notification.Position.TOP_END);
                return;
            }

            try {
                service.validateWord(value, gameComplexityLevel, dictionary);
            } catch (Exception e) {
                wordField.setInvalid(true);
                Notification.show(String.format("Input is invalid: %s", e.getMessage()), NOTIFICATION_DURATION, Notification.Position.TOP_END);
                return;
            }

            Tuple2<String, String> result = service.check(value, secretWord);
            wordField.setEnabled(false);
            lastRow.setBull(result._1);
            lastRow.setCow(result._2);

            if (isVictory(result)) {
                finishGame();
            } else {
                addRow();
            }
        });
        gameLayout.getLeftDeskLayout().addRow(row);
    }

    private boolean isVictory(Tuple2<String, String> result) {
        return result._1.equals(result._2) && Integer.parseInt(result._1) == (gameComplexityLevel);
    }

    private void startGame() {

        String userName = startLayout.getUserField().getValue();
        if (CollectionUtils.isEmpty(dictionary) || updateDictionary) {
            dictionary = service.getCache(gameLanguage, gameComplexityLevel);
        }

        if (CollectionUtils.isEmpty(dictionary)) {
            dictionary = service.createCache(gameLanguage, gameComplexityLevel);
        }

        if (finishGame != null) {
            finishGame.setVisible(false);
        }

        startLayout.setVisible(false);
        secretWord = service.createSecretWord(userName, dictionary);
        gameLayout = new GameLayout();
        addRow();
        gameLayout.setVisible(true);
        add(gameLayout);
    }

    private void finishGame() {
        gameLayout.setVisible(false);
        finishGame = new Label("You won");
        add(finishGame);
        startLayout.setVisible(true);
    }
}
