package id.ac.ui.cs.advprog.eshop.functional;

import java.util.List;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {
    /**
     * The port number assigned to the running application during test execution.
     * Set automatically during each test run by Spring Framework's test context.
     */
    @LocalServerPort
    private int serverPort;

    /**
     * THe base URL for testing. Default to {@code http://localhost}
     */
    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;
    private String createProductPageUrl;
    private String homePageUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
        createProductPageUrl = baseUrl + "/product/create";
        homePageUrl = baseUrl + "/product/list";
    }

    @Test
    void goToCreatePageButton_isWorking(ChromeDriver driver) {
        driver.get(homePageUrl);
        WebElement goToCreatePageBtn = driver.findElement(By.linkText("Create Product"));
        goToCreatePageBtn.click();
        String currentUrl = driver.getCurrentUrl();

        assertEquals(createProductPageUrl, currentUrl);
    }

    @Test
    void pageTitle_isCorrect(ChromeDriver driver) {
        driver.get(createProductPageUrl);
        String pageTitle = driver.getTitle();

        assertEquals("Create New Product", pageTitle);
    }

    @Test
    void createProductFromForm_isWorking(ChromeDriver driver) {
        driver.get(createProductPageUrl);
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        String productName = "Sampo Cap Bambang";
        nameInput.sendKeys(productName);

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        String productQuantity = "100";
        quantityInput.sendKeys(productQuantity);

        WebElement submitButton = driver.findElement(By.tagName("button"));
        submitButton.click();

        String currentUrl = driver.getCurrentUrl();
        assertEquals(homePageUrl, currentUrl);

        List<WebElement> tdTags = driver.findElements(By.tagName("td"));

        // productName, productQuantity, actions
        assertEquals(3, tdTags.size());

        String savedProductName = tdTags.get(0).getText();
        assertEquals(productName, savedProductName);

        String savedProductQuantity = tdTags.get(1).getText();
        assertEquals(productQuantity, savedProductQuantity);
    }
}
