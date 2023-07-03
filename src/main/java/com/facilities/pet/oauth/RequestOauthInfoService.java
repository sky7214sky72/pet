package com.facilities.pet.oauth;

import com.facilities.pet.domain.enums.OauthProvider;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class RequestOauthInfoService {
  private final Map<OauthProvider, OauthApiClient> clients;

  public RequestOauthInfoService(List<OauthApiClient> clients) {
    this.clients = clients.stream().collect(
        Collectors.toUnmodifiableMap(OauthApiClient::oAuthProvider, Function.identity())
    );
  }

  public OauthInfoResponse request(OauthLoginParams params) {
    OauthApiClient client = clients.get(params.oauthProvider());
    String accessToken = client.requestAccessToken(params);
    return client.requestOauthInfo(accessToken);
  }
}
