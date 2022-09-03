package com.example.algotask.model;

import javax.persistence.Entity;
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
@Table(name="category")
public class Category {

	@Id
	@NotBlank(message = "must not be null")
	private String productcategory;

	@NotBlank(message = "must not be null")
	private String discount;

	@NotBlank(message = "must not be null")
	private String gst;

	@NotBlank(message = "must not be null")
	private String deliverycharge;



}
