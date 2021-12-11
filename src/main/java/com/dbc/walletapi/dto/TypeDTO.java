package com.dbc.walletapi.dto;


import com.dbc.walletapi.entity.TipoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeDTO {


    private Integer idUser; // id do usuario
    private Integer idGerente; // se for um gerente, devolve Id dele
    private String nomeCompleto; // se for um gerente, devolve nome completo dele
    private String email; // se for um gerente, devolve email dele
    private String usuario; // login
    private TipoStatus status; // se for um gerente, devolve status dele
    private List<ServicoDTO> servicoDTOList; // se for um gerente, devolve todos os servicos dele

}
