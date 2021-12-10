package com.dbc.walletapi.repository;

import com.dbc.walletapi.entity.RegraEntity;
import com.dbc.walletapi.entity.ServicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<ServicoEntity, Integer> {
}
