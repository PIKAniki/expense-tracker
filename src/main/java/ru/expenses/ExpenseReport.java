package ru.expenses;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Отчёты по расходам.
 */
public class ExpenseReport {

    /** Суммы расходов по категориям, отсортированные по названию категории. */
    public static Map<String, BigDecimal> totalsByCategory(List<Expense> expenses) {
        Map<String, BigDecimal> totals = new TreeMap<>();
        for (Expense e : expenses) {
            totals.merge(e.category(), e.amount(), BigDecimal::add);
        }
        return totals;
    }
}
