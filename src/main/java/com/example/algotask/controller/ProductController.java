package com.example.algotask.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.algotask.model.BasicResponse;
import com.example.algotask.model.Category;
import com.example.algotask.model.Product;
import com.example.algotask.model.ProductResponse;
import com.example.algotask.repository.CategoryRepository;
import com.example.algotask.repository.ProductRepository;
//import com.example.algotask.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductRepository pRepository;

	@Autowired
	private CategoryRepository cRepository;

	// fetching all products
	@GetMapping("/products")
	public List<ProductResponse> getProducts(){

		List<Product> products = pRepository.findAll();

		//creating list of productResponse which will be sent as json response
		List<ProductResponse> prList = new ArrayList<>();

		for(Product prod : products) {

			// Creating Response Object and adding all the values
			ProductResponse pr = new ProductResponse();
			pr.setProductId(prod.getProductid().toString());
			pr.setName(prod.getProductname());
			pr.setProductType(prod.getProducttype());
			pr.setCategory(prod.getProductcategory());
			pr.setBasePrice(prod.getProductprice().toString());

			System.out.println(prod.getProductprice().toString());


			// fetching the category  from the category table
		  Optional<Category> category = cRepository.findById(prod.getProductcategory());
		  if(category.isPresent()) {
			  System.out.println(category.get());
			  double dis =prod.getProductprice().doubleValue()*Double.parseDouble( StringUtils.chop(category.get().getDiscount()))/100;
			  double gst = (prod.getProductprice().doubleValue()-dis)*Double.parseDouble( StringUtils.chop(category.get().getGst()))/100;

			  pr.setDiscount(String.valueOf(dis));

			  //puting gst and delivery in the charges
			  Map<String,String> map = new HashMap<>();
			  map.put("gst",String.valueOf(gst));
			  map.put("delivery", category.get().getDeliverycharge());

			  pr.setCharges(map);
			  pr.setFinalPrice(String.valueOf(prod.getProductprice().doubleValue()-dis+gst+Double.parseDouble(category.get().getDeliverycharge())));


		  }else {
			  throw new RuntimeException("Category not found");
		  }
		  //adding productResponse object to list
		  prList.add(pr);
		}

			return prList;
     }


	//fetching single product by id
	@GetMapping("/products/{productid}")
	public ProductResponse getProduct(@PathVariable long productid){
		Optional<Product> product = pRepository.findById(productid);

		//creating response object which will be sent as json response
		ProductResponse pr = new ProductResponse();
		if (product.isPresent()) {
			//putting values into the response object
			pr.setProductId(product.get().getProductid().toString());
			pr.setName(product.get().getProductname());
			pr.setProductType(product.get().getProducttype());
			pr.setCategory(product.get().getProductcategory());
			pr.setBasePrice(product.get().getProductprice().toString());

			System.out.println(product.get().getProductprice().toString());
			//fetching the category table for gst, discount and delivery
		  Optional<Category> category = cRepository.findById(product.get().getProductcategory());
		  if(category.isPresent()) {
			  System.out.println(category.get());
			  double dis =product.get().getProductprice().doubleValue()*Double.parseDouble( StringUtils.chop(category.get().getDiscount()))/100;
			  double gst = (product.get().getProductprice().doubleValue()-dis)*Double.parseDouble( StringUtils.chop(category.get().getGst()))/100;

			  pr.setDiscount(String.valueOf(dis));

			  Map<String,String> map = new HashMap<>();
			  map.put("gst",String.valueOf(gst));
			  map.put("delivery", category.get().getDeliverycharge());

			  pr.setCharges(map);

			  pr.setFinalPrice(String.valueOf(product.get().getProductprice().doubleValue()-dis+gst+Double.parseDouble(category.get().getDeliverycharge())));


		  }else {
			  throw new RuntimeException("Category not found");
		  }

		}else {
			  throw new RuntimeException("Product not found for id"+productid);
		}
		return pr;
	}

	// adding product
	@PostMapping("/products")
	public BasicResponse saveProduct (@RequestBody Product product) {

		try {
			 pRepository.save(product);
		}catch(Exception e) {
			BasicResponse br = new BasicResponse();
			br.setSuccess(false);
			br.setMessage("Product creation failed");
			return br;
		}

		BasicResponse br = new BasicResponse();
		br.setSuccess(true);
		br.setMessage("Product create Success");
		return br;
	}


	// updating product
	@PutMapping("/products/{productid}")
	public BasicResponse updateProduct(@PathVariable Long productid,@RequestBody Product product) {

		try {
			product.setProductid(productid);
			pRepository.save(product);
		}catch(Exception e) {
			BasicResponse br = new BasicResponse();
			br.setSuccess(false);
			br.setMessage("Product update failed");
			return br;
		}

		BasicResponse br = new BasicResponse();
		br.setSuccess(true);
		br.setMessage("Product update Success");
		return br;
	}

	// deleting product
	@DeleteMapping("/products/{productid}")
	public BasicResponse deleteProduct (@PathVariable Long productid) {
		try {
			pRepository.deleteById(productid);
		}catch(Exception e) {
			BasicResponse br = new BasicResponse();
			br.setSuccess(false);
			br.setMessage("Product delete failed");
			return br;
		}

		BasicResponse br = new BasicResponse();
		br.setSuccess(true);
		br.setMessage("Product delete Success");
		return br;

	}

}
