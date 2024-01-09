package tesis.dermacan;

import static org.junit.Assert.*;

import org.junit.Test;

public class loginTest {


    @Test
    public void email_vacio(){
        String email = "";
        String pass = "12345678";

        boolean resultadoEsperado = false;
        boolean resultadoActual = validar_login(email, pass);
        assertEquals(resultadoEsperado, resultadoActual);
    }

    @Test
    public void password_vacia(){
        String email = "randy@gmail.com";
        String pass = "";

        boolean resultadoEsperado = false;
        boolean resultadoActual = validar_login(email, pass);
        assertEquals(resultadoEsperado, resultadoActual);
    }

    @Test
    public void password_corta(){
        String email = "randy@gmail.com";
        String pass = "123456";

        boolean resultadoEsperado = false;
        boolean resultadoActual = validar_login(email, pass);
        assertEquals(resultadoEsperado, resultadoActual);
    }

    @Test
    public void password_incorrecta(){
        String email = "randy@gmail.com";
        String pass = "1234567";

        boolean resultadoEsperado = false;
        boolean resultadoActual = validar_login(email, pass);
        assertEquals(resultadoEsperado, resultadoActual);
    }

    @Test
    public void legin_exitoso(){
        String email = "randy@gmail.com";
        String pass = "Ruae1234";

        boolean resultadoEsperado = true;
        boolean resultadoActual = validar_login(email, pass);
        assertEquals(resultadoEsperado, resultadoActual);
    }

    public boolean validar_login(String correo, String pass) {

        if (correo.isEmpty()) {
            System.out.println("El correo está vacío");
            return false;
        }

        if (pass.isEmpty()) {
            System.out.println("La contraseña está vacío");
            return false;
        }
        if (pass!="Ruae1234"){
            System.out.println("La contraseña es incorrecta");
            return false;
        }
        if (pass.length() >8){
            System.out.println("La contraseña es demasiado corta");
            return false;
        }
        System.out.println("El Login se completó con éxito");
        return true;
    }
}