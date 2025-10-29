// utils/ScreenshotUtils.java
package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    private static int screenshotCount = 1;
    private static String currentTestName = "";

    public static String takeScreenshot(WebDriver driver, String stepName) {
        try {
            // Cria diretório se não existir
            File evidencesDir = new File("evidencias/screenshots");
            if (!evidencesDir.exists()) {
                evidencesDir.mkdirs();
            }

            // Formata nome do arquivo
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            String fileName = currentTestName + "_" + String.format("%02d", screenshotCount) +
                    "_" + stepName.replace(" ", "_") + "_" + timestamp + ".png";

            File screenshotFile = new File(evidencesDir, fileName);

            // Tira screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, screenshotFile);

            System.out.println("==> Screenshot salvo: " + fileName);
            screenshotCount++;

            return screenshotFile.getAbsolutePath();

        } catch (IOException e) {
            System.err.println("==> Erro ao tirar screenshot: " + e.getMessage());
            return "";
        }
    }

    public static void resetCounter(String testName) {
        screenshotCount = 1;
        currentTestName = testName.replace(" ", "_").replace("/", "_");
        System.out.println("==> Contador de screenshots resetado para: " + currentTestName);
    }

    public static int getScreenshotCount() {
        return screenshotCount;
    }
}