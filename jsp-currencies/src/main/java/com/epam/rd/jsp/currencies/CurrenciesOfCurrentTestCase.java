package com.epam.rd.jsp.currencies;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class CurrenciesOfCurrentTestCase extends Currencies {
    private static final String REGEX = "\\s";

    public CurrenciesOfCurrentTestCase() throws IOException {
        final Path testCasePath = Paths.get("src", "test", "resources", "test-cases", "current.txt");
        try (Stream<String> lines = Files.lines(testCasePath, UTF_8)) {
            lines
                    .map(line -> line.split(REGEX))
                    .forEach(tokens -> addCurrency(tokens[0], new BigDecimal(tokens[1])));
        }
    }
}
