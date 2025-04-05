package javiki.course.sevices;

import javiki.course.*;
import javiki.course.car.CarQuality;
import javiki.course.car.TaxiCar;
import javiki.course.car.TaxiCarType;
import javiki.course.driver.Driver;
import javiki.course.order.OrderPool;
import javiki.course.passenger.PassengerPool;
import javiki.course.driver.DriverPool;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class SimulationService {
    private static final Random RANDOM = new Random();
    private static final Logger LOGGER = Logger.getLogger(SimulationService.class.getName());

    private final List<TaxiCarType> carTypes = List.of(
            new TaxiCarType("SUV", 4, CarQuality.PREMIUM),
            new TaxiCarType("Sedan", 4, CarQuality.STANDARD),
            new TaxiCarType("Hatchback", 4, CarQuality.ECONOMY),
            new TaxiCarType("Coupe", 2, CarQuality.PREMIUM),
            new TaxiCarType("Convertible", 2, CarQuality.STANDARD)
    );

    private final TaxiCarPool taxiCarPool;
    private final OrderPool orderPool;
    private final PassengerPool passengerPool;
    private final DriverPool driverPool;
    private final TaxiService taxiService;

    public SimulationService(TaxiCarPool taxiCarPool, OrderPool orderPool, PassengerPool passengerPool, DriverPool driverPool, TaxiService taxiService) {
        this.taxiCarPool = taxiCarPool;
        this.orderPool = orderPool;
        this.passengerPool = passengerPool;
        this.driverPool = driverPool;
        this.taxiService = taxiService;
    }

    public TaxiCarType getRandomCarType() {
        return carTypes.get(RANDOM.nextInt(carTypes.size()));
    }

    // Метод для создания случайного водителя с рандомным профилем
    private Driver createRandomDriver() {
        Profile randomProfile = Profile.generateRandomProfile();  // Генерация случайного профиля для водителя
        TaxiCar taxiCar = createRandomCar();
        LOGGER.info("Водитель " + randomProfile.getName() + " создан с машиной: " + taxiCar.getId() + " Координаты: " + taxiCar.getCoordinates());
        Driver driver = new Driver(randomProfile, orderPool);
        driverPool.addDriver(driver);  // Добавляем водителя в пул
        return driver;
    }

    // Метод для создания случайной машины
    private TaxiCar createRandomCar() {
        TaxiCarType carType = carTypes.get(RANDOM.nextInt(carTypes.size()));
        String carId = "Car-" + RANDOM.nextInt(1000);
        PointCoordinates startCoordinates = new PointCoordinates(RANDOM.nextInt(101), RANDOM.nextInt(101));
        TaxiCar taxiCar = new TaxiCar(carId, null, RANDOM.nextInt(1000), CarQuality.PREMIUM, startCoordinates, carType);
        taxiCarPool.addCar(taxiCar);
        return taxiCar;
    }

    public void runSimulation(int numberOfPassengers, int numberOfDrivers) {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfPassengers + numberOfDrivers);

        // Сначала создаем водителей и их машины
        for (int i = 0; i < numberOfDrivers; i++) {
            createRandomDriver();
        }

        // Выводим список водителей
        System.out.println("Список водителей:");
        driverPool.getAllDrivers().forEach(driver -> System.out.println(driver.getProfile().getName()));

        // Запуск водителей
        for (int i = 0; i < numberOfDrivers; i++) {
            Driver driver = driverPool.getAllDrivers().get(i);  // Получаем водителей из пула
            final int finalI = i;  // Создаём финальную переменную
            executorService.submit(() -> {
                driver.run();  // Запускаем каждого водителя в потоке
            });
        }

        // Создание и запуск пассажиров
        for (int i = 0; i < numberOfPassengers; i++) {
            passengerPool.createPassenger(orderPool, taxiService);  // Создание пассажира через пул
            final int finalI = i;  // Создаём финальную переменную
            executorService.submit(() -> {
                passengerPool.getAllPassengers().get(finalI).run();  // Запускаем каждого пассажира в потоке
            });
        }

        // Выводим список пассажиров
        System.out.println("Список пассажиров:");
        passengerPool.getAllPassengers().forEach(passenger -> System.out.println(passenger.getProfile().getName()));

        executorService.shutdown();
    }


}
