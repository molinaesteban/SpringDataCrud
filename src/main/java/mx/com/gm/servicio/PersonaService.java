package mx.com.gm.servicio;

import mx.com.gm.domain.Persona;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PersonaService {
    public List<Persona> listarPersonas();

    public void guardar(Persona persona);

    public void eliminar(Persona persona);

    public Persona encontrarPersona(Persona persona);

}
