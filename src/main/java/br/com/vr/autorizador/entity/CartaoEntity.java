package br.com.vr.autorizador.entity;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Cartao")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder=true)
public class CartaoEntity
{
  @Id
  private BigInteger numero;
  private Integer senha;
  private BigDecimal saldo;

}
