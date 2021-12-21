package com.dbc.walletapi.controller;

import com.dbc.walletapi.dto.LoginCreateDTO;
import com.dbc.walletapi.dto.ServicoDTO;
import com.dbc.walletapi.dto.TypeDTO;
import com.dbc.walletapi.entity.TipoMoeda;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.security.IAuthenticationFacade;
import com.dbc.walletapi.service.ServicoService;
import com.dbc.walletapi.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class GetUserWithCustomInterfaceController {

    @Autowired
    private  IAuthenticationFacade authenticationFacade;
    @Autowired
    private  TypeService typeService;
    @Autowired
    private ServicoService servicoService;

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

    @RequestMapping(value = "/list-servicos-mes-ano", method = RequestMethod.GET)
    @ResponseBody
    public BigDecimal getByAnoEMES(@RequestParam("ano") Integer ano, @RequestParam("mes") Integer mes) throws RegraDeNegocioException {
        Authentication authentication = authenticationFacade.getAuthentication();
        String idUser = authentication.getName();
        return servicoService.listByMesEAno(ano,mes,idUser);
    }

    @RequestMapping(value = "/list-servicos-dolar", method = RequestMethod.GET)
    @ResponseBody
    public BigDecimal getValorDolarOriginal() throws RegraDeNegocioException {
        Authentication authentication = authenticationFacade.getAuthentication();
        String idUser = authentication.getName();
        return servicoService.getValorOriginal(idUser, 1);
    }

    @RequestMapping(value = "/list-servicos-euro", method = RequestMethod.GET)
    @ResponseBody
    public BigDecimal getValorEuroOriginal() throws RegraDeNegocioException {
        Authentication authentication = authenticationFacade.getAuthentication();
        String idUser = authentication.getName();
        return servicoService.getValorOriginal(idUser, 2);
    }

    @RequestMapping(value = "/list-servicos-real", method = RequestMethod.GET)
    @ResponseBody
    public BigDecimal getValorRealOriginal() throws RegraDeNegocioException {
        Authentication authentication = authenticationFacade.getAuthentication();
        String idUser = authentication.getName();
        return servicoService.getValorOriginal(idUser, 0);
    }


}