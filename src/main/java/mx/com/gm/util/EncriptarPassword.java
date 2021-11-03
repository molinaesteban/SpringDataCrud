package mx.com.gm.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncriptarPassword {
    public static void main(String[] args) {

        var password="123";
        //password sin encriptar
        System.out.println("password = " + password);
        
        System.out.println("password encriptado: "+encriptarPassword(password));

    }

    public static String encriptarPassword(String password){
        /*
        BCrypt ayuda a encriptar los password le pasamos el password normal y la clase  nos retorna el encrptamiento.
         */
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

}
