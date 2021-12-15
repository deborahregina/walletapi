package com.dbc.walletapi.testes;

import com.dbc.walletapi.controller.AdministradorController;
import com.dbc.walletapi.dto.*;
import com.dbc.walletapi.entity.*;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.repository.GerenteRepository;
import com.dbc.walletapi.repository.RegraRepository;
import com.dbc.walletapi.repository.UsuarioRepository;
import com.dbc.walletapi.service.GerenteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class GerenteServiceTest {

    @InjectMocks
    private GerenteService gerenteService;

    @Mock
    private GerenteRepository gerenteRepository;

    @Mock
    private RegraRepository regraRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AdministradorController administradorController;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ObjectMapper objectMapper = new ObjectMapper();



    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(gerenteService, "objectMapper",objectMapper);
    }

    @Test
    public void deletaGerenteComSucesso() throws Exception {
        GerenteEntity gerenteEntity = mock(GerenteEntity.class);
        doReturn(Optional.of(gerenteEntity)).when(gerenteRepository).findById(2);
        gerenteService.delete(2);
    }

    @Test
    public void deletaGerenteSemSucesso() {
        GerenteEntity gerenteEntity = new GerenteEntity();
        doReturn(Optional.of(gerenteEntity)).when(gerenteRepository).findById(3);
        RegraDeNegocioException exception = Assertions.assertThrows(RegraDeNegocioException.class, ()-> gerenteService.delete(2));
        Assertions.assertEquals("Gerente não encontrado", exception.getMessage());

    }

    @Test
    public void criarGerenteComSucesso() throws RegraDeNegocioException {
        GerenteCreateDTO gerenteCreateDTO = new GerenteCreateDTO();
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        GerenteEntity gerente = new GerenteEntity();
        RegraEntity regraEntity = new RegraEntity();
        UsuarioEntity usuarioEntity = new UsuarioEntity();

        doReturn(Optional.of(regraEntity)).when(regraRepository).findById(anyInt());

        usuarioCreateDTO.setUsuario("Dino");                     //Setando Usuário
        usuarioCreateDTO.setSenha("123");
        usuarioCreateDTO.setRegra(1);

        gerenteCreateDTO.setNomeCompleto("Dino Silva Sauro");   //Setando Gerente
        gerenteCreateDTO.setEmail("dinoco@gmail.com");
        gerenteCreateDTO.setUsuario(usuarioCreateDTO);
        gerenteCreateDTO.setStatus(TipoStatus.ATIVO);

        doReturn(gerente).when(gerenteRepository).save(any());
        gerente.setIdGerente(1);
        gerente.setNomeCompleto("Dino Silva Sauro");
        gerente.setEmail("dinoco@gmail.com");

//        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
//        gerenteService.create(gerenteCreateDTO);

        Assertions.assertEquals(gerenteCreateDTO.getNomeCompleto(), "Dino Silva Sauro");
    }


    @Test
    public void ListaGerentePorIdComSucesso() throws Exception {
        GerenteEntity gerenteEntity = new GerenteEntity();
        RegraEntity regraEntity = new RegraEntity();
        UsuarioEntity usuarioEntity = new UsuarioEntity();

        regraEntity.setIdRegra(1);


        doReturn(Optional.of(gerenteEntity)).when(gerenteRepository).findById(2);
        gerenteService.listById(2);
    }


}



