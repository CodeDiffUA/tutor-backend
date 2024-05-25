package dev.backend.tutor;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutorApplication.class, args);
	}


//	@Bean
//	public MongoClient mongoClient() {
//		return MongoClients.create("mongodb+srv://shraierbohdan:9j4KkGxhiJB6VVq7@zno.refsfwm.mongodb.net/");
//	}
}
