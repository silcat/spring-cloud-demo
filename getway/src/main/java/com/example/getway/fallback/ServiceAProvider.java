package com.example.getway.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class ServiceAProvider implements FallbackProvider {
    @Override
    public ClientHttpResponse fallbackResponse(Throwable cause) {
        return fallbackResponse();
    }

    @Override
    public String getRoute() {
        return "service-a";
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
        return new Respone(HttpStatus.OK,"zuul fallback");

    }
}
