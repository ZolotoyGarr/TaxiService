package javiki.course.order;

public enum OrderStatus {
    PENDING,      // Заказ ожидает подтверждения
    ACCEPTED,     // Заказ принят водителем
    IN_PROGRESS,  // Поездка в процессе
    COMPLETED     // Поездка завершена
}
