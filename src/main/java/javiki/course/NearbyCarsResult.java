package javiki.course;

import javiki.course.car.TaxiCar;
import java.util.List;

public class NearbyCarsResult {
    private final List<TaxiCar> availableCars;
    private final boolean isFound;
    private final int averageWaitTime;

    public NearbyCarsResult(List<TaxiCar> availableCars, boolean isFound, int averageWaitTime) {
        this.availableCars = availableCars;
        this.isFound = isFound;
        this.averageWaitTime = averageWaitTime;
    }

    public List<TaxiCar> getAvailableCars() {
        return availableCars;
    }

    public boolean isFound() {
        return isFound;
    }

    public int getAverageWaitTime() {
        return averageWaitTime;
    }
}
