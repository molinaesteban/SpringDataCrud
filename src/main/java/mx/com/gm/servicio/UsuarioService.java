package mx.com.gm.servicio;

import lombok.extern.slf4j.Slf4j;
import mx.com.gm.dao.UsuarioDao;
import mx.com.gm.domain.Rol;
import mx.com.gm.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service("userDetailsService")
@Slf4j // manejo de login
public class UsuarioService implements UserDetailsService {

    // esta clase se va a apoyar de la clase usario entoces la inyectamos
    @Autowired
    private UsuarioDao usuarioDao;

    // se va a obtener el objeto de usuario por un username
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // usuarioDao va a interactuar con las clases roles y usuario

        //Recuperamos el objeto de usuario por medio de esta implementacion
        Usuario usuario = usuarioDao.findByUsername(username);

        if(usuario==null){
            throw new UsernameNotFoundException(username);

        }
        //si se encuentra el usuario recuperamos los roles asociados
        var roles = new ArrayList<GrantedAuthority>();

        for (Rol rol:usuario.getRoles()) {
            roles.add(new SimpleGrantedAuthority(rol.getNombre()));

        }
        // el objeto de tipo USER es de spring segurity
        return  new User(usuario.getUsername(), usuario.getPassword(), roles);

    }
}

