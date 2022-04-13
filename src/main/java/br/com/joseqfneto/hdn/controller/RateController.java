package br.com.joseqfneto.hdn.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.joseqfneto.hdn.service.RateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/rates")
public class RateController {

    @Autowired
    private RateService rateService;

    @GetMapping
    public ResponseEntity<Object> getRates() {
        
        return ResponseEntity.status(HttpStatus.OK).body(rateService.getRatesFromExchangeratesapi());
    }
        
}
