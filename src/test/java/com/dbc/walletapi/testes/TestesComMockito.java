package com.dbc.walletapi.testes;

import com.dbc.walletapi.dto.UsuarioCreateDTO;
import com.dbc.walletapi.entity.GerenteEntity;
import com.dbc.walletapi.entity.TipoStatus;
import com.dbc.walletapi.entity.UsuarioEntity;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.repository.GerenteRepository;
import com.dbc.walletapi.repository.UsuarioRepository;
import com.dbc.walletapi.service.GerenteService;
import com.dbc.walletapi.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class TestesComMockito {

    @Mock
    private GerenteService gerenteService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private GerenteRepository gerenteRepository;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUsuario() throws Exception{
        usuarioService = mock(UsuarioService.class);
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        usuarioCreateDTO.setUsuario("oliver");
        usuarioCreateDTO.setSenha("123");
        usuarioCreateDTO.setRegra(2);
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        doReturn(Optional.of(usuarioEntity)).when(usuarioRepository).findById(anyInt());
        usuarioService.create(usuarioCreateDTO);
        verify(usuarioService, Mockito.times(1)).create(usuarioCreateDTO);
    }

    @Test
    public void deletaGerenteComSucesso() throws Exception {       // Aqui pq não tem exception
        GerenteEntity gerenteEntity = new GerenteEntity();
        doReturn(Optional.of(gerenteEntity)).when(gerenteRepository).findById(2);
        gerenteService.delete(2);
        verify(gerenteRepository, times(1)).delete(gerenteEntity);
    }

    @Test
    public void criarUsuarioComSucesso() throws Exception{
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        usuarioService.create(usuarioCreateDTO);
        verify(usuarioService, times(1)).create(usuarioCreateDTO);
    }

    @Test
    public void listarGerentePorIdComSucesso() throws RegraDeNegocioException {   // Aqui pq volta um DTO
        GerenteEntity gerenteEntity = new GerenteEntity();
        gerenteEntity.setIdGerente(2);
        gerenteService.listById(2);
        verify(gerenteService, times(1)).listById(gerenteEntity.getIdGerente());
    }


}
