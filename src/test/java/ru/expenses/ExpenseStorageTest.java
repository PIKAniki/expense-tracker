package ru.expenses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ExpenseStorageTest {

    @TempDir
    Path dir;

    @Test
    void readsBackAppendedExpenses() {
        ExpenseStorage storage = new ExpenseStorage(dir.resolve("expenses.csv"));
        Expense first = new Expense(LocalDate.of(2026, 9, 1), new BigDecimal("350.50"), "food");
        Expense second = new Expense(LocalDate.of(2026, 9, 2), new BigDecimal("80"), "transport");

        storage.append(first);
        storage.append(second);

        assertEquals(List.of(first, second), storage.readAll());
    }

    @Test
    void writesHeaderOnlyOnce() throws IOException {
        Path file = dir.resolve("expenses.csv");
        ExpenseStorage storage = new ExpenseStorage(file);

        storage.append(new Expense(LocalDate.of(2026, 9, 1), BigDecimal.ONE, "food"));
        storage.append(new Expense(LocalDate.of(2026, 9, 2), BigDecimal.TEN, "food"));

        List<String> lines = Files.readAllLines(file);
        assertEquals(3, lines.size());
        assertEquals("date,sum,type", lines.get(0));
    }

    @Test
    void returnsEmptyListWhenFileIsMissing() {
        ExpenseStorage storage = new ExpenseStorage(dir.resolve("missing.csv"));

        assertTrue(storage.readAll().isEmpty());
    }

    @Test
    void skipsBlankLines() throws IOException {
        Path file = dir.resolve("expenses.csv");
        Files.writeString(file, "date,sum,type\n2026-09-01,100,food\n\n   \n2026-09-02,50,cafe\n");

        assertEquals(2, new ExpenseStorage(file).readAll().size());
    }
}
