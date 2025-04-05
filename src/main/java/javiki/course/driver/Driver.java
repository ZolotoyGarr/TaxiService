package javiki.course.driver;

import javiki.course.order.OrderRide;
import javiki.course.Profile;
import javiki.course.order.OrderStatus;
import javiki.course.passenger.Passenger;
import javiki.course.car.TaxiCar;
import javiki.course.order.OrderPool;
import javiki.course.passenger.PassengerStatus;

import java.util.List;
import java.util.logging.Logger;

public class Driver implements Runnable {
    private Profile profile;
    private final OrderPool orderPool;
    private TaxiCar taxiCar;
    private int orderCounter = 0;

    private static final Logger LOGGER = Logger.getLogger(Driver.class.getName());

    public Driver(Profile profile, OrderPool orderPool) {
        this.profile = profile;
        this.orderPool = orderPool;
    }

    @Override
    public void run() {
        try {
            while (orderCounter < 5) {
                // 1. Водитель ищет доступные заказы
                LOGGER.info("Водитель " + profile.getName() + " ищет доступные заказы...");
                List<OrderRide> availableOrders = orderPool.getAvailableOrders();

                if (availableOrders.isEmpty()) {
                    LOGGER.info("Нет доступных заказов. Водитель " + profile.getName() + " ожидает.");
                    Thread.sleep(5000);  // Ждем 5 секунд перед следующей попыткой
                    continue;
                }

                // 2. Водитель выбирает один заказ
                OrderRide selectedOrder = availableOrders.get(0);
                Passenger orderPassenger = selectedOrder.getPassenger();
                LOGGER.info("Водитель " + profile.getName() + " выбрал заказ от пассажира " + orderPassenger.getProfile().getName());

                // 3. Обновляем статус заказа на "ACCEPTED"
                selectedOrder.setOrderStatus(OrderStatus.ACCEPTED);
                selectedOrder.setDriver(this);
                orderPassenger.setStatus(PassengerStatus.WAITING_FOR_PICKUP);
                orderPool.updateOrderStatus(selectedOrder.getId(), OrderStatus.ACCEPTED);

                // 4. Водитель едет к пассажиру (симуляция поездки)
                LOGGER.info("Водитель " + profile.getName() + " едет к пассажиру " + orderPassenger.getProfile().getName());
                Thread.sleep(5000);  // Симуляция поездки

                // 5. Пассажир садится в такси
                orderPassenger.setStatus(PassengerStatus.IN_RIDE);
                LOGGER.info("Пассажир " + orderPassenger.getProfile().getName() + " сел в такси.");

                // 6. Выполнение поездки
                Thread.sleep(10000);  // Симуляция поездки (10 секунд)

                // 7. Завершение поездки
                LOGGER.info("Водитель " + profile.getName() + " завершил поездку с пассажиром " + orderPassenger.getProfile().getName());
                orderPassenger.setStatus(PassengerStatus.FINISHED);
                orderPool.updateOrderStatus(selectedOrder.getId(), OrderStatus.COMPLETED);
                orderCounter++;
            }
            LOGGER.info("Driver "+ this.getProfile().getName() + " has completed 5 orders");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.warning("Водитель " + profile.getName() + " был прерван.");
        }
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public OrderPool getOrderPool() {
        return orderPool;
    }

    public TaxiCar getTaxiCar() {
        return taxiCar;
    }

    public void setTaxiCar(TaxiCar taxiCar) {
        this.taxiCar = taxiCar;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "name='" + profile.getName() + '\'' +
                ", car=" + (taxiCar != null ? taxiCar.getId() : "No car") +
                '}';
    }

}
