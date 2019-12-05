package com.java18.movienight.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SPAController {
  @RequestMapping(value = "/{path:[^\\.]*}", method = RequestMethod.GET)
  public String redirect() {
    return "forward:/";
  }

  @RequestMapping(value = "/auction/login.js", method = RequestMethod.GET)
  public String errorAuctionPath(){
    return "forward:/";
  }
}
