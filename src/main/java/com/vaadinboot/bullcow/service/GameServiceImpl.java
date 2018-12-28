package com.vaadinboot.bullcow.service;

import com.vaadinboot.bullcow.domain.DictionaryEntity;
import com.vaadinboot.bullcow.domain.GameEntity;
import com.vaadinboot.bullcow.enums.GameLanguage;
import com.vaadinboot.bullcow.repository.DictionaryRepository;
import com.vaadinboot.bullcow.repository.GameRepository;
import com.vaadinboot.bullcow.ui.MainView;
import javaslang.Tuple;
import javaslang.Tuple2;
import lombok.extern.java.Log;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation for {@link GameService}.
 *
 * @author Nick Barban.
 */
@Service
@Log
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    private final DictionaryRepository dictionaryRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, DictionaryRepository dictionaryRepository) {
        this.gameRepository = gameRepository;
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public String createSecretWord(String userName, List<String> dictionary) {

        String secretWord = generateRandomWord(dictionary);

        while (!hasUniqueChars(secretWord)) {
            secretWord = generateRandomWord(dictionary);
        }

        GameEntity entity = new GameEntity(userName, secretWord);
        gameRepository.save(entity);
        return secretWord;
    }

    @Override
    public boolean hasUniqueChars(String word) {

        int MAX_CHAR = 256;

        if (word.length() > MAX_CHAR) {
            return false;
        }

        boolean[] chars = new boolean[MAX_CHAR];
        Arrays.fill(chars, false);

        for (int i = 0; i < word.length(); i++) {
            int index = (int) word.charAt(i);

            if (index > 256) {
                log.severe(String.format("Word: %s has wrong char at index %d", word, index));
                return false;
            }

            if (chars[index]) {
                log.warning(String.format("Word: %s has non-unique char at index %d", word, word.indexOf(index)));
                return false;
            }

            chars[index] = true;
        }

        return true;
    }

    @Override
    public Tuple2<String, String> check(String value, String secretWord) {
        return Tuple.of(getBull(value, secretWord), getCow(value, secretWord));
    }

    private String getBull(String value, String secretWord) {
        return String.valueOf(value.chars()
                .filter(ch -> secretWord.indexOf(ch) >= 0)
                .count());
    }

    private String getCow(String value, String secretWord) {
        return String.valueOf(value.chars()
                .filter(ch -> secretWord.indexOf(ch) >= 0)
                .filter(ch -> value.indexOf(ch) == secretWord.indexOf(ch))
                .count());
    }

    @Override
    public Boolean gameResult(Tuple2<String, String> result, int gameComplexityLevel) {
        return result._1.equals(result._2) && result._1.equals(String.valueOf(gameComplexityLevel));
    }

    @Override
    public void validateWord(String word, int length, List<String> dictionary) {
        Validate.notBlank(word, "The word can not be blank");
        Validate.isTrue(word.length() == length, String.format("The word should have length=%d", length));
        Validate.isTrue(hasUniqueChars(word), "The word should have only unique chars");
        Validate.isTrue(dictionary.contains(word), String.format("The word should be simple %s", MainView.gameCathegory));
    }

    @Override
    public List<String> getCache(GameLanguage gameLanguage, int maxLength) {
        return dictionaryRepository.findAllByLanguage(gameLanguage).stream()
                .map(DictionaryEntity::getWord)
                .filter(s -> s.length() == maxLength)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> createCache(GameLanguage language, int level) {
        DictionaryResource dictionaryResource;
        List<String> dictionary = null;
        int i = 0;

        while (CollectionUtils.isEmpty(dictionary)) {
            dictionaryResource = language.getResources().get(i++);
            dictionary = dictionaryResource.getDictionary(language);
        }

        dictionaryRepository.saveAll(dictionary.stream()
                .map(s -> new DictionaryEntity(s, MainView.gameLanguage))
                .collect(Collectors.toList()));

        return dictionary.stream()
                .filter(s -> s.length() == level)
                .filter(this::hasUniqueChars)
                .collect(Collectors.toList());
    }

    private String generateRandomWord(List<String> dictionary) {
        return dictionary.get((int) (Math.random() * dictionary.size()));
    }
}
