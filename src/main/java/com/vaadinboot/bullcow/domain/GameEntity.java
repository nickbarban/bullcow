package com.vaadinboot.bullcow.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Nick Barban.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameEntity {

    @Id
    private String userName;

    @Column
    private String secretWord;
}
