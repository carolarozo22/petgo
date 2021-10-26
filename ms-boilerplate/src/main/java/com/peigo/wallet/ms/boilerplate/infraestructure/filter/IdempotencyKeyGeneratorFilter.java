package com.peigo.wallet.ms.boilerplate.infraestructure.filter;

import com.github.damianwajser.idempotency.exception.ArgumentNotFoundException;
import com.github.damianwajser.idempotency.generators.IdempotencyKeyGenerator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.stream.Collectors;

public class IdempotencyKeyGeneratorFilter implements IdempotencyKeyGenerator<Object> {

    private static final String IDEMPOTENCY_DEFAULT_HEADER = "X-Idempotency-Key";

    private static final String DASH = "-";

    @Override
    public String generateKey(HttpHeaders headers, HttpMethod method, String path, Object body) {
        String key = getHeaderValue(headers, IDEMPOTENCY_DEFAULT_HEADER);
        return path.concat(DASH).concat(key).concat(DASH).concat(method.toString()).concat(DASH);
    }

    protected String getHeaderValue(HttpHeaders headers, String headerKey) {
        List<String> idempotencyHeader = headers.get(headerKey);
        String key;
        if (idempotencyHeader != null) {
            key = idempotencyHeader.stream().collect(Collectors.joining(DASH));
        } else {
            throw new ArgumentNotFoundException(headerKey);
        }
        return key;
    }
}
