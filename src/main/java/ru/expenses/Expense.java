package ru.expenses;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Одна запись о расходе.
 *
 * @param date     дата расхода
 * @param amount   сумма
 * @param category категория, например {@code food}
 */
public record Expense(LocalDate date, BigDecimal amount, String category) {

    /** Возвращает запись в виде строки CSV: {@code дата,сумма,категория}. */
    public String toCsv() {
        return date + "," + amount.toPlainString() + "," + category;
    }

    /**
     * Разбирает строку CSV, полученную из {@link #toCsv()}.
     *
     * @throws IllegalArgumentException если в строке не три поля
     */
    public static Expense fromCsv(String line) {
        String[] parts = line.split(",", 3);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Bad CSV line: " + line);
        }
        return new Expense(LocalDate.parse(parts[0]), new BigDecimal(parts[1]), parts[2]);
    }
}
