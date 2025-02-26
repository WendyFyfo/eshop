package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.management.InstanceNotFoundException;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository repository;

    @Mock
    private ProductService service;

    private Product product1;
    @BeforeEach
    void setUp(){
        product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
    }
    @Test
    public void ProductListPageTest() throws Exception {
        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Product' List")));
    }

    @Test
    public void CreateProductPageTest() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(content().string(containsString("Create New Product")));
    }

    @Test
    public void createProductPostTest() throws Exception {
        Mockito.when(service.create(product1)).thenReturn(product1);
        mockMvc.perform(post("/product/create").flashAttr("product", product1))
                .andExpect(status().is3xxRedirection());

        Mockito.when(service.findAll()).thenReturn(List.of(product1));
        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(content().string(containsString("Sampo Cap Bambang")))
                .andExpect(content().string(containsString("100")));
    }

    @Test
    public void editProductPageTest_ProductExists() throws Exception {
        repository.create(product1);

        mockMvc.perform(get("/product/edit/" + product1.getProductId()))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("existingProduct"))
                .andExpect(model().attribute("existingProduct", product1));
    }

    @Test
    public void editProductPageTest_ProductNotFound() throws Exception {
        Mockito.when(service.findProductById("product-not-exist")).thenThrow(new InstanceNotFoundException());
        mockMvc.perform(get("/product/edit/product-not-exist"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));
    }

    @Test
    public void editProductPostTest() throws  Exception{
        mockMvc.perform(post("/product/edit")
                        .flashAttr("product", product1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));
    }

    @Test
    public void testDeleteProductPost() throws Exception {
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";

        mockMvc.perform(post("/product/delete-product")
                        .param("productId", productId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));
    }
}
