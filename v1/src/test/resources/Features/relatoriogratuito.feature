# language: pt

@gratuito
Funcionalidade: Relatorio Gratuito

  COMO usuário logado que optou por adquirir o relatório gratuito
  QUERO ter uma tela no Serasa Linha
  PARA poder visualizar dados de acordo com minha pesquisa


  @gratuitopf
  Esquema do Cenario: Cenario: Exibição do relatório gratuito de pessoa física
    Dado que eu esteja logado
    E que eu solicite um relatório gratuito de pessoa física com o documento "<numero>"
    #Quando eu observo o header do relatório com o título "<relatorio>"
    #E de tipo "<pessoa>"
    Então eu devo ver o CPF "<cpf>"
    #E o Nome "<nome>"
    #E a Situação cadastral "<situacao cadastral>"
    #E a Data da Consulta "<data consulta>"


     Exemplos:
       |numero 	     |relatorio     	|pessoa          |cpf       	 |nome	           |situacao cadastral	|data consulta |
       |00000000272  |Dados Cadastrais  |Pessoa Fisica   |31861864043    |Gustavo Kuerten  |ATIVO               |Timestamp     |

#@gratuitopj
  #Esquema do Cenario: Exibição do relatório gratuito de pessoa jurídica
   # Dado que eu solicite um relatório gratuito de pessoa juridica com o documento "<numero>"
   # Quando eu observo o header do relatório com a descrição "<relatorio>"
   # Então eu devo ver o CNPJ "<cnpj>"
   # E o tipo de empresa "<matriz ou filial>"
   # E a Razão Social "<razao social>"
   # E a Data de Fundação "<data de fundacao>"
   # E o Nome Fantasia "<nome fantasia>"
   # E a Situação cadastral "<situacao cadastral>"
   # E a Data da Consulta "<data consulta>"
   # E nos dados cadastrais a Razão Social "<razao social>"
   # E o Tipo Empresarial "<tipo empresarial>"
   # E o Nome Fantasia "<nome fantasia>"
   # E a Data de Fundação "<data de fundacao>"
   # E a Natureza Jurídica "<natureza juridica>"
   # E no endereço o Endereço Completo "<endereco completo>"
   # E no CNAE o CNAE Primário "<cnae primario>"
   # E o CNAE Secundário "<cnae secundario>"


    #Exemplos:
    #  |numero         |relatorio                         |cnpj            |matriz ou filial |razao social	                        |data de fundacao |nome fantasia   |situacao cadastral	|data consulta  |razao social	                     |tipo empresarial	|nome fantasia	  |data de fundacao	|natureza juridica	|endereco completo	                    |cnae primario	                                                        |cnae secundario                                                     |
    #  |64858525000199	|Dados Cadastrais Pessoa Jurídica  |64858525000199	|Matriz           |Quiron Agência de Viagens e Turismo	|01032015	      | Quiron Turismo |ATIVO	            | TimeStamp	    |Quiron Agência de Viagens e Turismo |EPP	            |Quiron Turismo	  |15012005	        |Empresa LTDA.	    |Av Republica do Libano, 540, São Paulo	|0303030033 – Lorem ipsum dolor sit ametr Lorem ipsum dolor sit ametr	|0303030033 – Lorem ipsum dolor sit ametr Lorem ipsum dolor sit ametr|












