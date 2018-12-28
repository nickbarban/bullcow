package com.vaadinboot.bullcow.service;

import com.vaadinboot.bullcow.dto.OxfordApiDto;
import com.vaadinboot.bullcow.dto.OxfordApiMetadata;
import com.vaadinboot.bullcow.dto.WordDto;
import com.vaadinboot.bullcow.enums.DictionaryApi;
import com.vaadinboot.bullcow.enums.GameLanguage;
import com.vaadinboot.bullcow.ui.MainView;
import lombok.extern.java.Log;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation for {@link OxfordDictionaryResource}.
 *
 * @author Nick Barban.
 */
@Service
@Log
public class OxfordDictionaryResource implements DictionaryResource {

    private static final Integer LIMIT = 5000;

    private final RestTemplate restTemplate;

    private String url;

    private HttpHeaders headers;

    public OxfordDictionaryResource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OxfordDictionaryResource() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public List<String> getDictionary(final GameLanguage language) {
        url = DictionaryApi.OXFORD.getBaseUrl()
                .concat("wordlist/")
                .concat(language.getCode())
                .concat("/lexicalCategory=")
                .concat(MainView.gameCathegory)
                .concat("?offset={offset}")
                .concat("&limit={limit}");
        headers = new HttpHeaders();
        headers.set("app_id", "4f618ef7");
        headers.set("app_key", "2ad21c35d017d9a717f62d167d86056a");

        Integer offset = 0;
        OxfordApiDto<WordDto> response = getWords(offset);
        List<WordDto> results = response.getResults();

        long awaitSeconds = 1;
        while (response.getMetadata().getTotal() > (offset + LIMIT)) {
            try {
                Thread.sleep(1000 * awaitSeconds);
            } catch (InterruptedException e) {
                log.info(String.format("Interrupted awaiting: %s", e.getMessage()));
                awaitSeconds++;
            }
            offset = offset + LIMIT;
            response = getWords(offset);

            if (CollectionUtils.isEmpty(response.getResults())) {
                offset = offset - LIMIT;
                continue;
            }

            results.addAll(response.getResults());
        }

        response = getWords(offset);
        results.addAll(response.getResults());
        return results.stream()
                .map(WordDto::getWord)
                .collect(Collectors.toList());
    }

    private OxfordApiDto<WordDto> getWords(Integer offset) {
        HttpEntity<?> entity = new HttpEntity<>(this.headers);
        Map<String, Integer> params = new HashMap<>();
        params.put("offset", offset);
        params.put("limit", LIMIT);

        OxfordApiDto<WordDto> response;
        try {
            ParameterizedTypeReference<OxfordApiDto<WordDto>> ptr = new ParameterizedTypeReference<OxfordApiDto<WordDto>>() {
            };
            response = restTemplate.exchange(this.url, HttpMethod.GET, entity, ptr, params).getBody();
        } catch (Exception ex) {
            OxfordApiMetadata metadata = new OxfordApiMetadata((long) Integer.MAX_VALUE, null, null, LIMIT, offset);
            response = new OxfordApiDto<>(new ArrayList<>(), metadata);
        }
        logResponse(response);
        return response;
    }

    private void logResponse(OxfordApiDto response) {
        String logMessage = String.format("Fetched %d / %d", response.getMetadata().getOffset(), response.getMetadata().getTotal());
        log.info(logMessage);
    }
}
