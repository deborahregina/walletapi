package com.dbc.walletapi.dto;


import com.dbc.walletapi.entity.TipoStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeDTO {


    @ApiModelProperty(value = "ID do usuário")
    private Integer idUser; // id do usuario
    @ApiModelProperty(value = "ID do gerente (se usuário é um gerente)")
    private Integer idGerente; // se for um gerente, devolve Id dele
    @ApiModelProperty(value = "Nome completo do gerente (se usuário é um gerente)")
    private String nomeCompleto; // se for um gerente, devolve nome completo dele
    @ApiModelProperty(value = "E-mail do gerente (se usuário é um gerente)")
    private String email; // se for um gerente, devolve email dele
    @ApiModelProperty(value = "Login do usuário")
    private String usuario; // login
    @ApiModelProperty(value = "Status do gerente (se usuário é um gerente)")
    private TipoStatus status; // se for um gerente, devolve status dele
    @ApiModelProperty(value = "Lista de serviços atribuída ao gerente (se usuário é um gerente)")
    private List<ServicoDTO> servicoDTOList; // se for um gerente, devolve todos os servicos dele

}
