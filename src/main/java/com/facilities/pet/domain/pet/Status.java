package com.facilities.pet.domain.pet;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Status {
    SALES("STATUS_SALES","영업중"),
    CLOSURE("STATUS_CLOSURE","폐업");

    private String key;
    private String value;

    @JsonCreator
    public static Status create(String requestValue) {
        return Stream.of(values())
                .filter(v -> v.toString().equalsIgnoreCase(requestValue))
                .findFirst()
                .orElse(null);
    }
}
