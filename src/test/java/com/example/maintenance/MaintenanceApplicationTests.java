package com.example.maintenance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MaintenanceApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	/**
	 * Проверяем наличие фразы приветствия на домашней странце
	 */
	@Test
	public void homePageExistAndContainsWelcomeMessage() {
		assertThat(
				this.restTemplate.getForObject("http://localhost:" + port, String.class)
		).contains("Добро пожаловать");
	}
}
