/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Expresiones;

import Abstracto.Instruccion;
import Excepciones.Errores;
import static Expresiones.OperadoresAritmeticos.SUMA;
import Simbolo.*;
import static Simbolo.TipoDato.CADENA;
import static Simbolo.TipoDato.CARACTER;
import static Simbolo.TipoDato.DECIMAL;
import static Simbolo.TipoDato.ENTERO;

/**
 *
 * @author sajp-
 */
public class Aritmeticas extends Instruccion {

    private Instruccion operando1;
    private Instruccion operando2;
    private Instruccion operandoUnico;
    private OperadoresAritmeticos operador;

    //// Constructor para el operando unico que seria la negacion.
    public Aritmeticas(OperadoresAritmeticos operador, Instruccion operandoUnico, int linea, int col) {
        super(new Tipo(TipoDato.ENTERO), linea, col);
        this.operandoUnico = operandoUnico;
        this.operador = operador;
    }

    //// COnstructor para las diferentes operaciones aritmeticas.
    public Aritmeticas(Instruccion operando1, Instruccion operando2, OperadoresAritmeticos operador, int linea, int col) {
        super(new Tipo(TipoDato.ENTERO), linea, col);
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.operador = operador;
    }


    public Object Interpretar(Arbol arbol, TablaSimbolos tabla) {
        Object opIzq = null, opDer = null, Unico = null;
        if (this.operandoUnico!=null){
            Unico = this.operandoUnico.Interpretar(arbol, tabla);
            if (Unico instanceof Errores) {
                return Unico;
            }
        }else{
            opIzq = this.operando1.Interpretar(arbol, tabla);
            if (opIzq instanceof Errores){
                return opIzq;
            }
            opDer = this.operando2.Interpretar(arbol, tabla);
            if (opDer instanceof Errores){
                return opDer;
            }
        }

        //// Switch para verificar la operacion a realizar.
        return switch (operador){
            case SUMA ->
                this.Suma(opIzq, opDer);
            case NEGACION ->
                this.Negacion(Unico);
            default -> 
                new Errores("ERROR semantico", "operando inexistente", this.linea, this.col);
        };
    }

    public Object Suma(Object op1, Object op2) {
        //// Obtenemos el tipo de los operandos.
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();

        //// Se evalua que clase de datos se tiene y se forman las operaciones.
        switch (tipo1) {
            case ENTERO -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(TipoDato.ENTERO);
                        return (int) op1 + (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(TipoDato.DECIMAL);
                        return (int) op1 + (double) op2;
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(TipoDato.ENTERO);
                        return ((Integer) op1) + (int) ((Character) op2);
                    }
                    case CADENA -> {
                        this.tipo.setTipo(TipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        return new Errores("Error Semantico:", "Suma erronea.", this.linea, this.col);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(TipoDato.DECIMAL);
                        return (double) op1 + (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(TipoDato.DECIMAL);
                        return (double) op1 + (double) op2;
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(TipoDato.DECIMAL);
                        return ((double) op1) + (int) ((Character) op2);
                    }
                    case CADENA -> {
                        this.tipo.setTipo(TipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        return new Errores("Error Semantico:", "Suma erronea.", this.linea, this.col);
                    }
                }
            }
            case BOOLEANO -> {
                switch (tipo2) {
                    case CADENA -> {
                        this.tipo.setTipo(TipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        return new Errores("Error Semantico:", "Suma erronea.", this.linea, this.col);
                    }
                }
            }
            case CARACTER -> {
                switch (tipo2){
                    case ENTERO -> {
                        this.tipo.setTipo(TipoDato.ENTERO);
                        return (int)((Character) op1) + ((int) op2);
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(TipoDato.DECIMAL);
                        return (double)((Character) op1) + ((double) op2);
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(TipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    case CADENA -> {
                        this.tipo.setTipo(TipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        return new Errores("ERROR semantico", "Suma erronea", this.linea, this.col);
                    }
                }
            }
            case CADENA -> {
                this.tipo.setTipo(TipoDato.CADENA);
                return op1.toString() + op2.toString();
            }
            default -> {
                return new Errores("Error Semantico:", "Suma erronea.", this.linea, this.col);
            }
        }

    }
    
    public Object Negacion(Object op1){
        var opU = this.operandoUnico.tipo.getTipo();
        switch (opU){
            case ENTERO ->{
                this.tipo.setTipo(TipoDato.ENTERO);
                return (int)op1 * -1;
            }
            case DECIMAL ->{
                this.tipo.setTipo(TipoDato.ENTERO);
                return (double)op1 * -1;
            }
            default -> {
                return new Errores("Error Semantico:", "Negacion erronea.", this.linea, this.col);
            }
        }
    }
}
