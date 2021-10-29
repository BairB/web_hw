package ru.domain.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebTestTask2 {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        // убедитесь, что файл chromedriver.exe расположен именно в каталоге C:\tmp
        //System.setProperty("webdriver.chrome.driver", "C:\\tmp\\chromedriver.exe");
        WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldRemainErrorIfWrongName(){
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Ivan Pe");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+79031234567");
        driver.findElement(By.cssSelector("[class = 'checkbox__box']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expectedText, actualText);
    }

    @Test
    void shouldRemainErrorIfCorrectNameAndEmptyPhone(){
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Иванов");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[class = 'checkbox__box']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        String expectedText = "Поле обязательно для заполнения";
        assertEquals(expectedText, actualText);
    }

    @Test
    void shouldRemainErrorIfWrongPhone(){
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Арсений");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("79031234567");
        driver.findElement(By.cssSelector("[class = 'checkbox__box']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expectedText, actualText);
    }

    @Test
    void shouldRemainErrorIfCorrectPhoneAndEmptyName(){
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+79031234567");
        driver.findElement(By.cssSelector("[class = 'checkbox__box']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        String expectedText = "Поле обязательно для заполнения";
        assertEquals(expectedText, actualText);
    }

    @Test
    void shouldRemainErrorIfEmptyFields(){
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[class = 'checkbox__box']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("span[data-test-id = \"name\"] span + span +span")).getText();
        String expectedText = "Поле обязательно для заполнения";
        assertEquals(expectedText, actualText);
    }

    @Test
    void shouldRemainErrorIfCheckboxNotMarked(){
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Иванов");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+79034567891");
        //driver.findElement(By.cssSelector("[class = 'checkbox__box']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__text")).getText();
        String expectedText = "Я соглашаюсь с условиями обработки и использования моих персональных данных" +
                " и разрешаю сделать запрос в бюро кредитных историй";
        assertEquals(expectedText, actualText);
    }
}
