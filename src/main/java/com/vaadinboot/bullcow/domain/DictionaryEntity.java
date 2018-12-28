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
import javax.persistence.Table;

/**
 * @author Nick Barban.
 */
@Entity
@Table(name = "dictionary_entity", schema = "bullcow")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryEntity {

    @Id
    @Column(name = "word")
    private String word;

    @Column(name = "language")
    @Enumerated(EnumType.STRING)
    private GameLanguage language;

}
