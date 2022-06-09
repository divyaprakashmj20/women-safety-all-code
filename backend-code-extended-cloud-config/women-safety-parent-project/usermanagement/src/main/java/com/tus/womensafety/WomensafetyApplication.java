package com.tus.womensafety;

import com.tus.womensafety.entity.UserEntity;
import com.tus.womensafety.repository.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableResourceServer
@EnableSwagger2
public class WomensafetyApplication {

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(WomensafetyApplication.class, args);
		WomensafetyApplication womensafetyApplication = new WomensafetyApplication();
//		womensafetyApplication.loadData();
	}

//	@Bean
//	InitializingBean sendDatabase() {
//		return () -> {
//			System.out.println("TEst");
//					UserEntity userEntity = userRepository.findByEmail("admin@gmail.com").get(0);
//
////			userRepository.save(new User("John"));
////			userRepository.save(new User("Rambo"));
//		};
//	}


//	@Override
//	public void run(String... args = ["1"]) throws Exception {
//		UserEntity userEntity = userRepository.findByEmail("admin@gmail.com").get(0);
//		System.out.println(userEntity);
//	}

}
