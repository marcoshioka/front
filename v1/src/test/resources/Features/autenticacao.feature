# language: pt

@autenticacao
Funcionalidade: Autenticação

  COMO usuário navegando pelo Serasa Linha
  QUERO um botão "Entrar" no header do site
  PARA que quando o mesmo for acionado, eu consiga informar e-mail e senha e assim ter acesso à minha área logada.

  @botaoentrar
  Cenario: Botão Entrar no header do site
    Dado que eu esteja navegando no Serasa Linha
    Quando eu visualizo o header do site
    Então eu devo ver o botão entrar

  @modalautenticacao
  Esquema do Cenario: Modal de autenticação
    Dado que eu esteja navegando no Serasa Linha
    Quando eu aciono o botão Entrar no header do site
    Então eu devo ver um modal com o "<campo>"
    Exemplos:
      |campo              |
      |usuario (e-mail)   |
      |senha              |
      |visualizar senha   |
      |esqueci minha senha|
      |botao Acessar      |
      |fechar o modal     |

  @visualsenha
  Cenario: Ícone de visualização de senha
    Dado que eu acionei o botão Entrar no Serasa Linha
    Quando eu aciono o ícone de visualização de senha no modal
    Então eu devo ver os caracteres que forem digitados nesse campo

  @esquecisenha
  Cenario: Link Esqueci minha senha
    Dado que eu acionei o botão Entrar no Serasa Linha
    Quando eu aciono o link Esqueci minha senha no modal
    Então eu devo ver um modal onde digite o e-mail do cadastro
    E receba um e-mail contendo o link para registro de nova senha

  @loginsemsucesso
  Esquema do Cenario: Login sem sucesso
    Dado que eu acionei o botão Entrar no Serasa Linha
    Quando eu utilizo um usuário "<email>" no modal
    E uma senha "<password>"
    E aciono o botão Acessar
    Então eu devo ver a mensagem "<texto>"
    Exemplos:

      #|email      |password  |texto                                                    |
      #|vazio      |correta   |Dados incorretos. Por favor verifique e tente novamente. |
      #|incompleto |correta   |Dados incorretos. Por favor verifique e tente novamente. |
      #|correto    |vazia     |Dados incorretos. Por favor verifique e tente novamente. |
      #|correto    |incorreta |Dados incorretos. Por favor verifique e tente novamente. |
      #|vazio      |vazia     |Dados incorretos. Por favor verifique e tente novamente. |
      #|incompleto |incorreta |Dados incorretos. Por favor verifique e tente novamente. |

      |email                   |password  |texto                                                    |
      |                        |#Teste123 |Dados incorretos. Por favor verifique e tente novamente. |
      |marcos.tes@gm.co        |#Teste123 |Dados incorretos. Por favor verifique e tente novamente. |
      |marcos.tester@gmail.com |          |Dados incorretos. Por favor verifique e tente novamente. |
      |marcos.tester@gmail.com |#Teste    |Dados incorretos. Por favor verifique e tente novamente. |
      |                        |          |Dados incorretos. Por favor verifique e tente novamente. |
      |marcos.tes@gm.co        |#Teste    |Dados incorretos. Por favor verifique e tente novamente. |


  @logincomsucesso
  Cenario: Login com sucesso
    Dado que eu acionei o botão Entrar no Serasa Linha
    Quando eu preencho o campo usuário corretamente no modal
    E o campo de senha também corretamente
    E aciono o botão Acessar
    Então eu devo ser direcionado para a área logada

    @loginindisponivel
  Cenario: Indisponibilidade de serviço de login
    Dado que eu acionei o botão Entrar no Serasa Linha
    Quando eu preencho o campo usuário corretamente no modal
    E o campo de senha também corretamente
    E aciono o botão Acessar
    Mas o serviço retorne algo diferente de sucesso/ insucesso
    Então eu devo ver a mensagem de alerta Ops! Serviço indisponível no momento. Tente novamente mais tarde.



