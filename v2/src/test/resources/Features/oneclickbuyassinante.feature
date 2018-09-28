# language: pt

@oneclickbuyassinante
Funcionalidade: One Click Buy Assinante

  COMO assinante
  QUERO continuar consumindo relatórios de forma rápida e prática quando minha fatura acabar
  PARA não bloquear minha experiência no consumo de relatórios e análise dos meus negócios


  Cenário: Apresentação do botão de 1-Click Buy com a franquia zerada
    Dado que eu esteja logado como usuário assinante
    E que esteja com a franquia zerada
    Quando eu tento realizar a compra de um novo relatório
    Então eu devo ver a opção de one click buy para assinante

  Cenário: Informação ao usuário assinante de compra via 1-Click buy
    Dado que eu esteja logado como usuário assinante
    Quando eu tento realizar uma compra de um relatório via One Click buy
    Então eu devo ver a informação de que as consultas serão cobradas direto no cartão cadastrado

  Cenário: Marcação 1-Click Buy no histórico do assinante
    Dado que eu esteja logado como usuário assinante
    Quando eu realizo uma compra de relatório via One Click buy
    Então eu devo ver no histórico do assinante a compra com o devido formato

  Esquema do Cenário: Apresentação do relatório para assinante em compra via 1-Click buy
    Dado que eu esteja logado como usuário assinante
    Quando eu pesquiso o documento "<numero>" para compra One Click buy assinante
    Então eu devo ver que o relatório foi apresentado corretamente para o assinante

    Exemplos:
      |numero           |
      |00000000272      |
      |00000000191      |
      |33000167000101   |
      |62173620000180   |


    #Dado que eu esteja logado como usuário avulso
    #Quando eu pesquiso o documento "<numero>"
    #E realizo uma compra via One Click buy de um relatório "<tipo>"
    #Então eu devo ver que o relatório foi apresentado corretamente com a descrição "<titulo>"
    #E com a natureza "<pessoa>"

    #Exemplos:
     # |numero           |tipo             |titulo                  |pessoa          |
     # |00000000272      |basico           |Relatório Básico        |Pessoa Física   |
     # |00000000272      |intermediario    |Relatório Intermediário |Pessoa Física   |
     # |00000000272      |completo         |Relatório Completo      |Pessoa Física   |
     # |33000167000101   |basico           |Relatório Básico        |Pessoa Jurídica |
     # |33000167000101   |intermediario    |Relatório Intermediário |Pessoa Jurídica |
     # |33000167000101   |completo         |Relatório Completo      |Pessoa Jurídica |


