package com.taikenfactory.htlt.domain;

public class HotAreaDTO {
	private String code;
	private double hotratio;
	
	public String getCode() {
		return code;
	}
	
	public double getHotratio() {
		return hotratio;
	}
	
	public HotAreaDTO(String code, double hotratio) {
		this.code = code;
		this.hotratio = hotratio;
	}
}
