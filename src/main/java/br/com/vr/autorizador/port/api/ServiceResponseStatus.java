package br.com.vr.autorizador.port.api;

public enum ServiceResponseStatus {
  CARTAO_CRIADO,
  CARTAO_EXISTENTE,
  CARTAO_NAO_EXISTE,
  TRANSACAO_OK,
  SALDO_INSUFICIENTE,
  SENHA_INVALIDA,
  CARTAO_INEXISTENTE;
}
