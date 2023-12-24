package com.br.mxtheuz.picpaysimplificado.controllers;

import com.br.mxtheuz.picpaysimplificado.domain.models.dto.CreateUserDTO;
import com.br.mxtheuz.picpaysimplificado.domain.models.dto.LoginDTO;
import com.br.mxtheuz.picpaysimplificado.domain.models.dto.Response;
import com.br.mxtheuz.picpaysimplificado.domain.models.entities.User;
import com.br.mxtheuz.picpaysimplificado.domain.models.enums.UserType;
import com.br.mxtheuz.picpaysimplificado.domain.repositories.IUserRepository;
import com.br.mxtheuz.picpaysimplificado.domain.services.IHashService;
import com.br.mxtheuz.picpaysimplificado.domain.services.IJWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private IUserRepository _userRepository;
    private IHashService _hashService;
    private IJWTService _jwtService;

    @Autowired
    public AuthController(IUserRepository userRepository, IHashService hashService, IJWTService jwtService) {
        _userRepository = userRepository;
        _hashService = hashService;
        _jwtService = jwtService;
    }


    @PostMapping("register")
    public ResponseEntity<Response> Register(@RequestBody CreateUserDTO dto) {
        boolean userWithEmail = _userRepository.findByEmail(dto.email()).isPresent();
        boolean userWithIdentifier = _userRepository.findByIdentifier(dto.identifier()).isPresent();

        if(userWithEmail || userWithIdentifier) {
            return ResponseEntity.status(400).body(new Response(400, "user with the provided email or(and) provided identifier already exists"));
        }

        User user = User.builder()
                .name(dto.name())
                .identifier(dto.identifier())
                .email(dto.email())
                .balance(0)
                .password(_hashService.hash(dto.password()))
                .build();

        if(Objects.equals(dto.type().toLowerCase(), "comum")) {
            user.setType(UserType.Common);
        } else {
            user.setType(UserType.Shopkeepe);
        }

        User result = _userRepository.save(user);

        return ResponseEntity.status(201).body(new Response(201, result));
    }

    @PostMapping("login")
    public ResponseEntity<Response> Login(@RequestBody LoginDTO dto) {
        Optional<User> user = _userRepository.findByEmail(dto.email());
        if(user.isEmpty()) {
            return ResponseEntity.status(401).body(new Response(401, "unauthorized"));
        }
        if(_hashService.verify(dto.password(), user.get().getPassword())) {
            return ResponseEntity.status(200).body(new Response(200, _jwtService.createToken(user.get().getId())));
        }
        return ResponseEntity.status(401).body(new Response(401, "unauthorized"));
    }

}
