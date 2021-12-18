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

}
