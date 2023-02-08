package com.ambica.entity;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface WeddingRepo extends JpaRepository<WeddingDetails,Long> {
	
	@Query(value="select t.* from wedding_details t order by wedding_id desc limit ?1 " , nativeQuery = true)
	public List<WeddingDetails> getLatestDetails(long limit);
	
	@Query(value="select t.* from wedding_details t where wedding_id=?1  " , nativeQuery = true)
	public WeddingDetails getWeddingDetails(long id);
	
	@Query(value="select t.* from wedding_details t where upper(replace(f_name,' ','')||replace(l_name,' ',''))=upper(?1) order by wedding_id desc limit 1" , nativeQuery = true)
	public WeddingDetails getWeddingDetailsName(String name);
	
	@Query(value="select t.* from wedding_details t where upper(f_name) like '%'||?1||'%' or upper(l_name) like '%'||?1||'%' or wedding_id=?1 " , nativeQuery = true)
	public List<WeddingDetails> getDetailsByName(String name);
	
	@Modifying
	@Query(value="insert into wedding_bkp select wedding_id,f_name,l_name,event_date,event_venue,teaser_link,wed_link,recp_link,rec_date,DATETIME('now') from wedding_details where wedding_id=?1",nativeQuery=true)
	public int takeBkp(long id);
}
