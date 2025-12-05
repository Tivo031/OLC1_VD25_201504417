/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadores;

/**
 *
 * @author sajp-
 */
public class Generador {
    public static void main(String[] args){
        generarCompilador();
    }
    
    // ejectuar el generador cuando se tiene el cup y el jflex, de este modo, se genera scanner y parser
    public static void generarCompilador(){
        try{    
          //Ruta donde se encuentran los archivos del analizador léxico y sintactico
         
           String ruta = "src/analizadores/";
          
           // 1. Se le indica el archivo lexico.jflex
           // 2. "-d" especifica dónde debe generar la clase scanner.java
           // 3. ruta es la carpeta destino
           String Flex[] = {ruta + "Lexer.jflex", "-d", ruta};
           // Ejecuta JFlex para generar automáticamente la clase scanner.java
           jflex.Main.generate(Flex);
           // "-destdir" indica la carpeta donde se generarán parser.java y sym.java
           // "-parser" define el nombre de la clase del parser que se generará
           // Finalmente, se indica el archivo sintactico.cup
           String Cup[] = {"-destdir", ruta, "-parser", "parser", ruta + "Parser.cup"};
           // Ejecuta CUP para generar parser.java y sym.java
           java_cup.Main.main(Cup);
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
