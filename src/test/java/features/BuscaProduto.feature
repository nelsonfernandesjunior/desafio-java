# language: pt
@Site
Funcionalidade: Busca de produto no site
  Como um usuário do site
  Eu quero buscar um produto
  Para encontrar rapidamente o que preciso comprar

  Background:
  Dado que estou na página inicial do site

  @Busca @Test-01
  Cenário: Busca de produto existente
    Quando eu busco pelo produto existente
    Então devo ver o resultado de busca contendo o produto

  @Busca @Test-02
  Cenário: Busca de produto com múltiplos resultados
    Quando eu busco pelo produto existente com múltiplos resultados
    Então devo ver uma lista com múltiplos produtos

  @Busca @Test-03
  Cenário: Busca de produto inexistente
    Quando eu busco pelo produto inexistente
    Então devo ver a mensagem sem resultado