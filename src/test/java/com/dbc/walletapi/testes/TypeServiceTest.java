package com.dbc.walletapi.testes;

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
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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

        doReturn(Optional.of(usuario)).when(usuarioRepository).findById(1);
                usuario.setIdUsuario(1);

        TypeDTO typeDTO = typeService.list("1");
        Assert.assertNotNull(typeDTO);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void listaTypeAdminSemSucesso() throws RegraDeNegocioException {

        UsuarioEntity usuario = new UsuarioEntity();

        doReturn(Optional.of(usuario)).when(usuarioRepository).findById(1);
        usuario.setIdUsuario(1);

        TypeDTO typeDTO = typeService.list("2");
        Assert.assertNotNull(typeDTO);

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

        usuario.setIdUsuario(1);
        gerenteEntity.setUsuario(usuario);
        doReturn(Optional.of(usuario)).when(usuarioRepository).findById(anyInt());
        doReturn(Optional.of(gerenteEntity)).when(gerenteRepository).findById(anyInt());


        TypeDTO typeDTO = typeService.list(String.valueOf(gerenteEntity.getUsuario().getIdUsuario()));
        Assert.assertNotNull(typeDTO);
    }


}
