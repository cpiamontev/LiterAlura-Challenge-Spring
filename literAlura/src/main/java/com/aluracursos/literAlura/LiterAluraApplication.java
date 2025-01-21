package com.aluracursos.literAlura;

import com.aluracursos.literAlura.principal.Principal;
import com.aluracursos.literAlura.repository.AutoresRepository;
import com.aluracursos.literAlura.repository.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication  implements CommandLineRunner {

	@Autowired
	private LibrosRepository repositorioLibros;
	@Autowired
	private AutoresRepository repositorioAutores;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorioLibros, repositorioAutores);
		principal.muestraElMenu();
	}
}
