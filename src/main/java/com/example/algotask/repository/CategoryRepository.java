package com.example.algotask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.algotask.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

}
