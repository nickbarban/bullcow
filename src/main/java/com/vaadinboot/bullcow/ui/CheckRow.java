package com.vaadinboot.bullcow.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;
import lombok.extern.java.Log;

/**
 * @author Nick Barban.
 */
@Getter
@Log
class CheckRow extends HorizontalLayout {

    private final TextField word;

    private final Button button;

    private Label bull;

    private Label cow;

    private String placeHolder = "";

    CheckRow() {
        super();

        this.word = new TextField("", placeHolder);
        this.word.setMaxLength(MainView.gameComplexityLevel);
        this.word.setMinLength(MainView.gameComplexityLevel);
        this.button = new Button("Check");
        this.bull = new Label();
        this.bull.setVisible(false);
        this.cow = new Label();
        this.cow.setVisible(false);

        add(word, button, bull, cow);
    }

    void setBull(String text) {
        this.bull.setText(text);
        this.bull.setVisible(true);
    }

    void setCow(String text) {
        this.cow.setText(text);
        this.cow.setVisible(true);
    }
}
