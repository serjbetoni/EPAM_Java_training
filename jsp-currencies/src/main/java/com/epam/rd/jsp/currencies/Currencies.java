package com.epam.rd.jsp.currencies;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Currencies {
    private static final int SCALE_FIVE = 5;
    private static final int SCALE_TEN = 10;
    private Map<String, BigDecimal> curs = new TreeMap<>();

    public void addCurrency(String currency, BigDecimal weight) {
        curs.put(currency, weight);
    }

    public Collection<String> getCurrencies() {
        return curs.keySet();
    }

    public Map<String, BigDecimal> getExchangeRates(String referenceCurrency) {
        Map<String, BigDecimal> relativeCurs = new TreeMap<>();
        for(Map.Entry<String,BigDecimal> entry : curs.entrySet()) {
            String key = entry.getKey();
            BigDecimal value = entry.getValue();
            BigDecimal referenceValue = curs.get(referenceCurrency);
            BigDecimal relativeRate = referenceValue.divide(value, SCALE_FIVE, RoundingMode.HALF_UP);
            relativeCurs.put(key, relativeRate);
        }
        return relativeCurs;
    }

    public BigDecimal convert(BigDecimal amount, String sourceCurrency, String targetCurrency) {
        BigDecimal sourseValue = curs.get(sourceCurrency);
        BigDecimal targetValue = curs.get(targetCurrency);
        BigDecimal relativeRate = sourseValue.divide(targetValue, SCALE_TEN, RoundingMode.HALF_UP);
        MathContext m = new MathContext(SCALE_TEN);
        return amount.multiply(relativeRate, m).setScale(SCALE_FIVE, RoundingMode.HALF_UP);
    }
}
