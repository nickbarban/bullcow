package com.vaadinboot.bullcow.enums;

/**
 * ${description}
 *
 * @author Nick Barban.
 */
public enum GameLanguage {
    ENGLISH("en"),
    RUSSIAN("ru");

    private final String code;

    GameLanguage(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
