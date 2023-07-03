package com.facilities.pet.oauth;

import com.facilities.pet.domain.enums.OauthProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverInfoResponse implements OauthInfoResponse {

  @JsonProperty("response")
  private Response response;

  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  static class Response {
    private String email;
    private String nickname;
  }

  @Override
  public String getEmail() {
    return response.email;
  }

  @Override
  public String getNickName() {
    return response.nickname;
  }

  @Override
  public OauthProvider getOauthprovider() {
    return OauthProvider.NAVER;
  }
}
