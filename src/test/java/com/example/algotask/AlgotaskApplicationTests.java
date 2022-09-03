package com.example.algotask;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.algotask.model.Product;
import com.example.algotask.repository.ProductRepository;


@SpringBootTest
class AlgotaskApplicationTests {

	@Autowired
	ProductRepository pRepository;

	@Test
	void testCreate() {
		Product prod = new Product();
		prod.setProductname("US Polo Assn");
		prod.setProducttype("T-Shirt");
		prod.setProductcategory("Clothing");
		prod.setProductprice(1200L);
		pRepository.save(prod);
		assertNotNull(prod);
		assertThat(prod.getProductid()).isNotEqualTo(null);

	}

	@Test
	void getAllProducts() {
		Product prod1 = new Product();
		prod1.setProductname("testname1");
		prod1.setProducttype("testtype1");
		prod1.setProductcategory("testcategory1");
		prod1.setProductprice(1200L);
		pRepository.save(prod1);

		Product prod2 = new Product();
		prod2.setProductname("testname2");
		prod2.setProducttype("testtype2");
		prod2.setProductcategory("testcategory2");
		prod2.setProductprice(1200L);
		pRepository.save(prod2);

		List<Product> products = pRepository.findAll();
		assertNotNull(products);
		assertEquals(2,products.size());
	}

	@Test
	void getProductById() {
		Product prod = new Product();
		prod.setProductname("testname3");
		prod.setProducttype("testtype3");
		prod.setProductcategory("testcategory");
		prod.setProductprice(1200L);
		pRepository.save(prod);

		Product product = pRepository.findById(prod.getProductid()).get();

		assertNotNull(product);
		assertEquals("testname3", product.getProductname());
		assertEquals("testtype3", product.getProducttype());
		assertEquals("testcategory", product.getProductcategory());
		assertNotNull(product.getProductprice());
	}

	@Test
	void updateProduct() {
		Product prod = new Product();
		prod.setProductname("testname4");
		prod.setProducttype("testtype4");
		prod.setProductcategory("testcategory4");
		prod.setProductprice(1200L);
		pRepository.save(prod);

		Product product = pRepository.findById(prod.getProductid()).get();
		product.setProducttype("testtype4update");

		Product newProduct = pRepository.save(product);

		assertEquals("testtype4update",newProduct.getProducttype());
		assertEquals("testname4",newProduct.getProductname());
	}

	@Test
	void deleteProduct() {
		Product prod1 = new Product();
		prod1.setProductname("testname8");
		prod1.setProducttype("testtype8");
		prod1.setProductcategory("testcategory8");
		prod1.setProductprice(1200L);
		pRepository.save(prod1);
		Long id = prod1.getProductid();

		Product prod2 = new Product();
		prod2.setProductname("testname9");
		prod2.setProducttype("testtype9");
		prod2.setProductcategory("testcategory9");
		prod2.setProductprice(1200L);
		pRepository.save(prod2);

		pRepository.delete(prod1);
		Optional<Product> existingProduct = pRepository.findById(id);

		List<Product> list = pRepository.findAll();

		assertEquals(1,list.size());
		assertThat(existingProduct).isEmpty();
	}

}
