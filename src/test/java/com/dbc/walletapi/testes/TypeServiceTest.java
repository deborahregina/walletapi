package com.dbc.walletapi.testes;

import com.dbc.walletapi.dto.LoginCreateDTO;
import com.dbc.walletapi.dto.TypeDTO;
import com.dbc.walletapi.repository.UsuarioRepository;
import com.dbc.walletapi.service.TypeService;
import com.dbc.walletapi.entity.*;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.repository.GerenteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.Optional;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TypeServiceTest {


    @InjectMocks
    private TypeService typeService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private GerenteRepository gerenteRepository;

    @Before
    public void init() {
        ReflectionTestUtils.setField(typeService, "objectMapper",objectMapper);
    }

    @Test
    public void listaTypeAdminComSucesso() throws RegraDeNegocioException {

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsuario("User");

        doReturn(Optional.of(usuario)).when(usuarioRepository).findById(1);
                usuario.setIdUsuario(1);

        TypeDTO typeDTO = typeService.list("1");
        Assert.assertNotNull(typeDTO);
        Assertions.assertEquals(typeDTO.getUsuario(), usuario.getUsuario());

    }

    @Test(expected = RegraDeNegocioException.class)
    public void listaTypeAdminSemSucesso() throws RegraDeNegocioException {

        doReturn(Optional.empty()).when(usuarioRepository).findById(1);
        TypeDTO typeDTO = typeService.list("1");
        Assertions.assertNull(typeDTO);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void listaTypeGerenteSemSucesso() throws RegraDeNegocioException {

        GerenteEntity gerenteEntity = new GerenteEntity();

        doReturn(Optional.of(gerenteEntity)).when(gerenteRepository).findById(anyInt());
        doReturn(Optional.empty()).when(usuarioRepository).findById(anyInt()); // Não encontrou o usuário

        TypeDTO typeDTO = typeService.list(String.valueOf(anyInt()));
        Assert.assertNull(typeDTO);
    }

    @Test
    public void listaTypeGerenteComSucesso() throws RegraDeNegocioException {

        GerenteEntity gerenteEntity = new GerenteEntity();
        UsuarioEntity usuario = new UsuarioEntity();

        usuario.setUsuario("user");
        usuario.setIdUsuario(1);
        gerenteEntity.setUsuario(usuario);
        doReturn(Optional.of(usuario)).when(usuarioRepository).findById(anyInt());
        doReturn(Optional.of(gerenteEntity)).when(gerenteRepository).findById(anyInt());


        TypeDTO typeDTO = typeService.list(String.valueOf(gerenteEntity.getUsuario().getIdUsuario()));
        Assert.assertNotNull(typeDTO);
        Assertions.assertEquals(gerenteEntity.getUsuario().getUsuario(), typeDTO.getUsuario());
    }

    @Test
    public void trocouSenhaComSucesso() throws RegraDeNegocioException {

        LoginCreateDTO loginDTO = new LoginCreateDTO();
        GerenteEntity gerenteEntity = new GerenteEntity();
        UsuarioEntity usuarioAntigo = new UsuarioEntity();
        UsuarioEntity usuarioNovo = new UsuarioEntity();


        loginDTO.setSenha("nova senha");
        loginDTO.setUsuario("novo usuario");

        usuarioAntigo.setUsuario("novo usuario");
        usuarioAntigo.setSenha("nova senha");
        usuarioAntigo.setIdUsuario(1);
        usuarioNovo.setSenha(loginDTO.getSenha());
        usuarioNovo.setSenha(loginDTO.getSenha());
        gerenteEntity.setUsuario(usuarioNovo);
        gerenteEntity.getUsuario().setIdUsuario(1);

        usuarioNovo.setIdUsuario(usuarioAntigo.getIdUsuario());
        doReturn(Optional.of(usuarioAntigo)).when(usuarioRepository).findById(1);
        gerenteEntity.setUsuario(usuarioNovo);
        doReturn(usuarioNovo).when(usuarioRepository).save(usuarioAntigo);

        TypeDTO typeDTO = typeService.alterarSenhaELoginUsuarioDoAutenticado(String.valueOf(gerenteEntity.getUsuario().getIdUsuario()),loginDTO);
        Assertions.assertEquals(usuarioNovo.getUsuario(), typeDTO.getUsuario());
    }
}
