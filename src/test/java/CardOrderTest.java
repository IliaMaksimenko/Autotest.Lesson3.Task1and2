import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CardOrderTest {

    private WebDriver driver;


    @BeforeEach
    public void setup() {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

        driver.get("http://localhost:9999");
        driver.manage().window().maximize();

    }

    @AfterEach
    public void tearDown() {

        driver.quit();

    }

    @Test
    public void shouldReceiveSuccessMessage() {

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("Иванов Иван");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("+79111111111");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();

        String expected = "  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.xpath("//*[@data-test-id=\"order-success\"]")).getText();

        assertEquals(expected, actual);

    }

    @Test
    public void shouldCheckValidationEmptyName() {

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("");
        driver.findElement(By.className("button__content")).click();
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("+79111111111");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();

        String expected = "Поле обязательно для заполнения";

        assertEquals(expected, driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText());

    }


    @Test
    public void shouldCheckValidationEmptyPhone() {

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("Иван Иванов");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();

        String expected = "Поле обязательно для заполнения";

        assertEquals(expected, driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText());

    }


    @Test
    public void shouldCheckValidationIncorrectName() {

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("Ivan");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("+79111111111");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        assertEquals(expected, driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText());

    }


    @Test
    public void shouldCheckValidationIncorrectPhone() {

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("Иван Иванов");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("791111111111");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText());

    }


    @Test
    public void shouldCheckValidationCheckBox() {

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("Иван Иванов");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("+79111111111");
        driver.findElement(By.className("button__content")).click();

        driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).click();

    }

}
