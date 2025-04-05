package javiki.course;

import javiki.course.car.TaxiCar;
import javiki.course.car.TaxiCarType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaxiCarPool {
    private final List<TaxiCar> carsPool = new ArrayList<>();
    private Map<TaxiCarType, List<TaxiCar>> allCarsByType;
    private Map<String, TaxiCar> carById;

    public List<TaxiCar> findByType(NearbyCarsRequest request) {
        TaxiCarType requiredType = request.taxiCarType;
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


    public void addCar(TaxiCar taxiCar) {
        carsPool.add(taxiCar);
    }

    public void removeCar(TaxiCar taxiCar) {
        carsPool.remove(taxiCar);
    }

}