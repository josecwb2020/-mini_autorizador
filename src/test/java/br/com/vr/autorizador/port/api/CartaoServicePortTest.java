package br.com.vr.autorizador.port.api;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.vr.autorizador.domain.Cartao;
import br.com.vr.autorizador.port.spi.CartaoPersistencePort;
import br.com.vr.autorizador.service.CartaoServicePortImpl;

@ExtendWith(MockitoExtension.class)
public class CartaoServicePortTest
{
  @Mock
  CartaoPersistencePort         persistencePort;

  @InjectMocks
  private CartaoServicePortImpl cartaoServicePort;

  private Cartao                cartao;

  @BeforeEach
  public void initilialize()
  {
    cartao = Cartao.builder()
            .numero(new BigInteger("6549873025634501"))
            .senha(Integer.parseInt("123456"))
            .saldo(new BigDecimal("500.00"))
            .build();
  }

  @Test
  public void deveCriarCartao()
  {
    Mockito.when(persistencePort.create(ArgumentMatchers.eq(cartao))).thenReturn(Optional.of(cartao));

    CartaoServiceResponse response = cartaoServicePort.criarCartao(cartao);

    Assertions.assertTrue(response.isOk());
    Assertions.assertEquals(response.getResponseStatus(), ServiceResponseStatus.CARTAO_CRIADO);
    Assertions.assertEquals(response.getCartao(), cartao);
  }

  @Test
  public void naoDeveCriarCartaoJaExistente()
  {
    Mockito.when(persistencePort.recovery(ArgumentMatchers.eq(cartao.getNumero()))).thenReturn(Optional.of(cartao));

    CartaoServiceResponse response = cartaoServicePort.criarCartao(cartao);

    Assertions.assertFalse(response.isOk());
    Assertions.assertEquals(response.getResponseStatus(), ServiceResponseStatus.CARTAO_EXISTENTE);
    Assertions.assertEquals(response.getCartao(), cartao);
  }

  @Test
  public void deveObterSaldoCartaoExistente()
  {
    Mockito.when(persistencePort.recovery(ArgumentMatchers.eq(cartao.getNumero()))).thenReturn(Optional.of(cartao));

    CartaoServiceResponse response = cartaoServicePort.obterSaldoCartao(cartao.getNumero());

    Assertions.assertTrue(response.isOk());
    Assertions.assertEquals(response.getResponseStatus(), ServiceResponseStatus.CARTAO_EXISTENTE);
    Assertions.assertEquals(response.getCartao(), cartao);
  }

  @Test
  public void naoDeveObterSaldoCartaoInexistente()
  {
    Mockito.when(persistencePort.recovery(ArgumentMatchers.eq(cartao.getNumero()))).thenReturn(Optional.empty());

    CartaoServiceResponse response = cartaoServicePort.obterSaldoCartao(cartao.getNumero());

    Assertions.assertFalse(response.isOk());
    Assertions.assertEquals(response.getResponseStatus(), ServiceResponseStatus.CARTAO_INEXISTENTE);
    Assertions.assertNull(response.getCartao());
  }

  @Test
  public void naoDeveFazerTransacaoSenhaInvalida()
  {
    Cartao cartaoTransacao = cartao.toBuilder()
            .senha(Integer.parseInt("654321"))
            .build();

    Mockito.when(persistencePort.recovery(ArgumentMatchers.eq(cartao.getNumero()))).thenReturn(Optional.of(cartao));

    CartaoServiceResponse response = cartaoServicePort.fazerTransacao(cartaoTransacao, new BigDecimal("10.00"));

    Assertions.assertFalse(response.isOk());
    Assertions.assertEquals(response.getResponseStatus(), ServiceResponseStatus.SENHA_INVALIDA);
    Assertions.assertEquals(response.getCartao(), cartao);
  }

  @Test
  public void naoDeveFazerTransacaoCartaoInexistente()
  {
    Mockito.when(persistencePort.recovery(ArgumentMatchers.eq(cartao.getNumero()))).thenReturn(Optional.empty());

    CartaoServiceResponse response = cartaoServicePort.fazerTransacao(cartao, new BigDecimal("10.00"));

    Assertions.assertFalse(response.isOk());
    Assertions.assertEquals(response.getResponseStatus(), ServiceResponseStatus.CARTAO_INEXISTENTE);
    Assertions.assertNull(response.getCartao());
  }

  @Test
  public void naoDeveFazerTransacaoSaldoUnsuficiente()
  {
    Mockito.when(persistencePort.recovery(ArgumentMatchers.eq(cartao.getNumero()))).thenReturn(Optional.of(cartao));

    CartaoServiceResponse response = cartaoServicePort.fazerTransacao(cartao, cartao.getSaldo().add(new BigDecimal("0.01")));

    Assertions.assertFalse(response.isOk());
    Assertions.assertEquals(response.getResponseStatus(), ServiceResponseStatus.SALDO_INSUFICIENTE);
    Assertions.assertEquals(response.getCartao(), cartao);
  }

  @Test
  public void deveFazerTransacaoOk()
  {
    Cartao cartaoTransacao = cartao.toBuilder()
            .saldo(cartao.getSaldo().add(new BigDecimal("0.01")))
            .build();

    Mockito.when(persistencePort.recovery(ArgumentMatchers.eq(cartao.getNumero()))).thenReturn(Optional.of(cartao));
    Mockito.when(persistencePort.update(ArgumentMatchers.any())).thenReturn(Optional.of(cartaoTransacao));

    CartaoServiceResponse response = cartaoServicePort.fazerTransacao(cartao, cartao.getSaldo().subtract(new BigDecimal("0.01")));

    Assertions.assertTrue(response.isOk());
    Assertions.assertEquals(response.getResponseStatus(), ServiceResponseStatus.TRANSACAO_OK);
    Assertions.assertEquals(response.getCartao(), cartaoTransacao);
  }
}
