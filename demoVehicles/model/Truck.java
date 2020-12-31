package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity(name = "trucks")
@Table(name = "trucks")
public class Truck extends Vehicle{
    @Column(name = "load_capacity")
    private Double loadCapacity;

    public Truck(){}


    public Truck(String type, String model, BigDecimal price, String fuelType, Double loadCapacity) {
        super(type, model, price, fuelType);
        this.loadCapacity = loadCapacity;
    }

    public Truck(Long id, String type, String model, BigDecimal price, String fuelType, Double loadCapacity) {
        super(id, type, model, price, fuelType);
        this.loadCapacity = loadCapacity;
    }
    public Double getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(Double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    @Override
    public String toString() {
        return "Truck{" + super.toString()+
                "loadCapacity=" + loadCapacity +
                '}';
    }
}
