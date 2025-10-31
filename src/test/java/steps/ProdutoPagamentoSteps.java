package steps;

import functions.ProdutoPagamentoFunctions;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class ProdutoPagamentoSteps {

//    @Dado("que estou na página inicial do site")
//    public void queEstouNaPaginaInicialDoSite() {
//        // Já implementado nos Hooks
//    }

    @Quando("realizo a busca pelo produto {string}")
    public void realizoABuscaPeloProduto(String produto) throws InterruptedException {
        ProdutoPagamentoFunctions.realizarBuscaProduto(produto);
    }

    @E("adiciono o produto ao carrinho")
    public void adicionoOProdutoAoCarrinho() throws InterruptedException {
        ProdutoPagamentoFunctions.adicionarProdutoAoCarrinho();
    }

    @E("acesso o carrinho de compras")
    public void acessoOCarrinhoDeCompras() {
        ProdutoPagamentoFunctions.acessarCarrinhoCompras();
    }

    @E("procedo para o checkout")
    public void procedoParaOCheckout() {
        ProdutoPagamentoFunctions.procederCheckout();
    }

    @Então("devo ver o produto {string} na tela de pagamento")
    public void devoVerOProdutoNaTelaDePagamento(String produto) {
        ProdutoPagamentoFunctions.verificarProdutoNaTelaPagamento(produto);
    }

    @E("adiciono o produto ao carrinho com quantidade {string}")
    public void adicionoOProdutoAoCarrinhoComQuantidade(String quantidade) throws InterruptedException {
        ProdutoPagamentoFunctions.adicionarProdutoComQuantidade(quantidade);
    }

    @Então("devo ver a quantidade {string} para o produto {string}")
    public void devoVerAQuantidadeParaOProduto(String quantidade, String produto) {
        ProdutoPagamentoFunctions.verificarQuantidadeProduto(quantidade, produto);
    }

    @Quando("acesso o carrinho de compras vazio")
    public void acessoOCarrinhoDeComprasVazio() {
        ProdutoPagamentoFunctions.acessarCarrinhoComprasVazio();
    }

    @Então("devo ver a mensagem {string}")
    public void devoVerAMensagem(String mensagem) {
        ProdutoPagamentoFunctions.verificarMensagemCarrinhoVazio(mensagem);
    }

    @E("não devo conseguir acessar o checkout")
    public void naoDevoConseguirAcessarOCheckout() {
        ProdutoPagamentoFunctions.verificarCheckoutNaoAcessivel();
    }
}