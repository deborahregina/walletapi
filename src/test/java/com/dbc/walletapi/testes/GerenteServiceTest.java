package com.dbc.walletapi.testes;
import com.dbc.walletapi.dto.*;
import com.dbc.walletapi.entity.*;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.repository.GerenteRepository;
import com.dbc.walletapi.repository.RegraRepository;
import com.dbc.walletapi.repository.UsuarioRepository;
import com.dbc.walletapi.service.GerenteService;
import com.dbc.walletapi.service.ServicoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @DisplayName("Criacao de gerente com sucesso")
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

        doReturn(usuarioEntity).when(usuarioRepository).save(any());
        gerenteEntity.setIdGerente(1);                         // Setando Gerente Entity
        gerenteEntity.setNomeCompleto("Dino Silva Sauro");
        gerenteEntity.setEmail("dinoco@gmail.com");
        gerenteEntity.setStatus(TipoStatus.ATIVO);
        gerenteEntity.setUsuario(usuarioEntity);
        doReturn(gerenteEntity).when(gerenteRepository).save(any());


        GerenteDTO gerenteCriado = gerenteService.create(gerenteCreateDTO);  // Pegando o DTO do Service

        Assertions.assertNotNull(gerenteCriado);
        Assertions.assertEquals(gerenteCriado.getIdGerente(), gerenteEntity.getIdGerente());
        Assertions.assertEquals(gerenteCriado.getNomeCompleto(), gerenteEntity.getNomeCompleto());
        Assertions.assertEquals(gerenteCriado.getEmail(), gerenteEntity.getEmail());
        Assertions.assertEquals(gerenteCriado.getStatus(), gerenteEntity.getStatus());
    }

    @DisplayName("Criacao de gerente sem sucesso, quando não é possível encontrar usuário.")
    @Test(expected = RegraDeNegocioException.class)
    public void criarGerenteSemSucessoRegraDoUsarioNaoEncontrada() throws RegraDeNegocioException {

        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        GerenteCreateDTO gerenteCreateDTO = new GerenteCreateDTO();

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

    @DisplayName("Senha criptografada com sucesso")
    @Test
    public void criptografouSenhaGerente() throws RegraDeNegocioException {

        GerenteEntity gerente = new GerenteEntity();
        GerenteCreateDTO gerenteCreateDTO = new GerenteCreateDTO();
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        UsuarioEntity usuario = new UsuarioEntity();
        RegraEntity regra = new RegraEntity();

        usuarioCreateDTO.setUsuario("novoUsuario");
        usuarioCreateDTO.setSenha("senha123");

        usuario.setIdUsuario(1);
        usuario.setUsuario(usuarioCreateDTO.getUsuario());
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuarioCreateDTO.getSenha()));
        gerenteCreateDTO.setUsuario(usuarioCreateDTO);

        doReturn(Optional.of(regra)).when(regraRepository).findById(anyInt());
        usuario.setRegraEntity(regra);
        doReturn(usuario).when(usuarioRepository).save(any());
        gerente.setUsuario(usuario);
        doReturn(gerente).when(gerenteRepository).save(any());
        usuario.setGerenteEntity(gerente);


        GerenteDTO gerenteDTO = gerenteService.create(gerenteCreateDTO);
        Assertions.assertNotEquals(usuario.getSenha(),usuarioCreateDTO.getSenha()); // senha salva é diferente da senha passada.

    }

    @DisplayName("Senha não foi criptografada.")
    @Test
    public void naoCriptografouSenhaGerente() throws RegraDeNegocioException {

        GerenteEntity gerente = new GerenteEntity();
        GerenteCreateDTO gerenteCreateDTO = new GerenteCreateDTO();
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        UsuarioEntity usuario = new UsuarioEntity();
        RegraEntity regra = new RegraEntity();

        usuarioCreateDTO.setUsuario("novoUsuario");
        usuarioCreateDTO.setSenha("senha123");

        usuario.setIdUsuario(1);
        usuario.setUsuario(usuarioCreateDTO.getUsuario());
        usuario.setSenha(usuarioCreateDTO.getSenha());
        gerenteCreateDTO.setUsuario(usuarioCreateDTO);

        doReturn(Optional.of(regra)).when(regraRepository).findById(anyInt());
        usuario.setRegraEntity(regra);
        doReturn(usuario).when(usuarioRepository).save(any());
        gerente.setUsuario(usuario);
        doReturn(gerente).when(gerenteRepository).save(any());
        usuario.setGerenteEntity(gerente);


        GerenteDTO gerenteDTO = gerenteService.create(gerenteCreateDTO);
        Assertions.assertEquals(usuario.getSenha(),usuarioCreateDTO.getSenha()); // senha salva é igual da senha passada.

    }

    @DisplayName("Criação de usuário com senha nula.")
    @Test(expected = RegraDeNegocioException.class)
    public void tentouCriarGerenteComSenhaNula() throws RegraDeNegocioException {

        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        UsuarioEntity usuario = new UsuarioEntity();
        GerenteCreateDTO gerenteCreateDTO = new GerenteCreateDTO();
        GerenteEntity gerenteEntity = new GerenteEntity();
        RegraEntity regra = new RegraEntity();

        gerenteCreateDTO.setEmail("novoemail@gmail.com");
        gerenteCreateDTO.setStatus(TipoStatus.ATIVO);
        gerenteCreateDTO.setNomeCompleto("Nome Completo");
        usuarioCreateDTO.setUsuario("novo user");

        doReturn(Optional.of(regra)).when(regraRepository).findById(anyInt());

        usuario.setRegraEntity(regra);
        usuario.setSenha(usuarioCreateDTO.getSenha());
        usuario.setGerenteEntity(gerenteEntity);
        usuarioCreateDTO.setRegra(2);
        gerenteCreateDTO.setUsuario(usuarioCreateDTO);

        GerenteDTO gerenteDTO = gerenteService.create(gerenteCreateDTO);
        Assertions.assertNull(gerenteDTO);

    }

    @DisplayName("Criacao criacao de usuário com senha vazia")
    @Test(expected = NullPointerException.class)
    public void tentouCriarGerenteComSenhaVazia() throws RegraDeNegocioException {

        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        UsuarioEntity usuario = new UsuarioEntity();
        GerenteCreateDTO gerenteCreateDTO = new GerenteCreateDTO();
        GerenteEntity gerenteEntity = new GerenteEntity();
        RegraEntity regra = new RegraEntity();

        gerenteCreateDTO.setEmail("novoemail@gmail.com");
        gerenteCreateDTO.setStatus(TipoStatus.ATIVO);
        gerenteCreateDTO.setNomeCompleto("Nome Completo");
        usuarioCreateDTO.setUsuario("novo user");
        usuarioCreateDTO.setSenha("");

        doReturn(Optional.of(regra)).when(regraRepository).findById(anyInt());

        usuario.setRegraEntity(regra);
        usuario.setSenha(usuarioCreateDTO.getSenha());
        usuario.setGerenteEntity(gerenteEntity);
        gerenteEntity.setUsuario(usuario);
        usuarioCreateDTO.setRegra(2);
        gerenteCreateDTO.setUsuario(usuarioCreateDTO);

        GerenteDTO gerenteDTO = gerenteService.create(gerenteCreateDTO);
        Assertions.assertNull(gerenteDTO);

    }

    @DisplayName("Listagem de gerentes feita com sucesso.")
    @Test
    public void listaGerenteComSucesso() throws Exception {

        List<GerenteDTO> gerentesDTO = gerenteService.list();
        Assertions.assertNotNull(gerentesDTO);
    }

    @DisplayName("Listagem de gerente por id com sucesso.")
    @Test
    public void retornaGerentePorIdComSucesso() throws RegraDeNegocioException {
        GerenteEntity gerenteEntity = new GerenteEntity();
        RegraEntity regraEntity = new RegraEntity();
        UsuarioEntity usuarioEntity = new UsuarioEntity();

        regraEntity.setIdRegra(2);                         // Setando id da regra

        usuarioEntity.setRegraEntity(regraEntity);// Setando a regra no usuário
        usuarioEntity.setSenha("123");

        gerenteEntity.setUsuario(usuarioEntity);          // Setando usuário no gerente
        gerenteEntity.setIdGerente(2);                    // Setando id do Gerente

        doReturn(Optional.of(gerenteEntity)).when(gerenteRepository).findById(anyInt());


        GerenteDTO gerenteDTO = gerenteService.listById(gerenteEntity.getIdGerente());
        Assertions.assertNotNull(gerenteDTO);
    }

    @DisplayName("Listagem por nome de gerente com sucesso.")
    @Test
    public void listarPorNomeComSucesso(){
        List<GerenteDTO> gerenteDTOS = gerenteService.listByName("Dino");
        Assertions.assertNotNull(gerenteDTOS);
    }

    @DisplayName("Atualização de dados de gerente com sucesso.")
    @Test
    public void updateGerenteComSucesso() throws RegraDeNegocioException {
       GerenteEntity gerenteEntity = new GerenteEntity();
       UsuarioEntity usuarioEntity = new UsuarioEntity();
       GerenteAtualizaDTO gerenteAtualizaDTO = new GerenteAtualizaDTO();

       gerenteAtualizaDTO.setNomeCompleto("Nome Atualizado");
       gerenteAtualizaDTO.setEmail("email@gmail.com");


       gerenteEntity.setEmail(gerenteAtualizaDTO.getEmail());
       gerenteEntity.setStatus(TipoStatus.ATIVO);
       gerenteEntity.setUsuario(usuarioEntity);
       gerenteEntity.setNomeCompleto(gerenteAtualizaDTO.getNomeCompleto());

       usuarioEntity.setRegraEntity(new RegraEntity());
       usuarioEntity.setGerenteEntity(gerenteEntity);

       doReturn(Optional.of(gerenteEntity)).when(gerenteRepository).findById(anyInt());
       doReturn(gerenteEntity).when(gerenteRepository).save(any());


        GerenteDTO gerenteDTO = gerenteService.update(2,gerenteAtualizaDTO);
        Assertions.assertNotNull(gerenteDTO);
        Assertions.assertEquals(gerenteDTO.getNomeCompleto(),gerenteAtualizaDTO.getNomeCompleto());
        Assertions.assertEquals(gerenteDTO.getEmail(),gerenteAtualizaDTO.getEmail());

    }

    @DisplayName("Deleta gerente com sucesso.")
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
        doReturn(true).when(servicoService).servicosInativos(listaServicos);
        gerenteService.delete(2);
        Assertions.assertEquals(TipoStatus.INATIVO,gerenteEntity.getStatus());
        Assertions.assertEquals(TipoStatus.INATIVO,usuario.getStatus());

    }

    @DisplayName("Deleta gerente sem sucesso pois possui serviços ativos.")
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
        doReturn(false).when(servicoService).servicosInativos(listaServicos);
        gerenteService.delete(2);
        Assertions.assertEquals(TipoStatus.ATIVO,gerenteEntity.getStatus());
        Assertions.assertEquals(TipoStatus.ATIVO,usuario.getStatus());

    }

    @DisplayName("Deleta gerente sem sucesso pois gerente não foi encontrado.")
    @Test(expected = RegraDeNegocioException.class)
    public void deletaGerenteSemSucessoIdNaoEncotrado() throws Exception {
        doReturn(Optional.empty()).when(gerenteRepository).findById(anyInt());
        gerenteService.delete(3);
    }

    @DisplayName("Deleta gerente sem sucesso pois ele já está inativo.")
    @Test(expected = RegraDeNegocioException.class)
    public void deletaGerenteSemSucessoGerenteJaInativo() throws Exception{
        GerenteEntity gerenteEntity = new GerenteEntity();
        gerenteEntity.setStatus(TipoStatus.INATIVO);
        doReturn(Optional.of(gerenteEntity)).when(gerenteRepository).findById(2);
        gerenteService.delete(2);
    }


}



