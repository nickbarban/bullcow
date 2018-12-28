package com.vaadinboot.bullcow.enums;

import com.vaadinboot.bullcow.service.DictionaryResource;
import com.vaadinboot.bullcow.service.OxfordDictionaryResource;
import com.vaadinboot.bullcow.service.OzhegovOrgDictionaryResource;
import com.vaadinboot.bullcow.service.SlovarozhegovaRuDictionaryResource;
import javaslang.Tuple;
import javaslang.Tuple2;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ${description}
 *
 * @author Nick Barban.
 */
@Getter
public enum GameLanguage {
    ENGLISH("en", Tuple.of('A', 'Z'), new OxfordDictionaryResource()),
    RUSSIAN("ru", Tuple.of('А', 'Я'), new OzhegovOrgDictionaryResource(), new SlovarozhegovaRuDictionaryResource());

    private final String code;

    private final List<DictionaryResource> resources;

    private final Tuple2<Character, Character> range;

    GameLanguage(String code, Tuple2<Character, Character> range, DictionaryResource... resources) {
        this.code = code;
        this.resources = Arrays.asList(resources);
        this.range = range;
    }

    public static List<String> codes() {
        return Arrays.stream(values()).map(GameLanguage::getCode).collect(Collectors.toList());
    }

    public static GameLanguage getLanguageByCode(String code) {
        for (GameLanguage language : GameLanguage.values()) {
            if (language.getCode().equalsIgnoreCase(code)) {
                return language;
            }
        }

        throw new IllegalArgumentException("Can not find language with code: " + code);
    }
}
