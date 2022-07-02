package de.c24.finacc.klt.service;

import de.c24.finacc.klt.model.ConvertRequest;

public interface IConvert {
    public Double convertAmount(ConvertRequest request);
}
