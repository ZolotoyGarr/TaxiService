package javiki.course.order;

import javiki.course.driver.Driver;
import javiki.course.passenger.Passenger;

import java.time.LocalDateTime;

public class OrderRide {
    private String id;
    private LocalDateTime orderDateTime;
    private Passenger passenger;
    private OrderStatus orderStatus;  // Новый статус для заказа
    private Driver driver;

    public OrderRide(String id, LocalDateTime orderDateTime, Passenger passenger) {
        this.id = id;
        this.orderDateTime = orderDateTime;
        this.passenger = passenger;
        this.orderStatus = OrderStatus.PENDING;  // Статус заказа по умолчанию
    }

    // Геттеры и сеттеры
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
