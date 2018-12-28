package com.vaadinboot.bullcow.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Nick Barban.
 */
@Entity
@Table(name = "game_entity", schema = "bullcow")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameEntity {

    @Id
    @Column(name = "user_name")
    private String userName;

    @Column(name = "secret_word")
    private String secretWord;
}
