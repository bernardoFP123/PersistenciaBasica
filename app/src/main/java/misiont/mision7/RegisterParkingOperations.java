package misiont.mision7;

import java.util.List;

/**
 * Created by Bernardo_NoAdmin on 15/02/2018.
 */

public interface RegisterParkingOperations{
    void addRegisters(List<Registro> registros);
    List<Registro> getRegisters();
    void addRegistersToExternallFile(List<Registro> registros,String fileName);
}