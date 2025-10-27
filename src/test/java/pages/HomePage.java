package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.time.Duration;

public class HomePage extends BasePage {
    private String url;

    @FindBy(className = "logo")
    protected WebElement logo;

    public HomePage(WebDriver driver) {
        super(driver);
        carregarConfiguracoes();
    }

    public HomePage() {
        super();
        carregarConfiguracoes();
    }

    private void carregarConfiguracoes() {
        try {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream("src/test/resources/config/config.properties");
            properties.load(fis);
            this.url = properties.getProperty("url");
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar a URL do site.", e);
        }
    }

    public void acessarSite() {
        if (this.driver == null) {
            throw new IllegalStateException("Navegador não foi inicializado.");
        }

        driver.get(url);
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
        System.out.println("=> Página inicial acessada: " + url);
    }

    public void fecharNavegador() {
        if (this.driver != null) {
            driver.quit();
            System.out.println("=> Navegador fechado.");
        }
    }

    public void iniciarSessao() {
        acessarSite();
    }

    public void recarregarPagina() {
        driver.navigate().refresh();
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
        System.out.println("=> Página recarregada.");
    }

}