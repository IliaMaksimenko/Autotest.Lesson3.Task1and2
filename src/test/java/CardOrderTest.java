import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class CardOrderTest {

    private WebDriver driver;

    @Test
    public void shouldReceiveSuccessMessage() {

        WebDriverManager.chromedriver().setup();
//        System.setProperty("webdriver.chrome.driver", "D:\\Java\\WebDriverChromium\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

        driver.get("http://localhost:9999");
        driver.manage().window().maximize();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("Иванов Иван");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("+79111111111");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();

        String expected = "  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("#root > div > div > p")).getText();

        driver.quit();

    }
}
