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
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ManagerProductProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerProductProjectApplication.class, args);
    }

    @Configuration
    @EnableSpringDataWebSupport
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

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/image/**").addResourceLocations("file:/home/thuan/Uploads/");
        }
    }

    @Configuration
    @EnableWebSecurity
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            auth.inMemoryAuthentication()
                    .withUser("user").password(encoder.encode("12345")).roles(Roles.USER)
                    .and()
                    .withUser("admin").password(encoder.encode("12345")).roles(Roles.ADMIN);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/admin").hasRole(Roles.ADMIN)
                    .and().authorizeRequests().antMatchers("/admin**").hasRole(Roles.ADMIN)
                    .and().authorizeRequests().antMatchers("/admin/**").hasRole(Roles.ADMIN)
                    .and().authorizeRequests().antMatchers("/user").hasRole(Roles.USER)
                    .and().authorizeRequests().antMatchers("/user**").hasRole(Roles.USER)
                    .and().authorizeRequests().antMatchers("/user/**").hasRole(Roles.USER)
                    .and().authorizeRequests().antMatchers("/**").permitAll()
                    .and().formLogin()
                    .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
        }
    }

    interface Roles {
        String USER = "1";
        String ADMIN = "2";
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
