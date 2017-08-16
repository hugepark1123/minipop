package hugepark.toy.minipop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class MinipopApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinipopApplication.class, args);
	}
}

@RestController
class Hello {
	
	@GetMapping("/hello")
	public String hello() {
		return "hello world";
	}
}
