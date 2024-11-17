package com.domain.store.repository;

import com.domain.store.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Role, Long> {
}