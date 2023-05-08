package com.ambica.entity;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WatchRepo extends JpaRepository<WatchDetails, Long> {
	
	@Query(value="select count(DISTINCT ip_addr ) from wed_watch_details t where wedding_id=?1  " , nativeQuery = true)
	public long getWatchCount(long id);
	
	@Query(value="select new map(t.weddingId as wedId , count(DISTINCT t.ipAdd ) as cnt ) from wed_watch_details t group by t.weddingId ")
	public Map<?,?> getAllCount();
}
