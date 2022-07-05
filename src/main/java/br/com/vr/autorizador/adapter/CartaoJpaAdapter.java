package br.com.vr.autorizador.adapter;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vr.autorizador.domain.Cartao;
import br.com.vr.autorizador.entity.CartaoEntity;
import br.com.vr.autorizador.port.spi.CartaoPersistencePort;
import br.com.vr.autorizador.repository.CartaoRepository;
import br.com.vr.autorizador.util.CartaoMapper;

@Service
public class CartaoJpaAdapter implements CartaoPersistencePort
{
  @Autowired
  private CartaoRepository cartaoRepository;

  @Override
  public Optional<Cartao> create(Cartao pCartao)
  {
    CartaoEntity tCartaoEntity = CartaoMapper.toCartaoEntity(pCartao);
    return Optional.of(CartaoMapper.toCartao(cartaoRepository.save(tCartaoEntity)));
  }

  @Override
  public Optional<Cartao> recovery(BigInteger pNumero)
  {
    CartaoEntity tCartaoEntity = cartaoRepository.findByNumero(pNumero);
    return tCartaoEntity == null ? Optional.empty() : Optional.of(CartaoMapper.toCartao(tCartaoEntity));
  }

  @Override
  public Optional<Cartao> update(Cartao pCartao)
  {
    CartaoEntity tCartaoEntity = CartaoMapper.toCartaoEntity(pCartao);
    return Optional.of(CartaoMapper.toCartao(cartaoRepository.save(tCartaoEntity)));
  }

  @Override
  public Optional<Cartao> delete(BigInteger pNumero)
  {
    return Optional.of(CartaoMapper.toCartao(cartaoRepository.deleteByNumero(pNumero)));
  }

}
