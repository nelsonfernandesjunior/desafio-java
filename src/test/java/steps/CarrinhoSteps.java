package steps;

import functions.CarrinhoFunctions;
import functions.BuscaProdutoFunctions;
import io.cucumber.java.PendingException;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.cucumber.datatable.DataTable;

public class CarrinhoSteps {

//    @Dado("que estou na página inicial do site")
//    public void queEstouNaPaginaInicialDoSite() {
////        BuscaProdutoFunctions.abrirPaginaInicialSite();
//    }

    @Quando("eu seleciono um produto na página inicial")
    public void euSelecionoUmProdutoNaPaginaInicial() throws InterruptedException {
        CarrinhoFunctions.selecionarProdutoPaginaInicial();
    }

    @E("eu adiciono o produto ao carrinho")
    public void euAdicionoOProdutoAoCarrinho() {
        CarrinhoFunctions.adicionarProdutoAoCarrinho();
    }

    @Então("o produto deve aparecer no carrinho")
    public void oProdutoDeveAparecerNoCarrinho() {
        CarrinhoFunctions.verificarProdutoNoCarrinho();
    }

    @E("eu altero a quantidade para {int} unidades")
    public void euAlteroAQuantidadeParaUnidades(Integer quantidade) {
        CarrinhoFunctions.alterarQuantidade(quantidade);
    }

    @E("o carrinho deve mostrar {int} unidades do produto")
    public void oCarrinhoDeveMostrarUnidadesDoProduto(Integer quantidade) {
        CarrinhoFunctions.verificarQuantidadeNoCarrinho(quantidade);
    }

    @E("eu volto para a página inicial")
    public void euVoltoParaAPaginaInicial() {
        CarrinhoFunctions.voltarParaPaginaInicial();
    }

    @E("eu adiciono outro produto ao carrinho")
    public void euAdicionoOutroProdutoAoCarrinho() throws InterruptedException {
        CarrinhoFunctions.selecionarSegundoProduto();
        CarrinhoFunctions.adicionarProdutoAoCarrinho();
    }

    @Então("o carrinho deve conter {int} produtos diferentes")
    public void oCarrinhoDeveConterProdutosDiferentes(Integer quantidade) {
        CarrinhoFunctions.verificarDoisProdutosNoCarrinho();
    }

    @Quando("eu seleciono um produto esgotado")
    public void euSelecionoUmProdutoEsgotado() {
        CarrinhoFunctions.selecionarProdutoEsgotado();
    }

    @E("eu adicionar {int} unidades")
    public void euAdicionarUnidades(Integer quantidade) {
        CarrinhoFunctions.alterarQuantidade(quantidade);
        CarrinhoFunctions.adicionarProdutoAoCarrinho();
    }

    @Então("verifico que o carrinho está vazio")
    public void verificoQueOCarrinhoEstaVazio() {
        CarrinhoFunctions.verificarCarrinhoVazio();
    }
}