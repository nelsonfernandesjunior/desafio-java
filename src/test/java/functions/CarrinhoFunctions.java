package functions;

import pages.CarrinhoPage;

public class CarrinhoFunctions {

    static CarrinhoPage carrinhoPage = new CarrinhoPage();

//    public static void navegarParaPaginaInicial() {
//        // Implementação para navegar para a página inicial
//        System.out.println("==> Acessando página inicial");
//        // Exemplo: driver.get(WebDriverFactory.getBaseUrl());
//    }

    public static void selecionarProdutoPaginaInicial() throws InterruptedException {
        carrinhoPage.selecionarProdutoNaPaginaInicial();
    }

    public static void adicionarProdutoAoCarrinho() {
        carrinhoPage.adicionarProdutoAoCarrinho();
    }

    public static void verificarProdutoNoCarrinho() {
        carrinhoPage.verificarProdutoNoCarrinho();
    }

    public static void verificarOsProdutosNoCarrinho() {
        carrinhoPage.verificarOsProdutosNoCarrinho();
    }

    public static void alterarQuantidade(int quantidade) {
        carrinhoPage.alterarQuantidade(quantidade);
    }

    public static void verificarQuantidadeNoCarrinho(int quantidade) {
        carrinhoPage.verificarQuantidadeNoCarrinho(quantidade);
    }

    public static void voltarParaPaginaInicial() {
        carrinhoPage.voltarParaPaginaInicial();
    }

    public static void selecionarSegundoProduto() throws InterruptedException {
        carrinhoPage.selecionarSegundoProduto();
    }

    public static void verificarDoisProdutosNoCarrinho() {
        carrinhoPage.verificarDoisProdutosNoCarrinho();
    }

    public static void selecionarProdutoEsgotado() {
        carrinhoPage.selecionarProdutoEsgotado();
    }

    public static void verificarCarrinhoVazio() {
        carrinhoPage.verificarCarrinhoVazio();
    }
}