package javiki.course.passenger;

import java.util.LinkedList;
import java.util.Queue;

public class PassengerQueue {
    private final Queue<Passenger> queue = new LinkedList<>();

    public synchronized void addPassengerToQueue(Passenger passenger) {
        queue.add(passenger);  // Добавляем пассажира в очередь
    }

    public synchronized Passenger getNextPassenger() {
        return queue.poll();  // Извлекаем следующего пассажира из очереди
    }

    public synchronized boolean hasPassengers() {
        return !queue.isEmpty();  // Проверка, есть ли пассажиры в очереди
    }
}

