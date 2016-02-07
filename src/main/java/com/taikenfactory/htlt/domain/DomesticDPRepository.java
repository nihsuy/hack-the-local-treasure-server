package com.taikenfactory.htlt.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DomesticDPRepository extends JpaRepository<DomesticDP, Long> {
	Collection<Object> getSumsByMeshDateTime();
	Collection<Object> getSumsByDateTime();
	Collection<Object> getSumsByDateTimePrefecture();
	List<Object> getHotRatiosByLocal();
}
