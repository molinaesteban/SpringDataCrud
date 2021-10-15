package mx.com.gm.web;

import lombok.extern.slf4j.Slf4j;
import mx.com.gm.domain.Persona;
import mx.com.gm.servicio.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@Slf4j
public class ControladorInicio {
    
    @Autowired // inyectamos capa de servicio (capa logica)
    private PersonaService personaService;
    
    @GetMapping("/")
    public String inicio(Model model){
        var personas = personaService.listarPersonas();
        model.addAttribute("personas", personas);
        return "index";
    }

    // este metodo va a funcionar para agregar y modificar Getmapping(peticion)
    @GetMapping("/agregar")
    public String agregar(Persona persona){
        return "modificar"; // retun a la vista
    }

    // mapeamos la accion de guardar para recuperar la informacion
    @PostMapping ("/guardar") // La peticion recibe informacion de tipo post del formulario
    // recibimos el objeto del formulario
    public String guardar(@Valid Persona persona, Errors errores)   {
        if(errores.hasErrors()){
            return "modificar";
        }
    personaService.guardar(persona); // insertamos la infomacion en la base de datos
    return "redirect:/";

    }
    // se vincula el parametro  idPersona con la inyeccion de la clase Persona
    //metiante spring

    @GetMapping("/editar/{idpersona}")
    // model la usamos para compartir la siguiente peticion
    public String editar(Persona persona, Model model){
      persona =  personaService.encontrarPersona(persona);
        model.addAttribute("persona",persona);
        return "modificar";
    }

    @GetMapping("/eliminar/{idpersona}")
    public String eliminar(Persona persona){
      personaService.eliminar(persona);
      return "redirect:/";
    }

    




}
