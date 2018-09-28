# language: pt

@personalizarheader
Funcionalidade: Personalizar header de acordo com o tipo de usuário

  COMO Serasa
  QUERO dar uma experiência diferenciada
  PARA assinantes e usuário do avulso

  COMO assinante
  QUERO visualizar o consumo da minha franquia em tempo real
  PARA melhorar controlar e prever o consumo de relatórios



  Cenário: Exibição da palavra franquia para assinante
    Dado que eu esteja logado como assinante
    Quando eu observo o header
    Então eu devo ver a exibição da palavra franquia

  Cenário: Consumo do valor de franquia em tempo real
    Dado que eu esteja logado como assinante
    Quando eu realizo a compra de um relatório
    Então eu devo ver que o consumo da franquia é atualizado em tempo real

  Cenário: Exibição do valor de franquia no formato  R$ 9.999,99
    Dado que eu esteja logado como assinante
    Quando eu observo o header
    Então eu devo ver que o formato da franquia é "R$ 9.999,99"

  Cenário: Exibição de label e formato padrão de saldo no avulso
    Dado que eu esteja logado como avulso
    Quando eu observo o header
    Então eu devo ver a exibição da palavra saldo
    E eu devo ver que o formato do saldo é "R$ 9.999,99"

  Cenário: Não exibição do botão de compra de créditos para assinante
    Dado que eu esteja logado como assinante
    Quando eu observo a área logada
    Então eu devo ver que não é exibido o botão de compra de créditos

  Cenário: Exibição da data de renovação da franquia
    Dado que eu esteja logado como assinante
    Quando eu observo a área logada
    Então eu devo ver a data de renovação da franquia


  Cenário: Exibição da franquia restante
    Dado que eu esteja logado como assinante
    Quando eu observo a área logada
    Então a exibição da franquia restante