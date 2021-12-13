package com.dbc.walletapi.security;

import com.dbc.walletapi.entity.GerenteEntity;
import com.dbc.walletapi.entity.TipoStatus;
import com.dbc.walletapi.repository.GerenteRepository;
import com.dbc.walletapi.repository.UsuarioRepository;
import com.dbc.walletapi.service.GerenteService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;

public class CreateGerenteTest {

    @InjectMocks
    private GerenteService gerenteService;

    @Mock
    private GerenteRepository gerenteRepository;

    @Mock
    private UsuarioRepository UsuarioRepository;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testaCreate() {

        GerenteEntity gerente = new GerenteEntity();
        gerente.setStatus(TipoStatus.ATIVO);
        gerente.setEmail("novoemail");

        doReturn(gerente).when(gerenteRepository).save(any());
        //gerenteService.create(gerente);


    }

}
