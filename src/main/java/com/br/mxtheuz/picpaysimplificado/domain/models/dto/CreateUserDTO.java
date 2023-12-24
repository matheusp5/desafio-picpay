package com.br.mxtheuz.picpaysimplificado.domain.models.dto;

public record CreateUserDTO(
         String name,
         String identifier,
         String email,
         String password,
         String type
         ) {
}
