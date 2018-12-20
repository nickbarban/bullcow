package com.vaadinboot.bullcow.service;

import com.vaadinboot.bullcow.dto.OxfordApiDto;
import com.vaadinboot.bullcow.dto.OxfordApiMetadata;
import com.vaadinboot.bullcow.dto.WordDto;
import com.vaadinboot.bullcow.enums.GameLanguage;
import com.vaadinboot.bullcow.ui.MainView;
import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation for {@link DictionaryResource}.
 *
 * @author Nick Barban.
 */
@Service
@Log
public class OxfordDictionaryResourceImpl implements DictionaryResource {

    private static final String BASE_API = "https://od-api.oxforddictionaries.com:443/api/v1/";

    private static final Integer LIMIT = 5000;

    private final RestTemplate restTemplate;

    private final GameService service;

    private String url;

    private HttpHeaders hesders;

    public OxfordDictionaryResourceImpl(RestTemplate restTemplate, GameService service) {
        this.restTemplate = restTemplate;
        this.service = service;
    }

    @Override
    public List<String> getDictionary(final GameLanguage language, int gameComplexityLevel) {
        url = BASE_API
                .concat("wordlist/")
                .concat(language.getCode())
                .concat("/lexicalCategory=")
                .concat(MainView.gameCathegory)
                .concat("?offset={offset}")
                .concat("&limit={limit}");
        hesders = new HttpHeaders();
        hesders.set("app_id", "4f618ef7");
        hesders.set("app_key", "2ad21c35d017d9a717f62d167d86056a");

        Integer offset = 0;
        OxfordApiDto response = getWords(offset);
        List<WordDto> results = response.getResults();

        long awaitSeconds = 1;
        while (response.getMetadata().getTotal() > (offset + LIMIT)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.info(String.format("Interrupted awaiting: %s", e.getMessage()));
                awaitSeconds++;
            }
            offset = offset + LIMIT;
            response = getWords(offset);
            results.addAll(response.getResults());
        }

        response = getWords(offset);
        results.addAll(response.getResults());
        List<String> result = results.stream()
                .map(WordDto::getWord)
                .filter(s -> s.length() == gameComplexityLevel)
                .collect(Collectors.toList());
        log.info(String.format("%d words have unique chars and length=%d", result.size(), gameComplexityLevel));

        return result;
    }

    private OxfordApiDto getWords(Integer offset) {
        HttpEntity<?> entity = new HttpEntity<>(this.hesders);
        Map<String, Integer> params = new HashMap<>();
        params.put("offset", offset);
        params.put("limit", LIMIT);

        OxfordApiDto response;
        try {
            response = restTemplate.exchange(this.url, HttpMethod.GET, entity, OxfordApiDto.class, params).getBody();
        } catch (Exception ex) {
            OxfordApiMetadata metadata = new OxfordApiMetadata((long) Integer.MAX_VALUE, null, null, LIMIT, offset);
            response = new OxfordApiDto(Collections.EMPTY_LIST, metadata);
        }
        logResponse(response);
        return response;
    }

    private void logResponse(OxfordApiDto response) {

//        log.info(response.getResults().get(0).getWord() + ":~:" + response.getResults().get(response.getResults().size() - 1).getWord());

        String logMessage = String.format("Fetched %d / %d", response.getMetadata().getOffset(), response.getMetadata().getTotal());
        log.info(logMessage);
    }
}
