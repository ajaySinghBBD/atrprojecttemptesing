package com.kindnesskattle.bddAtcProject.Repository;


import com.kindnesskattle.bddAtcProject.Entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {


}

