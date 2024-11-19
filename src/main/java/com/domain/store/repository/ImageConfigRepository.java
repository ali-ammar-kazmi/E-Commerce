package com.domain.store.repository;

import com.domain.store.model.ImageConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageConfigRepository extends JpaRepository<ImageConfig, Long> {
    List<ImageConfig> findAllByProductId(Long id);
}
