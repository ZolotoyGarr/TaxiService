package javiki.course.passenger;

import javiki.course.Profile;
import javiki.course.PointCoordinates;
import javiki.course.order.OrderRide;
import javiki.course.order.OrderPool;
import javiki.course.sevices.TaxiService;
import javiki.course.NearbyCarsRequest;
import javiki.course.NearbyCarsResult;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

public class Passenger implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(Passenger.class.getName());

    private Profile profile;
    private PassengerStatus status = PassengerStatus.WAITING_FOR_ACCEPTANCE;
    private PointCoordinates passengerLocation;
    private OrderRide currentRide;
    private static final Random RANDOM = new Random();

    // Конструктор пассажира
    public Passenger() {
        // По умолчанию создается профиль с именем, полученным из случайного UUID
        this.profile = new Profile("Пассажир " + UUID.randomUUID().toString().substring(0, 5));
        this.passengerLocation = new PointCoordinates(RANDOM.nextInt(101), RANDOM.nextInt(101)); // случайное местоположение
    }

    public void initializePassenger(OrderPool orderPool, TaxiService taxiService) {
        // Генерация случайного профиля
        this.profile = Profile.generateRandomProfile();

        // Генерация случайного местоположения пассажира
        this.passengerLocation = new PointCoordinates(RANDOM.nextInt(101), RANDOM.nextInt(101));

        // Создание нового заказа для пассажира
        OrderRide order = new OrderRide(UUID.randomUUID().toString(), LocalDateTime.now(), this);
        orderPool.addOrder(order);  // Добавляем заказ в OrderPool

        LOGGER.info("Пассажир " + profile.getName() + " создал заказ на такси.");

        // Создаем запрос на такси с помощью NearbyCarsRequest
        NearbyCarsRequest request = new NearbyCarsRequest(order.getId(), this, taxiService.getRandomCarType(), passengerLocation);

        // Получаем ближайшие машины и выводим результат
        NearbyCarsResult result = taxiService.findNearbyCars(request);
        if (result.isFound()) {
            result.getAvailableCars().forEach(car -> LOGGER.info("Машина: " + car.getId() + ", Класс качества: " + car.getTaxiCarType().getCarQuality()));
            LOGGER.info("Среднее время ожидания: " + result.getAverageWaitTime() + " минут.");
        } else {
            LOGGER.warning("Не найдено доступных машин в радиусе " + taxiService.getMAX_DISTANCE() + " км.");
        }
    }


    @Override
    public void run() {
        try {
            // 2. Пассажир ожидает, пока такси приедет
            while (status == PassengerStatus.WAITING_FOR_ACCEPTANCE) {
                Thread.sleep(1000);
            }

            while (status == PassengerStatus.IN_RIDE) {
                Thread.sleep(3000); // Симуляция поездки
            }

            // 4. Лог завершения поездки
            LOGGER.info("Пассажир " + profile.getName() + " завершил поездку.");
            status = PassengerStatus.FINISHED;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.warning("Пассажир " + profile.getName() + " был прерван.");
        }
    }

    // Геттеры и сеттеры
    public Profile getProfile() {
        return profile;
    }

    public PassengerStatus getStatus() {
        return status;
    }

    public void setStatus(PassengerStatus status) {
        this.status = status;
    }

    public PointCoordinates getPassengerLocation() {
        return passengerLocation;
    }

    public void setPassengerLocation(PointCoordinates passengerLocation) {
        this.passengerLocation = passengerLocation;
    }

    public OrderRide getCurrentOrder() {
        return currentRide;
    }

    public void setCurrentOrder(OrderRide currentRide) {
        this.currentRide = currentRide;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "name='" + profile.getName() + '\'' +
                ", location=" + passengerLocation +
                '}';
    }

}
