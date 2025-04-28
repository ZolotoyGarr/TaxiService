package javiki.course.driver;

import javiki.course.order.OrderRide;
import javiki.course.Profile;
import javiki.course.order.OrderStatus;
import javiki.course.passenger.Passenger;
import javiki.course.car.TaxiCar;
import javiki.course.passenger.PassengerStatus;

import java.util.logging.Logger;

public class Driver implements Runnable {
    private Profile profile;
    private TaxiCar taxiCar;
    private int orderCounter = 0;
    private OrderRide currentOrder;

    private static final Logger LOGGER = Logger.getLogger(Driver.class.getName());

    public Driver(Profile profile) {
        this.profile = profile;
    }

    @Override
    public void run() {
        try {
            while (orderCounter < 3) {  // ограничение на 5 заказов
                if (currentOrder != null) {
                    String passengersName = currentOrder.getPassenger().getProfile().getName();
                    LOGGER.info("Водитель " + profile.getName() + " выполняет заказ от пассажира " + passengersName);
                    currentOrder.setOrderStatus(OrderStatus.ACCEPTED);  // Устанавливаем статус заказа на "ACCEPTED"

                    // Симуляция поездки к пассажиру
                    Thread.sleep(5000);

                    currentOrder.getPassenger().setStatus(PassengerStatus.IN_RIDE);  // Пассажир садится в такси
                    LOGGER.info("Пассажир " + passengersName + " сел в такси.");

                    // Симуляция самой поездки (10 секунд)
                    Thread.sleep(10000);

                    // Завершение поездки
                    currentOrder.getPassenger().setStatus(PassengerStatus.FINISHED);
                    currentOrder.setOrderStatus(OrderStatus.COMPLETED);
                    LOGGER.info("Водитель " + profile.getName() + " завершил поездку с пассажиром " + passengersName);
                    orderCounter++;
                    LOGGER.info("Количество выполненных заказов у " + getProfile().getName() + ": " + getOrderCounter());
                    currentOrder = null;  // Обнуляем текущий заказ, чтобы водитель стал доступным для нового
                } else {
                    LOGGER.info("Водитель " + profile.getName() + " ожидает новый заказ...");
                    Thread.sleep(5000);  // Ожидание, если нет текущего заказа
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.warning("Водитель " + profile.getName() + " был прерван.");
        }
    }

    public synchronized void assignOrder(OrderRide order) {
        this.currentOrder = order;
    }


    public void setTaxiCar(TaxiCar taxiCar) {
        this.taxiCar = taxiCar;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public TaxiCar getTaxiCar() {
        return taxiCar;
    }

    public OrderRide getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(OrderRide currentOrder) {
        this.currentOrder = currentOrder;
    }

    public int getOrderCounter() {
        return orderCounter;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "name='" + profile.getName() + '\'' +
                ", car=" + (taxiCar != null ? taxiCar.getId() : "Нет машины") +
                '}';
    }
}
