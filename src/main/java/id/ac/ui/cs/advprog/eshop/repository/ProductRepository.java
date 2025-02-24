package id.ac.ui.cs.advprog.eshop.repository;

import  id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import javax.management.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository{
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findProductById (String Id) throws InstanceNotFoundException {
        for(Product existingProduct : productData) {
            if(existingProduct.getProductId().equals(Id)) {
                return existingProduct;
            }
        }
        throw new InstanceNotFoundException();
    }

    public Product edit(Product product) {
        Product existingProduct = null;
        try {
            existingProduct = findProductById(product.getProductId());
        } catch (InstanceNotFoundException e) {
            return null;
        }
        existingProduct.setProductName(product.getProductName());
        existingProduct.setProductQuantity(product.getProductQuantity());
        return existingProduct;
    }

    public void delete(String Id) {
        productData.removeIf(product -> product.getProductId().equals(Id));
        return;
    }
}