package javiki.course;

import javiki.course.car.TaxiCarType;
import javiki.course.passenger.Passenger;

public class NearbyCarsRequest {
    final String id;
    final Passenger passenger;
    final TaxiCarType taxiCarType;
    final PointCoordinates requestLocation;

    public NearbyCarsRequest(String id, Passenger passenger, TaxiCarType taxiCarType, PointCoordinates requestLocation) {
        this.id = id;
        this.passenger = passenger;
        this.taxiCarType = taxiCarType;
        this.requestLocation = requestLocation;
    }

    public String getId() {
        return id;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public TaxiCarType getTaxiCarType() {
        return taxiCarType;
    }

    public PointCoordinates getRequestLocation() {
        return requestLocation;
    }
}
