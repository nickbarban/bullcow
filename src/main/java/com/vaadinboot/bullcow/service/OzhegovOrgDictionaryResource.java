package com.vaadinboot.bullcow.service;

import com.vaadinboot.bullcow.enums.DictionaryApi;
import com.vaadinboot.bullcow.enums.GameLanguage;
import lombok.extern.java.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Nick Barban.
 */
@Service
@Qualifier("ozhegovOrgDictionaryResource")
@Log
public class OzhegovOrgDictionaryResource implements DictionaryResource {

    private static final List<String> TRANSLITERATIONS = Arrays.asList("a", "b", "v", "g", "d", "e", "yo", "zh", "z", "i", "j", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "f", "h", "ts", "ch", "sh", "shch", "ee", "yu", "ya");

    private static final String SUB_PAGE_PATH = "alfabet/";

    @Override
    public List<String> getDictionary(GameLanguage language) {
        Map<Integer, String> charactersWithTransliterations = IntStream.range(0, TRANSLITERATIONS.size())
                .boxed()
                .collect(Collectors.toMap(i -> i += 1072, TRANSLITERATIONS::get, (p1, p2) -> p1));

        Map<Integer, List<String>> wordsByLetterCodes = charactersWithTransliterations.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> getWordsFor(entry.getValue()), (p1, p2) -> p1));

        wordsByLetterCodes.forEach((integer, strings) -> log.info(String.format("i=%d letter=%s[%s] words:%d",
                integer, (char) integer.intValue(), charactersWithTransliterations.get(integer), strings.size())));

        return wordsByLetterCodes.entrySet().stream()
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<String> getWordsFor(String letter) {
        String aPageName = letter + ".shtml";
        String baseUrl = DictionaryApi.OZHEGOV_ORG_PARSER.getBaseUrl();
        String url = baseUrl.concat(aPageName);
        List<String> result;
        try {
            result = getWords(url, ParseElementType.PARENT);
        } catch (Exception e) {
            log.warning(String.format("Can not fetch words from %s. Message: %s.", url, e.getMessage()));
            return new ArrayList<>();
        }

        int i = 1;
        while (true) {
            i++;
            try {
                url = baseUrl.concat(SUB_PAGE_PATH).concat(letter + i + ".shtml");
                List<String> words = getWords(url, ParseElementType.CHILD);
                Objects.requireNonNull(result).addAll(words);
            } catch (Exception ex) {
                log.warning(String.format("Can not fetch words from %s. Message: %s.", url, ex.getMessage()));
                break;
            }
        }

        log.info(String.format("Fetch %d pages and %d words for %s letter at: %s", i, result.size(), letter, url));
        return result;
    }

    private List<String> getWords(String url, ParseElementType entityType) throws Exception {
        log.info(String.format("Connect to %s", url));
        Document doc = Jsoup.connect(url).get();
        return doc.select(entityType.getElementSelector()).eachText();
    }

    private enum ParseElementType {
        PARENT("body > table > tbody > tr:nth-child(4) > td:nth-child(2) > table > tbody > tr > td.article > blockquote > a"),
        CHILD("body > table > tbody > tr:nth-child(4) > td:nth-child(2) > table > tbody > tr > td.article > blockquote:nth-child(3) > a");

        private final String elementSelector;

        ParseElementType(String elementSelector) {
            this.elementSelector = elementSelector;
        }

        public String getElementSelector() {
            return elementSelector;
        }
    }
}
