package br.com.joseqfneto.hdn.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joseqfneto.hdn.DTO.ExchangeRateDTO;
import br.com.joseqfneto.hdn.DTO.RateDTO;
import br.com.joseqfneto.hdn.repository.RateRepository;

@Service
public class RateService {

    @Autowired
    public RateRepository rateRepository;

    // devem ser passados por parâmetro posteriormente
    private String key = "29a45f74f5d225005b8ed764b18d668a";
    private String base = "EUR";
    private String symbols = "BRL,USD";

    public RateDTO getRatesFromExchangeratesapi() {
        // declarações de variáveis
        String params = "?access_key=" + key;
        params += "&base=" + base;
        params += "&symbols=" + symbols;

        // objeto de retorno desta api
        RateDTO rateDTO = new RateDTO();

        // obtendo os valores atualizados de cambio
        ExchangeRateDTO exchangeRateDTO = rateRepository.getRatesFromExchangeratesapi(params);

        // preencher os atributos do DTO de retorno
        rateDTO.setCurrency(base);
        rateDTO.setRequestDay(LocalDate.now());
        rateDTO.setRates(exchangeRateDTO.getRates());

        // verificando se é hora de comprar ou vender no método isTimeToBuy
        Boolean isTimeToBuy = this.isTimeToBuy(exchangeRateDTO.getRates());

        // preenchendo se é hora de comprar
        rateDTO.setIsTimeToBuy(isTimeToBuy);

        // preenchendo se é hora de vender
        rateDTO.setIsTimeToSell(!isTimeToBuy);

        return rateDTO;
    }

    private ExchangeRateDTO getHistoricalFromApi(LocalDate date) {
        String params = "/" + date.toString();
        params += "?access_key=" + key;
        params += "&base=" + base;
        params += "&symbols=" + symbols;

        return rateRepository.getHistoricalFromExchangeratesapi(params);
    }

    private Boolean isTimeToBuy(Map<String, String> currentRate) {
        Boolean isTime = false;
        LocalDate today = LocalDate.now();

        // map para coletar o histórico por moeda
        Map<String, Double> ratesAvg = new HashMap<String, Double>();

        // coletando e tirando a média das taxas da última semana
        for (int i = 1; i <= 7; i++) {
            today = today.minusDays(i);
            // coletando as taxas da última semana
            ExchangeRateDTO e = getHistoricalFromApi(today);

            // tirando a média conforme chegam as taxas
            if (!e.getRates().isEmpty()) {
                e.getRates().forEach((key, val) -> {
                    if (ratesAvg.get(key) != null) {
                        ratesAvg.put(key, ((ratesAvg.get(key) + Double.parseDouble(val)) / 2));
                    } else {
                        ratesAvg.put(key, Double.parseDouble(val));
                    }
                });
            }
        }
        if (!ratesAvg.isEmpty()) {
            // percorrendo as médias e comparando com o atual
            currentRate.forEach((key, val) -> {
                Boolean b = isTime;
                if (Double.parseDouble(val) < ratesAvg.get(key)) {
                    b = true;
                }
            });

        } else {
            // implementar exception!
        }

        return isTime;
    }

}
