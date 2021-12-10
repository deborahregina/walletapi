package com.dbc.walletapi.repository;

import com.dbc.walletapi.entity.GerenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GerenteRepository extends JpaRepository<GerenteEntity, Integer> {

    @Query(value = "select * from gerente g where g.status = 0 ", nativeQuery = true)
    List<GerenteEntity> listaGerentesAtivos();

    @Query(value = "select * from gerente g where g.status = 0 and g.id_gerente = :idGerente", nativeQuery = true)
    GerenteEntity getGerenteById(Integer idGerente);


}
