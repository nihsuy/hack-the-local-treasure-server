package com.taikenfactory.htlt.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
	name = "domesticmga",
	uniqueConstraints={ @UniqueConstraint(columnNames={ "fk_meshid", "month", "weekday", "time", "gender", "age" }) }
)
@NamedQueries({
	@NamedQuery(
		name = "DomesticMGA.getSumsByMeshMonthWeekdayTime",
		query = "SELECT dm.mesh, dm.month, dm.weekday, dm.time, SUM(dm.population) "
			+ "FROM DomesticMGA dm "
			+ "WHERE dm.gender IS NOT NULL AND dm.age IS NOT NULL "
			+ "GROUP BY dm.mesh, dm.month, dm.weekday, dm.time "
			+ "ORDER BY dm.mesh, dm.month, dm.weekday, dm.time"),
	@NamedQuery(
		name = "DomesticMGA.getSumsByMeshMonthWeekdayTimeGender",
		query = "SELECT dm.mesh, dm.month, dm.weekday, dm.time, dm.gender, SUM(dm.population) "
			+ "FROM DomesticMGA dm "
			+ "WHERE dm.gender IS NOT NULL AND dm.age IS NOT NULL "
			+ "GROUP BY dm.mesh, dm.month, dm.weekday, dm.time, dm.gender "
			+ "ORDER BY dm.mesh, dm.month, dm.weekday, dm.time, dm.gender"),
	@NamedQuery(
		name = "DomesticMGA.getSumsByMeshMonthWeekdayTimeAge",
		query = "SELECT dm.mesh, dm.month, dm.weekday, dm.time, dm.age, SUM(dm.population) "
			+ "FROM DomesticMGA dm "
			+ "WHERE dm.gender IS NOT NULL AND dm.age IS NOT NULL "
			+ "GROUP BY dm.mesh, dm.month, dm.weekday, dm.time, dm.age "
			+ "ORDER BY dm.mesh, dm.month, dm.weekday, dm.time, dm.age")
})
public class DomesticMGA {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "fk_meshid", nullable = false)
    private Mesh mesh;
    
    @Column(name = "month", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date month;
   
    @Column(name = "weekday", nullable = false)
    private String weekday;

    @Column(name = "time")
    private String time;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private String age;
    
    @Column(name = "population", nullable = false)
    private int population;
    
    public DomesticMGA() {
    	
    }
    
    public DomesticMGA(Mesh mesh, Date month, String weekday, String time, String gender, String age, int population) {
    	this.mesh = mesh;
    	this.month = month;
    	this.weekday = weekday;
    	this.time = time;
    	this.gender = gender;
    	this.age = age;
    	this.population = population;
    }

    public long getId() {
    	return id;
    }
    
    public Date getMonth() {
    	return month;
    }

    public String getWeekday() {
    	return weekday;
    }

    public String getTime() {
    	return time;
    }

    public String getGender() {
    	return gender;
    }

    public String getAge() {
    	return age;
    }

    public int getPopulation() {
    	return population;
    }

}
