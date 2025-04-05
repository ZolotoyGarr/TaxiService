package javiki.course.order;

import java.util.*;
import java.util.stream.Collectors;

public class OrderPool {
    private final List<OrderRide> orderPool = new ArrayList<>();  // Пул заказов

    // Метод для добавления заказа в пул
    public void addOrder(OrderRide order) {
        orderPool.add(order);
    }

    // Метод для получения доступных заказов (т.е. тех, которые ожидают подтверждения)
    public List<OrderRide> getAvailableOrders() {
        return orderPool.stream()
                .filter(order -> order.getOrderStatus() == OrderStatus.PENDING) // Проверяем, что заказ в статусе "ожидает подтверждения"
                .collect(Collectors.toList());
    }

    // Метод для обновления статуса заказа
    public boolean updateOrderStatus(String orderId, OrderStatus newStatus) {
        OrderRide order = orderPool.stream()
                .filter(o -> o.getId().equals(orderId))
                .findFirst()
                .orElse(null);
        if (order != null) {
            order.setOrderStatus(newStatus);  // Обновляем статус самого заказа
            return true;
        }
        return false;
    }

    // Метод для получения всех заказов
    public List<OrderRide> getAllOrders() {
        return new ArrayList<>(orderPool);
    }

    // Метод для удаления заказа из пула
    public void removeOrder(OrderRide order) {
        orderPool.remove(order);
    }
}
