package id.ac.ui.cs.advprog.eshop.repository;
import id.ac.ui.cs.advprog.eshop.model.Car;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public interface CarRepository {
    static int id = 0;
    List<Car> carData = null;
    public Car create(Car car);

    public Iterator<Car> findAll();

    public Car findById(String id);

    public Car update(String id, Car updatedCar);

    public void delete(String id);
}