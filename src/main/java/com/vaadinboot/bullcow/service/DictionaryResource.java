package com.vaadinboot.bullcow.service;

import com.vaadinboot.bullcow.enums.GameLanguage;

import java.util.List;

/**
 * @author Nick Barban.
 */
public interface DictionaryResource {

    List<String> getDictionary(GameLanguage language, int gameComplexityLevel);
}
