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
	name = "domesticdp",
	uniqueConstraints={ @UniqueConstraint(columnNames={ "fk_meshid", "date", "time", "prefecture" }) }
)
@NamedQueries({
	@NamedQuery(
		name = "DomesticDP.getSumsByMeshDateTime",
		query = "SELECT dd.mesh, dd.date, dd.time, SUM(dd.population) "
			+ "FROM DomesticDP dd "
			+ "WHERE dd.prefecture IS NOT NULL "
			+ "GROUP BY dd.mesh, dd.date, dd.time "
			+ "ORDER BY dd.mesh, dd.date, dd.time"),
	@NamedQuery(
		name = "DomesticDP.getSumsByDateTime",
		query = "SELECT dd.date, dd.time, SUM(dd.population) "
			+ "FROM DomesticDP dd "
			+ "WHERE dd.prefecture IS NOT NULL "
			+ "GROUP BY dd.date, dd.time "
			+ "ORDER BY dd.date, dd.time"),
	@NamedQuery(
		name = "DomesticDP.getSumsByDateTimePrefecture",
		query = "SELECT dd.date, dd.time, dd.prefecture, SUM(dd.population) "
			+ "FROM DomesticDP dd "
			+ "WHERE dd.prefecture IS NOT NULL "
			+ "GROUP BY dd.date, dd.time, dd.prefecture "
			+ "ORDER BY dd.date, dd.time, dd.prefecture")
})
@NamedNativeQueries({
	@NamedNativeQuery(
		name = "DomesticDP.getHotRatiosByLocal",
		query = "SELECT m.code, dd2.population2, dd3.population3, dd4.population4 "
			+ "FROM Mesh m "
			+ "JOIN "
			+ "(SELECT dd.fk_meshid fk_meshid2, AVG(dd.population) population2 "
			+ "FROM DomesticDP dd "
			+ "WHERE dd.prefecture = '28' AND dd.time IN ('43', '44', '45') "
			+ "GROUP BY dd.fk_meshid ) dd2 "
			+ "ON m.id = dd2.fk_meshid2 "
			+ "JOIN "
			+ "(SELECT dd.fk_meshid fk_meshid3, AVG(dd.population) population3 "
			+ "FROM DomesticDP dd "
			+ "WHERE dd.prefecture IS NULL AND dd.time IN ('43', '44', '45') "
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
public class DomesticDP {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "fk_meshid", nullable = false)
    private Mesh mesh;
    
    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
   
    @Column(name = "time", nullable = false)
    private String time;

    @Column(name = "prefecture")
    private String prefecture;

    @Column(name = "population", nullable = false)
    private int population;

    public DomesticDP() {
    
    }
    
    public DomesticDP(Mesh mesh, Date date, String time, String prefecture, int population) {
    
    	this.mesh = mesh;
    	this.date = date;
    	this.time = time;
    	this.prefecture = prefecture;
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

    public String getPrefecture() {
    	return prefecture;
    }

    public int getPopulation() {
    	return population;
    }

}
