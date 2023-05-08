package com.ambica.entity;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

@Entity(name="wedding_details")
public class WeddingDetails {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="wedding_id")
	public Long weddingId;
		
	@Column(name="f_name")
	public String firstName;
	
	@Column(name="l_name")
	public String secondName;
	
	@Column(name="title")
	public String title;
	
	@Column(name="event_type")
	public String type;
	
	@Column(name="event_date")
	public String eventDate;
	
	@Column(length=100000,name="inv_pic")
	public String invitePic;
	
	@Column(length=100000,name="wed_pic")
	public String wedPic;

	@Column(name="event_venue")
	public String eventVenue; 
	
	@Column(name="teaser_link")
	public String teaserLink;
	
	@Column(name="wed_link")
	public String wedLink;
	
	@Column(name="rec_date")
	public String recDate;
	
	@Column(name="recp_link")
	public String recpLink;
	
	@Column(name="ctime")
	public Date ctime; 
	
	@Column(name="mtime")
	public Date mtime; 
	
	@Transient
	public long count;

	public Long getWeddingId() {
		return weddingId;
	}

	public void setWeddingId(Long weddingId) {
		this.weddingId = weddingId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getInvitePic() {
		return invitePic;
	}

	public void setInvitePic(String invitePic) {
		this.invitePic = invitePic;
	}

	public String getWedPic() {
		return wedPic;
	}

	public void setWedPic(String wedPic) {
		this.wedPic = wedPic;
	}

	public String getEventVenue() {
		return eventVenue;
	}

	public void setEventVenue(String eventVenue) {
		this.eventVenue = eventVenue;
	}

	public String getTeaserLink() {
		return teaserLink;
	}

	public void setTeaserLink(String teaserLink) {
		this.teaserLink = teaserLink;
	}

	public String getWedLink() {
		return wedLink;
	}

	public void setWedLink(String wedLink) {
		this.wedLink = wedLink;
	}

	public String getWedDate() {
		return recDate;
	}

	public void setWedDate(String wedDate) {
		this.recDate = wedDate;
	}

	public String getRecpLink() {
		return recpLink;
	}

	public void setRecpLink(String recpLink) {
		this.recpLink = recpLink;
	}
	
	public long getPostedTime()
	{
		Date now=new Date();
		if(this.ctime ==null)
			return 0;
		long days_diff=(now.getTime()-this.ctime.getTime()) /(1000 * 60 * 60 * 24)%365;
		//System.out.println("" );
		return days_diff;
	}
	
	@Override
	public String toString() {
		
		return "WeddingDetails [weddingId=" + weddingId + ", firstName=" + firstName + ", secondName=" + secondName
				+ ", title "+title+ ", type "+type+ ", eventDate=" + eventDate  + ", eventVenue=" + eventVenue + ", teaserLink=" + teaserLink + ", wedLink="
				+ wedLink + ", recDate=" + recDate + ", recpLink=" + recpLink + ",count ="+count+"]";
	}	
	
}
