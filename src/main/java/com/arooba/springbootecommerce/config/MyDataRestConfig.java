package com.arooba.springbootecommerce.config;

import com.arooba.springbootecommerce.entity.*;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    @Value("${allowed.origins}")
    private String[] allowedOrigins;
    private EntityManager entityManager;

    @Autowired
    void MyDataRestConfig(EntityManager entityManagerWired) {
        entityManager = entityManagerWired;
    }
    @Override
    public void configureRepositoryRestConfiguration (
            RepositoryRestConfiguration config,
            CorsRegistry cors
    ) {
        HttpMethod[] unSupportedActions = {
                HttpMethod.PUT,
                HttpMethod.POST,
                HttpMethod.DELETE
        };

        disableHttpMethods(Product.class, config, unSupportedActions);
        disableHttpMethods(ProductCategory.class, config, unSupportedActions);
        disableHttpMethods(Country.class, config, unSupportedActions);
        disableHttpMethods(State.class, config, unSupportedActions);
        disableHttpMethods(Order.class, config, unSupportedActions);

        exposeIDs(config);

        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(allowedOrigins);
    }

    private static void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] unSupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(unSupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(unSupportedActions));
    }

    private void exposeIDs(RepositoryRestConfiguration config) {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        List<Class> entityClasses = new ArrayList<>();

        for (EntityType entityType : entities) {
            entityClasses.add(entityType.getJavaType());
        }

        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
