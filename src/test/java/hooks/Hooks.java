package hooks;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import utils.DriverManager;
import functions.WebDriverFactory;
import pages.HomePage;

public class Hooks {
    private static boolean navegadorAberto = false;
    private static HomePage homePage;

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("=> Iniciando o cenário de teste: " + scenario.getName());

        if (!navegadorAberto) {
            // Cria o driver usando WebDriverFactory
            WebDriver driver = WebDriverFactory.createDriver();
            DriverManager.setDriver(driver);

            // Inicializa a HomePage com o driver
            homePage = new HomePage(driver);
            homePage.iniciarSessao();

            navegadorAberto = true;
            System.out.println("=> Navegador aberto e site acessado via Hooks");
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        System.out.println("=> Finalizando o cenário de teste: " + scenario.getName());

        if (scenario.isFailed()) {
            System.out.println("=> Cenário FALHOU: " + scenario.getName());
        }
        // NÃO fecha o navegador aqui - mantém aberto para próximos cenários
    }








    @AfterAll
    public static void finalizarTodosTestes() {
        System.out.println("=> Finalizando TODOS os testes - Fechando navegador");

        if (navegadorAberto && homePage != null) {
            homePage.fecharNavegador();
            navegadorAberto = false;
            System.out.println("==>avegador fechado com sucesso");
        }
    }

    public static WebDriver getDriver() {
        return DriverManager.getDriver();
    }


    // opcao

//    @After
//    public void tearDown(Scenario scenario) {
//        System.out.println("=> Finalizando o cenário de teste: " + scenario.getName());
//
//        if (scenario.isFailed()) {
//            System.out.println("=> Cenário FALHOU: " + scenario.getName());
//        }
//
//        // Fechar navegador após CADA cenário - resolve problemas de estado
//        if (navegadorAberto) {
//            DriverManager.quitDriver();
//            navegadorAberto = false;
//            System.out.println("==> Navegador fechado após cenário");
//        }
//    }
//
//    @Before
//    public void setUp(Scenario scenario) {
//        System.out.println("=> Iniciando o cenário de teste: " + scenario.getName());
//
//        // SEMPRE abrir novo navegador para cada cenário
//        WebDriver driver = WebDriverFactory.createDriver();
//        DriverManager.setDriver(driver);
//        homePage = new HomePage(driver);
//        homePage.iniciarSessao();
//        navegadorAberto = true;
//        System.out.println("=> Navegador aberto e site acessado via Hooks");
//    }




}
