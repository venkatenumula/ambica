package com.ambica.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity(name="wed_watch_details")
@IdClass(WatchDetails.class)
public class WatchDetails implements Serializable {
	
	@Id
	@Column(name="wedding_id")
	public Long weddingId;
	
	@Id
	@Column(name="ip_addr")
	public String ipAdd;
	
	@Id
	@Column(name="access_date")
	public String accessDate;
	
	@Override
	public String toString() {
		return "WatchDetails [weddingId=" + weddingId + ", ipAdd=" + ipAdd + ", accessDate=" + accessDate + "]";
	}

	public WatchDetails() {
		super();
	}

	public WatchDetails(Long weddingId2, String localAddr, String iaccDate) {
		this.weddingId=weddingId2;
		this.ipAdd=localAddr;
		this.accessDate=iaccDate;
	}

}
