package com.example.algotask.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductResponse {

	private String productId;

	private String name;

	private String productType;

	private String category;

	private String basePrice;

	private String discount;

	private Map<String, String> charges;

	private String finalPrice;
}
