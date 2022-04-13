package br.com.joseqfneto.hdn.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.joseqfneto.hdn.DTO.ExchangeRateDTO;
import br.com.joseqfneto.hdn.DTO.RateDTO;

@Service
public class RateService {

    @Autowired
    public RestTemplate restTemplate;

    private String key = "29a45f74f5d225005b8ed764b18d668a";
    private String base = "EUR";
    private String symbols = "BRL,USD";

    public RateDTO getRatesFromExchangeratesapi() {
        // declarações de variáveis
        String uri = "http://api.exchangeratesapi.io/v1/latest";
        uri += "?access_key=" + key;
        uri += "&base=" + base;
        uri += "&symbols=" + symbols;

        // obtendo os valores atualizados de cambio
        ExchangeRateDTO exchangeRateDTO = restTemplate.getForObject(uri, ExchangeRateDTO.class);

        // objeto de retorno desta api
        RateDTO rateDTO = new RateDTO();

        // preencher os atributos do DTO de retorno
        rateDTO.setCurrency(base);
        rateDTO.setRequestDay(LocalDate.now());
        rateDTO.setRates(exchangeRateDTO.getRates());

        // verificando se é hora de comprar ou vender no método isTimeToBuy
        Boolean isTimeToBuy = this.isTimeToBuy(exchangeRateDTO.getRates());

        //preenchendo se é hora de comprar
        rateDTO.setIsTimeToBuy(isTimeToBuy);

        // preenchendo se é hora de vender
        rateDTO.setIsTimeToSell(!isTimeToBuy);

        return rateDTO;
    }

    private ExchangeRateDTO getHistoricalFromExchangeratesapi(LocalDate date) {
        String uri = "http://api.exchangeratesapi.io/v1/" + date.toString();

        uri += "?access_key=" + key;
        uri += "&base=" + base;
        uri += "&symbols=" + symbols;

        return restTemplate.getForObject(uri, ExchangeRateDTO.class);
    }

    private Boolean isTimeToBuy(Map<String, String> currentRate) {
        Boolean isTime = false;
        LocalDate today = LocalDate.now();

        // map para coletar o histórico por moeda
        Map<String, Double> ratesAvg = new HashMap<String, Double>();
        // Map<String, String> ratesAvg = new HashMap<String, String>();

        // coletando e tirando a média das taxas da última semana
        for (int i = 1; i <= 7; i++) {
            today = today.minusDays(i);
            // coletando as taxas da última semana
            ExchangeRateDTO e = getHistoricalFromExchangeratesapi(today);

            // tirando a média conforme chegam as taxas
            if (!e.getRates().isEmpty()) {
                e.getRates().forEach((key, val) -> {
                    if (ratesAvg.get(key) != null) {
                        ratesAvg.put(key, ((ratesAvg.get(key) + Double.parseDouble(val)) / 2));
                    } else {
                        // ArrayList<String> arr = new ArrayList();
                        // arr.add(val);
                        ratesAvg.put(key, Double.parseDouble(val));
                    }
                });
            }
        }

        if (!ratesAvg.isEmpty()) {
            // percorrendo as médias e comparando com o atual
            currentRate.forEach((key, val) -> {
                if (Double.parseDouble(val) < ratesAvg.get(key)) {
                    // isTime = true;
                }
            });
        } else {
            // implementar exception!
        }

        // System.out.println(ratesHistoric.toString());
        return isTime;
    }

}
