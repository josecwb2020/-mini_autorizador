package br.com.vr.autorizador.port.api;

import java.math.BigDecimal;
import java.math.BigInteger;

import br.com.vr.autorizador.domain.Cartao;

public interface CartaoServicePort {
  CartaoServiceResponse criarCartao(Cartao pCartao);
  CartaoServiceResponse obterSaldoCartao(BigInteger pNumero);
  CartaoServiceResponse fazerTransacao(Cartao pCartao, BigDecimal pValor);
}
