package br.com.joseqfneto.hdn.DTO;

import java.time.LocalDate;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RateDTO {
    private String currency;
    private LocalDate requestDay;
    private Map<String, String> rates;
    private Boolean isTimeToBuy;
    private Boolean isTimeToSell;
}
