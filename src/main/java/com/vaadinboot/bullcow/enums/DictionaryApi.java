package com.vaadinboot.bullcow.enums;

/**
 * @author Nick Barban.
 */

public enum DictionaryApi {
    OXFORD("https://od-api.oxforddictionaries.com:443/api/v1/"),
    OZHEGOV_ORG_PARSER("http://www.ozhegov.org/"),
    SLOVAROZHEGOVA_RU_PARSER("https://slovarozhegova.ru/letter.php?charkod=");

    private final String baseUrl;

    DictionaryApi(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
