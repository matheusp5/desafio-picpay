package com.br.mxtheuz.picpaysimplificado.domain.services;

public interface IJWTService {
    String createToken(String uuid);
    String decodeToken(String token);
}
