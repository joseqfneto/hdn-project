# Consulta Api Exchange Rates
Projeto de Api Rest em Java usando Spring Boot para acesso a Api exchangeratesapi.

## Acesso
Acesse o recurso da Api através do endPoint /rates usando o método GET.

## Resposta
Você receberá um objeto com as seguintes propriedades:

currency: Moeda base(neste caso de exemplo, a moeda disponível para uma conta de teste na plataforma é o Euro(EUR)).

requestDay: Dia da requisição.

rates: Objeto moedas em questão(BRL e USD) e seus valores no momento da requisição.

isTimeToBuy: Se é momento de compra baseado na variação média da última semana.

isTimeToSell: Se é momento de venda baseado na variação média da última semana.
