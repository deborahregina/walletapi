package com.dbc.walletapi.testes;
import com.dbc.walletapi.dto.*;
import com.dbc.walletapi.entity.*;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.repository.GerenteRepository;
import com.dbc.walletapi.repository.ServicoRepository;
import com.dbc.walletapi.service.ServicoService;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ServicoServiceTest {

    @Spy
    @InjectMocks
    private ServicoService servicoService;

    @Mock
    private ServicoRepository servicoRepository;

    @Mock
    private GerenteRepository gerenteRepository;


    private final ObjectMapper objectMapper = new ObjectMapper();


    @Before
    public void init() {
        ReflectionTestUtils.setField(servicoService, "objectMapper",objectMapper);
    }


    @Test
    public void deletaServicoComSucessoIdEncontrado() throws Exception {
        ServicoEntity servicoEntity = mock(ServicoEntity.class);
        doReturn(Optional.of(servicoEntity)).when(servicoRepository).findById(2);
        servicoService.delete(2);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deletaServicoSemSucessoIdNaoEncontrado() throws RegraDeNegocioException {
        doReturn(Optional.empty()).when(servicoRepository).findById(anyInt());
        servicoService.delete(3);
    }


    @Test
    public void criaServicoComSucessoGerenteExiste() throws RegraDeNegocioException {

    ServicoCreateDTO servicoCreateDTO = new ServicoCreateDTO();
    ServicoEntity servicoSalvo = new ServicoEntity();
    GerenteEntity gerenteEntity = new GerenteEntity();

        doReturn(Optional.of(gerenteEntity)).when(gerenteRepository).findById(anyInt());
        servicoCreateDTO.setNome("Google");
        servicoCreateDTO.setDescricao("Novo serviço");
        servicoCreateDTO.setMoeda(TipoMoeda.DOLAR);
        servicoCreateDTO.setValor(new BigDecimal("10.00"));
        servicoCreateDTO.setWebSite("www.google.com.br");
        servicoCreateDTO.setPeriocidade(TipoPeriodicidade.ANUAL);

        servicoSalvo.setNome("Google");
        servicoSalvo.setDescricao("Novo serviço");
        servicoSalvo.setMoeda(TipoMoeda.DOLAR);
        servicoSalvo.setValor(new BigDecimal("10.00"));
        servicoSalvo.setWebSite("www.google.com.br");
        servicoSalvo.setPeriocidade(TipoPeriodicidade.ANUAL);
        servicoSalvo.setGerenteEntity(gerenteEntity);
        doReturn(servicoSalvo).when(servicoRepository).save(any());


        ServicoDTO servicoDTO = servicoService.create(servicoCreateDTO,2);  // Pegando o DTO do Service
        Assertions.assertNotNull(servicoDTO);

}

    @Test(expected = RegraDeNegocioException.class)
    public void criaServicoSemSucessoGerenteNaoExistente() throws RegraDeNegocioException {

        ServicoCreateDTO servicoCreateDTO = new ServicoCreateDTO();
        ServicoEntity servicoSalvo = new ServicoEntity();
        GerenteEntity gerenteEntity = new GerenteEntity();

        doReturn(Optional.empty()).when(gerenteRepository).findById(anyInt());
        servicoCreateDTO.setNome("Google");
        servicoCreateDTO.setDescricao("Novo serviço");
        servicoCreateDTO.setMoeda(TipoMoeda.DOLAR);
        servicoCreateDTO.setValor(new BigDecimal("10.00"));
        servicoCreateDTO.setWebSite("www.google.com.br");
        servicoCreateDTO.setPeriocidade(TipoPeriodicidade.ANUAL);

        ServicoDTO servicoDTO = servicoService.create(servicoCreateDTO,3);  // Pegando o DTO do Service
        Assertions.assertNull(servicoDTO);

    }

    @Test
    public void updateServicoComSucessoServicoExistente() throws RegraDeNegocioException {

        ServicoAtualizaDTO servicoAtualizaDTO = new ServicoAtualizaDTO();
        ServicoEntity servicoSalvo = new ServicoEntity();
        GerenteEntity gerenteEntity = new GerenteEntity();

        doReturn(Optional.of(gerenteEntity)).when(gerenteRepository).findById(anyInt());
        servicoAtualizaDTO.setNome("Google");
        servicoAtualizaDTO.setDescricao("Novo serviço");
        servicoAtualizaDTO.setMoeda(TipoMoeda.DOLAR);
        servicoAtualizaDTO.setValor(new BigDecimal("10.00"));
        servicoAtualizaDTO.setWebSite("www.google.com.br");
        servicoAtualizaDTO.setPeriocidade(TipoPeriodicidade.ANUAL);

        doReturn(Optional.of(servicoSalvo)).when(servicoRepository).findById(anyInt());
        servicoSalvo.setNome("Google");
        servicoSalvo.setDescricao("Novo serviço");
        servicoSalvo.setMoeda(TipoMoeda.DOLAR);
        servicoSalvo.setValor(new BigDecimal("10.00"));
        servicoSalvo.setWebSite("www.google.com.br");
        servicoSalvo.setPeriocidade(TipoPeriodicidade.ANUAL);
        servicoSalvo.setGerenteEntity(gerenteEntity);
        doReturn(servicoSalvo).when(servicoRepository).save(any());

        ServicoDTO servicoDTO = servicoService.update(servicoAtualizaDTO,2);
        Assertions.assertNotNull(servicoDTO);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void updateServicoSemSucessoServicoNaoExistente() throws RegraDeNegocioException {


        ServicoAtualizaDTO servicoAtualizaDTO = new ServicoAtualizaDTO();
        ServicoEntity servicoSalvo = new ServicoEntity();
        GerenteEntity gerenteEntity = new GerenteEntity();

        doReturn(Optional.of(gerenteEntity)).when(gerenteRepository).findById(anyInt());
        servicoAtualizaDTO.setNome("Google");
        servicoAtualizaDTO.setDescricao("Novo serviço");
        servicoAtualizaDTO.setMoeda(TipoMoeda.DOLAR);
        servicoAtualizaDTO.setValor(new BigDecimal("10.00"));
        servicoAtualizaDTO.setWebSite("www.google.com.br");
        servicoAtualizaDTO.setPeriocidade(TipoPeriodicidade.ANUAL);

        doReturn(Optional.empty()).when(servicoRepository).findById(anyInt()); // a "busca no banco" não retorna um objeto.
        servicoSalvo.setNome("Google");
        servicoSalvo.setDescricao("Novo serviço");
        servicoSalvo.setMoeda(TipoMoeda.DOLAR);
        servicoSalvo.setValor(new BigDecimal("10.00"));
        servicoSalvo.setWebSite("www.google.com.br");
        servicoSalvo.setPeriocidade(TipoPeriodicidade.ANUAL);
        servicoSalvo.setGerenteEntity(gerenteEntity);
        doReturn(servicoSalvo).when(servicoRepository).save(any());

        ServicoDTO servicoDTO = servicoService.update(servicoAtualizaDTO,3);
        Assertions.assertNull(servicoDTO); // Não salvou
    }

    @Test(expected = RegraDeNegocioException.class)
    public void updateServicoSemSucessoGerenteInexistente() throws RegraDeNegocioException {

        ServicoAtualizaDTO servicoAtualizaDTO = new ServicoAtualizaDTO();
        ServicoEntity servicoSalvo = new ServicoEntity();
        GerenteEntity gerenteEntity = new GerenteEntity();

        doReturn(Optional.empty()).when(gerenteRepository).findById(anyInt());
        servicoAtualizaDTO.setNome("Google");
        servicoAtualizaDTO.setDescricao("Novo serviço");
        servicoAtualizaDTO.setMoeda(TipoMoeda.DOLAR);
        servicoAtualizaDTO.setValor(new BigDecimal("10.00"));
        servicoAtualizaDTO.setWebSite("www.google.com.br");
        servicoAtualizaDTO.setPeriocidade(TipoPeriodicidade.ANUAL);

        servicoSalvo.setNome("Google");
        servicoSalvo.setDescricao("Novo serviço");
        servicoSalvo.setMoeda(TipoMoeda.DOLAR);
        servicoSalvo.setValor(new BigDecimal("10.00"));
        servicoSalvo.setWebSite("www.google.com.br");
        servicoSalvo.setPeriocidade(TipoPeriodicidade.ANUAL);

        ServicoDTO servicoDTO = servicoService.update(servicoAtualizaDTO,1);
        Assertions.assertNull(servicoDTO); // Não salvou

    }

    @Test
    public void listByIdServicoComSucessoServicoExistente() throws RegraDeNegocioException {

        ServicoDTO servicoDTO = new ServicoDTO();
        ServicoEntity servicoSalvo = new ServicoEntity();
        GerenteEntity gerenteEntity = new GerenteEntity();
        GerenteDTO gerenteDTO = new GerenteDTO();

        doReturn(Optional.of(gerenteEntity)).when(gerenteRepository).findById(anyInt());
        servicoDTO.setNome("Google");
        servicoDTO.setDescricao("Novo serviço");
        servicoDTO.setMoeda(TipoMoeda.DOLAR);
        servicoDTO.setValor(new BigDecimal("10.00"));
        servicoDTO.setWebSite("www.google.com.br");
        servicoDTO.setPeriocidade(TipoPeriodicidade.ANUAL);
        servicoDTO.setGerente(gerenteDTO);


        servicoSalvo.setNome("Google");
        servicoSalvo.setDescricao("Novo serviço");
        servicoSalvo.setMoeda(TipoMoeda.DOLAR);
        servicoSalvo.setValor(new BigDecimal("10.00"));
        servicoSalvo.setWebSite("www.google.com.br");
        servicoSalvo.setPeriocidade(TipoPeriodicidade.ANUAL);
        servicoSalvo.setGerenteEntity(gerenteEntity);
        doReturn(Optional.of(servicoSalvo)).when(servicoRepository).findById(anyInt());

        ServicoDTO servicolist = servicoService.listById(anyInt());
        Assertions.assertNotNull(servicolist);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void listByIdServicoSemSucessoServicoInexistente() throws RegraDeNegocioException {

        ServicoDTO servicoDTO = new ServicoDTO();
        GerenteEntity gerenteEntity = new GerenteEntity();
        GerenteDTO gerenteDTO = new GerenteDTO();

        doReturn(Optional.of(gerenteEntity)).when(gerenteRepository).findById(anyInt());
        servicoDTO.setNome("Google");
        servicoDTO.setDescricao("Novo serviço");
        servicoDTO.setMoeda(TipoMoeda.DOLAR);
        servicoDTO.setValor(new BigDecimal("10.00"));
        servicoDTO.setWebSite("www.google.com.br");
        servicoDTO.setPeriocidade(TipoPeriodicidade.ANUAL);
        servicoDTO.setGerente(gerenteDTO);

        doReturn(Optional.empty()).when(servicoRepository).findById(anyInt());

        ServicoDTO servicolist = servicoService.listById(anyInt());
        Assertions.assertNotNull(servicolist);

    }

    @Test
    public void listServicosComSucesso() {

        GerenteEntity gerenteEntity = new GerenteEntity();
        List<ServicoEntity> servicosEntity = new ArrayList<>();

        doReturn(servicosEntity).when(servicoRepository).findAll();

        List<ServicoDTO> servicosDTO =  servicoService.list();
        Assertions.assertNotNull(servicosDTO);

    }

    @Test
    public void listServicosPorNomeComSucesso() {                // Conferir
        List<ServicoEntity> listaServicos = new ArrayList<>();
        ServicoDTO servicoDTO = new ServicoDTO();
        ServicoEntity servicoEntity = new ServicoEntity();

        doReturn(listaServicos).when(servicoRepository).findAll();

        List<ServicoDTO> servicoDTOList = servicoService.listByName(anyString());
        Assertions.assertNotNull(servicoDTOList);

    }


}
