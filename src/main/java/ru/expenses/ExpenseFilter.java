package ru.expenses;

import java.time.LocalDate;
import java.util.List;

/**
 * Отбор расходов по условиям.
 */
public class ExpenseFilter {

    /** Расходы указанной категории, регистр не учитывается. */
    public static List<Expense> byCategory(List<Expense> expenses, String category) {
        return expenses.stream()
                .filter(e -> e.category().equalsIgnoreCase(category))
                .toList();
    }

    /** Расходы за период; обе границы включаются. */
    public static List<Expense> between(List<Expense> expenses, LocalDate from, LocalDate to) {
        return expenses.stream()
                .filter(e -> !e.date().isBefore(from) && !e.date().isAfter(to))
                .toList();
    }
}
