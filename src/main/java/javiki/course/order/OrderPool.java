package javiki.course.order;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class OrderPool {
    private final BlockingQueue<OrderRide> orderPool = new LinkedBlockingQueue<>();

    public List<OrderRide> getAvailableOrders() {
        return orderPool.stream()
                .filter(order -> order.getOrderStatus() == OrderStatus.PENDING)
                .collect(Collectors.toList());
    }
    // Добавление заказа
    public void addOrder(OrderRide order) {
        orderPool.offer(order);  // offer не блокирует
    }

    // Получение следующего заказа
    public OrderRide getNextOrder() throws InterruptedException {
        return orderPool.take();  // Блокируется, если заказов нет
    }

    // Получение всех заказов (если нужно)
    public BlockingQueue<OrderRide> getAllOrders() {
        return orderPool;
    }

    // Удаление заказа, если вдруг понадобится
    public void removeOrder(OrderRide order) {
        orderPool.remove(order);
    }
}
