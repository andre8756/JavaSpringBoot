package br.com.andre.springJava;

import br.com.andre.springJava.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringJavaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringJavaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal();
		principal.exibeMenu();

	}
}
