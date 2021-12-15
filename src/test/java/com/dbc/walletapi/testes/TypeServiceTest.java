package com.dbc.walletapi.testes;

import com.dbc.walletapi.repository.GerenteRepository;
import com.dbc.walletapi.repository.UsuarioRepository;
import com.dbc.walletapi.service.TypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

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
}
