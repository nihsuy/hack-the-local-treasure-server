package com.taikenfactory.htlt.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MeshRepository extends JpaRepository<Mesh, Long> {
	Optional<Mesh> findByCode(String code);
}
