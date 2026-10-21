package ru.expenses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ExpenseReportTest {

    private static Expense expense(String amount, String category) {
        return new Expense(LocalDate.of(2026, 9, 1), new BigDecimal(amount), category);
    }

    @Test
    void sumsAmountsByCategory() {
        List<Expense> expenses = List.of(
                expense("100", "food"),
                expense("50.50", "transport"),
                expense("25", "food"));

        Map<String, BigDecimal> totals = ExpenseReport.totalsByCategory(expenses);

        assertEquals(new BigDecimal("125"), totals.get("food"));
        assertEquals(new BigDecimal("50.50"), totals.get("transport"));
    }

    @Test
    void sortsCategoriesByName() {
        Map<String, BigDecimal> totals = ExpenseReport.totalsByCategory(
                List.of(expense("1", "transport"), expense("1", "cafe"), expense("1", "food")));

        assertEquals(List.of("cafe", "food", "transport"), List.copyOf(totals.keySet()));
    }

    @Test
    void sumsOnlyExpensesOfGivenMonth() {
        List<Expense> expenses = List.of(
                new Expense(LocalDate.of(2026, 8, 31), new BigDecimal("500"), "food"),
                new Expense(LocalDate.of(2026, 9, 1), new BigDecimal("100"), "food"),
                new Expense(LocalDate.of(2026, 9, 30), new BigDecimal("20.50"), "cafe"),
                new Expense(LocalDate.of(2026, 10, 1), new BigDecimal("300"), "food"));

        assertEquals(new BigDecimal("120.50"), ExpenseReport.totalForMonth(expenses, YearMonth.of(2026, 9)));
    }

    @Test
    void monthWithoutExpensesGivesZero() {
        assertEquals(BigDecimal.ZERO, ExpenseReport.totalForMonth(List.of(expense("10", "food")), YearMonth.of(2025, 1)));
    }

    @Test
    void returnsEmptyMapForNoExpenses() {
        assertTrue(ExpenseReport.totalsByCategory(List.of()).isEmpty());
    }
}
