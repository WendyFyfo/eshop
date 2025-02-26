package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductServiceImpl service;

    @Mock
    private ProductRepository repository;

    private Product product1;
    private Product product2;
    @BeforeEach
    void setUp() {
        product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);

        product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
    }

    @Test
    void testCreateAndFind() {
        when(repository.create(product1)).thenReturn(product1);
        service.create(product1);

        when(repository.findAll()).thenReturn(List.of(product1).iterator());
        Iterator<Product> productIterator = service.findAll().iterator();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        assertEquals(product1.getProductName(), savedProduct.getProductName());
        assertEquals(product1.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testCreateAndFindMultipleProduct() {
        when(repository.create(product1)).thenReturn(product1);
        service.create(product1);
        when(repository.create(product2)).thenReturn(product2);
        service.create(product2);

        when(repository.findAll()).thenReturn(List.of(product1, product2).iterator());
        Iterator<Product> productIterator = service.findAll().iterator();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        assertEquals(product1.getProductName(), savedProduct.getProductName());
        assertEquals(product1.getProductQuantity(), savedProduct.getProductQuantity());

        assertTrue(productIterator.hasNext());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertEquals(product2.getProductName(), savedProduct.getProductName());
        assertEquals(product2.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindEmpty() {
        when(repository.findAll()).thenReturn(Collections.emptyIterator());
        Iterator<Product> productIterator = service.findAll().iterator();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProductAttribute() {
        when(repository.create(product1)).thenReturn(product1);
        service.create(product1);

        Product updatedProduct = product1;
        updatedProduct.setProductName("Sampo Cap Usep");
        updatedProduct.setProductQuantity(50);
        when(repository.edit(updatedProduct)).thenReturn(updatedProduct);
        service.edit(updatedProduct);

        when(repository.findAll()).thenReturn(List.of(product1).iterator());
        Iterator<Product> productIterator = service.findAll().iterator();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertNotEquals(savedProduct.getProductName(), "Sampo Cap Bambang");
        assertNotEquals(savedProduct.getProductQuantity(), 100);
    }

    @Test
    void testEditNonExistentProduct() {
        Product nonExistentProduct = new Product();
        when(repository.edit(nonExistentProduct)).thenReturn(null);
        assertDoesNotThrow(() -> service.edit(nonExistentProduct));
    }

    @Test
    void testDeleteProduct() {
        when(repository.create(product1)).thenReturn(product1);
        service.create(product1);

        when(repository.findAll()).thenReturn(List.of(product1).iterator());
        Iterator<Product> productIterator = service.findAll().iterator();
        assertTrue(productIterator.hasNext());
        service.delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void TestDeleteNonExistingProduct() {
        assertDoesNotThrow(() -> service.delete("Not Exist"));
    }
}
