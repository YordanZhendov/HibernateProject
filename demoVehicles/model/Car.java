package model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity(name = "cars")
@Table(name = "cars")
public class Car extends Vehicle{
    private int seats;

    public Car(){}

    public Car(Long id, String type, String model, BigDecimal price, String fuelType, int seats) {
        super(id, type, model, price, fuelType);
        this.seats = seats;
    }

    public Car(String type, String model, BigDecimal price, String fuelType, int seats) {
        super(type, model, price, fuelType);
        this.seats = seats;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Car{" + super.toString()+
                "seats=" + seats +
                '}';
    }
}
