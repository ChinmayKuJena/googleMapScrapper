package com.googleMapScapper.googleMapScapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.googleMapScapper.googleMapScapper.Service.ScrapingService;

@SpringBootApplication
public class GoogleMapScapperApplication implements CommandLineRunner {
	@Autowired
	private ScrapingService scrapingService;

	public static void main(String[] args) {
		SpringApplication.run(GoogleMapScapperApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// System.out.println(
		// scrapingService.scrape("Rairangpur").toString());

	}
}
