package com.facilities.pet.oauth;

import com.facilities.pet.domain.enums.OauthProvider;
import org.springframework.util.MultiValueMap;

/**
 * . OAuth 요청을 위한 파라미터 값을 가지는 인터페이스
 */
public interface OauthLoginParams {
  OauthProvider oauthProvider();
  MultiValueMap<String, String> makeBody();
}
