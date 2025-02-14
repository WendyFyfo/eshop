package id.ac.ui.cs.advprog.eshop.repository;

import  id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

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

    public Product findProductById (String Id) {
        for(Product existingProduct : productData) {
            if(existingProduct.getProductId().equals(Id)) {
                return existingProduct;
            }
        }
        return null;
    }

    public Product edit(Product product) {
        Product existingProduct = findProductById(product.getProductId());
        existingProduct.setProductName(product.getProductName());
        existingProduct.setProductQuantity(product.getProductQuantity());
        return existingProduct;
    }

    public void delete(String Id) {
        productData.removeIf(product -> product.getProductId().equals(Id));
        return;
    }
}