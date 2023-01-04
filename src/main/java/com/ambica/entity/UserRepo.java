package com.ambica.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<UserLogin, String> {
	
	@Query(value="select t.* from login_tab t where ph_no=?1 " , nativeQuery = true)
	public UserLogin fetchUserData(String id);

}
