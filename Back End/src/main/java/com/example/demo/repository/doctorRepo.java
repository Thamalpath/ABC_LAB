package com.example.demo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.doctor;

public interface doctorRepo extends JpaRepository<doctor, Long>{
	@Query(value = "SELECT * FROM doctor WHERE name = :name AND password = :password", nativeQuery = true)
    doctor findByEmailAndPassword(@Param("name") String name, @Param("password") String password);

	  @Query(value = "SELECT * FROM doctor WHERE start_time = :startTime", nativeQuery = true)
	    List<doctor> findByStartTimeGreaterThanOrEqual(@Param("startTime") String startTime);

	    @Query(value = "SELECT * FROM doctor WHERE end_time = :endTime", nativeQuery = true)
	    List<doctor> findByEndTimeLessThanOrEqual(@Param("endTime") Integer endTime);
	    	    
	    
}
