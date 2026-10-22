package ru.expenses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Запускает приложение целиком через {@link Main#main(String[])} на временном файле.
 */
class ExpenseAppIT {

    @TempDir
    Path dir;

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setProperty("expenses.file", dir.resolve("expenses.csv").toString());
        System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.clearProperty("expenses.file");
    }

    private String run(String... args) {
        out.reset();
        Main.main(args);
        return out.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void addedExpensesAppearInListAndTotal() {
        run("add", "2026-09-01", "350.50", "food");
        run("add", "2026-09-03", "1200", "transport");

        String list = run("list");
        assertTrue(list.contains("2026-09-01  350.50  food"));
        assertTrue(list.contains("2026-09-03  1200  transport"));
        assertEquals("Total: 1550.50", run("total"));
    }

    @Test
    void monthCommandPrintsTotalForThatMonth() {
        run("add", "2026-08-31", "500", "food");
        run("add", "2026-09-01", "350.50", "food");
        run("add", "2026-09-03", "1200", "transport");

        assertEquals("Total for 2026-09: 1550.50", run("month", "2026-09"));
    }

    @Test
    void reportCommandPrintsTotalsByCategory() {
        run("add", "2026-09-01", "100", "food");
        run("add", "2026-09-02", "40", "cafe");
        run("add", "2026-09-03", "25.50", "food");

        assertEquals("cafe: 40" + System.lineSeparator() + "food: 125.50", run("report"));
    }

    @Test
    void acceptsCommaAsDecimalSeparator() {
        assertEquals("Added: 2026-09-05,99.90,cafe", run("add", "2026-09-05", "99,90", "cafe"));
    }

    @Test
    void doesNotSaveNonPositiveAmount() {
        run("add", "2026-09-05", "-150", "food");
        run("add", "2026-09-05", "0", "food");

        assertEquals("", run("list"));
    }

    @Test
    void printsUsageForUnknownCommand() {
        assertTrue(run("remove").startsWith("Usage:"));
    }
}
