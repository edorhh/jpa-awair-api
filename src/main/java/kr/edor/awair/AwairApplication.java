package kr.edor.awair;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
@PropertySource("classpath:application.properties")
public class AwairApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwairApplication.class, args);
	}

}
