package utils;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static WebDriver driver;



    public static WebDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("Driver não foi inicializado. Verifique se os Hooks executaram corretamente.");
        }
        return driver;
    }

    public static void setDriver(WebDriver webDriver) {
        driver = webDriver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static boolean isDriverInitialized() {
        return driver != null;
    }
}