package br.com.vr.autorizador.domain;

import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder=true)
public class Cartao {
  private BigInteger numero;
  private Integer senha;
  private BigDecimal saldo;
}
