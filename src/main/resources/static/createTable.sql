DROP TABLE cartao;

CREATE TABLE cartao (
	NUMERO DECIMAL(16)   NOT NULL,
	SENHA  DECIMAL (8)   NOT NULL,
	SALDO  DECIMAL(14,2) NOT NULL,
	PRIMARY KEY(NUMERO)
)