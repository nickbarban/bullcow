package com.vaadinboot.bullcow.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadinboot.bullcow.enums.GameLanguage;
import lombok.Getter;
import lombok.extern.java.Log;

/**
 * @author Nick Barban.
 */
@Getter
@Log
class StartLayout extends com.vaadin.flow.component.orderedlayout.HorizontalLayout {

    private final TextField userField;

    private final Button startButton;

    private final ComboBox<Integer> gameLevel;

    private final ComboBox<GameLanguage> gameLanguage;

    StartLayout() {
        super();

        userField = new TextField();
        userField.setLabel("Input user name");
        gameLevel = new ComboBox<>();
        gameLevel.setLabel("Level");
        gameLevel.setItems(MainView.gameComplexityLevels);
        gameLevel.setValue(MainView.gameComplexityLevel);
        gameLevel.addValueChangeListener(event -> {
            int newGameComplexityLevel = event.getValue();

            if (MainView.gameComplexityLevel != newGameComplexityLevel) {
                MainView.gameComplexityLevel = newGameComplexityLevel;
            }
        });
        gameLevel.setWidth("80px");
        gameLanguage = new ComboBox<>();
        gameLanguage.setLabel("Language");
        gameLanguage.setItems(GameLanguage.values());
        gameLanguage.setValue(MainView.gameLanguage);
        gameLanguage.addValueChangeListener(event -> {
            GameLanguage newGameLanguage = event.getValue();

            if (!MainView.gameLanguage.equals(newGameLanguage)) {
                MainView.gameLanguage = newGameLanguage;
                MainView.updateDictionary = true;
            }
        });
//        gameLanguage.setWidth("80px");
        startButton = new Button("Start");
        setAlignItems(Alignment.BASELINE);
        add(userField, gameLevel, gameLanguage, startButton);
        setSpacing(true);
    }
}
