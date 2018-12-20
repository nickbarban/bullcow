package com.vaadinboot.bullcow.repository;

import com.vaadinboot.bullcow.domain.DictionaryEntity;
import com.vaadinboot.bullcow.enums.GameLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Nick Barban.
 */
public interface DictionaryRepository extends JpaRepository<DictionaryEntity, String> {
    List<DictionaryEntity> findAllByLanguage(GameLanguage gameLanguage);
}
