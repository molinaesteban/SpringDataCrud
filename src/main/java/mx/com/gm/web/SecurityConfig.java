package mx.com.gm.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity // habilitiamos la seguridad de spring para esta clase

public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /*sobrescritura de metodo este metodo sirve para agregar mas usuarios y
    personalizar los usuarios*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // en este caso creamos usuariso en memoria
        // autenticacion
        auth.inMemoryAuthentication()
                .withUser("admin")
                   .password("{noop}123") // noop para que no se encripte el password
                        .roles("ADMIN","USER")
                            .and()
                                .withUser("user")
                                    .password("{noop}123")
                                        .roles("USER")
                ;

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
