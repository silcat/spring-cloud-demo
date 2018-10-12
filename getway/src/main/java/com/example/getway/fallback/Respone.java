package com.example.getway.fallback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Respone implements ClientHttpResponse {
    private HttpStatus status;
    private String errorMsg;

    @Override
    public HttpStatus getStatusCode() throws IOException {
        return status;
    }

    @Override
    public int getRawStatusCode() throws IOException {
        return status.value();
    }

    @Override
    public String getStatusText() throws IOException {
        return status.getReasonPhrase();
    }

    @Override
    public void close() {
    }

    @Override
    public InputStream getBody() throws IOException {
        return new ByteArrayInputStream(errorMsg.getBytes());
    }

    @Override
    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
