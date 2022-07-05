package br.com.vr.autorizador.util;

import java.math.BigInteger;

import br.com.vr.autorizador.domain.Cartao;
import br.com.vr.autorizador.entity.CartaoEntity;
import br.com.vr.autorizador.port.api.CartaoServiceResponse;
import br.com.vr.autorizador.rest.v1.CartaoRequest;
import br.com.vr.autorizador.rest.v1.CartaoResponse;
import br.com.vr.autorizador.rest.v1.TransacaoRequest;

public class CartaoMapper
{
  private CartaoMapper()
  {
    throw new IllegalStateException("Classe utilitária");
  }

  public static CartaoEntity toCartaoEntity(Cartao pCartao)
  {
    return CartaoEntity.builder()
            .numero(pCartao.getNumero())
            .senha(pCartao.getSenha())
            .saldo(pCartao.getSaldo())
            .build();
  }

  public static Cartao toCartao(CartaoEntity pCartaoEntity)
  {
    return Cartao.builder()
            .numero(pCartaoEntity.getNumero())
            .senha(pCartaoEntity.getSenha())
            .saldo(pCartaoEntity.getSaldo())
            .build();
  }

  public static Cartao toCartao(CartaoRequest pCartaoRequest)
  {
    pCartaoRequest.validate();

    return Cartao.builder()
            .numero(new BigInteger(pCartaoRequest.getNumero()))
            .senha(Integer.parseInt(pCartaoRequest.getSenha()))
            .build();
  }

  public static CartaoResponse toCartaoResponse(CartaoServiceResponse pCartaoServiceResponse)
  {
    return CartaoResponse.builder()
            .numero(pCartaoServiceResponse.getCartao().getNumero().toString())
            .senha(pCartaoServiceResponse.getCartao().getSenha().toString())
            .build();
  }

  public static Cartao toCartao(TransacaoRequest pTransacaoRequest) throws IllegalArgumentException
  {
    pTransacaoRequest.validate();

    return Cartao.builder()
            .numero(new BigInteger(pTransacaoRequest.getNumero()))
            .senha(Integer.parseInt(pTransacaoRequest.getSenha()))
            .build();
  }
}
