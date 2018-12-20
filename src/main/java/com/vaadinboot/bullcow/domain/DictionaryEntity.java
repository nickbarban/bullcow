package com.vaadinboot.bullcow.domain;

import com.vaadinboot.bullcow.enums.GameLanguage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 * @author Nick Barban.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryEntity {

    @Id
    private String word;

    @Column
    @Enumerated(EnumType.STRING)
    private GameLanguage language;

}
