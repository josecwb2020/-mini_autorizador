package br.com.vr.autorizador.rest.v1;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.autorizador.domain.Cartao;
import br.com.vr.autorizador.port.api.CartaoServicePort;
import br.com.vr.autorizador.port.api.CartaoServiceResponse;
import br.com.vr.autorizador.util.CartaoMapper;

@RestController
public class CartaoController
{
  private static final int SC_UNPROCESSABLE_ENTITY = 422;
  @Autowired
  private CartaoServicePort cartaoService;

  @PostMapping("/cartoes")
  public CartaoResponse criarNovoCartao(@RequestBody CartaoRequest pCartaoRequest, HttpServletResponse response) {
    Cartao tCartao = CartaoMapper.toCartao(pCartaoRequest);

    CartaoServiceResponse tResponse = cartaoService.criarCartao(tCartao);

    response.setStatus(tResponse.isOk() ? HttpServletResponse.SC_CREATED : SC_UNPROCESSABLE_ENTITY);

    return CartaoMapper.toCartaoResponse(tResponse);
  }

  @GetMapping("/cartoes/{numeroCartao}")
  public BigDecimal criarNovoCartao(@PathVariable("numeroCartao") String pNumeroCartao, HttpServletResponse response) {

    BigInteger tNumeroCartao = new BigInteger(pNumeroCartao);

    CartaoServiceResponse tResponse = cartaoService.obterSaldoCartao(tNumeroCartao);

    if (tResponse.isOk())
    {
      response.setStatus(HttpServletResponse.SC_OK);
      return tResponse.getCartao().getSaldo();
    }
    else
    {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
  }

  @PostMapping("/transacoes")
  public String realizarTransacao(@RequestBody TransacaoRequest pCartaoRequest, HttpServletResponse response) {

    Cartao tCartao = CartaoMapper.toCartao(pCartaoRequest);
    BigDecimal tValor = new BigDecimal(pCartaoRequest.getValor());

    CartaoServiceResponse tResponse = cartaoService.fazerTransacao(tCartao, tValor);

    response.setStatus(tResponse.isOk() ? HttpServletResponse.SC_CREATED : SC_UNPROCESSABLE_ENTITY);

    return tResponse.getResponseStatus().toString();
  }
}
