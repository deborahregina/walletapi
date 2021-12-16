package com.dbc.walletapi.controller;

import com.dbc.walletapi.dto.TypeDTO;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.security.IAuthenticationFacade;
import com.dbc.walletapi.service.TypeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}