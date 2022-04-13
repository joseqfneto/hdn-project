package br.com.joseqfneto.hdn.DTO;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExchangeRateDTO {
    private Boolean success;
    private Timestamp timestamp;
    private String base;
    private LocalDate date;
    private Map<String, String> rates;
}
