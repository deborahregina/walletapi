package com.dbc.walletapi.testes;
import com.dbc.walletapi.dto.*;
import com.dbc.walletapi.entity.*;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.repository.GerenteRepository;
import com.dbc.walletapi.repository.ServicoRepository;
import com.dbc.walletapi.service.ServicoService;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ServicoServiceTest {

    @InjectMocks
    private ServicoService servicoService;

    @Mock
    private ServicoRepository servicoRepository;

    @Mock
    private GerenteRepository gerenteRepository;


    private final ObjectMapper objectMapper = new ObjectMapper();

    //private final DateTimeFormatter formatData = DateTimeFormatter.ofPattern("dd-MM-yyyy");;


    @Before
    public void init() {
        ReflectionTestUtils.setField(servicoService, "objectMapper",objectMapper);
    }


    @Test
    public void deletaServicoComSucessoIdEncontrado() throws Exception {
        ServicoEntity servicoEntity = new ServicoEntity();
        doReturn(Optional.of(servicoEntity)).when(servicoRepository).findById(anyInt());
        servicoService.delete(2);
        Assertions.assertEquals(TipoStatus.INATIVO, servicoEntity.getStatus());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deletaServicoSemSucessoIdNaoEncontrado() throws RegraDeNegocioException {
        doReturn(Optional.empty()).when(servicoRepository).findById(anyInt());
        servicoService.delete(3);
    }

    @Test
    public void criaServicoComSucessoGerenteExistente() throws RegraDeNegocioException {
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
        Assertions.assertNotNull(servicoDTO.getGerente());
        Assertions.assertEquals(servicoDTO.getNome(),"Google");
        Assertions.assertEquals(servicoDTO.getDescricao(),"Novo serviço");
        Assertions.assertEquals(servicoDTO.getMoeda(),TipoMoeda.DOLAR);
        Assertions.assertEquals(servicoDTO.getPeriocidade(),TipoPeriodicidade.ANUAL);
        Assertions.assertEquals(servicoDTO.getValor(),new BigDecimal("10.00"));
        Assertions.assertEquals(servicoDTO.getWebSite(),"www.google.com.br");
}

    @Test(expected = RegraDeNegocioException.class)
    public void criaServicoSemSucessoGerenteInexistente() throws RegraDeNegocioException {

        ServicoCreateDTO servicoCreateDTO = new ServicoCreateDTO();
       

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
        Assertions.assertEquals(servicoAtualizaDTO.getMoeda(),servicoDTO.getMoeda());
        Assertions.assertEquals(servicoAtualizaDTO.getDescricao(),servicoDTO.getDescricao());
        Assertions.assertEquals(servicoAtualizaDTO.getNome(),servicoDTO.getNome());
        Assertions.assertEquals(servicoAtualizaDTO.getValor(),servicoDTO.getValor());
        Assertions.assertEquals(servicoAtualizaDTO.getPeriocidade(),servicoDTO.getPeriocidade());
        Assertions.assertEquals(servicoAtualizaDTO.getWebSite(),servicoDTO.getWebSite());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void updateServicoSemSucessoServicoInexistente() throws RegraDeNegocioException {
        ServicoAtualizaDTO servicoAtualizaDTO = new ServicoAtualizaDTO();
        ServicoEntity servicoSalvo = new ServicoEntity();
        GerenteEntity gerenteEntity = new GerenteEntity();

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

        ServicoDTO servicoDTO = servicoService.update(servicoAtualizaDTO,3);
        Assertions.assertNull(servicoDTO); // Não salvou
        Assertions.assertNotEquals(servicoAtualizaDTO.getDescricao(),servicoDTO.getDescricao());
        Assertions.assertNotEquals(servicoAtualizaDTO.getNome(),servicoDTO.getNome());
        Assertions.assertNotEquals(servicoAtualizaDTO.getPeriocidade(),servicoDTO.getPeriocidade());
        Assertions.assertNotEquals(servicoAtualizaDTO.getWebSite(),servicoDTO.getWebSite());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void updateServicoSemSucessoGerenteInexistente() throws RegraDeNegocioException {
        ServicoAtualizaDTO servicoAtualizaDTO = new ServicoAtualizaDTO();
        ServicoEntity servicoSalvo = new ServicoEntity();

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
        Assertions.assertNotEquals(servicoAtualizaDTO.getDescricao(),servicoDTO.getDescricao());
        Assertions.assertNotEquals(servicoAtualizaDTO.getNome(),servicoDTO.getNome());
        Assertions.assertNotEquals(servicoAtualizaDTO.getPeriocidade(),servicoDTO.getPeriocidade());
        Assertions.assertNotEquals(servicoAtualizaDTO.getWebSite(),servicoDTO.getWebSite());
    }

    @Test
    public void listByIdServicoComSucessoServicoExistente() throws RegraDeNegocioException {
        ServicoEntity servicoSalvo = new ServicoEntity();
        GerenteEntity gerenteEntity = new GerenteEntity();

        servicoSalvo.setNome("Google");
        servicoSalvo.setDescricao("Novo serviço");
        servicoSalvo.setMoeda(TipoMoeda.DOLAR);
        servicoSalvo.setValor(new BigDecimal("10.00"));
        servicoSalvo.setWebSite("www.google.com.br");
        servicoSalvo.setPeriocidade(TipoPeriodicidade.ANUAL);
        servicoSalvo.setGerenteEntity(gerenteEntity);
        doReturn(Optional.of(servicoSalvo)).when(servicoRepository).findById(anyInt());

        ServicoDTO servicolistado = servicoService.listById(anyInt());
        Assertions.assertNotNull(servicolistado);
        Assertions.assertEquals(servicolistado.getNome(),"Google");
        Assertions.assertEquals(servicolistado.getDescricao(),"Novo serviço");
        Assertions.assertEquals(servicolistado.getMoeda(),TipoMoeda.DOLAR);
        Assertions.assertEquals(servicolistado.getPeriocidade(),TipoPeriodicidade.ANUAL);
        Assertions.assertEquals(servicolistado.getValor(),new BigDecimal("10.00"));
        Assertions.assertEquals(servicolistado.getWebSite(),"www.google.com.br");
    }

    @Test(expected = RegraDeNegocioException.class)
    public void listByIdServicoSemSucessoServicoInexistente() throws RegraDeNegocioException {
        doReturn(Optional.empty()).when(servicoRepository).findById(anyInt());
        ServicoDTO servicolist = servicoService.listById(anyInt());
        Assertions.assertNull(servicolist);
    }

    @Test
    public void listaServicosComSucesso() {
        List<ServicoDTO> servicosDTO =  servicoService.list();
        Assertions.assertNotNull(servicosDTO);
    }

    @Test
    public void listaServicosPorNomeComSucesso() {                // Conferir
        List<ServicoEntity> listaServicos = new ArrayList<>();

        doReturn(listaServicos).when(servicoRepository).findAll();

        List<ServicoDTO> servicoDTOList = servicoService.listByName("anyString()");
        Assertions.assertNotNull(servicoDTOList);
    }

    @Test
    public void listaServicoInativosComListaInativa() {
        List<ServicoEntity> servicoEntities = new ArrayList<>();
        ServicoEntity servicoInativo = new ServicoEntity();

        servicoInativo.setStatus(TipoStatus.INATIVO);
        servicoEntities.add(servicoInativo);
        Assert.assertTrue(servicoService.ServicosInativos(servicoEntities));
    }

    @Test
    public void listaServicoInativosComListaAtiva() {
        List<ServicoEntity> servicoEntities = new ArrayList<>();
        ServicoEntity servicoAnativo = new ServicoEntity();
        servicoAnativo.setStatus(TipoStatus.ATIVO);
        servicoEntities.add(servicoAnativo);
        Assert.assertFalse(servicoService.ServicosInativos(servicoEntities));
    }

    @Test
    public void listaServicoInativosComListaMista() {
        List<ServicoEntity> servicoEntities = new ArrayList<>();
        ServicoEntity servicoAnativo = new ServicoEntity();
        ServicoEntity servicoInativo = new ServicoEntity();
        servicoAnativo.setStatus(TipoStatus.ATIVO);
        servicoInativo.setStatus(TipoStatus.INATIVO);
        servicoEntities.add(servicoAnativo);
        servicoEntities.add(servicoInativo);
        Assert.assertFalse(servicoService.ServicosInativos(servicoEntities));
    }
}