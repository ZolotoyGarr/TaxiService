package javiki.course.car;

public class TaxiCarType {
    final String name;
    final int seatsCount;
    final CarQuality carQuality;

    public TaxiCarType(String name, int seatsCount, CarQuality carQuality) {
        this.name = name;
        this.seatsCount = seatsCount;
        this.carQuality = carQuality;
    }

    public String getName() {
        return name;
    }

    public int getSeatsCount() {
        return seatsCount;
    }

    public CarQuality getCarQuality() {
        return carQuality;
    }
}
