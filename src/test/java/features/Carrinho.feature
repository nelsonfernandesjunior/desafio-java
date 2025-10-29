# language: pt
@Site
Funcionalidade: Adicionar produtos ao carrinho de compras
  Como um cliente do site
  Eu quero adicionar produtos ao carrinho
  Para finalizar minha compra posteriormente

  Background:
  Dado que estou na página inicial do site

  @Carrinho @CT-01
  Cenário: Adicionar um produto ao carrinho a partir da página inicial
    Quando eu seleciono um produto na página inicial
    E eu adiciono o produto ao carrinho
    Então o produto deve aparecer no carrinho

  @Carrinho @CT-02
  Cenário: Adicionar múltiplas unidades do mesmo produto
    Quando eu seleciono um produto na página inicial
    E eu altero a quantidade para 3 unidades
    E eu adiciono o produto ao carrinho
    Então os produtos devem aparecer no carrinho
    E o carrinho deve mostrar 3 unidades do produto

  @Carrinho @CT-03
  Cenário: Adicionar produtos diferentes ao carrinho
    Quando eu seleciono um produto na página inicial
    E eu adiciono o produto ao carrinho
    E eu volto para a página inicial
    E eu adiciono outro produto ao carrinho
    Então o carrinho deve conter 2 produtos diferentes

  @Carrinho @CT-06
  Cenário: Adicionar produto esgotado ao carrinho
    Quando eu seleciono um produto esgotado
    Então verifico que o carrinho está vazio

  @Carrinho @CT-07
  Cenário: Adicionar produto com quantidade máxima
    Quando eu seleciono um produto na página inicial
    E eu adicionar 12 unidades
    E eu adiciono o produto ao carrinho
    Então os produtos devem aparecer no carrinho
    E o carrinho deve mostrar 10 unidades do produto