package de.c24.finacc.klt.model;

import lombok.Data;

@Data
public class ConvertRequest {
    private Double amount;
    private String from;
    private String to;
}
