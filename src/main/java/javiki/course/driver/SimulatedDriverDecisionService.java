package javiki.course.driver;

import javiki.course.order.OrderRide;
import javiki.course.passenger.Passenger;

import java.util.Random;
import java.util.logging.Logger;

public class SimulatedDriverDecisionService implements DriverDecisionService {
    @Override
    public boolean decide(OrderRide order, Driver driver) {
        return false;
    }
//    private static final Logger LOGGER = Logger.getLogger(Passenger.class.getName());
//    @Override
//    public boolean decide(OrderRide order, Driver driver) {
//        // Эмуляция решения — например, 70% принимают, 30% отказываются
//        boolean accepted = new Random().nextDouble() < 0.7;
//        if (accepted) {
//            driver.acceptOrderRequest(order);
//        } else {
//            driver.declineOrderRequest();
//        }
//        return accepted;
//    }
}
