package com.vaadinboot.bullcow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Nick Barban.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OxfordLanguageDto implements Serializable {
    private String region;
    private String source;
    private LanguageDto sourceLanguage;
    private String type;
}
