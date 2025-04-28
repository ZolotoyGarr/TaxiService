package javiki.course.car;

import javiki.course.NearbyCarsRequest;
import javiki.course.PointCoordinates;
import javiki.course.driver.DriverPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TaxiCarPool {
    private final List<TaxiCar> carsPool = new ArrayList<>();
    private Map<TaxiCarType, List<TaxiCar>> allCarsByType;
    private Map<String, TaxiCar> carById;
    private static final Logger LOGGER = Logger.getLogger(TaxiCarPool.class.getName());
    private static final Random RANDOM = new Random();

    private final List<TaxiCarType> carTypes = List.of(
            new TaxiCarType("SUV", 4, CarQuality.PREMIUM),
            new TaxiCarType("Sedan", 4, CarQuality.STANDARD),
            new TaxiCarType("Hatchback", 4, CarQuality.ECONOMY),
            new TaxiCarType("Coupe", 2, CarQuality.PREMIUM),
            new TaxiCarType("Convertible", 2, CarQuality.STANDARD)
    );

    // Метод для создания случайной машины
    public TaxiCar createRandomCar() {
        TaxiCarType carType = carTypes.get(RANDOM.nextInt(carTypes.size()));
        String carId = "Car-" + RANDOM.nextInt(1000);
        PointCoordinates startCoordinates = new PointCoordinates(RANDOM.nextInt(101), RANDOM.nextInt(101));
        TaxiCar taxiCar = new TaxiCar(carId, null, RANDOM.nextInt(1000), CarQuality.PREMIUM, startCoordinates, carType);
        addCar(taxiCar);
        return taxiCar;
    }

    public List<TaxiCar> findByType(NearbyCarsRequest request) {
        TaxiCarType requiredType = request.getTaxiCarType();
        return getAvailableCars().stream()
                .filter(taxiCar -> taxiCar.getTaxiCarType().equals(requiredType))
                .collect(Collectors.toList());
    }

    //Тогда .map(TaxiCar::new) будет эквивалентно:
    //.map(car -> new TaxiCar(car))
    //То есть для каждого элемента потока (car) будет вызван конструктор копирования.
    public List<TaxiCar> getAvailableCars() {
        return carsPool.stream()
                .filter(car -> car.getIsAvailable().get())
                .toList();
    }


    public TaxiCar findCarById(String carId) {
        return carsPool.stream()
                .filter(car -> car.getId().equals(carId))
                .findFirst()
                .orElse(null);
    }

    public boolean updateCarAvailability(String carId, boolean isAvailable) {
        TaxiCar car = findCarById(carId);
        return car != null && car.getIsAvailable().compareAndSet(!isAvailable, isAvailable);
    }

    public TaxiCarType getRandomCarType() {
        return carTypes.get(RANDOM.nextInt(carTypes.size()));
    }

    public void addCar(TaxiCar taxiCar) {
        carsPool.add(taxiCar);
    }

    public void removeCar(TaxiCar taxiCar) {
        carsPool.remove(taxiCar);
    }

}