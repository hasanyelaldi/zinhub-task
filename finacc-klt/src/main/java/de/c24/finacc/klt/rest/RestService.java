package de.c24.finacc.klt.rest;

import de.c24.finacc.klt.service.ConvertImpl;
import de.c24.finacc.klt.model.ConvertRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest Service
 */
@RestController
@RequestMapping("/api")
public class RestService {

    @Autowired
    ConvertImpl convertImpl;

    /**
     * Test Convert endpoint
     * <p>This method returns target amount</p>
     *
     * @return Target Price
     */
    @PostMapping("/convert")
    public ResponseEntity<Double> convert(@Valid @RequestBody ConvertRequest request) {
        return ResponseEntity.ok(convertImpl.convertAmount(request));
    }


}
