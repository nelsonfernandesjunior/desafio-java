package hooks;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import utils.DriverManager;
import functions.WebDriverFactory;
import pages.HomePage;
import utils.PdfReportGenerator;
import utils.ScreenshotUtils;

public class Hooks {
    private static boolean navegadorAberto = false;
    private static HomePage homePage;
    private String currentScenarioName; // ← ADICIONAR ESTA VARIÁVEL

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("=> Iniciando o cenário de teste: " + scenario.getName());
        this.currentScenarioName = scenario.getName(); // ← INICIALIZAR A VARIÁVEL

        // Prepara sistema de screenshots
        ScreenshotUtils.resetCounter(scenario.getName());
        PdfReportGenerator.clearScreenshots();

        if (!navegadorAberto) {
            WebDriver driver = WebDriverFactory.createDriver();
            DriverManager.setDriver(driver);

            homePage = new HomePage(driver);
            homePage.iniciarSessao();

            navegadorAberto = true;
            System.out.println("=> Navegador aberto e site acessado via Hooks");
        }

        // Primeiro screenshot - página inicial
        takeScreenshot("00_Pagina_Inicial");
    }

    @After
    public void tearDown(Scenario scenario) {
        System.out.println("=> Finalizando o cenário de teste: " + scenario.getName());

        // Screenshot final
        takeScreenshot("99_Final_" + (scenario.isFailed() ? "FALHA" : "SUCESSO"));

        // Gera PDF do relatório
        String status = scenario.isFailed() ? "FALHOU" : "PASSOU";
        PdfReportGenerator.generatePdfReport(currentScenarioName, status); // ← USAR A VARIÁVEL

        if (scenario.isFailed()) {
            System.out.println("=> Cenário FALHOU: " + scenario.getName());
        }
    }

    @AfterAll
    public static void finalizarTodosTestes() {
        System.out.println("=> Finalizando TODOS os testes - Fechando navegador");

        if (navegadorAberto && homePage != null) {
            homePage.fecharNavegador();
            navegadorAberto = false;
            System.out.println("==> Navegador fechado com sucesso");
        }
    }

    private void takeScreenshot(String stepName) {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            String screenshotPath = ScreenshotUtils.takeScreenshot(driver, stepName);
            PdfReportGenerator.addScreenshot(screenshotPath);
        }
    }
}