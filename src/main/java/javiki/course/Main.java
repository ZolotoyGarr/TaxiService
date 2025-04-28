package javiki.course;

import javiki.course.car.TaxiCarPool;
import javiki.course.driver.DriverPool;
import javiki.course.operator.OperatorPool;
import javiki.course.order.OrderPool;
import javiki.course.passenger.PassengerPool;
import javiki.course.sevices.SimulationService;
import javiki.course.sevices.TaxiService;

public class Main {
    public static void main(String[] args) {
        OrderPool orderPool = new OrderPool();
        TaxiCarPool taxiCarPool = new TaxiCarPool();
        PassengerPool passengerPool = new PassengerPool();
        DriverPool driverPool = new DriverPool(taxiCarPool);
        TaxiService taxiService = new TaxiService(100, taxiCarPool);
        OperatorPool operatorPool = new OperatorPool(orderPool, driverPool);
        SimulationService simulationService = new SimulationService(orderPool, passengerPool, driverPool, operatorPool, taxiService);
        simulationService.runSimulation(6, 2);
    }
}
