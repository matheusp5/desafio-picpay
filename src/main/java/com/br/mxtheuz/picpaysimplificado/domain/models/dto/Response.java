package com.br.mxtheuz.picpaysimplificado.domain.models.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Response {
    private int code;
    private Object content;
}
