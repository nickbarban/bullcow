package com.vaadinboot.bullcow.service;

import com.vaadinboot.bullcow.enums.DictionaryApi;
import com.vaadinboot.bullcow.enums.GameLanguage;
import org.assertj.core.api.Assertions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

/**
 * Test suite for {@link OxfordDictionaryResource}.
 *
 * @author Nick Barban.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OzhegovParserTest {

    private DictionaryResource sut;

    @Autowired
    private GameService service;

    @Test
    @Ignore
    public void shouldGetDictionaryFromOzhegovOrg() {
        sut = new OzhegovOrgDictionaryResource();
        final int level = 5;

        List<String> dictionary = sut.getDictionary(GameLanguage.RUSSIAN);

        Assertions.assertThat(dictionary)
                .isNotEmpty()
                .allMatch(s -> s.length() == level);
    }

    @Test
    public void shouldGetDictionaryFromSlovarozhegovaRu() {
        sut = new SlovarozhegovaRuDictionaryResource();

        List<String> dictionary = sut.getDictionary(GameLanguage.RUSSIAN);

        Assertions.assertThat(dictionary)
                .isNotEmpty()
                .startsWith("А")
                .endsWith("ЯЩУР");
    }

    @Test
    public void shouldParseCorrectWordBySelector() throws IOException {
        String selector = "body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td:nth-child(2) > index > div:nth-child(3) > table > tbody > tr > td > a > strong";
        String letter = "192";
        String url = DictionaryApi.SLOVAROZHEGOVA_RU_PARSER.getBaseUrl().concat(letter);

        Document doc = Jsoup.connect(url).get();

        List<String> strings = doc.select(selector).eachText();

        System.out.println(new String(new char[100]).replace('\0', 'T'));
        System.out.println(strings.size());
        System.out.println(strings);
        System.out.println(new String(new char[100]).replace('\0', 'T'));

        Assertions.assertThat(strings).isNotEmpty().startsWith("А").endsWith("АЯТОЛЛА");
    }

    @Test
    public void shouldCheckRussianWordsForUniqueChars() {
        boolean result = service.hasUniqueChars("word", GameLanguage.ENGLISH);
        Assertions.assertThat(result).isTrue();
        result = service.hasUniqueChars("мелодия", GameLanguage.RUSSIAN);
        Assertions.assertThat(result).isTrue();
        result = service.hasUniqueChars("clock", GameLanguage.ENGLISH);
        Assertions.assertThat(result).isFalse();
        result = service.hasUniqueChars("мама", GameLanguage.RUSSIAN);
        Assertions.assertThat(result).isFalse();
    }
}