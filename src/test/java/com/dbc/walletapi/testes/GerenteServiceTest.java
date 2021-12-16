package com.dbc.walletapi.testes;

import com.dbc.walletapi.controller.AdministradorController;
import com.dbc.walletapi.dto.*;
import com.dbc.walletapi.entity.*;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.repository.GerenteRepository;
import com.dbc.walletapi.repository.RegraRepository;
import com.dbc.walletapi.repository.UsuarioRepository;
import com.dbc.walletapi.service.GerenteService;
import com.dbc.walletapi.service.ServicoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
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
    private ServicoService servicoService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Before
    public void init() {
        ReflectionTestUtils.setField(gerenteService, "objectMapper",objectMapper);
    }

    @Test
    public void deletaGerenteComSucessoIdEncontradoServicosInativos() throws Exception {
        UsuarioEntity usuario = new UsuarioEntity();
        GerenteEntity gerenteEntity = new GerenteEntity();
        List<ServicoEntity> listaServicos = new ArrayList<>();


        gerenteEntity.setServicos(listaServicos);
        usuario.setUsuario("usuario");
        usuario.setIdUsuario(1);
        usuario.setStatus(TipoStatus.ATIVO);
        usuario.setIdUsuario(3);
        gerenteEntity.setUsuario(usuario);

        doReturn(Optional.of(gerenteEntity)).when(gerenteRepository).findById(anyInt());
        doReturn(true).when(servicoService).ServicosInativos(listaServicos);
        gerenteService.delete(2);
        Assertions.assertEquals(TipoStatus.INATIVO,gerenteEntity.getStatus());
        Assertions.assertEquals(TipoStatus.INATIVO,usuario.getStatus());

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deletaGerenteSemSucessoIdEncontradoServicosAtivos() throws Exception {
        UsuarioEntity usuario = new UsuarioEntity();
        GerenteEntity gerenteEntity = new GerenteEntity();
        List<ServicoEntity> listaServicos = new ArrayList<>();


        gerenteEntity.setServicos(listaServicos);
        usuario.setUsuario("usuario");
        usuario.setIdUsuario(1);
        usuario.setStatus(TipoStatus.ATIVO);
        usuario.setIdUsuario(3);
        gerenteEntity.setUsuario(usuario);

        doReturn(Optional.of(gerenteEntity)).when(gerenteRepository).findById(anyInt());
        doReturn(false).when(servicoService).ServicosInativos(listaServicos);
        gerenteService.delete(2);
        Assertions.assertEquals(TipoStatus.ATIVO,gerenteEntity.getStatus());
        Assertions.assertEquals(TipoStatus.ATIVO,usuario.getStatus());

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deletaGerenteSemSucessoIdNaoEncotrado() throws Exception {
        doReturn(Optional.empty()).when(gerenteRepository).findById(anyInt());
        gerenteService.delete(3);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deletaGerenteSemSucessoGerenteJaInativo() throws Exception{
        GerenteEntity gerenteEntity = new GerenteEntity();
        gerenteEntity.setStatus(TipoStatus.INATIVO);
        doReturn(Optional.of(gerenteEntity)).when(gerenteRepository).findById(2);
        gerenteService.delete(2);
    }

    @Test
    public void criarGerenteComSucessoUsarioExistente() throws RegraDeNegocioException {
        GerenteCreateDTO gerenteCreateDTO = new GerenteCreateDTO();
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        GerenteEntity gerenteEntity = new GerenteEntity();
        RegraEntity regraEntity = new RegraEntity();
        UsuarioEntity usuarioEntity = new UsuarioEntity();

        doReturn(Optional.of(regraEntity)).when(regraRepository).findById(anyInt());

        usuarioCreateDTO.setUsuario("Dino");                     //Setando Usuário do DTO
        usuarioCreateDTO.setSenha("123");
        usuarioCreateDTO.setRegra(1);

        gerenteCreateDTO.setNomeCompleto("Dino Silva Sauro");   //Setando Gerente do DTO
        gerenteCreateDTO.setEmail("dinoco@gmail.com");
        gerenteCreateDTO.setUsuario(usuarioCreateDTO);
        gerenteCreateDTO.setStatus(TipoStatus.ATIVO);

        usuarioEntity.setUsuario("Dino");                      // Setando Usuário Entity
        usuarioEntity.setSenha("123");
        usuarioEntity.setRegraEntity(new RegraEntity());

        gerenteEntity.setIdGerente(1);                         // Setando Gerente Entity
        gerenteEntity.setNomeCompleto("Dino Silva Sauro");
        gerenteEntity.setEmail("dinoco@gmail.com");
        gerenteEntity.setStatus(TipoStatus.ATIVO);
        gerenteEntity.setUsuario(usuarioEntity);
        doReturn(gerenteEntity).when(gerenteRepository).save(any());


        GerenteDTO gerenteCriado = gerenteService.create(gerenteCreateDTO);  // Pegando o DTO do Service

        Assertions.assertNotNull(gerenteCriado);
        Assertions.assertEquals(gerenteCriado.getNomeCompleto(), "Dino Silva Sauro");
        Assertions.assertEquals(gerenteCriado.getEmail(), "dinoco@gmail.com");
        Assertions.assertEquals(gerenteCriado.getStatus(), TipoStatus.ATIVO);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void criarGerenteSemSucessoRegraDoUsarioNaoEncontrada() throws RegraDeNegocioException {

        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        GerenteCreateDTO gerenteCreateDTO = new GerenteCreateDTO();
        RegraEntity regraEntity = new RegraEntity();

        usuarioCreateDTO.setUsuario("Dino");
        usuarioCreateDTO.setSenha("123");
        usuarioCreateDTO.setRegra(3);

        gerenteCreateDTO.setNomeCompleto("Dino da Silva Sauro");
        gerenteCreateDTO.setEmail("dinoco@gmail.com");
        gerenteCreateDTO.setUsuario(usuarioCreateDTO);
        gerenteCreateDTO.setStatus(TipoStatus.ATIVO);

        doReturn(Optional.empty()).when(regraRepository).findById(anyInt());
        GerenteDTO gerenteCriado = gerenteService.create(gerenteCreateDTO);
        Assertions.assertNull(gerenteCriado);
    }

    @Test
    public void ListaGerenteComSucesso() throws Exception {
        List<GerenteEntity> gerentesEntity = new ArrayList<>();
        doReturn(gerentesEntity).when(gerenteRepository).findAll();

        List<GerenteDTO> gerentesDTO = gerenteService.list();
        Assertions.assertNotNull(gerentesDTO);
    }

    @Test
    public void RetornaGerentePorIdComSucesso() throws RegraDeNegocioException {
        GerenteEntity gerenteEntity = new GerenteEntity();
        RegraEntity regraEntity = new RegraEntity();
        UsuarioEntity usuarioEntity = new UsuarioEntity();

        regraEntity.setIdRegra(1);                         // Setando id da regra

        usuarioEntity.setRegraEntity(regraEntity);// Setando a regra no usuário
        usuarioEntity.setSenha("123");

        gerenteEntity.setUsuario(usuarioEntity);          // Setando usuário no gerente
        gerenteEntity.setIdGerente(2);                    // Setando id do Gerente

        doReturn(Optional.of(gerenteEntity)).when(gerenteRepository).findById(anyInt());


        GerenteDTO gerenteDTO = gerenteService.listById(gerenteEntity.getIdGerente());
        Assertions.assertNotNull(gerenteDTO);
    }

    @Test
    public void ListarPorNomeComSucesso(){
        List<GerenteDTO> gerenteDTOS = gerenteService.listByName("Dino");
        Assertions.assertNotNull(gerenteDTOS);
    }

    @Test
    public void updateGerenteComSucesso() throws RegraDeNegocioException {                  //Conferir
       GerenteEntity gerenteEntity = new GerenteEntity();
       UsuarioEntity usuarioEntity = new UsuarioEntity();
       GerenteAtualizaDTO gerenteAtualizaDTO = new GerenteAtualizaDTO();
       GerenteDTO gerenteDTO = new GerenteDTO();

       gerenteAtualizaDTO.setNomeCompleto("Nome Atualizado");
       gerenteAtualizaDTO.setEmail("email@gmail.com");


       doReturn(Optional.of(usuarioEntity)).when(usuarioRepository).findById(anyInt());
       gerenteEntity.setEmail(gerenteAtualizaDTO.getEmail());
       gerenteEntity.setStatus(TipoStatus.ATIVO);
       gerenteEntity.setUsuario(usuarioEntity);
       gerenteEntity.setNomeCompleto(gerenteAtualizaDTO.getNomeCompleto());

       usuarioEntity.setRegraEntity(new RegraEntity());
       usuarioEntity.setGerenteEntity(gerenteEntity);

       doReturn(Optional.of(gerenteEntity)).when(gerenteRepository).findById(anyInt());
       doReturn(gerenteEntity).when(gerenteRepository).save(any());


        gerenteDTO = gerenteService.update(2,gerenteAtualizaDTO);
        Assertions.assertNotNull(gerenteDTO);
        Assertions.assertEquals(gerenteDTO.getNomeCompleto(),gerenteAtualizaDTO.getNomeCompleto());
        Assertions.assertEquals(gerenteDTO.getEmail(),gerenteAtualizaDTO.getEmail());

    }





}



