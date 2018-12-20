package com.vaadinboot.bullcow.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Nick Barban.
 */
@Getter
@Setter
public class OxfordApiDto {

    private OxfordApiMetadata metadata;
    private List<WordDto> results;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public OxfordApiDto(@JsonProperty("results") List<WordDto> results,
                        @JsonProperty("metadata") OxfordApiMetadata metadata) {
        this.metadata = metadata;
        this.results = results;
    }
}
