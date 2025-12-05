package javausac;

import Abstracto.Instruccion;
import Simbolo.Arbol;
import Simbolo.TablaSimbolos;
import analizadores.parser;
import analizadores.scanner;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.LinkedList;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class EditorCompilador extends JFrame {

    private JTabbedPane tabbedEntrada;
    private JTextArea consolaTextArea;
    private Font editorFont = new Font(Font.MONOSPACED, Font.PLAIN, 14);

    // >>> CONFIGURACIÓN DE EXTENSIÓN DE ARCHIVOS <<<
    private static final String EXTENSION = "ju";
    private static final String EXTENSION_DESCRIPCION = "Archivos del lenguaje (*." + EXTENSION + ")";

    // contador de pestañas creadas
    private int contadorTabs = 1;

    // File chooser para abrir/guardar
    private JFileChooser fileChooser = new JFileChooser();

    public EditorCompilador() {
        setTitle("JavaUSAC");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        configurarFileChooser();
        setJMenuBar(crearMenuBar());

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(crearPanelCentral(), BorderLayout.CENTER);
    }

    /// Configuración del JFileChooser para usar la extensión .sp (o la que definas)
    private void configurarFileChooser() {
        FileNameExtensionFilter filtro =
                new FileNameExtensionFilter(EXTENSION_DESCRIPCION, EXTENSION);
        fileChooser.setFileFilter(filtro);
        // Si quieres que NO se puedan ver "Todos los archivos", descomenta:
        // fileChooser.setAcceptAllFileFilterUsed(false);
    }

    private JMenuBar crearMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // ----- Menú Archivo -----
        JMenu menuArchivo = new JMenu("Archivo");

        JMenuItem itemNuevo = new JMenuItem("Nuevo");
        itemNuevo.addActionListener(e -> crearNuevaPestana());
        menuArchivo.add(itemNuevo);

        JMenuItem itemAbrir = new JMenuItem("Abrir");
        itemAbrir.addActionListener(e -> abrirArchivo());
        menuArchivo.add(itemAbrir);

        JMenuItem itemGuardar = new JMenuItem("Guardar");
        itemGuardar.addActionListener(e -> guardarArchivo());
        menuArchivo.add(itemGuardar);

        JMenuItem itemGuardarComo = new JMenuItem("Guardar como...");
        itemGuardarComo.addActionListener(e -> guardarComoArchivo());
        menuArchivo.add(itemGuardarComo);

        menuBar.add(menuArchivo);

        // ----- Menú Pestañas -----
        JMenu menuPestanas = new JMenu("Pestañas");
        JMenuItem itemCerrar = new JMenuItem("Cerrar pestaña actual");
        itemCerrar.addActionListener(e -> cerrarPestanaActual());
        menuPestanas.add(itemCerrar);
        menuBar.add(menuPestanas);

        // ----- Menú Ejecutar -----
        JMenu menuEjecutar = new JMenu("Ejecutar");
        JMenuItem itemAnalizar = new JMenuItem("Analizar");
        itemAnalizar.addActionListener(e -> ejecutarAnalisis());
        menuEjecutar.add(itemAnalizar);
        menuBar.add(menuEjecutar);

        // ----- Menú Reportes (por ahora vacío) -----
        menuBar.add(new JMenu("Reportes"));

        return menuBar;
    }

    private JComponent crearPanelCentral() {
        // ---------- Panel ENTRADA ----------
        JPanel entradaPanel = new JPanel(new BorderLayout());
        JLabel lblEntrada = new JLabel("Entrada");
        lblEntrada.setFont(lblEntrada.getFont().deriveFont(Font.BOLD));
        lblEntrada.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
        entradaPanel.add(lblEntrada, BorderLayout.NORTH);

        tabbedEntrada = new JTabbedPane();
        tabbedEntrada.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        // Al iniciar SOLO una pestaña
        tabbedEntrada.addTab("Sin Titulo", crearScrollConTextArea());

        entradaPanel.add(tabbedEntrada, BorderLayout.CENTER);

        // ---------- Panel CONSOLA ----------
        JPanel consolaPanel = new JPanel(new BorderLayout());
        JLabel lblConsola = new JLabel("Consola");
        lblConsola.setFont(lblConsola.getFont().deriveFont(Font.BOLD));
        lblConsola.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
        consolaPanel.add(lblConsola, BorderLayout.NORTH);

        consolaTextArea = new JTextArea();
        consolaTextArea.setEditable(false);
        consolaTextArea.setFont(editorFont);
        consolaTextArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane consolaScroll = new JScrollPane(consolaTextArea);
        consolaPanel.add(consolaScroll, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                entradaPanel,
                consolaPanel
        );
        splitPane.setResizeWeight(0.7);
        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);

        return splitPane;
    }

    private JScrollPane crearScrollConTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setFont(editorFont);
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textArea.setTabSize(4);
        return new JScrollPane(textArea);
    }

    /// Metodo para crear una nueva pestaña
    private void crearNuevaPestana() {
        contadorTabs++;
        JScrollPane nuevoScroll = crearScrollConTextArea();
        String titulo = "Sin Titulo " + "(" + contadorTabs + ")";
        tabbedEntrada.addTab(titulo, nuevoScroll);
        tabbedEntrada.setSelectedIndex(tabbedEntrada.getTabCount() - 1);
    }

    /// Obtener el JTextArea de la pestaña actual
    private JTextArea getTextAreaActual() {
        int index = tabbedEntrada.getSelectedIndex();
        if (index == -1) {
            return null;
        }
        JScrollPane scroll = (JScrollPane) tabbedEntrada.getComponentAt(index);
        JViewport viewport = scroll.getViewport();
        return (JTextArea) viewport.getView();
    }

    /// Cerrar pestaña actual (si solo queda una, la limpia)
    private void cerrarPestanaActual() {
        int index = tabbedEntrada.getSelectedIndex();
        if (index == -1) return;

        if (tabbedEntrada.getTabCount() == 1) {
            // solo limpiamos el contenido
            JTextArea area = getTextAreaActual();
            if (area != null) {
                area.setText("");
                area.putClientProperty("file", null);
            }
            tabbedEntrada.setTitleAt(0, "Sin Titulo");
            contadorTabs = 1;
        } else {
            tabbedEntrada.removeTabAt(index);
        }
    }

    /// "Ejecutar → Analizar": toma el texto de la pestaña actual y lo manda a la consola
    private void ejecutarAnalisis() {
        JTextArea editor = getTextAreaActual();
        if (editor == null) return;
        try{
            String texto = editor.getText();

        // ejecutar interprete
            scanner s = new scanner(new BufferedReader(new StringReader(texto)));
            parser p = new parser(s);
            var resultado = p.parse();
            var ast = new Arbol((LinkedList<Instruccion>) resultado.value);
            var tabla = new TablaSimbolos();
            tabla.setNombre("GLOBAL");
            ast.setConsola("");
            for (var a: ast.getInstrucciones()){
                a.Interpretar(ast, tabla);
            }
            consolaTextArea.setText(ast.getConsola());
        }catch(Exception ex){
            consolaTextArea.setText("Error al Analizar!");
        }
    }

    /// ABRIR archivo en una nueva pestaña
    private void abrirArchivo() {
        int resultado = fileChooser.showOpenDialog(this);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            return; // usuario canceló
        }

        File archivo = fileChooser.getSelectedFile();
        try {
            String contenido = leerArchivo(archivo);

            // Crear pestaña nueva y poner el contenido
            crearNuevaPestana();
            JTextArea editor = getTextAreaActual();
            if (editor != null) {
                editor.setText(contenido);
                editor.setCaretPosition(0);
                // Guardar referencia al archivo en la pestaña
                editor.putClientProperty("file", archivo);

                int index = tabbedEntrada.getSelectedIndex();
                tabbedEntrada.setTitleAt(index, archivo.getName());
            }

            consolaTextArea.append("Archivo abierto: " + archivo.getAbsolutePath() + "\n");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al abrir el archivo:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /// GUARDAR archivo de la pestaña actual (si tiene archivo asociado)
    private void guardarArchivo() {
        JTextArea editor = getTextAreaActual();
        if (editor == null) {
            return;
        }

        File archivo = (File) editor.getClientProperty("file");

        // Si la pestaña aún no está asociada a un archivo, usamos "Guardar como..."
        if (archivo == null) {
            guardarComoArchivo();
            return;
        }

        try {
            escribirArchivo(archivo, editor.getText());
            consolaTextArea.append("Archivo guardado: " + archivo.getAbsolutePath() + "\n");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al guardar el archivo:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /// GUARDAR COMO archivo (siempre pide ruta)
    private void guardarComoArchivo() {
        JTextArea editor = getTextAreaActual();
        if (editor == null) {
            return;
        }

        int resultado = fileChooser.showSaveDialog(this);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            return; // usuario canceló
        }

        File archivo = fileChooser.getSelectedFile();

        // Si el usuario no puso la extensión, se la agregamos
        String nombre = archivo.getAbsolutePath();
        String sufijo = "." + EXTENSION;
        if (!nombre.toLowerCase().endsWith(sufijo)) {
            archivo = new File(nombre + sufijo);
        }

        try {
            escribirArchivo(archivo, editor.getText());
            consolaTextArea.append("Archivo guardado como: " + archivo.getAbsolutePath() + "\n");

            // Asociar el archivo a esta pestaña de ahora en adelante
            editor.putClientProperty("file", archivo);

            int index = tabbedEntrada.getSelectedIndex();
            if (index != -1) {
                tabbedEntrada.setTitleAt(index, archivo.getName());
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al guardar el archivo:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /// Leer archivo como String
    private String leerArchivo(File archivo) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                sb.append(linea).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    /// Escribir String a archivo
    private void escribirArchivo(File archivo, String contenido) throws IOException {
        try (FileWriter fw = new FileWriter(archivo)) {
            fw.write(contenido);
        }
    }

    public static void main(String[] args) {
        // Usar Nimbus
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new EditorCompilador().setVisible(true));
    }
}