package testTaskConsoleApp;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class DateGenerator {

    private Random rand;

    public DateGenerator() {
        rand = new Random();
    }

    public LocalDate getRandomDate(LocalDate start, LocalDate end) {
        long daysBetween = ChronoUnit.DAYS.between(start, end);
        long randomDays = rand.nextLong(daysBetween + 1);
        return start.plusDays(randomDays);
    }
}
