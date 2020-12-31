package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity(name = "planes")
@Table(name = "planes")
public class Plane extends Vehicle{
    @Column(name = "passenger_capacity")
    private int passengerCapacity;

    public Plane(){}

    public Plane(Long id, String type, String model, BigDecimal price, String fuelType, int passengerCapacity) {
        super(id, type, model, price, fuelType);
        this.passengerCapacity = passengerCapacity;
    }

    public Plane(String type, String model, BigDecimal price, String fuelType, int passengerCapacity) {
        super(type, model, price, fuelType);
        this.passengerCapacity = passengerCapacity;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    @Override
    public String toString() {
        return "Plane{" + super.toString()+
                "passengerCapacity=" + passengerCapacity +
                '}';
    }
}
