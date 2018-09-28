#language: pt

@heroflowupgrade
Funcionalidade:  Hero Flow - Upgrade

COMO usuário avulso da v2
QUERO poder realizar upgrade para assinante
PARA usurfruir dos benefícios de um assinante


Esquema do Cenário: Upgrade de usuário avulso para assinante
Dado que eu esteja logado como um usuario avulso com o CNPJ "<numero_cnpj>"
Quando eu inicio o upgrade de avulso para assinante
  E seleciono o plano "<tipo_plano>"
  E utilizo o cartao "<cartao_numero>"
  E o codigo validador "<cvv>"
  E o nome "<nome_cartao>"
Então eu devo ver a mensagem de sucesso apos termino do upgrade
  E a alteração do header
  E conseguir consumir a franquia com um relatorio "<relatorio_tipo>"


  Exemplos:
    | numero_cnpj | tipo_plano | cartao_numero | cvv | nome_cartao | relatorio_tipo |

