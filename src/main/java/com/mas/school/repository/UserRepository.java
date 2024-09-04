package com.mas.school.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mas.school.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);
    List<User> findUsersById(Long Id);

    User findByTelephone(String telephone);

    Boolean existsByUsername(String username);

    Boolean existsByTelephone(String telephone);
    
    @Query("SELECT a FROM User a WHERE " +
 		   "CAST(a.id AS string) LIKE %:keyword% OR " +
 		   "a.username LIKE %:keyword% OR " +
			"a.telephone LIKE %:keyword%")
  Page<User> searchByKeywordInAllColumns(@Param("keyword") String keyword, Pageable pageable);
	



}
