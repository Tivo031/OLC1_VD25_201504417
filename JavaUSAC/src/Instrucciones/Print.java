/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Instrucciones;
import Abstracto.Instruccion;
import Simbolo.*;

/**
 *
 * @author sajp-
 */
public class Print extends Instruccion {
     private Instruccion expresion;

    public Print(Instruccion expresion, int linea, int col) {
        super(new Tipo(TipoDato.VOID), linea, col);
        this.expresion = expresion;
    }
    
    @Override
    public Object Interpretar(Arbol arbol, TablaSimbolos tabla){
        var resultado = this.expresion.Interpretar(arbol, tabla);
        arbol.Print(resultado.toString());
        return null;
    }
}
