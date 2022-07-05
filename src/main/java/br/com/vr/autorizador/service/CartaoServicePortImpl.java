package br.com.vr.autorizador.service;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vr.autorizador.domain.Cartao;
import br.com.vr.autorizador.port.api.CartaoServicePort;
import br.com.vr.autorizador.port.api.CartaoServiceResponse;
import br.com.vr.autorizador.port.api.ServiceResponseStatus;
import br.com.vr.autorizador.port.spi.CartaoPersistencePort;

@Service
public class CartaoServicePortImpl implements CartaoServicePort
{

  public static final BigDecimal SALDO_INICIAL = new BigDecimal("500.00");

  @Autowired
  private CartaoPersistencePort  persistencePort;

  @Override
  @Transactional
  public CartaoServiceResponse criarCartao(Cartao pCartao)
  {
    pCartao.setSaldo(SALDO_INICIAL);
    return persistencePort.recovery(pCartao.getNumero())
            .map(tCartao -> {
              return CartaoServiceResponse.builder()
                      .ok(false)
                      .responseStatus(ServiceResponseStatus.CARTAO_EXISTENTE)
                      .cartao(tCartao)
                      .build();
            })
            .orElse(persistencePort.create(pCartao)
                    .map(tCartao -> {
                      return CartaoServiceResponse.builder()
                              .ok(true)
                              .responseStatus(ServiceResponseStatus.CARTAO_CRIADO)
                              .cartao(tCartao)
                              .build();
                    })
                    .orElse(CartaoServiceResponse.builder()
                            .ok(false)
                            .responseStatus(ServiceResponseStatus.CARTAO_INEXISTENTE)
                            .build()));
  }

  @Override
  public CartaoServiceResponse obterSaldoCartao(BigInteger pNumero)
  {
    return persistencePort.recovery(pNumero)
            .map(tCartao -> {
              return CartaoServiceResponse.builder()
                      .ok(true)
                      .responseStatus(ServiceResponseStatus.CARTAO_EXISTENTE)
                      .cartao(tCartao)
                      .build();
            })
            .orElse(CartaoServiceResponse.builder()
                    .ok(false)
                    .responseStatus(ServiceResponseStatus.CARTAO_INEXISTENTE)
                    .build());
  }

  @Override
  @Transactional
  public CartaoServiceResponse fazerTransacao(Cartao pCartao, BigDecimal pValor)
  {
    return persistencePort.recovery(pCartao.getNumero())
            .map(tCartao -> {
              if (!tCartao.getSenha().equals(pCartao.getSenha()))
              {
                return CartaoServiceResponse.builder()
                        .ok(false)
                        .responseStatus(ServiceResponseStatus.SENHA_INVALIDA)
                        .cartao(tCartao)
                        .build();
              }
              else
              {
                if (tCartao.getSaldo().compareTo(pValor) < 0)
                {
                  return CartaoServiceResponse.builder()
                          .ok(false)
                          .responseStatus(ServiceResponseStatus.SALDO_INSUFICIENTE)
                          .cartao(tCartao)
                          .build();
                }
                else
                {
                  tCartao.setSaldo(tCartao.getSaldo().subtract(pValor));
                  return persistencePort.update(tCartao)
                          .map(tCartaoAtualizado -> {
                            return CartaoServiceResponse.builder()
                                    .ok(true)
                                    .responseStatus(ServiceResponseStatus.TRANSACAO_OK)
                                    .cartao(tCartaoAtualizado)
                                    .build();
                          })
                          .orElse(CartaoServiceResponse.builder()
                                  .ok(false)
                                  .responseStatus(ServiceResponseStatus.CARTAO_INEXISTENTE)
                                  .build());
                }
              }
            })
            .orElse(CartaoServiceResponse.builder()
                    .ok(false)
                    .responseStatus(ServiceResponseStatus.CARTAO_INEXISTENTE)
                    .build());
  }
}
