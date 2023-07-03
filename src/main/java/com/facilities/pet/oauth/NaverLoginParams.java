package com.facilities.pet.oauth;

import com.facilities.pet.domain.enums.OauthProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * . authorizationCode, state를 받아옴
 */
@Getter
@NoArgsConstructor
public class NaverLoginParams implements OauthLoginParams {

  private String authorizationCode;
  private String state;

  @Override
  public OauthProvider oauthProvider() {
    return OauthProvider.NAVER;
  }

  @Override
  public MultiValueMap<String, String> makeBody() {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("code", authorizationCode);
    body.add("state", state);
    return body;
  }
}
