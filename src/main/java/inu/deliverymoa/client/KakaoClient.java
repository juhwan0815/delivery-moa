package inu.deliverymoa.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import static org.springframework.http.HttpHeaders.*;


@FeignClient(name = "kakao-service", url = "https://kapi.kakao.com")
public interface KakaoClient {

    @PostMapping("/v2/user/me")
    KakaoProfile getKakaoProfile(@RequestHeader(AUTHORIZATION) String authorization,
                                 @RequestHeader(CONTENT_TYPE) String contentType);
}
