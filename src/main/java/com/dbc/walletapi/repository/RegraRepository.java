package com.dbc.walletapi.repository;

import com.dbc.walletapi.entity.RegraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegraRepository extends JpaRepository<RegraEntity, Integer> {
}
