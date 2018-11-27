package com.footnotesforthefuture.simple_blog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafWebConfig {
	
	@Bean
    public ClassLoaderTemplateResolver templateResolver() {
        
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        
        templateResolver.setPrefix("BOOT-INF/classes/templates/");
        templateResolver.setCheckExistence(true);
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setOrder(0);
        
        return templateResolver;
    }
}
