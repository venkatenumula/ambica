package com.ambica.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import com.ambica.entity.WeddingDetails;

@Component
public class CommonUtil {
	
	public WeddingDetails generateWedding(HttpServletRequest req) throws IOException, ServletException
	{		
		WeddingDetails newWedding=new WeddingDetails();	
		
		newWedding.firstName=req.getParameter("fname").replaceAll(" ", "") ;
		newWedding.secondName=req.getParameter("sname") .replaceAll(" ", "");
		newWedding.title=req.getParameter("title") ;
		newWedding.type=req.getParameter("type") ;
		newWedding.eventDate=req.getParameter("eventdate") ;
		newWedding.eventVenue=req.getParameter("venue") ;
		newWedding.recDate=req.getParameter("recdate") ;
		
		newWedding.teaserLink =req.getParameter("tcode") ;
		newWedding.wedLink =req.getParameter("wcode") ;
		newWedding.recpLink =req.getParameter("rcode") ;
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String date=sdf.format(new Date());
		newWedding.ctime=new Date();
		newWedding.mtime=new Date();
		System.out.println("new wedd:"+newWedding);
		InputStream pic=null;
		byte[] sourceBytes ;
		
		try {
	    
		Part file1=req.getPart("ipic");
		Part file2=req.getPart("cpic");	
		
		System.out.println("pic1:"+file1.getSize() + " pic2:"+file2.getSize());
		
		if(file1.getSize()!=0 )
		{
		pic = file1.getInputStream();
		sourceBytes = IOUtils.toByteArray(pic);		
		newWedding.invitePic = Base64.getEncoder().encodeToString(sourceBytes);
		System.out.println("Invitation picture size (MB): "+file1.getSize()/1048576.0 );
		}
		
		if(file2.getSize()!=0 )
		{
		pic = file2.getInputStream();
		sourceBytes = IOUtils.toByteArray(pic);		
		newWedding.wedPic = Base64.getEncoder().encodeToString(sourceBytes);
		System.out.println("Couple picture size (MB): "+file2.getSize()/1048576.0  );
		}
		}
		finally
		{
			if(pic!=null)
			pic.close();			
		}				
		return newWedding;
		
	}
	
	public WeddingDetails compareAndBuild(WeddingDetails newObj,WeddingDetails dbObj)
	{
		
		if(newObj.invitePic==null)
		{
			newObj.invitePic=dbObj.invitePic;
		}
		
		if(newObj.wedPic ==null)
		{
			newObj.wedPic=dbObj.wedPic;
		}
		return null;
	}

}
