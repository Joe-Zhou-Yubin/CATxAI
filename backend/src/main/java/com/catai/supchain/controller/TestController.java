package com.catai.supchain.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/member")
  @PreAuthorize("hasRole('MEMBER') or hasRole('MANAGER')")
  public String userAccess() {
    return "Member Content.";
  }

  @GetMapping("/manage")
  @PreAuthorize("hasRole('MANAGER')")
  public String moderatorAccess() {
    return "Manager Board.";
  }

}