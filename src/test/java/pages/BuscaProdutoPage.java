package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BuscaProdutoPage extends BasePage {

    // Constantes
    private static final String PRODUTO_UNICO = "HP PRO TABLET 608 G1";
    private static final String PRODUTO_MULTIPLO = "BOMBIZINI GUZINI";
    private static final String PRODUTO_INEXISTENTE = "AKITA INU";

    private By searchBar = By.id("menuSearch");
    private By autoComplete = By.id("autoComplete");
    private By resultsSearch = By.cssSelector("a.product p.roboto-regular");
    private By buscarProdutoExistente = By.id("search-input");
    private By resultadoBusca = By.id("search-results");

    public void buscarProdutoUnico() throws InterruptedException {
        abrirSearchBar();
        buscarProdutoExistente(PRODUTO_UNICO);
    }

    public void buscarProdutoMultiplo() throws InterruptedException {
        abrirSearchBar();
        buscarProdutoExistente(PRODUTO_MULTIPLO);
    }

    public BuscaProdutoPage() {
        super();
    }

    public void abrirSearchBar() throws InterruptedException {
        System.out.println("=> Abrindo a barra de busca.");
        click(searchBar);
        System.out.println("=> Barra de busca aberta.");
        waitForElementVisible(searchBar, 3);
        click(searchBar);
    }

    public void buscarProdutoExistente(String produto) {
        System.out.println("=> Buscando produto: " + produto);
        sendKeysAndEnter(autoComplete, produto);
    }

    public void resultadoBuscaProduto() {
        String produtoEsperado = PRODUTO_UNICO;

        waitForElementVisible(resultsSearch, 3);
        List<WebElement> produtos = driver.findElements(resultsSearch);

        System.out.println("==> " + produtos.size() + " produtos '" + produtoEsperado + "' encontrados");

        Assert.assertFalse("==> Nenhum produto encontrado", produtos.isEmpty());
        Assert.assertTrue("==> Apenas " + produtos.size() + " produto(s) encontrado(s) - esperados múltiplos", produtos.size() >= 1);

        System.out.println("==> Múltiplos produtos validados com sucesso");
    }

    public void resultadoBuscaProdutosMultiplos() {
        String produtoEsperado = PRODUTO_MULTIPLO;

        waitForElementVisible(resultsSearch, 3);
        List<WebElement> produtos = driver.findElements(resultsSearch);

        System.out.println("==> " + produtos.size() + " produtos '" + produtoEsperado + "' encontrados");

        Assert.assertFalse("==> Nenhum produto encontrado", produtos.isEmpty());
        Assert.assertTrue("==> Apenas " + produtos.size() + " produto(s) encontrado(s) - esperados múltiplos", produtos.size() >= 3);

        System.out.println("==> Múltiplos produtos validados com sucesso");
    }

    public void buscarProdutoInexistente() throws InterruptedException {
        abrirSearchBar();
        buscarProdutoExistente(PRODUTO_INEXISTENTE);
    }

    public void validarMensagemProdutoInexistente() {
        String mensagemEsperada = "No results for \"" + PRODUTO_INEXISTENTE + "\"";

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.findElement(By.tagName("body")).getText().contains(mensagemEsperada));

        Assert.assertTrue(
                "Mensagem '" + mensagemEsperada + "' não encontrada",
                driver.findElement(By.tagName("body")).getText().contains(mensagemEsperada)
        );

        System.out.println("==> Mensagem de produto inexistente validada");
    }

}