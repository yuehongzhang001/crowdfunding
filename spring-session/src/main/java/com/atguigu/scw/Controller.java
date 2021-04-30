package com.atguigu.scw;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class Controller {

	@GetMapping("/set")
	public String set(HttpSession session) {
		session.setAttribute("msg", "hello");
		return "ok";
	}
	
	@GetMapping("/get")
	public String get(HttpSession session) {
		return (String) session.getAttribute("msg");
	}
}
