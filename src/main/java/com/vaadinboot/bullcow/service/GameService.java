package com.vaadinboot.bullcow.service;

import com.vaadinboot.bullcow.enums.GameLanguage;
import javaslang.Tuple2;

import java.util.List;

/**
 * Business logic for .
 *
 * @author Nick Barban.
 */
public interface GameService {
    String createSecretWord(String userName, List<String> dictionary, GameLanguage language);

    boolean hasUniqueChars(String word, GameLanguage language);

    Tuple2<String, String> check(String value, String secretWord);

    Boolean gameResult(Tuple2<String, String> result, int gameComplexityLevel);

    void validateWord(String value, int maxLength, List<String> dictionary, GameLanguage language);

    List<String> getCache(GameLanguage gameLanguage, int maxLength);

    List<String> createCache(GameLanguage dictionary, int level);
}
