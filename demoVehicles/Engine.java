import model.Car;
import model.Plane;
import model.Truck;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class Engine implements Runnable{
    private final BufferedReader bufferedReader;
    private final EntityManager entityManager;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        bufferedReader=new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        Car car1=new Car("car","BMW 330XD",new BigDecimal(55000),"Diesel",5);
        Truck truck1=new Truck("truck","Pejo",new BigDecimal(30000),"Benzin",5.4);
        Plane plane1=new Plane("Airbus","747",new BigDecimal(230000),"Diesel",440);

        entityManager.getTransaction().begin();
            entityManager.persist(car1);
            entityManager.persist(truck1);
            entityManager.persist(plane1);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
            Car foundC=entityManager.find(Car.class,1L);
            System.out.printf("Found car1: %s%n",foundC);
            Truck foundT=entityManager.find(Truck.class,2L);
            System.out.printf("Found truck1: %s%n",foundT);
            Plane foundP=entityManager.find(Plane.class,3L);
            System.out.printf("Found plane1: %s%n",foundP);
        entityManager.getTransaction().commit();
    }
}
