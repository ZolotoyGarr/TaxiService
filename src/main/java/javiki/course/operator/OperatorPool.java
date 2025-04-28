package javiki.course.operator;

import javiki.course.DistanceCalculator;
import javiki.course.driver.Driver;
import javiki.course.driver.DriverPool;
import javiki.course.order.OrderPool;
import javiki.course.order.OrderRide;
import javiki.course.order.OrderStatus;
import javiki.course.passenger.Passenger;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

public class OperatorPool {
    private final Semaphore operators = new Semaphore(5);  // 5 операторов
    private final OrderPool orderPool;
    private final DriverPool driverPool;
    private volatile boolean running = true;


    private static final Logger LOGGER = Logger.getLogger(OperatorPool.class.getName());

    public OperatorPool(OrderPool orderPool, DriverPool driverPool) {
        this.orderPool = orderPool;
        this.driverPool = driverPool;
    }

    public boolean acquireOperator() {
        return operators.tryAcquire();
    }

    public void releaseOperator() {
        operators.release();
    }

    public void processOrders() {
        LOGGER.info("Оператор начал обработку заказов");
        while (!Thread.currentThread().isInterrupted() && running) {
            if (acquireOperator()) {
                try {
                    List<OrderRide> pendingOrders = orderPool.getAvailableOrders();

                    for (OrderRide order : pendingOrders) {
                        if (order.getDriver() == null && order.getOrderStatus() == OrderStatus.PENDING) {
                            Passenger passenger = order.getPassenger();
                            Driver driver = findNearestDriver(passenger);

                            if (driver != null) {
                                order.setDriver(driver);
                                order.setOrderStatus(OrderStatus.ACCEPTED);
                                passenger.setCurrentOrder(order);
                                driver.setCurrentOrder(order);
                                order.setWaitingForDriver(false);
                                LOGGER.info("Оператор назначил водителя " + driver.getProfile().getName() +
                                        " пассажиру " + passenger.getProfile().getName());
                            } else {
                                if (!order.isWaitingForDriver()) {
                                    LOGGER.info("Нет доступных водителей для пассажира " + passenger.getProfile().getName());
                                    order.setWaitingForDriver(true);
                                }
                            }
                        }
                    }

                    // Пауза после полной обработки всех заказов
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } finally {
                    releaseOperator();
                }
            }
        }
    }

    // Поиск ближайшего свободного водителя
    private Driver findNearestDriver(Passenger passenger) {
        double minDistance = Double.MAX_VALUE;
        Driver nearestDriver = null;

        for (Driver driver : driverPool.getAvailableDrivers()) {
            if (driver.getCurrentOrder() != null) {
                continue;
            }

            double distance = DistanceCalculator.calculateDistance(
                    passenger.getPassengerLocation(),
                    driver.getTaxiCar().getCoordinates()
            );

            if (distance < minDistance) {
                minDistance = distance;
                nearestDriver = driver;
            }
        }
        return nearestDriver;
    }

    public void shutdown() {
        running = false;
    }
}
