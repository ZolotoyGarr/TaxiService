package javiki.course;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class Profile {
    private String name;
    private String surname;
    private LocalDate yearOfBirth;
    private LocalDateTime createDateTime;
    private String photoId;
    private String ID;
    private int rating;

    private static final Random RANDOM = new Random();
    private static final String[] NAMES = {
            "Иван", "Алексей", "Мария", "Елена", "Олег", "Анна", "Дмитрий", "Евгений", "Ирина", "Сергей",  "Света", "Даша", "Оля", "Гриша", "Петя", "Саша"
    };
    private static final String[] SURNAMES = {
            "Иванов", "Петров", "Сидоров", "Кузнецов", "Попов", "Ковалев", "Лебедев", "Васильев", "Михайлов", "Новиков"
    };

    // Конструктор для явного создания профиля
    public Profile(String name, String surname, LocalDate yearOfBirth, LocalDateTime createDateTime, String photoId, String ID, int rating) {
        this.name = name;
        this.surname = surname;
        this.yearOfBirth = yearOfBirth;
        this.createDateTime = createDateTime;
        this.photoId = photoId;
        this.ID = ID;
        this.rating = rating;
    }

    // Конструктор для профиля с только именем
    public Profile(String name) {
        this.name = name;
    }

    // Метод для генерации случайного профиля
    public static Profile generateRandomProfile() {
        String name = NAMES[RANDOM.nextInt(NAMES.length)];
        String surname = SURNAMES[RANDOM.nextInt(SURNAMES.length)];
        LocalDate yearOfBirth = LocalDate.of(RANDOM.nextInt(60) + 1960, RANDOM.nextInt(12) + 1, RANDOM.nextInt(28) + 1); // Случайный год рождения (с 1960 по 2020)
        LocalDateTime createDateTime = LocalDateTime.now(); // Дата создания — текущая дата и время
        String photoId = UUID.randomUUID().toString(); // Генерация случайного ID фото
        String ID = UUID.randomUUID().toString(); // Генерация уникального ID
        int rating = RANDOM.nextInt(5) + 1; // Рейтинг от 1 до 5

        return new Profile(name, surname, yearOfBirth, createDateTime, photoId, ID, rating);
    }

    // Геттеры
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getYearOfBirth() {
        return yearOfBirth;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public String getPhotoId() {
        return photoId;
    }

    public String getId() {
        return ID;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", createDateTime=" + createDateTime +
                ", photoId='" + photoId + '\'' +
                ", ID='" + ID + '\'' +
                ", rating=" + rating +
                '}';
    }
}
