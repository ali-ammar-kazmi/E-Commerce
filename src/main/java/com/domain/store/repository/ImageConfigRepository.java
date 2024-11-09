package com.domain.store.repository;

import com.domain.store.model.ImageConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageConfigRepository extends JpaRepository<ImageConfig, Long> {
    List<ImageConfig> findAllByProductId(Long id);
}
