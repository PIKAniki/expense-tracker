package ru.expenses;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ExpenseStorage {

    private final Path file;

    public ExpenseStorage(Path file) {
        this.file = file;
    }

    public void append(Expense expense) {
        try {
            if (Files.notExists(file)) {
                Files.writeString(file, "date,amount,category" + System.lineSeparator());
            }
            Files.writeString(file, expense.toCsv() + System.lineSeparator(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot write " + file, e);
        }
    }

    public List<Expense> readAll() {
        List<Expense> result = new ArrayList<>();
        if (Files.notExists(file)) {
            return result;
        }
        try {
            List<String> lines = Files.readAllLines(file);
            for (int i = 1; i < lines.size(); i++) {
                result.add(Expense.fromCsv(lines.get(i)));
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot read " + file, e);
        }
        return result;
    }
}
