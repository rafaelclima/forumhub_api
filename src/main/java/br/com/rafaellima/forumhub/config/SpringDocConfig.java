package br.com.rafaellima.forumhub.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("ForumHub API")
						.description(
								"API RESTful para um fórum de discussões desenvolvida com Spring Boot. " +
										"Permite gerenciamento de usuários, tópicos e respostas com autenticação JWT.")
						.version("1.0.0")
						.contact(new Contact()
								.name("Rafael Lima")
								.email("rafaelclima.ti@gmail.com")
								.url("https://github.com/rafaelclima"))
						.license(new License()
								.name("MIT License")
								.url("https://opensource.org/licenses/MIT")))
				.addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
				.components(new Components()
						.addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
	}

	private SecurityScheme createAPIKeyScheme() {
		return new SecurityScheme()
				.type(SecurityScheme.Type.HTTP)
				.bearerFormat("JWT")
				.scheme("bearer")
				.description("Insira o token JWT obtido através do endpoint de login. " +
						"Formato: Bearer {seu_token_aqui}");
	}
}
