package com.taikenfactory.htlt.domain;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DomesticMGARepository  extends JpaRepository<DomesticMGA, Long> {
	Collection<Object> getSumsByMeshMonthWeekdayTime();
	Collection<Object> getSumsByMeshMonthWeekdayTimeGender();
	Collection<Object> getSumsByMeshMonthWeekdayTimeAge();
}
