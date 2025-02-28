package id.ac.ui.cs.advprog.eshop.repository;

import  id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import javax.management.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public interface ProductRepository{
    List<Product> productData = null;

    public Product create(Product product);

    public Iterator<Product> findAll();

    public Product findProductById (String Id) throws Exception;

    public Product edit(Product product);

    public void delete(String Id);
}