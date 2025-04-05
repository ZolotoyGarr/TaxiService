package javiki.course.driver;

import java.util.*;
import java.util.stream.Collectors;

public class DriverPool {
    private final List<Driver> driverPool = new ArrayList<>();

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
