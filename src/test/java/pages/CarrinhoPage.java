package pages;

import functions.WebDriverFactory;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CarrinhoPage extends BasePage {

    private By grupoSpeakers = By.id("speakersImg");
    private By wirelessSpeaker = By.id("22");
    private By adicionarCarrinho = By.name("save_to_cart");
    private By botaoCheckOut = By.id("checkOutButton");
    private By iconeCarrinho = By.id("menuCart");
    private By adicionarMaisQuantidade = By.xpath("//div[@class='plus']");
    private By quantidadeProduto = By.name("quantity");
//    private By qtdeProdutoCarrinho = By.xpath("//td[@class='smollCell quantityMobile']");
    private By qtdeProdutoCarrinho = By.xpath("//label[@class='roboto-regular ng-binding']");
    private By grupoTablets = By.id("tabletsImg");
    private By proTablet = By.id("18");
    private By botaoHome = By.className("logo");
    private By grupoHeadPhones = By.id("headphonesImg");
    private By boseSoundLink = By.id("13");
    private By botaoAddToCart = By.xpath("//button[@name='save_to_cart']");
    private By totalItensCarrinho = By.xpath("//label[@class='roboto-regular ng-binding']");
    private By textoCarrinhoVazio = By.id("shoppingCart");
    private By soldOutMessage = By.xpath("//button[contains(@class,'disabled')]//span[contains(text(),'SOLD OUT')]");
    private By successMessage = By.cssSelector(".successMessage");

    private By botaoRemove = By.cssSelector("a.remove.red");

    private static final String PRODUTO_1 = "HP ROAR WIRELESS SPEAKER";
    private static final String PRODUTO_2 = "BOMBIZINI GUZINI";
    private static final String PRODUTO_3 = "HP PRO TABLET 608 G1";
    private static final String PRODUTO_4 = "BOSE SOUNDLINK AROUND-EAR WIRELESS HEADPHONES II";

    public CarrinhoPage() {
        super();
    }

    public void selecionarProdutoNaPaginaInicial() throws InterruptedException {
        System.out.println("==> Garantir que está na página inicial");
        garantirQueEstaNaPaginaInicial();
        System.out.println("==> Selecionando produto na página inicial");

        garantirQueEstaNaPaginaInicial();
        click(grupoSpeakers);
        waitForElementVisible(wirelessSpeaker, 4);
        scrollToElement(wirelessSpeaker);
        click(wirelessSpeaker);
    }

    public void adicionarProdutoAoCarrinho() {
        System.out.println("==> Adicionando produto ao carrinho");
        click(adicionarCarrinho);

        try {
            waitForElementVisible(successMessage, 3);
            System.out.println("==> Produto adicionado ao carrinho com sucesso");
        } catch (Exception e) {
            System.out.println("==>  Mensagem de sucesso não apareceu, mas continuando...");
        }
    }

    public void verificarProdutoNoCarrinho() {
        System.out.println("==> Verificando produto no carrinho");
        verificarProdutoNoCarrinhoPorNome(PRODUTO_1);
        removerProdutoDoCarrinho();
    }

    public void verificarOsProdutosNoCarrinho() {
        System.out.println("==> Verificando produto no carrinho");
        verificarProdutoNoCarrinhoPorNome(PRODUTO_1);
    }

    public boolean verificarProdutoNoCarrinhoPorNome(String nomeProduto) {
        System.out.println("==> Verificando produto '" + nomeProduto + "' no carrinho");
        acessarCarrinho();
        waitForElementVisible(botaoCheckOut, 10);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.findElement(By.id("shoppingCart")).getText().contains(nomeProduto));

        Assert.assertTrue(
                "==> Produto '" + nomeProduto + "' não encontrado no carrinho",
                driver.findElement(By.tagName("body")).getText().contains(nomeProduto)
        );

        System.out.println("==> Produto '" + nomeProduto + "' verificado no carrinho");
        return false;
    }

    public void adicionarQuantidade() {
        waitForElementVisible(adicionarMaisQuantidade, 10);
        click(adicionarMaisQuantidade);
    }

    public void alterarQuantidade(int quantidade) {
        System.out.println("==> Alterando quantidade para: " + quantidade);
        waitForElementVisible(quantidadeProduto, 5);
        WebElement quantityElement = driver.findElement(quantidadeProduto);
        click(quantidadeProduto);
        limparCampo(quantidadeProduto);
        quantityElement.sendKeys(String.valueOf(quantidade));
        System.out.println("==> Quantidade alterada para " + quantidade);
    }

    public void verificarQuantidadeNoCarrinho(int quantidadeEsperada) {
        System.out.println("==> Verificando quantidade no carrinho: " + quantidadeEsperada);
        System.out.println("==> Quantidade esperada: " + quantidadeEsperada);

        int quantidadeAtual = obterQuantidadeDoProduto(PRODUTO_1);
        System.out.println("==> Conteúdo capturado QUANTIDADE DO PRODUTO [quantidadeAtual] depois: " + quantidadeAtual);

        Assert.assertEquals("==> Quantidade incorreta no carrinho", quantidadeEsperada, quantidadeAtual);
        System.out.println("==> Quantidade verificada: " + quantidadeAtual);
        removerProdutoDoCarrinho();
    }

    public void voltarParaPaginaInicial() {
        System.out.println("==> Voltando para página inicial");
        click(botaoHome);
        waitForElementVisible(By.className("logo"), 5);
    }

    public void selecionarSegundoProduto() throws InterruptedException {
        click(grupoTablets);
        waitForElementVisible(proTablet, 4);
        scrollToElement(proTablet);
        click(proTablet);
    }

    public void verificarDoisProdutosNoCarrinho() {
        System.out.println(" ==>Verificando 2 produtos no carrinho");
        acessarCarrinho();
        waitForElementVisible(totalItensCarrinho, 3);

        String textoTotalItensCarrinho = driver.findElement(totalItensCarrinho).getText();
        System.out.println("**************************");
        System.out.println(textoTotalItensCarrinho);
        Assert.assertTrue("2 itens encontrados: ", textoTotalItensCarrinho.contains("(2 Items)"));

        System.out.println("==> 2 produtos verificados no carrinho");
        removerProdutoDoCarrinho();
        removerProdutoDoCarrinho();
    }

    public void acessarCarrinho() {
        System.out.println("==> Acessando carrinho de compras");
        click(iconeCarrinho);
        System.out.println("==> Carrinho acessado");
    }

    public int obterQuantidadeDoProduto(String nomeProduto) {
        System.out.println("==> Buscando quantidade do produto: " + nomeProduto);

        String textoQuantidade = getText(qtdeProdutoCarrinho);
        System.out.println("==> Conteúdo capturado QUANTIDADE DO PRODUTO: " + textoQuantidade);

        try {
            // Extrair apenas os números do texto
            String apenasNumeros = textoQuantidade.replaceAll("[^0-9]", "");
            System.out.println("==> Apenas números extraídos: '" + apenasNumeros + "'");

            if (apenasNumeros.isEmpty()) {
                System.out.println("==> Nenhum número encontrado no texto");
                return 0;
            }

            int quantidade = Integer.parseInt(apenasNumeros);
            System.out.println("==> Quantidade convertida: " + quantidade);
            return quantidade;

        } catch (NumberFormatException e) {
            System.out.println("==> Erro ao converter quantidade: '" + textoQuantidade + "' - " + e.getMessage());
            return 0;
        }
    }

    public boolean verificarBotaoAddToCartDesabilitado() {
        try {
            WebElement button = driver.findElement(botaoAddToCart);
            return !button.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verificarMensagemSoldOut() {
        return isElementVisible(soldOutMessage);
    }

    public void selecionarProdutoEsgotado() {
        garantirQueEstaNaPaginaInicial();
        System.out.println("==> Selecionando produto esgotado na página inicial");

        click(grupoHeadPhones);
        waitForElementVisible(boseSoundLink, 4);
        scrollToElement(boseSoundLink);
        click(boseSoundLink);
        isElementVisible(adicionarCarrinho);
        isElementClickable(adicionarCarrinho);
        Assert.assertFalse("Botão 'Adicionar ao Carrinho' deveria NÃO estar clicável para produto esgotado",
                isElementClickable(adicionarCarrinho));

        System.out.println("==> Produto esgotado - botão visível mas não clicável");
        acessarCarrinho();
    }

    public void verificarCarrinhoVazio() {
        System.out.println("==> Verificando se o carrinho está vazio");
        acessarCarrinho();

        String carrinhoTexto = driver.findElement(textoCarrinhoVazio).getText();
        Assert.assertTrue("==> O carrinho não está vazio", carrinhoTexto.contains("Your shopping cart is empty"));

        System.out.println("==> Carrinho vazio verificado");
    }

    public void removerProdutoDoCarrinho() {
        click(botaoRemove);

    }
}