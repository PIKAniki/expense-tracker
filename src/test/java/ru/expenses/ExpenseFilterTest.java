package ru.expenses;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class ExpenseFilterTest {

    private final Expense sep1 = new Expense(LocalDate.of(2026, 9, 1), BigDecimal.ONE, "Food");
    private final Expense sep15 = new Expense(LocalDate.of(2026, 9, 15), BigDecimal.ONE, "cafe");
    private final Expense sep30 = new Expense(LocalDate.of(2026, 9, 30), BigDecimal.ONE, "food");
    private final List<Expense> expenses = List.of(sep1, sep15, sep30);

    @Test
    void includesBothBoundsOfPeriod() {
        List<Expense> result = ExpenseFilter.between(expenses, LocalDate.of(2026, 9, 1), LocalDate.of(2026, 9, 30));

        assertEquals(expenses, result);
    }

    @Test
    void excludesDatesOutsidePeriod() {
        List<Expense> result = ExpenseFilter.between(expenses, LocalDate.of(2026, 9, 2), LocalDate.of(2026, 9, 29));

        assertEquals(List.of(sep15), result);
    }

    @Test
    void filtersByCategoryIgnoringCase() {
        assertEquals(List.of(sep1, sep30), ExpenseFilter.byCategory(expenses, "FOOD"));
    }
}
