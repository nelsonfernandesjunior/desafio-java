package functions;

import org.junit.Assert;
import pages.ProdutoPagamentoPage;

public class ProdutoPagamentoFunctions {

    static ProdutoPagamentoPage produtoPagamentoPage = new ProdutoPagamentoPage();

    public static void realizarBuscaProduto(String produto) throws InterruptedException {
        System.out.println("==> [Functions] Realizando busca pelo produto: " + produto);
        produtoPagamentoPage.realizarBuscaProduto(produto);
    }

    public static void adicionarProdutoAoCarrinho() throws InterruptedException {
        System.out.println("==> [Functions] Adicionando primeiro produto ao carrinho");
        produtoPagamentoPage.adicionarProdutoAoCarrinho();
    }

    public static void acessarCarrinhoCompras() {
        System.out.println("==> [Functions] Acessando carrinho de compras");
        produtoPagamentoPage.acessarCarrinhoCompras();
    }

    public static void acessarCarrinhoComprasVazio() {
        System.out.println("==> [Functions] Acessando carrinho de compras para CT-12");
        produtoPagamentoPage.acessarCarrinhoCompras();
    }

    public static void procederCheckout() {
        System.out.println("==> [Functions] Procedendo para checkout");
        produtoPagamentoPage.procederCheckout();
    }

    public static void verificarProdutoNaTelaPagamento(String produto) {
        System.out.println("==> [Functions] Verificando produto na tela de pagamento: " + produto);

        Assert.assertTrue("Produto '" + produto + "' não encontrado na tela de pagamento",
                produtoPagamentoPage.produtoEstaNaTelaPagamento(produto));

        ProdutoPagamentoFunctions.produtoPagamentoPage.voltarPaginaAnterior();
        ProdutoPagamentoFunctions.produtoPagamentoPage.removerProdutoDoCarrinho();

        System.out.println("==> [Functions] Produto '" + produto + "' verificado na tela de pagamento");
    }

    public static void adicionarProdutoComQuantidade(String quantidade) throws InterruptedException {
        System.out.println("==> [Functions] Adicionando produto com quantidade: " + quantidade);
        produtoPagamentoPage.adicionarProdutoComQuantidade(quantidade);
    }

    public static void verificarQuantidadeProduto(String quantidade, String produto) {
        System.out.println("==> [Functions] Verificando quantidade " + quantidade + " para produto: " + produto);

        int quantidadeEsperada = Integer.parseInt(quantidade);
        Assert.assertTrue("Quantidade do produto '" + produto + "' incorreta",
                produtoPagamentoPage.verificarQuantidadeProduto(produto, quantidadeEsperada));

        System.out.println("==> [Functions] Quantidade " + quantidade + " verificada para produto: " + produto);

        ProdutoPagamentoFunctions.produtoPagamentoPage.voltarPaginaAnterior();
        ProdutoPagamentoFunctions.produtoPagamentoPage.removerProdutoDoCarrinho();
    }

    public static void verificarMensagemCarrinhoVazio(String mensagem) {
        System.out.println("==> [Functions] Verificando mensagem de carrinho vazio: " + mensagem);

        Assert.assertTrue("Mensagem de carrinho vazio não encontrada ou incorreta",
                produtoPagamentoPage.verificarMensagemCarrinhoVazio(mensagem));

        System.out.println("==> [Functions] Mensagem de carrinho vazio verificada: " + mensagem);
    }

    public static void verificarCheckoutNaoAcessivel() {
        System.out.println("==> [Functions] Verificando que checkout não está acessível");

        Assert.assertFalse("Botão checkout não deveria estar visível ou habilitado com carrinho vazio",
                produtoPagamentoPage.checkoutEstaAcessivel());

        System.out.println("==> [Functions] Checkout não está acessível (conforme esperado)");
    }
}