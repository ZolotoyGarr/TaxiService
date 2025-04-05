package javiki.course.passenger;

import javiki.course.order.OrderPool;
import javiki.course.order.OrderRide;
import javiki.course.sevices.TaxiService;

import java.util.*;

public class PassengerPool {
    private final List<Passenger> passengersPool = new ArrayList<>();

    // Метод для добавления пассажира в пул
    public void addPassenger(Passenger passenger) {
        passengersPool.add(passenger);
    }

    // Метод для удаления пассажира из пула
    public void removePassenger(Passenger passenger) {
        passengersPool.remove(passenger);
    }

    // Метод для создания нового пассажира и добавления его в пул
    public Passenger createPassenger(OrderPool orderPool, TaxiService taxiService) {
        Passenger passenger = new Passenger();
        passenger.initializePassenger(orderPool, taxiService);  // Инициализация пассажира
        this.addPassenger(passenger);  // Добавление пассажира в пул
        return passenger;
    }

    public List<Passenger> getAllPassengers() {
        return passengersPool;
    }

    @Override
    public String toString() {
        return "PassengerPool{" +
                "passengersPool=" + passengersPool +
                '}';
    }
}
