package ru.expenses;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }
        ExpenseStorage storage = new ExpenseStorage(Path.of("expenses.csv"));
        switch (args[0]) {
            case "add" -> add(storage, args);
            case "list" -> storage.readAll().forEach(e ->
                    System.out.println(e.date() + "  " + e.amount() + "  " + e.category()));
            case "total" -> System.out.println("Total: " + storage.readAll().stream()
                    .map(Expense::amount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            default -> printUsage();
        }
    }

    private static void add(ExpenseStorage storage, String[] args) {
        if (args.length != 4) {
            printUsage();
            return;
        }
        try {
            Expense expense = new Expense(LocalDate.parse(args[1]), new BigDecimal(args[2]), args[3]);
            storage.append(expense);
            System.out.println("Added: " + expense.toCsv());
        } catch (DateTimeParseException | NumberFormatException e) {
            System.err.println("Invalid date or amount");
        }
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  add <yyyy-MM-dd> <amount> <category>");
        System.out.println("  list");
        System.out.println("  total");
    }
}
