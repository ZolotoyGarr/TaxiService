package javiki.course.driver;

import javiki.course.order.OrderRide;

public interface DriverDecisionService {
    boolean decide(OrderRide order, Driver driver);
}
