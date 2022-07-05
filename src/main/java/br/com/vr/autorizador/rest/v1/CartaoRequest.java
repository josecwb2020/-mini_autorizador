package br.com.vr.autorizador.rest.v1;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder=true)
public class CartaoRequest
{
  @JsonProperty("numeroCartao")
  private String numero;

  @JsonProperty("senha")
  private String senha;

  public void validate() throws IllegalArgumentException, NumberFormatException
  {
    if (Objects.isNull(numero) || numero.trim().isEmpty())
    {
      throw new IllegalArgumentException("Número nulo ou inválido");
    }
    if (Objects.isNull(senha) || senha.trim().isEmpty() || senha.trim().length() > 10)
    {
      throw new IllegalArgumentException("Senha nula, inválida ou com tamanho maior que 10");
    }
  }
}
