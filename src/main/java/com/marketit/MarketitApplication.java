package com.marketit;

import com.marketit.order.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@Slf4j
@SpringBootApplication
public class MarketitApplication {

	private final ItemRepository itemRepository;

	public MarketitApplication(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(MarketitApplication.class, args);
	}

	@Bean
	@Profile("local")
	public TestInit testInit() {
		return new TestInit(itemRepository);
	}
}
