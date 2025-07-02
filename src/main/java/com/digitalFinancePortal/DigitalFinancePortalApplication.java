package com.digitalFinancePortal;


import com.digitalFinancePortal.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DigitalFinancePortalApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DigitalFinancePortalApplication.class, args);
		UserRepository userRepository= context.getBean(UserRepository.class);
	}

}
