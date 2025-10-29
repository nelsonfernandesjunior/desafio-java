package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.PageFactory;
import utils.DriverManager;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        if (this.driver == null) {
            throw new IllegalStateException("Driver não foi inicializado. Verifique os Hooks.");
        }
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void click(By locator) {
        System.out.println("🖱️ Tentando clicar no elemento: " + locator);

        if (driver == null) {
            throw new IllegalStateException("==> DRIVER É NULL! Verifique a inicialização nos Hooks.");
        }

        for (int tentativa = 1; tentativa <= 3; tentativa++) {
            try {
                System.out.println("🔄 Tentativa " + tentativa + "/3");

                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                element.click();

                System.out.println("==> Clique realizado na tentativa " + tentativa);
                return;

            } catch (Exception e) {
                System.out.println("==> Tentativa " + tentativa + " falhou: " + e.getMessage());

                if (tentativa == 3) {
                    try {
                        WebElement element = driver.findElement(locator);
                        JavascriptExecutor js = (JavascriptExecutor) driver;
                        js.executeScript("arguments[0].click();", element);
                        System.out.println("==> Clique forçado via JavaScript");
                        return;
                    } catch (Exception jsException) {
                        throw new RuntimeException("Falha total ao clicar: " + locator, jsException);
                    }
                }
                try { Thread.sleep(1000); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
            }
        }
    }

    // Aguardar elemento estar visível
    public void waitForElementVisible(By locator, int seconds) {
        System.out.println("==> Aguardando elemento visível: " + locator + " (timeout: " + seconds + "s)");
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        System.out.println("==> Elemento está visível");
    }

    // Aguardar elemento estar clicável
    public void waitForElementClickable(By locator, int seconds) {
        System.out.println("==> Aguardando elemento clicável: " + locator + " (timeout: " + seconds + "s)");
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        customWait.until(ExpectedConditions.elementToBeClickable(locator));
        System.out.println("==> Elemento está clicável");
    }

    // Verificar se elemento está visível
    public boolean isElementVisible(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            boolean visible = element.isDisplayed();
            System.out.println("==> Elemento visível: " + locator + " = " + visible);
            return visible;
        } catch (Exception e) {
            System.out.println("==> Elemento não encontrado: " + locator);
            return false;
        }
    }

    public boolean isElementClickable(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            boolean clickable = element.isDisplayed() && element.isEnabled();
            System.out.println("==> Elemento clicável: " + locator + " = " + clickable);
            return clickable;
        } catch (Exception e) {
            System.out.println("==> Elemento não clicável: " + locator + " - " + e.getMessage());
            return false;
        }
    }

    // Enviar texto para um campo
    public void sendKeys(By locator, String text) {
        System.out.println("==>  Digitando texto no elemento: " + locator);
        System.out.println("==> Texto: '" + text + "'");

        if (driver == null) {
            throw new IllegalStateException("==> DRIVER É NULL! Verifique a inicialização nos Hooks.");
        }

        for (int tentativa = 1; tentativa <= 3; tentativa++) {
            try {
                System.out.println("==> Tentativa " + tentativa + "/3");

                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

                element.clear();

                element.sendKeys(text);

                System.out.println("==> Texto digitado com sucesso na tentativa " + tentativa);
                return;

            } catch (Exception e) {
                System.out.println("==> Tentativa " + tentativa + " falhou: " + e.getMessage());

                if (tentativa == 3) {
                    try {
                        WebElement element = driver.findElement(locator);
                        JavascriptExecutor js = (JavascriptExecutor) driver;

                        js.executeScript("arguments[0].value = '';", element);

                        js.executeScript("arguments[0].value = arguments[1];", element, text);

                        System.out.println("==> Texto digitado via JavaScript");
                        return;
                    } catch (Exception jsException) {
                        throw new RuntimeException("Falha total ao digitar texto: " + locator, jsException);
                    }
                }

                try { Thread.sleep(1000); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
            }
        }
    }

    // Enviar texto e pressionar ENTER
    public void sendKeysAndEnter(By locator, String text) {
        System.out.println("==> Digitando texto e pressionando ENTER no elemento: " + locator);
        System.out.println("==> Texto: '" + text + "'");

        if (driver == null) {
            throw new IllegalStateException("==> DRIVER É NULL! Verifique a inicialização nos Hooks.");
        }

        for (int tentativa = 1; tentativa <= 3; tentativa++) {
            try {
                System.out.println("🔄 Tentativa " + tentativa + "/3");

                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

                element.clear();
                element.sendKeys(text);
                element.sendKeys(Keys.ENTER);

                System.out.println("==> Texto digitado e ENTER pressionado na tentativa " + tentativa);
                return;

            } catch (Exception e) {
                System.out.println("==> Tentativa " + tentativa + " falhou: " + e.getMessage());

                if (tentativa == 3) {
                    try {
                        WebElement element = driver.findElement(locator);
                        JavascriptExecutor js = (JavascriptExecutor) driver;

                        js.executeScript("arguments[0].value = '';", element);
                        js.executeScript("arguments[0].value = arguments[1];", element, text);

                        js.executeScript(
                                "var event = new KeyboardEvent('keydown', { key: 'Enter', code: 'Enter', keyCode: 13, which: 13 }); " +
                                        "arguments[0].dispatchEvent(event);", element
                        );

                        System.out.println("==> Texto digitado e ENTER via JavaScript");
                        return;
                    } catch (Exception jsException) {
                        throw new RuntimeException("Falha total ao digitar texto e pressionar ENTER: " + locator, jsException);
                    }
                }

                try { Thread.sleep(1000); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
            }
        }
    }

    // Metodo getText que já imprime no log automaticamente
    public String getText(By locator) {
        System.out.println("==> Obtendo texto do elemento: " + locator);

        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            String text = element.getText();

            System.out.println("==> Texto obtido: '" + text + "'");
            System.out.println("==> Conteúdo capturado: " + text);

            return text;
        } catch (Exception e) {
            System.out.println("==> Erro ao obter texto: " + e.getMessage());
            throw new RuntimeException("Falha ao obter texto do elemento: " + locator, e);
        }
    }

    // Scroll até o topo da página
    public void scrollToTop() {
        System.out.println("==>  Fazendo scroll para o topo da página");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");
    }

    // Scroll até o final da página
    public void scrollToBottom() {
        System.out.println("==>  Fazendo scroll para o final da página");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    // Scroll até um elemento específico
    public void scrollToElement(By locator) {
        System.out.println("==> Fazendo scroll até o elemento: " + locator);

        try {
            WebElement element = driver.findElement(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            System.out.println("==> Scroll até elemento realizado");

            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("==> Erro ao fazer scroll até elemento: " + e.getMessage());
            throw new RuntimeException("Falha ao fazer scroll até elemento: " + locator, e);
        }
    }

    // Scroll até um elemento específico (versão WebElement)
    public void scrollToElement(WebElement element) {
        System.out.println("==> Fazendo scroll até o elemento");

        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            System.out.println("==> Scroll até elemento realizado");
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("==> Erro ao fazer scroll até elemento: " + e.getMessage());
            throw new RuntimeException("Falha ao fazer scroll até elemento", e);
        }
    }

    // Scroll por quantidade específica de pixels
    public void scrollByPixels(int x, int y) {
        System.out.println("==> Fazendo scroll de X: " + x + "px, Y: " + y + "px");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(" + x + ", " + y + ");");
    }

    // Scroll suave até um elemento
    public void smoothScrollToElement(By locator) {
        System.out.println("==> Fazendo scroll suave até o elemento: " + locator);

        try {
            WebElement element = driver.findElement(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            System.out.println("==> Scroll suave realizado");
            Thread.sleep(800); // Pausa maior para scroll suave
        } catch (Exception e) {
            System.out.println("==> Erro no scroll suave: " + e.getMessage());
            // Tenta o scroll normal como fallback
            scrollToElement(locator);
        }
    }

    public void limparCampo(By locator) {
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .perform();
    }

    public void garantirQueEstaNaPaginaInicial() {
        String urlAtual = driver.getCurrentUrl();
        String urlBase = "https://www.advantageonlineshopping.com";

        if (!urlAtual.equals(urlBase + "/")) {
            System.out.println("🔄 Navegando para página inicial...");
            driver.get(urlBase);
            System.out.println("✅ Na página inicial");
        }
    }

    public void fecharNavegador() {
        if (driver != null) {
            driver.quit();
            System.out.println("Navegador fechado com sucesso!");
        }
    }

    public void fecharAba() {
        if (driver != null) {
            driver.close();
            System.out.println("Aba fechada com sucesso!");
        }
    }

}