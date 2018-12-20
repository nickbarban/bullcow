package com.vaadinboot.bullcow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nick Barban.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OxfordApiMetadata {

    private Long total;
    private String sourceLanguage;
    private String provider;
    private Integer limit;
    private Integer offset;
}
