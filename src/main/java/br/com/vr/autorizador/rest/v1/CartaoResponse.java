package br.com.vr.autorizador.rest.v1;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder=true)
public class CartaoResponse
{
  @JsonProperty("numeroCartao")
  private String numero;

  @JsonProperty("senha")
  private String senha;

}
