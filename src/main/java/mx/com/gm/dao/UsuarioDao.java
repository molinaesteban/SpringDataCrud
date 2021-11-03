package mx.com.gm.dao;

import mx.com.gm.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioDao extends JpaRepository<Usuario,Long> {
   /*
   Para configurar la seguriada de esta clase necesitamos definir
   el metodo findByusername.
    */
    Usuario findByUsername(String username);

}
