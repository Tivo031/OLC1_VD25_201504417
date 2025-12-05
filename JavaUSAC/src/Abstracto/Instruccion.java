/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Abstracto;
import Simbolo.Arbol;
import Simbolo.TablaSimbolos;
import Simbolo.Tipo;

/**
 *
 * @author sajp-
 */
public abstract class Instruccion {
    public Tipo tipo;
    public int linea;
    public int col;

    public Instruccion(Tipo tipo, int linea, int col) {
        this.tipo = tipo;
        this.linea = linea;
        this.col = col;
    }
    
    public abstract Object Interpretar(Arbol arbol, TablaSimbolos tablaSimbolos);
}
