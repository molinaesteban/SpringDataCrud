package mx.com.gm.servicio;

import mx.com.gm.dao.PersonaDao;
import mx.com.gm.domain.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaServiceImpl implements PersonaService {


    // inyectamos la instancia de Dao
    @Autowired
    private PersonaDao personaDao;
    // el controlador no usara directamente la capa de Dao sino este service

    // conectamos nuestra capa de servicio con la capa de datos


    @Override
    @Transactional (readOnly = true) // transaccion solo en caso necesario
    // se agrega para que en caso de error realice roolback y no actualice la informacion en tablas afectadas
    public List<Persona> listarPersonas() {
        return (List<Persona>) personaDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Persona persona) {
        personaDao.save(persona);

    }

    @Override
    @Transactional
    public void eliminar(Persona persona) {
        personaDao.delete(persona);

    }

    @Override
    @Transactional (readOnly = true)
    public Persona encontrarPersona(Persona persona) {
      return personaDao.findById(persona.getIdpersona()).orElseGet(null);

    }
}
