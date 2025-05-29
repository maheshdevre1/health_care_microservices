package com.health_care.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	
	
	@Query("SELECT u FROM User u WHERE u.username = :username")
	User findUserDetails(@Param("username") String username);


	//User getUserDetails(String username);

}
