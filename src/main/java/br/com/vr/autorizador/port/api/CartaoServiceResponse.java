package br.com.vr.autorizador.port.api;

import br.com.vr.autorizador.domain.Cartao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartaoServiceResponse {
  private boolean ok;
  private ServiceResponseStatus responseStatus;
  private Cartao cartao;
}
