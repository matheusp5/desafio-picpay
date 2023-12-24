package com.br.mxtheuz.picpaysimplificado.domain.services;

public interface IHashService {
    String hash(String password);
    boolean verify(String password, String hashed);
}
