package com.vaadinboot.bullcow.service;

import com.vaadinboot.bullcow.enums.DictionaryApi;
import com.vaadinboot.bullcow.enums.GameLanguage;
import lombok.extern.java.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Nick Barban.
 */
@Service
@Log
public class SlovarozhegovaRuDictionaryResource implements DictionaryResource {

    private static final int FIRST_LETTER_INDEX = 192;

    private static final int LAST_LETTER_INDEX = 223;

    private static final String CSS_SELECTOR = "body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td:nth-child(2) > index > div:nth-child(3) > table > tbody > tr > td > a > strong";

    private static final String BASE_URL = DictionaryApi.SLOVAROZHEGOVA_RU_PARSER.getBaseUrl();

    @Override
    public List<String> getDictionary(GameLanguage language) {

        return IntStream.range(FIRST_LETTER_INDEX, LAST_LETTER_INDEX + 1)
                .mapToObj(String::valueOf)
                .map(this::getWordsFor)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<String> getWordsFor(String letter) {
        String url = BASE_URL.concat(letter);
        List<String> result = getWords(url);

        log.info(String.format("Fetch %d words by url: %s", result.size(), url));
        return result;
    }

    private List<String> getWords(String url) {

        log.info(String.format("Connect to %s", url));

        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            log.warning(String.format("Can not connect to %s. Message: %s.", url, e.getMessage()));
            return new ArrayList<>();
        }

        return doc.select(CSS_SELECTOR).eachText();
    }
}
