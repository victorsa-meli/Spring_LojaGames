package com.generation.lojagames.repository;


import com.generation.lojagames.model.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categorias, Long> {

    public List<Categorias> findAllByTipoContainingIgnoreCase(@Param("tipo") String tipo);
}
