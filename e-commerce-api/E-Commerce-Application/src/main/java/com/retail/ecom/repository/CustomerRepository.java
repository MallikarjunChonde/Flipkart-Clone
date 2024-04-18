package com.retail.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retail.ecom.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
