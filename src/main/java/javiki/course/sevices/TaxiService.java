package javiki.course.sevices;

import javiki.course.*;
import javiki.course.car.CarQuality;
import javiki.course.car.TaxiCar;
import javiki.course.car.TaxiCarType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class TaxiService {
    private static final Logger logger = Logger.getLogger(TaxiService.class.getName());

    private final int MAX_DISTANCE;
    private final TaxiCarPool taxiCarPool;

    // Список доступных типов машин
    private final List<TaxiCarType> carTypes = List.of(
            new TaxiCarType("SUV", 4, CarQuality.PREMIUM),
            new TaxiCarType("Sedan", 4, CarQuality.STANDARD),
            new TaxiCarType("Hatchback", 4, CarQuality.ECONOMY),
            new TaxiCarType("Coupe", 2, CarQuality.PREMIUM),
            new TaxiCarType("Convertible", 2, CarQuality.STANDARD)
    );

    public TaxiService(int MAX_DISTANCE, TaxiCarPool taxiCarPool) {
        this.MAX_DISTANCE = MAX_DISTANCE;
        this.taxiCarPool = taxiCarPool;
    }

    // Метод для нахождения ближайших машин
    public NearbyCarsResult findNearbyCars(NearbyCarsRequest request) {
        double minDistance = Double.MAX_VALUE;
        List<TaxiCar> availableCars = taxiCarPool.getAvailableCars();
        List<TaxiCar> nearbyAvailableCars = new ArrayList<>();

        for (TaxiCar car : availableCars) {
            double distance = DistanceCalculator.calculateDistance(car.getCoordinates(), request.getRequestLocation());
            if (distance <= MAX_DISTANCE) {
                nearbyAvailableCars.add(car);
                minDistance = Math.min(minDistance, distance);
            }
        }

        boolean isFound = !nearbyAvailableCars.isEmpty();
        // Подсчет среднего времени ожидания
        int averageWaitTime = isFound ? (int) Math.ceil(minDistance / 0.5) : 0; // 0.5 км/мин (30 км/ч)

        if (isFound) {
            logger.info("Найдено " + nearbyAvailableCars.size() + " машин в радиусе " + MAX_DISTANCE + " км.");
        } else {
            logger.warning("Нет доступных машин в радиусе " + MAX_DISTANCE + " км.");
        }

        return new NearbyCarsResult(nearbyAvailableCars, isFound, averageWaitTime);
    }

    // Метод для получения случайного типа машины
    public TaxiCarType getRandomCarType() {
        Random random = new Random();
        return carTypes.get(random.nextInt(carTypes.size())); // Выбираем случайный тип автомобиля из списка
    }

    // Геттеры для MAX_DISTANCE и taxiCarPool
    public int getMAX_DISTANCE() {
        return MAX_DISTANCE;
    }

    public TaxiCarPool getTaxiCarPool() {
        return taxiCarPool;
    }
}
