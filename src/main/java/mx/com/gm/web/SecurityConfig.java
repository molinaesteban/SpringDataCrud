package mx.com.gm.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // habilitiamos la seguridad de spring para esta clase

public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /*sobrescritura de metodo este metodo sirve para agregar mas usuarios y
    personalizar los usuarios*/

    // implementacion jpa para cargar los usuarios
    //inyectamos el servicio de usuario creado

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // definimos es tipo de incripcion a utilizar
    @Bean // este metodo va a estar disponible en el contenedor de Spring
    public BCryptPasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }

    //Metodo par utilizar DetailsService y tambien el tipo de codificacion
    // y pasar un argumento de forma automatica
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }


    /*
    Este metodo lo ulitizamos para restringir las url de la aplicacion
    Se va a restringir a los usuarios las paginas que puedan ver  depentiendo el rol del usuario
     */
    // autorizacion

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/editar/**","/agregar/**","/eliminar") // idicamso los paht a utilizar
                    .hasRole("ADMIN")  // queines son los roles que pueden acceder a los path
                .antMatchers("/") // acceso al path raiz en este caso la lista de personas
                    .hasAnyRole("USER","ADMIN") // acceso a cualquier usuario
                .and()
                    .formLogin()
                    .loginPage("/login") // login personalizado
                .and()
                    .exceptionHandling().accessDeniedPage("/errores/403")
        ;

    }
}
