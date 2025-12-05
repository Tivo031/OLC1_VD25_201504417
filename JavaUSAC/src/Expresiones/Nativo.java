/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Expresiones;
import Simbolo.*;
import Abstracto.Instruccion;

/**
 *
 * @author sajp-
 */
public class Nativo extends Instruccion {
    public Object valor;

    public Nativo(Object valor, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.valor = valor;
    }
    
   @Override
   public Object Interpretar (Arbol arbol, TablaSimbolos tabla){
       return this.valor;
   }
}
