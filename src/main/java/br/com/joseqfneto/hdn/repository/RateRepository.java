package br.com.joseqfneto.hdn.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import br.com.joseqfneto.hdn.DTO.ExchangeRateDTO;

@Repository
public class RateRepository {

    @Autowired
    private RestTemplate restTemplate;

    public ExchangeRateDTO getRatesFromExchangeratesapi(String params) {
        String uri = "http://api.exchangeratesapi.io/v1/latest" + params;

        return restTemplate.getForObject(uri, ExchangeRateDTO.class);
    }

    public ExchangeRateDTO getHistoricalFromExchangeratesapi(String params) {
        String uri = "http://api.exchangeratesapi.io/v1/" + params;

        return restTemplate.getForObject(uri, ExchangeRateDTO.class);
    }
}
