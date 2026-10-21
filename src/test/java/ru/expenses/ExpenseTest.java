package ru.expenses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ExpenseTest {

    @Test
    void convertsToCsvLine() {
        Expense expense = new Expense(LocalDate.of(2026, 9, 1), new BigDecimal("350.50"), "food");

        assertEquals("2026-09-01,350.50,food", expense.toCsv());
    }

    @Test
    void parsesCsvLine() {
        Expense expense = Expense.fromCsv("2026-09-03,1200,transport");

        assertEquals(LocalDate.of(2026, 9, 3), expense.date());
        assertEquals(new BigDecimal("1200"), expense.amount());
        assertEquals("transport", expense.category());
    }

    @Test
    void keepsCommaInsideCategory() {
        Expense expense = Expense.fromCsv("2026-09-05,99.90,cafe, lunch");

        assertEquals("cafe, lunch", expense.category());
    }

    @Test
    void rejectsLineWithoutAllFields() {
        assertThrows(IllegalArgumentException.class, () -> Expense.fromCsv("2026-09-01,100"));
    }
}
