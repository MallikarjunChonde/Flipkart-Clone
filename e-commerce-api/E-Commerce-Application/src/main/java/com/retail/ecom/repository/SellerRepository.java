package com.retail.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retail.ecom.model.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer>{

}
