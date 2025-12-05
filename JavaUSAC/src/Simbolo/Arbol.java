/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Simbolo;
import Abstracto.Instruccion;
import Excepciones.Errores;
import java.util.LinkedList;
/**
 *
 * @author sajp-
 */
public class Arbol {
    private LinkedList<Instruccion> instrucciones;
    private TablaSimbolos tablaGlobal;
    private LinkedList<Errores> errores;
    private String consola;

    public Arbol(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
        this.tablaGlobal = new TablaSimbolos();
        this.errores = new LinkedList<>();
        this.consola = "";
    }

    public LinkedList<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public TablaSimbolos getTablaGlobal() {
        return tablaGlobal;
    }

    public void setTablaGlobal(TablaSimbolos tablaGlobal) {
        this.tablaGlobal = tablaGlobal;
    }

    public LinkedList<Errores> getErrores() {
        return errores;
    }

    public void setErrores(LinkedList<Errores> errores) {
        this.errores = errores;
    }

    public String getConsola() {
        return consola;
    }

    public void setConsola(String consola) {
        this.consola = consola;
    }
    
    // Permite concatenar el valor para la consola.
    public void Print(String valor)
    {
        this.consola += valor + "\n";
    }
}
