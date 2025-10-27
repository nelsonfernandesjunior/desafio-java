package functions;

import pages.BuscaProdutoPage;
import pages.HomePage;
import hooks.Hooks;

public class BuscaProdutoFunctions {
//    private static HomePage homePage;

    static BuscaProdutoPage buscaProdutoPage = new BuscaProdutoPage();

    public static void buscarProdutoExistente() throws InterruptedException {
        buscaProdutoPage.buscarProdutoUnico();
    }

    public static void buscarProdutoExistenteMultiplosResultados() throws InterruptedException {
        buscaProdutoPage.buscarProdutoMultiplo();
    }

    public static void resultadoBuscaProduto() {
        buscaProdutoPage.resultadoBuscaProduto();
    }

    public static void resultadoBuscaProdutosMultiplos() {
        buscaProdutoPage.resultadoBuscaProdutosMultiplos();
    }

    public static void buscarProdutoInexistente() throws InterruptedException {
        buscaProdutoPage.buscarProdutoInexistente();
    }

    public static void validarMensagemProdutoInexistente() throws InterruptedException {
        buscaProdutoPage.validarMensagemProdutoInexistente();
    }
}