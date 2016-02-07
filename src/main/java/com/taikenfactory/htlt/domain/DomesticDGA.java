package com.taikenfactory.htlt.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
	name = "domesticdga",
	uniqueConstraints={ @UniqueConstraint(columnNames={ "fk_meshid", "date", "time", "gender", "age" }) }
)
@NamedQueries({
	@NamedQuery(
		name = "DomesticDGA.getSumsByMeshDateTime",
		query = "SELECT dd.mesh, dd.date, dd.time, SUM(dd.population) "
			+ "FROM DomesticDGA dd "
			+ "WHERE dd.gender IS NOT NULL AND dd.age IS NOT NULL "
			+ "GROUP BY dd.mesh, dd.date, dd.time "
			+ "ORDER BY dd.mesh, dd.date, dd.time"),
	@NamedQuery(
		name = "DomesticDGA.getSumsByMeshDateTimeGender",
		query = "SELECT dd.mesh, dd.date, dd.time, dd.gender, SUM(dd.population) "
			+ "FROM DomesticDGA dd "
			+ "WHERE dd.gender IS NOT NULL AND dd.age IS NOT NULL "
			+ "GROUP BY dd.mesh, dd.date, dd.time, dd.gender "
			+ "ORDER BY dd.mesh, dd.date, dd.time, dd.gender"),
	@NamedQuery(
		name = "DomesticDGA.getSumsByMeshDateTimeAge",
		query = "SELECT dd.mesh, dd.date, dd.time, dd.age, SUM(dd.population) "
			+ "FROM DomesticDGA dd "
			+ "WHERE dd.gender IS NOT NULL AND dd.age IS NOT NULL "
			+ "GROUP BY dd.mesh, dd.date, dd.time, dd.age "
			+ "ORDER BY dd.mesh, dd.date, dd.time, dd.age"),
	@NamedQuery(
		name = "DomesticDGA.getSumsByDateTime",
		query = "SELECT dd.date, dd.time, SUM(dd.population) "
			+ "FROM DomesticDGA dd "
			+ "WHERE dd.gender IS NOT NULL AND dd.age IS NOT NULL "
			+ "GROUP BY dd.date, dd.time "
			+ "ORDER BY dd.date, dd.time"),
	@NamedQuery(
		name = "DomesticDGA.getSumsByDateTimeGender",
		query = "SELECT dd.date, dd.time, dd.gender, SUM(dd.population) "
			+ "FROM DomesticDGA dd "
			+ "WHERE dd.gender IS NOT NULL AND dd.age IS NOT NULL "
			+ "GROUP BY dd.date, dd.time, dd.gender "
			+ "ORDER BY dd.date, dd.time, dd.gender"),
	@NamedQuery(
		name = "DomesticDGA.getSumsByDateTimeAge",
		query = "SELECT dd.date, dd.time, dd.age, SUM(dd.population) "
			+ "FROM DomesticDGA dd "
			+ "WHERE dd.gender IS NOT NULL AND dd.age IS NOT NULL "
			+ "GROUP BY dd.date, dd.time, dd.age "
			+ "ORDER BY dd.date, dd.time, dd.age")
//	+ "WHERE dd.mesh = dm.mesh AND dd.date = dm.date AND dd.time = dm.time AND dd.date = :date AND dd.time = :time")
})
@NamedNativeQueries({
	@NamedNativeQuery(
		name = "DomesticDGA.getHotRatiosByGender",
		query = "SELECT m.code, dd2.population2, dd3.population3, dd4.population4 "
			+ "FROM Mesh m "
			+ "JOIN "
			+ "(SELECT dd.fk_meshid fk_meshid2, AVG(dd.population) population2 "
			+ "FROM DomesticDGA dd "
			+ "WHERE dd.gender = :gender AND dd.age IS NULL AND dd.time IN ('43', '44', '45') "
			+ "GROUP BY dd.fk_meshid ) dd2 "
			+ "ON m.id = dd2.fk_meshid2 "
			+ "JOIN "
			+ "(SELECT dd.fk_meshid fk_meshid3, AVG(dd.population) population3 "
			+ "FROM DomesticDGA dd "
			+ "WHERE dd.gender IS NULL AND dd.age IS NULL AND dd.time IN ('43', '44', '45') "
			+ "GROUP BY dd.fk_meshid ) dd3 "
			+ "ON m.id = dd3.fk_meshid3 "
			+ "JOIN "
			+ "(SELECT dd.fk_meshid fk_meshid4, AVG(dd.population) population4 "
			+ "FROM DomesticDP dd "
			+ "WHERE dd.prefecture = '28' AND dd.time IN ('41') "
			+ "GROUP BY dd.fk_meshid ) dd4 "
			+ "ON m.id = dd4.fk_meshid4 "
			+ "ORDER BY m.code"),
	@NamedNativeQuery(
		name = "DomesticDGA.getHotRatiosByAge",
		query = "SELECT m.code, dd2.population2, dd3.population3, dd4.population4 "
			+ "FROM Mesh m "
			+ "JOIN "
			+ "(SELECT dd.fk_meshid fk_meshid2, AVG(dd.population) population2 "
			+ "FROM DomesticDGA dd "
			+ "WHERE dd.gender IS NULL AND dd.age = :age AND dd.time IN ('43', '44', '45') "
			+ "GROUP BY dd.fk_meshid ) dd2 "
			+ "ON m.id = dd2.fk_meshid2 "
			+ "JOIN "
			+ "(SELECT dd.fk_meshid fk_meshid3, AVG(dd.population) population3 "
			+ "FROM DomesticDGA dd "
			+ "WHERE dd.gender IS NULL AND dd.age IS NULL AND dd.time IN ('43', '44', '45') "
			+ "GROUP BY dd.fk_meshid ) dd3 "
			+ "ON m.id = dd3.fk_meshid3 "
			+ "JOIN "
			+ "(SELECT dd.fk_meshid fk_meshid4, AVG(dd.population) population4 "
			+ "FROM DomesticDP dd "
			+ "WHERE dd.prefecture = '28' AND dd.time IN ('41') "
			+ "GROUP BY dd.fk_meshid ) dd4 "
			+ "ON m.id = dd4.fk_meshid4 "
			+ "ORDER BY m.code")
})
public class DomesticDGA {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "fk_meshid", nullable = false)
    private Mesh mesh;
    
    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
   
    @Column(name = "time")
    private String time;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private String age;
    
    @Column(name = "population", nullable = false)
    private int population;
    
    public DomesticDGA() {
    	
    }
    
    public DomesticDGA(Mesh mesh, Date date, String time, String gender, String age, int population) {
    	this.mesh = mesh;
    	this.date = date;
    	this.time = time;
    	this.gender = gender;
    	this.age = age;
    	this.population = population;
    }

    public long getId() {
    	return id;
    }
    
    public Date getDate() {
    	return date;
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
