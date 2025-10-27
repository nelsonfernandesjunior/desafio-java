package steps;

import functions.BuscaProdutoFunctions;
import io.cucumber.java.PendingException;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import hooks.Hooks;
import pages.HomePage;

public class BuscaProdutoSteps {

    @Dado("que estou na página inicial do site")
    public void queEstouNaPaginaInicialDoSite() {
//        BuscaProdutoFunctions.abrirPaginaInicialSite();
    }

    @Quando("eu busco pelo produto existente")
    public void euBuscoPeloProdutoExistente () throws InterruptedException {
        BuscaProdutoFunctions.buscarProdutoExistente();
    }

    @Então("devo ver o resultado de busca contendo o produto")
    public void devoVerOResultadoDeBuscaContendoOProduto() {
        BuscaProdutoFunctions.resultadoBuscaProduto();
    }

    @Quando("eu busco pelo produto existente com múltiplos resultados")
    public void euBuscoPeloProdutoExistenteComMultiplosResultados() throws InterruptedException {
        BuscaProdutoFunctions.buscarProdutoExistenteMultiplosResultados();
    }

    @Então("devo ver uma lista com múltiplos produtos")
    public void devoVerUmaListaComMultiplosProdutos() {
        BuscaProdutoFunctions.resultadoBuscaProdutosMultiplos();
    }

    @Quando("eu busco pelo produto inexistente")
    public void euBuscoPeloProdutoInexistente() throws InterruptedException {
        BuscaProdutoFunctions.buscarProdutoInexistente();
    }

    @Então("devo ver a mensagem sem resultado")
    public void devoVerAMensagemSemResultado() throws InterruptedException {
        BuscaProdutoFunctions.validarMensagemProdutoInexistente();
    }

}