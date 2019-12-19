package com.java18.movienight.controllers;

import com.java18.movienight.entities.RequestLog;
import com.java18.movienight.repositories.RequestLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;




@Controller
public class SPAController {

  @Autowired
  RequestLogRepo logRepo;


  @RequestMapping(value = "/{path:[^\\.]*}", method = RequestMethod.GET)
  public String redirect() {
    RequestLog log = new RequestLog("/{path:[^\\.]*}", "GET");
    log.statusCode = "301";
    logRepo.save(log);
    return "forward:/";
  }

  @RequestMapping(value = "/auction/login.js", method = RequestMethod.GET)

  public String errorAuctionPath(){
    RequestLog log = new RequestLog("/auction/login.js", "GET");
    log.statusCode = "301";
    logRepo.save(log);
    return "forward:/";
  }
}
