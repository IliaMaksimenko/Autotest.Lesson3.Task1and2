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
    public void setup(){

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
    public void tearDown(){

        driver.quit();

    }

    @Test
    public void shouldReceiveSuccessMessage() {

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("Иванов Иван");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("+79111111111");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();

        String expected = "  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("#root > div > div > p")).getText();

        assertEquals(expected, actual);

    }

    @Test
    public void shouldCheckValidation() {

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("Ivanov Ivan");
        driver.findElement(By.className("button__content")).click();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("#root > div > form > div:nth-child(1) > span > span > span.input__sub")).getText();

        assertEquals(expected, actual);

    }
}
