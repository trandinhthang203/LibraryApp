package com.spring.demo.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String roleId;
	
	private String name;
	
	private Integer description;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	private List<User> users;
	
	public Role() {
	}

	public Role(String roleId, String name, Integer description) {
		this.roleId = roleId;
		this.name = name;
		this.description = description;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDescription() {
		return description;
	}

	public void setDescription(Integer description) {
		this.description = description;
	}


}
