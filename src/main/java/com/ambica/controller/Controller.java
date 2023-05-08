package com.ambica.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.websocket.server.PathParam;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ambica.entity.*;
import com.ambica.util.CommonUtil;

@org.springframework.stereotype.Controller
public class Controller {

@Autowired
WeddingRepo repo;

@Autowired
UserRepo lrepo;

@Autowired
WatchRepo wrepo;

@Autowired
CommonUtil util;
	
	@RequestMapping(value="/",method = RequestMethod.GET )
	public String homePage(Model model)
	{
		System.out.println("GET Request in root path..");
		List<WeddingDetails > wedL=repo.getLatestDetails(5);
		System.out.println("size "+ wedL.size());
		
		wedL.forEach(w -> w.count=wrepo.getWatchCount(w.getWeddingId())) ;
		wedL.forEach(a->System.out.println(a));
		model.addAttribute("wed_list", wedL);
		
		return "home";
	}
	
	@GetMapping("/login")
	//@ResponseBody
	String test()
	{
		return "Login";
	}
	
	@GetMapping("/live/{name}")
	String getFullWedding(@PathVariable String name,Model model,HttpServletRequest req)
	{
		WeddingDetails wd=repo.getWeddingDetailsName(name);
		System.out.println("fetching live wedding of "+name);
		if(wd==null )
		{
			System.out.println("Found no data with "+name);
			return "error";
		}
		model.addAttribute("item", wd);
		model.addAttribute("wedMsg", wd.getFirstName()+"\n weds \n"+wd.getSecondName() )	;	
		System.out.println(wd);
		
		WatchDetails wad=new WatchDetails(wd.weddingId, req.getRemoteAddr() ,new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) ) ;
		wrepo.save(wad);
		return "fullWedding";
	}
	
	@GetMapping("/userAuth")
	@ResponseBody
	ResponseEntity<String>  authUser(@RequestParam String id,@RequestParam String pwd,HttpServletRequest request )
	{	
	  // System.out.println(id+" ,"+pwd);
	   UserLogin ul= lrepo.fetchUserData(id);
	   //System.out.println(ul);
	   
	 	if(ul!=null)
	 	{
	 		if(ul.pwd.equals(pwd))
	 		{
	 			System.out.println("Auth success");
	 			request.getSession().setAttribute("sessionId", id);
	 			request.getSession().setAttribute("name", ul.firstName );
	 			SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	 			String date=sdf.format(new Date());
	 			System.out.println(" current time :"+date+" lld "+ul.getLld()  );
	 			ul.setLld(date);
	 			lrepo.save(ul);
	 			return new ResponseEntity<>("success",HttpStatus.OK);	 			
	 		}
	 		else
	 		{
	 			System.out.println("Auth failure");
	 			return new ResponseEntity<>("Invalid login or password",HttpStatus.FORBIDDEN );
	 		}	 		
	 	}
	 	System.out.println("User not found");
	 	return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND );
	}
	
	@GetMapping("/adminHome")
	String getAdminPage(Model model,HttpServletRequest req )
	{
		System.out.println("session id "+req.getSession().getAttribute("sessionId")+" , IP - "+req.getLocalAddr() );
		//model.addAttribute("sessionKey", req.getSession().getAttribute("sessionId") );
		/*
		 * if(req.getSession().getAttribute("sessionId")==null) { return "error"; }
		 */
		List<WeddingDetails> wl=repo.getLatestDetails(10);
		
		wl.forEach(w -> w.count=wrepo.getWatchCount(w.getWeddingId())) ;
		model.addAttribute("wed_list",wl);
		return "AdminHome";
		
	}
	
	@GetMapping("/logout")
	String invalidateSession(HttpServletRequest req )
	{
		req.getSession().invalidate();
		System.out.println("invalidating session");
		return "redirect:/";
		
	}
	
	@GetMapping("/createEvent")
	String getEventForm(Model model,HttpServletRequest req)
	{		
		return "CreateEvent";		
	}
	
	@PostMapping("/createEvent")
	String createEvent(HttpServletRequest req,Model model) throws IOException, ServletException 
	{
		
		try
		{
		WeddingDetails newWedding=util.generateWedding(req);
		System.out.println(newWedding);
		repo.save(newWedding);
		
		model.addAttribute("title", "Details Added");
		model.addAttribute("data", "Added "+newWedding.title+" details to DB.");
		return "info";	
		}
		catch(Exception e)
		{
			System.out.println("error : "+e.getMessage());
			model.addAttribute("error", e.getMessage());
			return "adminError";
		}
	}
	
	@GetMapping("/modifyEvent")
	String getModifyForm(@RequestParam long id,Model model)
	{		
		try
		{
		WeddingDetails wd= repo.getWeddingDetails(id);
		System.out.println(wd);
		model.addAttribute("wed", wd);
		return "modifyDetails";
		}
		catch(Exception e)
		{
			System.out.println("error : "+e.getMessage());
			model.addAttribute("error", e.getMessage());
			return "adminError";
		}
	}
	
	@PostMapping("/modifyEvent")
	String modifyeventDetails(Model model,HttpServletRequest req) throws IOException, ServletException
	{		
		
		WeddingDetails reqObj=util.generateWedding(req);
		WeddingDetails dbObj= repo.getWeddingDetails(Long.parseLong( req.getParameter("wid")));
		reqObj.weddingId=dbObj.weddingId;
		reqObj.ctime =dbObj.ctime;
		reqObj.mtime =new Date();
		
		if(reqObj.invitePic==null)
		{
			reqObj.invitePic=dbObj.invitePic;
		}
		
		if(reqObj.wedPic ==null)
		{
			reqObj.wedPic=dbObj.wedPic;
		}
		
		System.out.println(reqObj);
		repo.save(reqObj);
		model.addAttribute("title", "Details Updated");
		model.addAttribute("data", "Updated details of wedding id "+reqObj.weddingId+" in DB.");
		return "info";
	}
	
	@GetMapping("/search")
	String searchWedding(@RequestParam String value,Model model)
	{
		
		List<WeddingDetails> lst=repo.getDetailsByName(value);
		System.out.println("searching with "+value + "found "+lst.size()+" results");
		model.addAttribute("wed_list", lst);
		return "searchResults";
	}
	
	@DeleteMapping("/deleteWedding")
	ResponseEntity<String>  deleteWedding(@RequestParam long id,HttpServletRequest req)
	{
		System.out.println("deleting wedding with "+id);
		
		if(req.getSession().getAttribute("sessionId")!=null)
		{
		System.out.println("bkp output"+repo.takeBkp(id));
		repo.deleteById(id);
		return new ResponseEntity<String>("Weddind data deleted sucessfully",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("You are not allowed to perform this operation",HttpStatus.FORBIDDEN);
			
		}
	}
}
