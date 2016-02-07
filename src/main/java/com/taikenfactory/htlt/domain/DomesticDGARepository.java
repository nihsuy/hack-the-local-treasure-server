package com.taikenfactory.htlt.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface DomesticDGARepository extends JpaRepository<DomesticDGA, Long> {
	Collection<Object> getSumsByMeshDateTime();
	Collection<Object> getSumsByMeshDateTimeGender();
	Collection<Object> getSumsByMeshDateTimeAge();
	Collection<Object> getSumsByDateTime();
	Collection<Object> getSumsByDateTimeGender();
	Collection<Object> getSumsByDateTimeAge();
	List<Object> getHotRatiosByGender(@Param("gender") String gender);
	List<Object> getHotRatiosByAge(@Param("age") String age);
}
