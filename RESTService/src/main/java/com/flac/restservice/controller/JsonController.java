package com.flac.restservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonController {

  @RequestMapping("/")
  public String hello() {
    return "Hello world!";
  }
}
