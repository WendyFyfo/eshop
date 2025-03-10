package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;

import javax.management.InstanceNotFoundException;
import java.util.List;

public interface ProductService {
    public Product create(Product product);
    public List<Product> findAll();
    public Product findProductById(String id) throws Exception;
    public Product edit(Product product);;
    public void delete(String Id);
}
