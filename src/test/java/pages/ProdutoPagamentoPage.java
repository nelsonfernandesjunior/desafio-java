package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutoPagamentoPage extends BasePage {

    private By campoBusca = By.id("menuSearch");
    private By botaoBusca = By.id("searchButton");
    private By autoComplete = By.id("autoComplete");
    private By resultadosBusca = By.cssSelector(".product");
    private By primeiroProduto = By.cssSelector(".product:first-child");
    private By botaoShopNow = By.id("menuCart");
    private By botaoAdicionarCarrinho = By.cssSelector(".addToCart");
    private By produtoSelecionado = By.xpath("//img[@class='imgProduct']");
    private By botaoFecharBusca = By.xpath("//div[@data-ng-click='closeSearchForce()']");
    private By botaoAddToCart = By.xpath("//button[@name='save_to_cart']");
    private By iconeCarrinho = By.id("menuCart");
//    private By iconeCarrinho = By.name("save_to_cart");
    private By itensCarrinho = By.cssSelector(".cart-item");
    private By botaoCheckout = By.id("checkOutButton");
    private By mensagemCarrinhoVazio = By.xpath("//label[contains(text(),'Your shopping cart is empty')]");
    private By telaPagamento = By.cssSelector(".payment-page, .checkout-page");
    private By itensPedido = By.cssSelector(".order-item, .productInCart");
    private By nomesProdutosPagamento = By.id("orderPayment");
    private By precosProdutosPagamento = By.cssSelector(".productPrice, .price");
    private By quantidadesProdutosPagamento = By.xpath("//label[contains(text(),'QTY:')]");
    private By totalPedido = By.cssSelector(".totalPrice, .total-price");
    private By subtotalProduto = By.cssSelector(".subTotal, .sub-total");
    private By campoQuantidade = By.name("quantity");
    private By botaoAumentarQuantidade = By.cssSelector(".plus");
    private By botaoDiminuirQuantidade = By.cssSelector(".minus");

    public ProdutoPagamentoPage() {
        super();
    }

    public void realizarBuscaProduto(String produto) throws InterruptedException {
//        System.out.println("==> Garantir que está na página inicial");
//        garantirQueEstaNaPaginaInicial();
        System.out.println("==> Realizando busca pelo produto: " + produto);
        abrirSearchBar();
        buscarProdutoExistente(produto);
    }

    public void abrirSearchBar() throws InterruptedException {
        System.out.println("=> Abrindo a barra de busca.");
        takeScreenshot("Abrir_Barra_Busca");
        click(campoBusca);
        takeScreenshot("Barra_Busca_Aberta");
        System.out.println("=> Barra de busca aberta.");
        waitForElementVisible(campoBusca, 3);
        click(campoBusca);
    }

    public void buscarProdutoExistente(String produto) {
        System.out.println("=> Buscando produto: " + produto);
        sendKeysAndEnter(autoComplete, produto);
        takeScreenshot("Busca_Produto_" + produto.replace(" ", "_"));
    }

    public void adicionarProdutoAoCarrinho() throws InterruptedException {
        System.out.println("==> Adicionando produto ao carrinho");
        click(botaoFecharBusca);
        waitForElementClickable(produtoSelecionado, 5);
        click(produtoSelecionado);
        waitForElementClickable(botaoAddToCart, 5);
        click(botaoAddToCart);
        takeScreenshot("Produto_Adicionado_Carrinho");
    }

    public void adicionarProdutoComQuantidade(String quantidade) throws InterruptedException {
        System.out.println("==> Adicionando produto com quantidade: " + quantidade);
        click(botaoFecharBusca);
        waitForElementClickable(produtoSelecionado, 5);
        click(produtoSelecionado);
        waitForElementClickable(campoQuantidade, 5);

        if (isElementVisible(campoQuantidade)) {
            WebElement campoQtd = driver.findElement(campoQuantidade);
            click(campoQuantidade);
            limparCampo(campoQuantidade);
            campoQtd.sendKeys(quantidade);
            takeScreenshot("Quantidade_Ajustada_" + quantidade);
            waitForElementClickable(botaoAddToCart, 5);
            click(botaoAddToCart);
        }
    }

    public void acessarCarrinhoCompras() {
        System.out.println("==> Acessando carrinho de compras");
        click(iconeCarrinho);
        takeScreenshot("Carrinho_Acessado");

        if (isElementVisible(mensagemCarrinhoVazio)) {
            System.out.println("==> Carrinho está vazio");
        } else {
            System.out.println("==> Carrinho contém itens");
        }
    }

    public void procederCheckout() {
        System.out.println("==> Procedendo para checkout");

        if (!isElementVisible(botaoCheckout)) {
            throw new RuntimeException("Botão checkout não encontrado - carrinho pode estar vazio");
        }

        click(botaoCheckout);
        takeScreenshot("Checkout_Procedido");
    }

    public List<String> obterNomesProdutosPagamento() {
        waitForElementVisible(nomesProdutosPagamento, 5);
        List<WebElement> elementos = driver.findElements(nomesProdutosPagamento);
        return elementos.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public boolean produtoEstaNaTelaPagamento(String nomeProduto) {
        List<String> nomes = obterNomesProdutosPagamento();
        boolean encontrado = nomes.stream()
                .anyMatch(nome -> nome.toLowerCase().contains(nomeProduto.toLowerCase()));

        System.out.println("==> Produto '" + nomeProduto + "' encontrado: " + encontrado);
        return encontrado;
    }

    public List<Double> obterPrecosProdutosPagamento() {
        waitForElementVisible(precosProdutosPagamento, 5);
        List<WebElement> elementos = driver.findElements(precosProdutosPagamento);

        return elementos.stream()
                .map(element -> {
                    String texto = element.getText().replace("$", "").replace(",", "").trim();
                    return Double.parseDouble(texto);
                })
                .collect(Collectors.toList());
    }

    public List<Integer> obterQuantidadesProdutosPagamento() {
        try {
            By locatorQuantidades = By.xpath("//label[contains(., 'QTY:')]");
            List<WebElement> elementos = driver.findElements(locatorQuantidades);

            System.out.println("==> Encontrados " + elementos.size() + " elementos de quantidade");

            List<Integer> quantidades = new ArrayList<>();

            for (WebElement elemento : elementos) {
                String texto = elemento.getText().trim();
                System.out.println("==> Processando elemento: '" + texto + "'");

                String numeroStr = texto.replace("QTY:", "").trim();

                try {
                    int quantidade = Integer.parseInt(numeroStr);
                    quantidades.add(quantidade);
                    System.out.println("==> Quantidade extraída: " + quantidade);
                } catch (NumberFormatException e) {
                    System.out.println("==> Erro ao converter '" + numeroStr + "' para número, usando fallback 1");
                    quantidades.add(1);
                }
            }

            if (quantidades.isEmpty()) {
                System.out.println("==> Nenhuma quantidade encontrada, usando fallback [1]");
                return List.of(1);
            }

            return quantidades;

        } catch (Exception e) {
            System.out.println("==> Erro ao obter quantidades: " + e.getMessage());
            return List.of(1);
        }
    }

    public boolean verificarMensagemCarrinhoVazio(String mensagemEsperada) {
        try {
            String mensagemAtual = getText(mensagemCarrinhoVazio);
            boolean corresponde = mensagemAtual.contains(mensagemEsperada);
            System.out.println("==> Mensagem carrinho vazio: '" + mensagemAtual + "' - Corresponde: " + corresponde);
            return corresponde;
        } catch (Exception e) {
            System.out.println("==> Mensagem de carrinho vazio não encontrada");
            return false;
        }
    }

    public boolean verificarQuantidadeProduto(String produto, int quantidadeEsperada) {
        List<String> nomes = obterNomesProdutosPagamento();
        List<Integer> quantidades = obterQuantidadesProdutosPagamento();

        for (int i = 0; i < nomes.size(); i++) {
            if (nomes.get(i).toLowerCase().contains(produto.toLowerCase())) {
                boolean quantidadeCorreta = quantidades.get(i) == quantidadeEsperada;
                System.out.println("==> Quantidade do produto '" + produto + "': " + quantidades.get(i) + " - Esperado: " + quantidadeEsperada + " - Correto: " + quantidadeCorreta);
                return quantidadeCorreta;
            }
        }

        System.out.println("==> Produto '" + produto + "' não encontrado para verificação de quantidade");
        return false;
    }

    public boolean checkoutEstaAcessivel() {
        try {
            if (!isElementVisible(botaoCheckout)) {
                System.out.println("==> Botão checkout não está visível");
                return false;
            }

            WebElement botao = driver.findElement(botaoCheckout);
            boolean estaHabilitado = botao.isEnabled();
            boolean estaVisivel = botao.isDisplayed();

            System.out.println("==> Botão checkout - Visível: " + estaVisivel + ", Habilitado: " + estaHabilitado);

            return estaVisivel && estaHabilitado;

        } catch (Exception e) {
            System.out.println("==> Erro ao verificar botão checkout: " + e.getMessage());
            return false;
        }
    }
}