package de.c24.finacc.klt.common;

import de.c24.finacc.klt.model.ExchangeApiResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Data
public class Cache {
    private List<HashMap<String, ExchangeApiResponse>> exchangeCache = new ArrayList<>();

}
