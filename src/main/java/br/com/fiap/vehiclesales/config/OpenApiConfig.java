package br.com.fiap.vehiclesales.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI api(@Value("${app.security.keycloak.external-url}") String keycloakUrl) {
        OAuthFlow authorizationCode = new OAuthFlow()
                .authorizationUrl(keycloakUrl + "/protocol/openid-connect/auth")
                .tokenUrl(keycloakUrl + "/protocol/openid-connect/token")
                .scopes(new Scopes().addString("openid", "Identidade OpenID Connect"));

        SecurityScheme oauth2 = new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .description("Login pelo Keycloak usando Authorization Code + PKCE")
                .flows(new OAuthFlows().authorizationCode(authorizationCode));

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("keycloak", oauth2))
                .info(new Info()
                        .title("Vehicle Sales API")
                        .version("v1")
                        .description("API do Tech Challenge SOAT para cadastro e venda de veículos")
                        .contact(new Contact().name("Equipe SOAT")));
    }
}
