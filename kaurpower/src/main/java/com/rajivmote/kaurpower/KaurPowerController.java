package com.rajivmote.kaurpower;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KaurPowerController {
	
	@RequestMapping("/")
	public String ping() {
		return "Pong from KaurPowerController";
	}

}
