
package com.faculdade.baozi_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faculdade.baozi_store.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}