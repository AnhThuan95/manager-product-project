package com.comdegym.managerproductproject;

import com.comdegym.managerproductproject.formatter.ManufacturerFormatter;
import com.comdegym.managerproductproject.service.ManufacturerService;
import com.comdegym.managerproductproject.service.ProductService;
import com.comdegym.managerproductproject.service.impl.ManufacturerServiceImpl;
import com.comdegym.managerproductproject.service.impl.ProductServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ManagerProductProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerProductProjectApplication.class, args);
    }

    @Configuration
    class WebConfig implements WebMvcConfigurer, ApplicationContextAware {

        private ApplicationContext appContext;

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            appContext = applicationContext;
        }

        @Override
        public void addFormatters(FormatterRegistry registry) {
            ManufacturerService manufacturerService = appContext.getBean(ManufacturerService.class);
            Formatter provinceFormatter = new ManufacturerFormatter(manufacturerService);
            registry.addFormatter(provinceFormatter);
        }
    }

    @Bean
    public ProductService productService(){

        return new ProductServiceImpl();
    }

    @Bean
    public ManufacturerService manufacturerService() {
        return new ManufacturerServiceImpl();
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("ValidationMessages");
        return messageSource;
    }
}
