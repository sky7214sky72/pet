package com.facilities.pet.oauth;

import com.facilities.pet.domain.enums.OauthProvider;

public interface OauthApiClient {

  OauthProvider oAuthProvider();
   String requestAccessToken(OauthLoginParams params);
   OauthInfoResponse requestOauthInfo(String accessToken);
}
