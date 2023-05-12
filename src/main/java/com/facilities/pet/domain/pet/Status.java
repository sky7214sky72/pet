package com.facilities.pet.domain.pet;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * . Status
 */
@Getter
@AllArgsConstructor
public enum Status {
  SALES("STATUS_SALES", "영업중"),
  CLOSURE("STATUS_CLOSURE", "폐업");

  private final String key;
  private final String value;

  /**
   * . create
   */
  @JsonCreator
  public static Status create(String requestValue) {
    return Stream.of(values())
        .filter(v -> v.toString().equalsIgnoreCase(requestValue))
        .findFirst()
        .orElse(null);
  }
}
