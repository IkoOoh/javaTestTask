package testTaskConsoleApp;

import java.util.Random;

public class NameGenerator {

    private final String[] maleNames = {
        "John", "Michael", "David", "Chris", "James", "Daniel",
        "Matthew", "Joshua", "Andrew", "Joseph", "William", "Alexander",
        "Henry", "Thomas", "Charles"
    };

    private final String[] femaleNames = {
        "Jane", "Emily", "Sarah", "Jessica", "Laura", "Anna",
        "Olivia", "Sophia", "Emma", "Mia", "Isabella", "Ava",
        "Aria", "Layla", "Zoe"
    };

    private final String[] surnames = {
        "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis",
        "Miller", "Wilson", "Moore", "Taylor", "Anderson", "Thomas",
        "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia",
        "Martinez", "Robinson", "Clark", "Rodriguez", "Lewis", "Lee",
        "Walker", "Hall", "Allen", "Young", "King", "Wright"
    };

    private Random rand;

    public NameGenerator() {
        rand = new Random();
    }

    public String getName() {
        boolean isMale = rand.nextBoolean();
        String name;
        String gender;

        if (isMale) {
            name = maleNames[rand.nextInt(maleNames.length)];
            gender = "male";
        } else {
            name = femaleNames[rand.nextInt(femaleNames.length)];
            gender = "female";
        }

        String surname = surnames[rand.nextInt(surnames.length)];
        return name + " " + surname + ":" + gender;
    }
}


