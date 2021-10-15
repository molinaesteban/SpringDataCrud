package mx.com.gm.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

// clase lisener para manejo de varios idiomas


@Configuration // registramos esta clase como un bean de configuracion de Spring
public class WebConfig implements WebMvcConfigurer{
    
    @Bean
    public LocaleResolver localeResolver(){
        var slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("es"));
        return slr;
    }


    // interceprtor para cambiar de idioma de forma dinamica
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        var lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");// lang para que despues desde la url escojamos el idioma
        return lci;
    }


    // registramos el interseptor y lo llamamos para que se pueda camibar el lenguaje dinamicamente
    @Override
    public void addInterceptors(InterceptorRegistry registro){
        registro.addInterceptor(localeChangeInterceptor());
    }

    // mapeo de paths que no necesariamente pasan por el controlador
    // mapeo de la vista de login
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login");
        registry.addViewController("/errores/403").setViewName("/errores/403")

        ;
    }
    
}

