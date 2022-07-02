package de.c24.finacc.klt.service;

import de.c24.finacc.klt.common.Cache;
import de.c24.finacc.klt.model.ConvertRequest;
import de.c24.finacc.klt.model.ExchangeApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class ConvertImpl implements IConvert {

    @Value("${baseUrl}")
    private String baseUrl;

    @Value("${keyName}")
    private String keyName;

    @Value("${keyValue}")
    private String keyValue;

    private final Cache cache;

    @Override
    public ConvertRequest getConvertRequest() {
        return new ConvertRequest();
    }

    /**
     * <p>This method returns the target amount,
     * if there is a response(based on source amount) written in the cache in the last 15 minutes,
     * it returns it, otherwise it sends a new request to get convert values.</p>
     *
     * @param request Convert Request
     * @return double target amount
     */
    @Override
    public Double convertAmount(ConvertRequest request) {
        ResponseEntity<ExchangeApiResponse> rateResponse = null;

        if (checkIsCacheFound(request)){
            rateResponse = ResponseEntity.ok(cache.getExchangeCache()
                    .stream()
                    .filter(c -> c.containsKey(request.getFrom()))
                    .findFirst()
                    .get()
                    .get(request.getFrom()));
        }else {
            rateResponse = new RestTemplate().exchange(baseUrl + "?base=" + request.getFrom(),
                    HttpMethod.GET,
                    generateEntity(),
                    ExchangeApiResponse.class );

            rateResponse.getBody().setCachedDate(LocalDateTime.now());

            fillCache(request, rateResponse);
        }
        return request.getAmount() * rateResponse.getBody().getRates().get(request.getTo());
    }

    /**
     * <p>This method fills cache with new response</p>
     *
     * @param request Convert Request
     * @param rateResponse Service Response
     */
    private void fillCache(ConvertRequest request, ResponseEntity<ExchangeApiResponse> rateResponse) {
        HashMap<String, ExchangeApiResponse> exchangeCache = new HashMap<>();
        exchangeCache.put(request.getFrom(), rateResponse.getBody());
        cache.getExchangeCache().add(exchangeCache);
    }

    /**
     * <p>This method returns if cache found</p>
     *
     * @param request Convert Request
     * @return boolean isCacheFound
     */
    private boolean checkIsCacheFound(ConvertRequest request) {
        return cache.getExchangeCache()
                .stream()
                .anyMatch(c -> c.containsKey(request.getFrom())
                        && c.get(request.getFrom()).getCachedDate().plusMinutes(1).isAfter(LocalDateTime.now()));
    }

    /**
     * <p>This method returns new Entity with Headers</p>
     *
     * @return HttpEntity<String> entity
     */
    private HttpEntity<String> generateEntity() {
        return new HttpEntity<>("body", generateHeaders());
    }

    /**
     * <p>This method returns new Headers</p>
     *
     * @return HttpHeaders headers
     */
    private HttpHeaders generateHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(keyName, keyValue);
        return headers;
    }


}
