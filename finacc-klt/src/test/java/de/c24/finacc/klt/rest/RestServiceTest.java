package de.c24.finacc.klt.rest;

import static org.assertj.core.api.Assertions.assertThat;

import de.c24.finacc.klt.model.ConvertRequest;
import de.c24.finacc.klt.service.ConvertImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * RestServiceTest
 */
class RestServiceTest {

    private RestService restService = new RestService();

    @Autowired
    ConvertImpl convertImpl;

    @Test
    @DisplayName("Convert")
    void convert() {
        ConvertRequest request = new ConvertRequest();
        request.setAmount(1000.0);
        request.setFrom("TRY");
        request.setTo("USD");

        ResponseEntity<Double> result = restService.convert(request);
        assertThat(result.getBody() > 0.0);
    }
}
