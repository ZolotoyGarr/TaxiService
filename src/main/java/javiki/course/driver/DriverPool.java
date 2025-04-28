package javiki.course.driver;

import javiki.course.Profile;
import javiki.course.car.CarQuality;
import javiki.course.car.TaxiCar;
import javiki.course.car.TaxiCarPool;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DriverPool {
    private final List<Driver> driverPool = new ArrayList<>();
    private final TaxiCarPool taxiCarPool;

    private static final Logger LOGGER = Logger.getLogger(DriverPool.class.getName());

    public DriverPool(TaxiCarPool taxiCarPool) {
        this.taxiCarPool = taxiCarPool;
    }

    // Метод для создания случайного водителя с рандомным профилем
    public Driver createRandomDriver() {
        Profile randomProfile = Profile.generateRandomProfile();  // Генерация случайного профиля для водителя
        TaxiCar taxiCar = taxiCarPool.createRandomCar();  // Создание машины
        LOGGER.info("Водитель " + randomProfile.getName() + " создан с машиной: " + taxiCar.getId() + " Координаты: " + taxiCar.getCoordinates());
        Driver driver = new Driver(randomProfile);
        driver.setTaxiCar(taxiCar);  // Устанавливаем машину водителю
        addDriver(driver);  // Добавляем водителя в пул
        return driver;
    }

    // Метод для добавления водителя в пул
    public void addDriver(Driver driver) {
        driverPool.add(driver);
    }

    // Метод для удаления водителя из пула
    public void removeDriver(Driver driver) {
        driverPool.remove(driver);
    }

    // Метод для получения доступных водителей (которые не в поездке)
    public List<Driver> getAvailableDrivers() {
        return driverPool.stream()
                .filter(driver -> driver.getTaxiCar().getIsAvailable().get())  // Проверяем, что машина водителя доступна
                .collect(Collectors.toList());
    }

    // Метод для получения всех водителей
    public List<Driver> getAllDrivers() {
        return new ArrayList<>(driverPool);
    }

    @Override
    public String toString() {
        return "DriverPool{" +
                "driverPool=" + driverPool +
                '}';
    }
}
