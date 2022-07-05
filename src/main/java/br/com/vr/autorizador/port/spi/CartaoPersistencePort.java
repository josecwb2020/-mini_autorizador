package br.com.vr.autorizador.port.spi;

import java.math.BigInteger;
import java.util.Optional;

import br.com.vr.autorizador.domain.Cartao;

public interface CartaoPersistencePort {
  Optional<Cartao> create(Cartao pCartao);
  Optional<Cartao> recovery(BigInteger pNumero);
  Optional<Cartao> update(Cartao pCartao);
  Optional<Cartao> delete(BigInteger pNumero);
}
