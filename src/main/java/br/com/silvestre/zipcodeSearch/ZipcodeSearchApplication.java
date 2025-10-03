package br.com.silvestre.zipcodeSearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "br.com.silvestre.zipcodeSearch")
@EnableJpaRepositories(basePackages = "br.com.silvestre.zipcodeSearch.repository")
public class ZipcodeSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipcodeSearchApplication.class, args);
	}

}
