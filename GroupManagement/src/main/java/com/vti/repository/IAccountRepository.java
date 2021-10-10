package com.vti.repository;

import com.vti.entity.Account;
import com.vti.entity.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account,Integer>, JpaSpecificationExecutor<Account> {
    Account findByUsername(String username);

//    public boolean existsByUserName(String username);
//
//    public boolean existsByEmail(String email);

    @Query("	SELECT 	status 		"
            + "	FROM 	Account 		"
            + " WHERE 	email = :email")
    AccountStatus findStatusByEmail(String email);

    Account findByEmail(String email);
}
