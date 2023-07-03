package com.facilities.pet.oauth;

import com.facilities.pet.domain.enums.OauthProvider;

public interface OauthInfoResponse {
  String getEmail();
  String getNickName();
  OauthProvider getOauthprovider();
}
