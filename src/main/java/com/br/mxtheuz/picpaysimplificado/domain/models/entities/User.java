package com.br.mxtheuz.picpaysimplificado.domain.models.entities;

import com.br.mxtheuz.picpaysimplificado.domain.models.enums.UserType;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "user")
@Table(name = "tb_users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String identifier;
    private String email;
    private String password;
    private float balance;
    private UserType type;
}
