package ru.expenses;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Expense(LocalDate date, BigDecimal amount, String category) {

    public String toCsv() {
        return date + "," + amount.toPlainString() + "," + category;
    }

    public static Expense fromCsv(String line) {
        String[] parts = line.split(",", 3);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Bad CSV line: " + line);
        }
        return new Expense(LocalDate.parse(parts[0]), new BigDecimal(parts[1]), parts[2]);
    }
}
