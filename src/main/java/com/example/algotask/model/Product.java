package com.example.algotask.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productid;

	@NotBlank(message = "must not be null")
	private String productname;

	@NotBlank(message = "must not be null")
	private String producttype;

	@NotBlank(message = "must not be null")
	private String productcategory;

	private Long productprice= 0L;
}
