package com.dbc.walletapi.repository;


import com.dbc.walletapi.entity.RegraEntity;
import com.dbc.walletapi.entity.ServicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicoRepository extends JpaRepository<ServicoEntity, Integer> {

    @Query(value = "select * from servico s where s.status = 0 and s.id_servico = :idServico", nativeQuery = true)
    Optional<ServicoEntity> getServicoById(Integer idServico);

    @Query(value = "select * from servico s where s.status = 0", nativeQuery = true)
    List<ServicoEntity> getServicosAtivos();
}
