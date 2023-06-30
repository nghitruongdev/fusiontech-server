package com.vnco.fusiontech.app;

import com.vnco.fusiontech.product.entity.VariantInventory;
import com.vnco.fusiontech.product.entity.VariantInventoryDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.Type;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ConfigurableHttpMethods;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static org.springframework.http.HttpMethod.*;

@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(em.getMetamodel().getEntities()
                              .stream().map(Type::getJavaType).toArray(Class[]::new));
    
        var exposure = config.getExposureConfiguration();
        exposure.forDomainType(VariantInventory.class)
                .withCollectionExposure(((metadata, httpMethods) -> httpMethods.disable(POST, PUT)))
                .withItemExposure(((metadata, httpMethods) -> httpMethods.disable(PUT, PATCH)))
                .disablePutForCreation();
        
        exposure.forDomainType(VariantInventoryDetail.class)
                        .withCollectionExposure(((metdata, httpMethods) ->
                                                         ConfigurableHttpMethods.NONE.enable(GET)))
                        .withItemExposure(((metdata, httpMethods) ->
                                                   ConfigurableHttpMethods.NONE.enable(GET, PATCH)));
    
    
        cors.addMapping("/**")
            .allowedOrigins("http://localhost:3000")
            //            .allowedMethods("GET", "POST", "PATCH", "DELETE")
            .allowedMethods("*")
            .allowCredentials(false)
            .maxAge(3600);
    }
    
    
}
