package javiki.course;

import javiki.course.driver.DriverPool;
import javiki.course.order.OrderPool;
import javiki.course.passenger.PassengerPool;
import javiki.course.sevices.SimulationService;
import javiki.course.sevices.TaxiService;

public class Main {
    public static void main(String[] args) {
        TaxiCarPool taxiCarPool = new TaxiCarPool();
        OrderPool orderPool = new OrderPool();
        PassengerPool passengerPool = new PassengerPool();
        DriverPool driverPool = new DriverPool();
        TaxiService taxiService = new TaxiService(100, taxiCarPool);
        SimulationService simulationService = new SimulationService(taxiCarPool, orderPool, passengerPool, driverPool, taxiService);
        simulationService.runSimulation(5, 1);
    }
}
