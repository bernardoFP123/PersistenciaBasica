package misiont.mision7;

import java.io.Serializable;

/**
 * Created by Bernardo_NoAdmin on 15/02/2018.
 */

public class Registro implements Serializable {

    String matricula;
    String numeroCliente;

    public Registro(String matricula, String numeroCliente) {
        this.matricula = matricula;
        this.numeroCliente = numeroCliente;
    }


    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(String numeroCliente) {
        this.numeroCliente = numeroCliente;
    }
}
