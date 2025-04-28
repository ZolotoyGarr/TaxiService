package javiki.course.sevices;

import javiki.course.driver.Driver;
import javiki.course.operator.OperatorPool;
import javiki.course.order.OrderPool;
import javiki.course.passenger.Passenger;
import javiki.course.passenger.PassengerPool;
import javiki.course.driver.DriverPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationService {
    private final OrderPool orderPool;
    private final PassengerPool passengerPool;
    private final OperatorPool operatorPool;
    private final DriverPool driverPool;
    private final TaxiService taxiService;

    public SimulationService(OrderPool orderPool, PassengerPool passengerPool, DriverPool driverPool, OperatorPool operatorPool, TaxiService taxiService) {
        this.orderPool = orderPool;
        this.passengerPool = passengerPool;
        this.operatorPool = operatorPool;
        this.driverPool = driverPool;
        this.taxiService = taxiService;
    }

    public void runSimulation(int numberOfPassengers, int numberOfDrivers) {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfPassengers + numberOfDrivers);

        for (int i = 0; i < numberOfDrivers; i++) {
            Driver driver = driverPool.createRandomDriver();
            executorService.submit(driver);
        }

        System.out.println("Список водителей:");
        driverPool.getAllDrivers().forEach(driver -> System.out.println(driver.getProfile().getName()));

        for (int i = 0; i < numberOfPassengers; i++) {
            Passenger passenger = passengerPool.createPassenger(orderPool, taxiService);
            executorService.submit(passenger);
        }

        System.out.println("Список пассажиров:");
        passengerPool.getAllPassengers().forEach(passenger -> System.out.println(passenger.getProfile().getName()));

        ExecutorService operatorService = Executors.newSingleThreadExecutor();
        operatorService.submit(operatorPool::processOrders);

        executorService.shutdown();

        // Ждем реального завершения всех водителей и пассажиров
        boolean allFinished = false;
        while (!allFinished) {
            allFinished = passengerPool.getAllPassengers().stream()
                    .allMatch(p -> p.getStatus().equals(javiki.course.passenger.PassengerStatus.FINISHED))
                    &&
                    driverPool.getAllDrivers().stream()
                            .allMatch(d -> d.getOrderCounter() >= 3); // или сколько заказов ты задал

            try {
                Thread.sleep(1000); // Проверяем раз в секунду
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Все пассажиры завершили поездки и водители выполнили свои заказы.");

        // Теперь останавливаем операторов
        operatorPool.shutdown();
        operatorService.shutdown();
        try {
            if (!operatorService.awaitTermination(10, TimeUnit.SECONDS)) {
                operatorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            operatorService.shutdownNow();
        }

        System.out.println("Симуляция завершена!");
    }
}