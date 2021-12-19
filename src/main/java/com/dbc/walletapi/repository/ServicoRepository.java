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

    @Query(value = " select * from servico s where extract(year from s.data_criacao) = :ano and extract(month from s.data_criacao) = :mes and s.status = 0", nativeQuery = true)
    List<ServicoEntity> getServicosPorMesEAno(Integer ano, Integer mes);
}
