package com.dbc.walletapi.repository;

import com.dbc.walletapi.entity.ServicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<ServicoEntity, Integer> {


    @Query(value = " select * from servico s where s.status = 0 order by s.data_criacao", nativeQuery = true)
    List<ServicoEntity> getServicosAtivos();

    @Query(value = " select * from servico s where s.status = 0 and s.id_gerente = :idGerente", nativeQuery = true)
    List<ServicoEntity> getServicosAtivosIdGerente(Integer idGerente);

    //soma total
    @Query(value = " select * from servico s where extract(year from s.data_criacao) = :ano and extract(month from s.data_criacao) <= :mes", nativeQuery = true)
    List<ServicoEntity> getServicosPorMesEAnoAtivosInativos(Integer ano, Integer mes);

    // deletar somas status = 1
    @Query(value = "select * from servico s where extract(month from s.data_delete) < :mes and extract(year from s.data_delete) = :ano", nativeQuery = true)
    List<ServicoEntity> getServicosPorMesEAnoInativos(Integer ano, Integer mes);

    @Query(value = " select * from servico s where extract(year from s.data_criacao) = :ano and extract(month from s.data_criacao) <= :mes and s.id_gerente = :idGerente", nativeQuery = true)
    List<ServicoEntity> getServicosPorMesEAnoEIDGerenteAtivoEInativo(Integer ano, Integer mes, Integer idGerente);

    @Query(value = " select * from servico s where extract(month from s.data_delete) < :mes and extract(year from s.data_delete) = :ano and s.id_gerente = :idGerente", nativeQuery = true)
    List<ServicoEntity> getServicosPorMesEAnoEIDGerenteInativo(Integer ano, Integer mes, Integer idGerente);

}
