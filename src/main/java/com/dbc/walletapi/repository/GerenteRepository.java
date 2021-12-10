package com.dbc.walletapi.repository;

import com.dbc.walletapi.entity.GerenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GerenteRepository extends JpaRepository<GerenteEntity, Integer> {
}
