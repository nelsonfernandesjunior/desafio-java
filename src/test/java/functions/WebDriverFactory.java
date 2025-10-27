package functions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class WebDriverFactory {

    public static Properties properties;

    static {
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/config/config.properties");
            properties.load(fileInputStream);
            System.out.println("==> Configurações carregadas com sucesso");
        } catch (IOException e) {
            System.err.println("==> Não foi possível carregar config.properties");
            System.err.println("==> Verifique se o arquivo existe em: src/test/resources/config/config.properties");
        }
    }

    public static WebDriver createDriver() {
        String browser = getProperty("browser", "chrome");
        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "firefox":
                setupFirefoxDriver();
                driver = new FirefoxDriver();
                break;

            case "chrome":
            default:
                setupChromeDriver();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--disable-extensions");
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-popup-blocking");
                options.addArguments("--ignore-certificate-errors");

                driver = new ChromeDriver(options);
                break;
        }

        return driver;
    }

    private static void setupChromeDriver() {
        String os = System.getProperty("os.name").toLowerCase();
        String driverPath;

        if (os.contains("win")) {
            driverPath = getProperty("chrome.driver.windows.path", "src/test/resources/drivers/chromedriver.exe");
        } else if (os.contains("mac")) {
            driverPath = getProperty("chrome.driver.mac.path", "src/test/resources/drivers/chromedriver");
        } else {
            driverPath = getProperty("chrome.driver.linux.path", "src/test/resources/drivers/chromedriver_linux");
        }

        System.setProperty("webdriver.chrome.driver", getAbsolutePath(driverPath));
        System.out.println("==> ChromeDriver configurado: " + driverPath);
    }

    private static void setupFirefoxDriver() {
        String driverPath = getProperty("firefox.driver.windows.path", "src/test/resources/drivers/geckodriver.exe");
        System.setProperty("webdriver.gecko.driver", getAbsolutePath(driverPath));
        System.out.println("==> GeckoDriver configurado: " + driverPath);
    }

    private static String getAbsolutePath(String relativePath) {
        if (relativePath == null || relativePath.trim().isEmpty()) {
            throw new IllegalArgumentException("==> Caminho do driver não pode ser nulo ou vazio");
        }

        if (relativePath.startsWith("/") || relativePath.contains(":") || relativePath.startsWith("\\")) {
            return relativePath;
        }

        String userDir = System.getProperty("user.dir");
        return userDir + "/" + relativePath;
    }

    private static String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            System.out.println("==>  Propriedade '" + key + "' não encontrada, usando padrão: " + defaultValue);
            return defaultValue;
        }
        return value;
    }

    public static String getBaseUrl() {
        return getProperty("base.url", "https://www.advantageonlineshopping.com");
    }
}