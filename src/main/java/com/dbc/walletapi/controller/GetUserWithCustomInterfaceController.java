package com.dbc.walletapi.controller;

import com.dbc.walletapi.dto.LoginCreateDTO;
import com.dbc.walletapi.dto.TypeDTO;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.security.IAuthenticationFacade;
import com.dbc.walletapi.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
public class GetUserWithCustomInterfaceController {

    @Autowired
    private  IAuthenticationFacade authenticationFacade;
    @Autowired
    private  TypeService typeService;

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public TypeDTO currentUserNameSimple() throws RegraDeNegocioException {
        Authentication authentication = authenticationFacade.getAuthentication();
        String idUser = authentication.getName();
        return typeService.list(idUser);
    }

    @RequestMapping(value = "/mudar-senha", method = RequestMethod.PUT,name = "Alterar o login e a senha do usuário que está autenticado no momento")
    @ResponseBody
    public TypeDTO alterarSenhaELoginUsuarioDoAutenticado(@RequestBody @Valid LoginCreateDTO loginDTO) throws RegraDeNegocioException {
        Authentication authentication = authenticationFacade.getAuthentication();
        String idUser = authentication.getName();
        return typeService.alterarSenhaELoginUsuarioDoAutenticado(idUser, loginDTO);
    }
}