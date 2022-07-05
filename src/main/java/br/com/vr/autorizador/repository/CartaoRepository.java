package br.com.vr.autorizador.repository;

import java.math.BigInteger;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.vr.autorizador.entity.CartaoEntity;

@Repository
public interface CartaoRepository extends JpaRepository<CartaoEntity, UUID>
{
  public CartaoEntity findByNumero(BigInteger pNumero);

  public CartaoEntity deleteByNumero(BigInteger pNumero);
}
