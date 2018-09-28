# language: pt

@arealogada
Funcionalidade: Área Logada

  COMO usuário logado
  QUERO inserir o documento CPF ou CNPJ
  PARA ver as opções de relatórios disponíveis, seu nome, preço e atributos e após selecionar o saldo atual, preço do relatório e saldo final


  Cenário: Mensagem de boas vindas de acordo com o horário
    Dado que eu realize login
    Quando eu observo o cabeçalho da área logada
    Então eu devo ver a mensagem de boas vindas de acordo com o horário

  Cenário: Máscara de CPF
    Dado que eu esteja na área logada
    Quando eu insiro um CPF
    Então eu devo ver que a máscara de CPF é aplicada

  Cenário: Máscara de CNPJ
    Dado que eu esteja na área logada
    Quando eu insiro um CNPJ
    Então eu devo ver que a máscara de CNPJ é aplicada

  Cenário: Validação de CPF
    Dado que eu esteja na área logada
    Quando eu realizo uma busca de um CPF pelo ícone de lupa
    Então eu devo ver que o CPF é validado pela quantidade de caracteres
    E se é um CPF válido

  Cenário: Validação de CNPJ
    Dado que eu esteja na área logada
    Quando eu realizo uma busca de um CNPJ pelo ícone de lupa
    Então eu devo ver que o CNPJ é validado pela quantidade de caracteres
    E se é um CNPJ válido

  Cenário: Mensagem de CPF inválido
    Dado que eu esteja na área logada
    Quando eu realizo uma busca com um CPF inválido
    Então eu devo ver uma mensagem de documento incorreto

  Cenário: Mensagem de CNPJ inválido
    Dado que eu esteja na área logada
    Quando eu realizo uma busca com um CNPJ inválido
    Então eu devo ver uma mensagem de documento incorreto

  Cenário: Busca por CPF válido
    Dado que eu esteja na área logada
    Quando eu realizo uma busca com um CPF válido
    Então eu devo ver o Nome Completo

  Cenário: Busca por CNPJ válido
    Dado que eu esteja na área logada
    Quando eu realizo uma busca com um CNPJ válido
    Então eu devo ver o Nome Fantasia
    E a Razão Social

  Cenário: Listagem dos relatórios por CPF válido
    Dado que eu esteja na área logada
    Quando eu realizo uma busca com um CPF válido
    Então eu devo ver a listagem dos relatórios disponíveis

  Cenário: Listagem dos relatórios por CNPJ válido
    Dado que eu esteja na área logada
    Quando eu realizo uma busca com um CNPJ válido
    Então eu devo ver a listagem dos relatórios disponíveis

  Cenário: Informação ao usuário de melhor relatório ao selecionar básico
    Dado que eu esteja na área logada
    Quando eu seleciono o relatório básico
    Então eu devo ver uma sugestão para mudar para o relatório intermediário

  Cenário: Informação ao usuário de melhor relatório ao selecionar intermediário
    Dado que eu esteja na área logada
    Quando eu seleciono o relatório intermediário
    Então eu devo ver uma sugestão para mudar para o relatório completo

  Esquema do Cenário: Exibição de informações ao selecionar relatório
    Dado que eu esteja na área logada
    Quando eu confirmo a escolha de relatório
    Então eu devo ver a exibição do "<dado>"
  Exemplos:
  |dado                 |
  |label saldo atual    |
  |valor saldo atual    |
  |label nome relatorio |
  |valor sinal negativo |
  |label saldo final    |
  |valor saldo final    |
  |botao gerar relatorio|

  Cenário: Gerar relatório
    Dado que eu esteja na área logada
    Quando eu confirmo a seleção do tipo de relatório
    E aciono o botão Gerar Relatório
    Então eu devo ser direcionado a página do relatório do documento consultado

