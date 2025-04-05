package javiki.course.car;

import javiki.course.PointCoordinates;
import javiki.course.driver.Driver;

import java.util.concurrent.atomic.AtomicBoolean;

public class TaxiCar {
    private final String id;
    private final Driver driver;
    private final int number;
    private final PointCoordinates coordinates;
    private final CarQuality carQuality;
    private final AtomicBoolean isAvailable = new AtomicBoolean(true);
    private final TaxiCarType taxiCarType;

    public TaxiCar(String id, Driver driver, int number, CarQuality carQuality, PointCoordinates coordinates, TaxiCarType taxiCarType) {
        this.id = id;
        this.driver = driver;
        this.number = number;
        this.carQuality = carQuality;
        this.coordinates = coordinates;
        this.taxiCarType = taxiCarType;
    }

    // Конструктор копирования
    public TaxiCar(TaxiCar other) {
        this.id = other.id;
        this.driver = other.driver;
        this.number = other.number;
        this.coordinates = new PointCoordinates(other.coordinates.getX(), other.coordinates.getY());
        this.taxiCarType = other.taxiCarType;
        this.carQuality = other.carQuality;
    }

    public PointCoordinates getCoordinates() {
        return coordinates;
    }

    public Driver getDriver() {
        return driver;
    }

    public String getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public AtomicBoolean getIsAvailable() {
        return isAvailable;
    }

    public TaxiCarType getTaxiCarType() {
        return taxiCarType;
    }

    // Метод для обновления доступности машины
    public void setAvailable(boolean available) {
        isAvailable.set(available);  // Устанавливаем значение доступности с использованием AtomicBoolean
    }
}
