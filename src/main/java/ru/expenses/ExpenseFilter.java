package ru.expenses;

import java.time.LocalDate;
import java.util.List;

public class ExpenseFilter {

    public static List<Expense> between(List<Expense> expenses, LocalDate from, LocalDate to) {
        return expenses.stream()
                .filter(e -> !e.date().isBefore(from) && !e.date().isAfter(to))
                .toList();
    }
}
