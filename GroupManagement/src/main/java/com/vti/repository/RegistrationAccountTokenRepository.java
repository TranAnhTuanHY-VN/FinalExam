package com.vti.repository;

import com.vti.entity.RegistrationAccountToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface RegistrationAccountTokenRepository extends JpaRepository<RegistrationAccountToken, Integer> {

	public RegistrationAccountToken findByToken(String token);

	public boolean existsByToken(String token);
	
	@Query("	SELECT 	token	"
			+ "	FROM 	RegistrationAccountToken "
			+ " WHERE 	account.id = :userId")
	public String findByUserId(int userId);

	@Transactional
	@Modifying
	@Query("	DELETE 							"
			+ "	FROM 	RegistrationAccountToken 	"
			+ " WHERE 	account.id = :userId")
	public void deleteByUserId(int userId);

}
