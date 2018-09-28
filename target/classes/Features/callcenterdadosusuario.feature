# language: pt

@callcenterdadosusuario
Funcionalidade:  Call Center - Visualizar Dados do Usuário

  COMO atendente do call center
  QUERO visulizar os dados do usuário
  PARA melhorar a performance do meu atendimento


  Cenário: Exibir customer ID, Order ID e Tipo de pedido na tela de histórico
    Dado que eu esteja logado como atendente do Call Center
    Quando eu realizo a consulta dos dados de um usuário
    Então eu devo ver o customer ID
    E o Order ID
    E o tipo de pedido na tela de histórico

  Cenário: Exibir as informações retornadas em português
    Dado que eu esteja logado como atendente do Call Center
    Quando eu realizo a consulta dos dados de um usuário no histórico
    Então eu devo ver que as informações retornadas estejam todas em português
