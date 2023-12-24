package com.br.mxtheuz.picpaysimplificado.controllers;

import com.br.mxtheuz.picpaysimplificado.domain.models.dto.CreateTransactionDTO;
import com.br.mxtheuz.picpaysimplificado.domain.models.dto.Response;
import com.br.mxtheuz.picpaysimplificado.domain.models.entities.User;
import com.br.mxtheuz.picpaysimplificado.domain.models.enums.UserType;
import com.br.mxtheuz.picpaysimplificado.domain.repositories.IUserRepository;
import com.br.mxtheuz.picpaysimplificado.domain.services.IJWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/transaction")
public class TransactionController {

    private IJWTService _jwtService;
    private IUserRepository _userRepository;

    @Autowired
    public TransactionController(IJWTService jwtService, IUserRepository userRepository) {
        _jwtService = jwtService;
        _userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Response> CreateTransaction(@RequestBody CreateTransactionDTO dto, @RequestHeader("Authorization") String bearer){
        try {
            String userId = _jwtService.decodeToken(bearer.replace("Bearer ", ""));
            Optional<User> payer = _userRepository.findById(userId);
            Optional<User> payee = _userRepository.findByEmail(dto.payee());
            if(payee.isEmpty() || payer.isEmpty()) {
                return ResponseEntity.status(400).body(new Response(400, "invalid users"));
            }
            if(dto.amount() <= 0) {
                return ResponseEntity.status(400).body(new Response(400, "amount must be higher than 0"));
            }
            if(payer.get().getBalance() < dto.amount()) {
                return ResponseEntity.status(400).body(new Response(400, "invalid amount"));
            }
            if(payer.get().getType() == UserType.Shopkeepe) {
                return ResponseEntity.status(400).body(new Response(400, "shopkeep only can receive money"));
            }

            payer.get().setBalance(payer.get().getBalance() - dto.amount());
            payee.get().setBalance(payee.get().getBalance() + dto.amount());
            _userRepository.save(payee.get());
            _userRepository.save(payer.get());

            return ResponseEntity.status(200).body(new Response(200, "success"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, "internal error"));
        }
    }
}
