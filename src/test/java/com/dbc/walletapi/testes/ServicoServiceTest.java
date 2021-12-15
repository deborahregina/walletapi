package com.dbc.walletapi.testes;
import com.dbc.walletapi.dto.*;
import com.dbc.walletapi.entity.*;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.repository.GerenteRepository;
import com.dbc.walletapi.repository.ServicoRepository;
import com.dbc.walletapi.service.ServicoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ServicoServiceTest {

    @InjectMocks
    private ServicoService servicoService;

    @Mock
    private ServicoRepository servicoRepository;

    @Mock
    private GerenteRepository gerenteRepository;


    private final ObjectMapper objectMapper = new ObjectMapper();



    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(servicoService, "objectMapper",objectMapper);

    }

    @Test
    public void deletaServicoComSucesso() throws Exception {
        ServicoEntity servicoEntity = mock(ServicoEntity.class);
        doReturn(Optional.of(servicoEntity)).when(servicoRepository).findById(2);
        servicoService.delete(2);
    }

    @Test
    public void deletaServicoSemSucesso() throws Exception {
        ServicoEntity servicoEntity = mock(ServicoEntity.class);
        when(servicoRepository.findById(anyInt())).thenThrow(new RegraDeNegocioException("Serviço não encontrado"));
        servicoService.delete(3);

    }

    @Test
    public void criaServicoComSucesso() throws RegraDeNegocioException {
        ServicoCreateDTO servicoCreateDTO = mock(ServicoCreateDTO.class);
        GerenteEntity gerente = mock(GerenteEntity.class);
        ServicoEntity servico = mock(ServicoEntity.class);
        UsuarioEntity usuario = mock(UsuarioEntity.class);
        ServicoDTO servicoDTO = mock(ServicoDTO.class);


        doReturn(Optional.of(gerente)).when(gerenteRepository).findById(anyInt());

        servicoCreateDTO.setNome("Google");
        servicoCreateDTO.setDescricao("Novo serviço");
        servicoCreateDTO.setMoeda(TipoMoeda.DOLAR);
        servicoCreateDTO.setValor(new BigDecimal("10.00"));
        servicoCreateDTO.setWebSite("www.google.com.br");
        servicoCreateDTO.setPeriocidade(TipoPeriodicidade.ANUAL);
        servico.setGerenteEntity(gerente);

        doReturn(servico).when(servicoRepository).save(any());
        doReturn(usuario).when(gerente).getUsuario();
        doReturn(servicoDTO).when(servicoService).fromEntity(servico);


        ServicoDTO servicoCriado = servicoService.create(servicoCreateDTO,anyInt());

        verify(gerenteRepository, times(1)).findById(1);

    }



}
