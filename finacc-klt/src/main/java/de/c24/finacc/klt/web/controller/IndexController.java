package de.c24.finacc.klt.web.controller;

import de.c24.finacc.klt.model.ConvertRequest;
import de.c24.finacc.klt.model.ExchangeApiResponse;
import de.c24.finacc.klt.util.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * IndexController
 */
@Controller
public class IndexController {

    /**
     * Index endpoint to show the index page
     *
     * @param model Spring's view model
     * @return view name
     */
    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("title", "Karten&Konten KLT");
        model.addAttribute("welcome", "Welcome to Check24");
        model.addAttribute("applicationTitle", "Check24 Currency Converter");

        List<Currency> currencies =  Arrays.asList(Currency.values());
        model.addAttribute("currencies", currencies);
        return "index";
    }

    /**
     * Index endpoint to show the index page
     *
     * @param model Spring's view model
     * @return double target amount
     */
    @RequestMapping(value="/convert",method=RequestMethod.POST)
    public String convert(@RequestParam Double amount,
                                @RequestParam String from, @RequestParam String to, Model model) {
        model.addAttribute("title", "Karten&Konten KLT");
        model.addAttribute("welcome", "Welcome to Check24");
        model.addAttribute("applicationTitle", "Check24 Currency Converter");

        ConvertRequest req = new ConvertRequest();
        req.setAmount(amount);
        req.setFrom(from);
        req.setTo(to);

        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ConvertRequest> requestEntity = new HttpEntity<>(req, headers);
        ResponseEntity<Double> response =
                template.exchange("http://localhost:8080/klt/api/convert", HttpMethod.POST, requestEntity,
                        Double.class);

        model.addAttribute("targetAmount", response.getBody());
        return "index";
    }


}
