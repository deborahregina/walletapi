package com.dbc.walletapi.testes;
import com.dbc.walletapi.dto.*;
import com.dbc.walletapi.entity.*;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.repository.GerenteRepository;
import com.dbc.walletapi.repository.ServicoRepository;
import com.dbc.walletapi.repository.UsuarioRepository;
import com.dbc.walletapi.service.ServicoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
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

    @Mock
    private UsuarioRepository usuarioRepository;


    private final ObjectMapper objectMapper = new ObjectMapper();


    @Before
    public void init() {
        ReflectionTestUtils.setField(servicoService, "objectMapper",objectMapper);
    }


    @DisplayName("Deletar serviço com sucesso.")
    @Test
    public void deletaServicoComSucessoIdEncontrado() throws Exception {
        ServicoEntity servicoEntity = new ServicoEntity();
        doReturn(Optional.of(servicoEntity)).when(servicoRepository).findById(anyInt());
        servicoService.delete(2);
        Assertions.assertEquals(TipoStatus.INATIVO, servicoEntity.getStatus());
    }

    @DisplayName("Deletar serviço não cadastrado.")
    @Test(expected = RegraDeNegocioException.class)
    public void deletaServicoSemSucessoIdNaoEncontrado() throws RegraDeNegocioException {
        doReturn(Optional.empty()).when(servicoRepository).findById(anyInt());
        servicoService.delete(3);
    }

    @DisplayName("Criação de serviço com sucesso.")
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

    @DisplayName("Criação de serviço sem sucesso, pois atribuído a gerente inexistente.")
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

    @DisplayName("Atualização de serviço com sucesso.")
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

    @DisplayName("Atualização de serviço não cadastrado.")
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

    @DisplayName("Atualização de serviço sem sucesso, pois atribuído a gerente inexistente.")
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

    @DisplayName("Lista serviço com serviço cadastrado.")
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

    @DisplayName("Lista serviço com serviço não cadastrado.")
    @Test(expected = RegraDeNegocioException.class)
    public void listByIdServicoSemSucessoServicoInexistente() throws RegraDeNegocioException {
        doReturn(Optional.empty()).when(servicoRepository).findById(anyInt());
        ServicoDTO servicolist = servicoService.listById(anyInt());
        Assertions.assertNull(servicolist);
    }

    @DisplayName("Lista de serviços.")
    @Test
    public void listaServicosComSucesso() {
        List<ServicoDTO> servicosDTO =  servicoService.list();
        Assertions.assertNotNull(servicosDTO);
    }

    @DisplayName("Lista de serviços por nome com sucesso.")
    @Test
    public void listaServicosPorNomeComSucesso() {
        List<ServicoEntity> listaServicos = new ArrayList<>();

        doReturn(listaServicos).when(servicoRepository).findAll();

        List<ServicoDTO> servicoDTOList = servicoService.listByName("anyString()");
        Assertions.assertNotNull(servicoDTOList);
    }

    @DisplayName("Procura por serviços inativos em uma lista com serviços inativos.")
    @Test
    public void listaServicoInativosComListaInativa() {
        List<ServicoEntity> servicoEntities = new ArrayList<>();
        ServicoEntity servicoInativo = new ServicoEntity();

        servicoInativo.setStatus(TipoStatus.INATIVO);
        servicoEntities.add(servicoInativo);
        Assert.assertTrue(servicoService.servicosInativos(servicoEntities));
    }

    @DisplayName("Procura por serviços inativos em uma lista com serviços ativos.")
    @Test
    public void listaServicoInativosComListaAtiva() {
        List<ServicoEntity> servicoEntities = new ArrayList<>();
        ServicoEntity servicoAnativo = new ServicoEntity();
        servicoAnativo.setStatus(TipoStatus.ATIVO);
        servicoEntities.add(servicoAnativo);
        Assert.assertFalse(servicoService.servicosInativos(servicoEntities));
    }

    @DisplayName("Procura por serviços inativos em uma lista com serviços ativos e inativos.")
    @Test
    public void listaServicoInativosComListaMista() {
        List<ServicoEntity> servicoEntities = new ArrayList<>();
        ServicoEntity servicoAnativo = new ServicoEntity();
        ServicoEntity servicoInativo = new ServicoEntity();
        servicoAnativo.setStatus(TipoStatus.ATIVO);
        servicoInativo.setStatus(TipoStatus.INATIVO);
        servicoEntities.add(servicoAnativo);
        servicoEntities.add(servicoInativo);
        Assert.assertFalse(servicoService.servicosInativos(servicoEntities));
    }


    @DisplayName("Lista serviços com ID do admin, passando mes e ano.")
    @Test
    public void listaServicosIDdeADMINUsandoMesEAno() throws Exception{
       List<ServicoEntity> servicoEntities = new ArrayList<>();
       UsuarioEntity usuario = new UsuarioEntity();
       ServicoEntity servico = new ServicoEntity();


       servicoEntities.add(servico);
       doReturn(Optional.of(usuario)).when(usuarioRepository).findById(1);
       usuario.setIdUsuario(1);


       //Integer listaServicos = servicoService.listByMesEAno(2021,9,"1");
      // Assertions.assertNotNull(listaServicos);
    }

    @DisplayName("Lista serviços com ID do gerente.")
    @Test
    public void listaServicosPorIdGerenteUsandoMesEAno() throws Exception{
        List<ServicoEntity> servicoEntities = new ArrayList<>();
        ServicoEntity servico = new ServicoEntity();
        UsuarioEntity usuario = new UsuarioEntity();
        GerenteEntity gerente = new GerenteEntity();

        servico.setGerenteEntity(gerente);
        servicoEntities.add(servico);
        gerente.setIdGerente(2);
        doReturn(Optional.of(usuario)).when(usuarioRepository).findById(anyInt());
        usuario.setIdUsuario(2);
        usuario.setGerenteEntity(gerente);
        gerente.setUsuario(usuario);

        //doReturn(Optional.of(gerente)).when(gerenteRepository).findById(anyInt());
        //doReturn(servicoEntities).when(servicoRepository).getServicosPorMesEAnoEIDGerente(anyInt(),anyInt(),anyInt());

        //List<ServicoDTO> listaServicos = servicoService.listByMesEAno(2021,9,"2");
        //Assertions.assertNotNull(listaServicos);
    }

    @DisplayName("Lista serviços com ID do gerente, mas usuario não encontrado.")
    @Test(expected = RegraDeNegocioException.class)
    public void listaServicosPorIdGerenteUsandoMesEAnoSemSucesso() throws Exception{
        List<ServicoEntity> servicoEntities = new ArrayList<>();
        ServicoEntity servico = new ServicoEntity();
        UsuarioEntity usuario = new UsuarioEntity();
        GerenteEntity gerente = new GerenteEntity();

        servico.setGerenteEntity(gerente);
        servicoEntities.add(servico);
        gerente.setIdGerente(2);
        doReturn(Optional.empty()).when(usuarioRepository).findById(anyInt());
        usuario.setIdUsuario(2);
        usuario.setGerenteEntity(gerente);
        gerente.setUsuario(usuario);


       // List<ServicoDTO> listaServicos = servicoService.listByMesEAno(2021,9,"2");
        //Assertions.assertNull(listaServicos);
    }

}