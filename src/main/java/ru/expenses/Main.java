package ru.expenses;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;

/**
 * Точка входа консольного приложения.
 * Путь к файлу с данными можно переопределить свойством {@code expenses.file}.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }
        ExpenseStorage storage = new ExpenseStorage(Path.of(System.getProperty("expenses.file", "expenses.csv")));
        switch (args[0]) {
            case "add" -> add(storage, args);
            case "list" -> storage.readAll().forEach(e ->
                    System.out.println(e.date() + "  " + e.amount() + "  " + e.category()));
            case "total" -> System.out.println("Total: " + storage.readAll().stream()
                    .map(Expense::amount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            case "month" -> month(storage, args);
            case "report" -> ExpenseReport.totalsByCategory(storage.readAll())
                    .forEach((category, total) -> System.out.println(category + ": " + total));
            default -> printUsage();
        }
    }

    private static void add(ExpenseStorage storage, String[] args) {
        if (args.length != 4) {
            printUsage();
            return;
        }
        try {
            BigDecimal amount = new BigDecimal(args[2].replace(',', '.'));
            if (amount.signum() <= 0) {
                System.err.println("Amount must be positive");
                return;
            }
            Expense expense = new Expense(LocalDate.parse(args[1]), amount, args[3]);
            storage.append(expense);
            System.out.println("Added: " + expense.toCsv());
        } catch (DateTimeParseException | NumberFormatException e) {
            System.err.println("Invalid date or amount");
        }
    }

    private static void month(ExpenseStorage storage, String[] args) {
        if (args.length != 2) {
            printUsage();
            return;
        }
        try {
            YearMonth month = YearMonth.parse(args[1]);
            System.out.println("Total for " + month + ": " + ExpenseReport.totalForMonth(storage.readAll(), month));
        } catch (DateTimeParseException e) {
            System.err.println("Invalid month, expected yyyy-MM");
        }
    }

    private static void printUsage() {
        System.out.println("Usage: expense-tracker <command>");
        System.out.println("  add <yyyy-MM-dd> <amount> <category>");
        System.out.println("  list");
        System.out.println("  total");
        System.out.println("  month <yyyy-MM>");
        System.out.println("  report");
    }
}
