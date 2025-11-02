# language: pt
@Site
Funcionalidade: Validação de produtos no carrinho na tela de pagamento
  Como usuário do Advantage Online Shopping
  Quero validar os produtos adicionados ao carrinho antes do pagamento
  Para garantir que estou comprando os itens corretos

  Background:
  Dado que estou na página inicial do site

  @Pagamento @CT-09
  Cenário: Carrinho vazio na tela de pagamento
    Quando acesso o carrinho de compras vazio
    Então devo ver a mensagem "Your shopping cart is empty"
    E não devo conseguir acessar o checkout

  @Pagamento @CT-10
  Cenário: Validar produto único no carrinho na tela de pagamento
    Quando realizo a busca pelo produto "HP ELITE X2 1011 G1 TABLET"
    E adiciono o produto ao carrinho
    E acesso o carrinho de compras
    E procedo para o checkout
    Então devo ver o produto "HP ELITE X2 1011 G1 TABLET" na tela de pagamento

  @Pagamento @CT-11
  Cenário: Validar quantidade de produtos no carrinho na tela de pagamento
    Quando realizo a busca pelo produto "HP ROAR WIRELESS SPEAKER"
    E adiciono o produto ao carrinho com quantidade "2"
    E acesso o carrinho de compras
    E procedo para o checkout
    Então devo ver a quantidade "2" para o produto "HP ROAR WIRELESS SPEAKER"

  @Pagamento @CT-12
  Cenário: Validar múltiplos produtos no carrinho na tela de pagamento
    Quando realizo a busca pelo produto "HP Z4000 WIRELESS MOUSE"
    E adiciono o produto ao carrinho
    E realizo a busca pelo produto "HP ENVY - 17T TOUCH LAPTOP"
    E adiciono o produto ao carrinho
    E acesso o carrinho de compras
    E procedo para o checkout
    Então devo ver o produto "HP Z4000 WIRELESS MOUSE" na tela de pagamento
    E devo ver o produto "HP ENVY - 17T TOUCH LAPTOP" na tela de pagamento
