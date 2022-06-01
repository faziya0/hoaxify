package com.hoaxify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hoaxify.entity.Hoax;


public interface HoaxRepository extends JpaRepository<Hoax,Integer>,JpaSpecificationExecutor<Hoax>{

	
	}
