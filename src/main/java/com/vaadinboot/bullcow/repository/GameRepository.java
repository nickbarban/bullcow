package com.vaadinboot.bullcow.repository;

import com.vaadinboot.bullcow.domain.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nick Barban.
 */
public interface GameRepository extends JpaRepository<GameEntity, String> {
}
