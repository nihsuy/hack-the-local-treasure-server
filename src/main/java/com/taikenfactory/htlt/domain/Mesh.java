package com.taikenfactory.htlt.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mesh")
public class Mesh {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(name = "code", unique = true, nullable = false)
    private String code;
    
    public Mesh() {
    	
    }
    
    public Mesh(String code) {
    	this.code = code;
    }

    public long getId() {
    	return id;
    }
    
    public String getCode() {
    	return code;
    }
    
}
