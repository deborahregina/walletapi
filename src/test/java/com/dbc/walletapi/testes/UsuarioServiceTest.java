package com.dbc.walletapi.testes;

import com.dbc.walletapi.entity.RegraEntity;
import com.dbc.walletapi.entity.TipoStatus;
import com.dbc.walletapi.entity.UsuarioEntity;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.repository.UsuarioRepository;
import com.dbc.walletapi.service.UsuarioService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;


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

        Assertions.assertEquals(usuarioEntityBuscado.get().getIdUsuario(), 1);
        Assertions.assertEquals(usuarioEntityBuscado.get().getUsuario(), "Dino");
        Assertions.assertEquals(usuarioEntityBuscado.get().getSenha(), "123");
        Assertions.assertEquals(usuarioEntityBuscado.get().getStatus(), TipoStatus.ATIVO);
    }

    @Test(expected = NoSuchElementException.class)
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
        doReturn(Optional.empty()).when(usuarioRepository).findByUsuario(anyString());
        Optional<UsuarioEntity> usuarioEntityBuscado = usuarioService.findByLogin("Dino");

        Assertions.assertNull(usuarioEntityBuscado.get());
    }

}
