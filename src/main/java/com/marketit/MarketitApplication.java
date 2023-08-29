package com.marketit;

import com.marketit.order.repository.ItemRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MarketitApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketitApplication.class, args);
	}

}
