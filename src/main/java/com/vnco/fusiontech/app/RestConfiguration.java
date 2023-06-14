package com.vnco.fusiontech.app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.Type;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config
                .exposeIdsFor(em.getMetamodel().getEntities()
                        .stream().map(Type::getJavaType).toArray(Class[]::new));

        cors.addMapping("/**")
                .allowCredentials(false).maxAge(3600)
                .allowedMethods("*")
            
         .allowedOrigins("http://localhost:3000");
    }

}
