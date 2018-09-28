# language: pt

@botaonovaconsulta
Funcionalidade: Botão Nova Consulta

  COMO usuário logado
  QUERO ter um botão Nova Consulta
  PARA realizar novas consultas

@titulodorelatorio
  Esquema do Cenário: Título do relatório
    Dado que eu realize a consulta do documento "<numero>"
    E gere um relatório "<tipo>"
    Quando eu observo o cabeçalho do relatório
    Então eu devo ver que o título "<descricao>" é apresentado

    Exemplos:
    |numero         |tipo         |descricao                               |
    |46444274036    |gratuito     |Relatorio Gratuito Pessoa Fisica        |
    |67894418000160 |gratuito     |Relatorio Gratuito Pessoa Juridica      |
    |46444274036    |basico       |Relatorio Basico Pessoa Fisica          |
    |67894418000160 |basico       |Relatorio Basico Pessoa Juridica        |
    |46444274036    |intermediario|Relatorio Intermediario Pessoa Fisica   |
    |67894418000160 |intermediario|Relatorio Intermediario Pessoa Juridica |
    |46444274036    |completo     |Relatorio Completo Pessoa Fisica        |
    |67894418000160 |completo     |Relatorio Completo Pessoa Juridica      |

    #|numero    |tipo         |descricao                               |
    #|cpf       |gratuito     |Relatorio Gratuito Pessoa Fisica        |
    #|cnpj      |gratuito     |Relatorio Gratuito Pessoa Juridica      |
    #|cpf       |basico       |Relatorio Basico Pessoa Fisica          |
    #|cnpj      |basico       |Relatorio Basico Pessoa Juridica        |
    #|cpf       |intermediario|Relatorio Intermediario Pessoa Fisica   |
    #|cnpj      |intermediario|Relatorio Intermediario Pessoa Juridica |
    #|cpf       |completo     |Relatorio Completo Pessoa Fisica        |




