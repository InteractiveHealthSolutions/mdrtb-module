package org.openmrs.module.mdrtb;


public class Oblast {

	public Oblast(String name, Integer id) {
		super();
		this.name = name;
		this.id = id;
	}
	
	public Oblast() {
		// TODO Auto-generated constructor stub
	}

	String name;
	Integer id;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

}