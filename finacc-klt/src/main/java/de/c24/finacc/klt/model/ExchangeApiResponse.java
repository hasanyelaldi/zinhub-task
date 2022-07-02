package de.c24.finacc.klt.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ExchangeApiResponse{
    private String base;
    private String date;
    private Map<String,Double> rates;
    private LocalDateTime cachedDate;

}
