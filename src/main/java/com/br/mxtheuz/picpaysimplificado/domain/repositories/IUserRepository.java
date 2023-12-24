package com.br.mxtheuz.picpaysimplificado.domain.repositories;

import com.br.mxtheuz.picpaysimplificado.domain.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByIdentifier(String identifier);
}
