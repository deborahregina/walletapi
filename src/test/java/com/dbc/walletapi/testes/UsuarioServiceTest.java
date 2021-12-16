package com.dbc.walletapi.testes;

import com.dbc.walletapi.dto.UsuarioDTO;
import com.dbc.walletapi.entity.RegraEntity;
import com.dbc.walletapi.entity.TipoStatus;
import com.dbc.walletapi.entity.UsuarioEntity;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.repository.UsuarioRepository;
import com.dbc.walletapi.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.internal.bytebuddy.dynamic.DynamicType;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
    }


    @Test
    public void findByLoginComSucesso() throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        RegraEntity regraEntity = new RegraEntity();

        regraEntity.setIdRegra(2);
        regraEntity.setNome("Gerente");

        usuarioEntity.setIdUsuario(1);
        usuarioEntity.setUsuario("Dino");
        usuarioEntity.setSenha("123");
        usuarioEntity.setStatus(TipoStatus.ATIVO);
        usuarioEntity.setRegraEntity(regraEntity);
        doReturn(Optional.of(usuarioEntity)).when(usuarioRepository).findByUsuario("Dino");
        Optional<UsuarioEntity> usuarioEntityBuscado = usuarioService.findByLogin("Dino");

        Assertions.assertNotNull(usuarioEntityBuscado);

    }

    @Test
    public void findByLoginSemSucesso() throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        RegraEntity regraEntity = new RegraEntity();

        regraEntity.setIdRegra(2);
        regraEntity.setNome("Gerente");

        usuarioEntity.setIdUsuario(1);
        usuarioEntity.setUsuario("Dino");
        usuarioEntity.setSenha("123");
        usuarioEntity.setStatus(TipoStatus.ATIVO);
        usuarioEntity.setRegraEntity(regraEntity);
        doReturn(Optional.of(usuarioEntity)).when(usuarioRepository).findByUsuario("Dino");
        Optional<UsuarioEntity> usuarioEntityBuscado = usuarioService.findByLogin("Dino");

        Assertions.assertNotNull(usuarioEntityBuscado);
    }

    @Test
    public void listarUsuarioComSucesso(){
        List<UsuarioEntity> listaUsuarios = new ArrayList<>();

        doReturn(listaUsuarios).when(usuarioRepository).findAll();

        List<UsuarioDTO> usuarioDTOList = usuarioService.list();
        Assertions.assertNotNull(usuarioDTOList);

    }
}
