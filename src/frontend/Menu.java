package frontend;

import backend.BaseConocimiento;
import backend.Calculadora;
import backend.Estudiante;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

/// Maneja toda la interaccion por consola del Sistema de Centro de Apoyo Academico.
public class Menu {

    private final BaseConocimiento base;
    private final Scanner sc;

    private static final List<String> CURSOS_VALIDOS = List.of(
            "Algoritmos", "Base de Datos", "Java", "Prolog", "Python", "Redes"
    );

    public Menu() {
        this.base = new BaseConocimiento();
        this.sc   = new Scanner(System.in);
    }

    // ── Menu principal ───────────────────────────────────────────────

    public void iniciar() {
        int opcion;
        do {
            lineaDecorativa();
            System.out.println("   CENTRO DE APOYO ACADEMICO");
            lineaDecorativa();
            System.out.println("  1. Ver todos los cursos disponibles");
            System.out.println("  2. Ver todos los estudiantes registrados");
            System.out.println("  3. Consultar estado de un estudiante");
            System.out.println("  4. Verificar pertenencia a un curso");
            System.out.println("  5. Listar estudiantes de un curso");
            System.out.println("  6. Ver resumen de cursos");
            System.out.println("  7. Calculadora");
            System.out.println("  0. Salir");
            lineaDecorativa();
            System.out.print("  Seleccione una opcion [0-7]: ");

            opcion = leerOpcion(0, 7);

            switch (opcion) {
                case 1 -> mostrarCursos();
                case 2 -> mostrarEstudiantes();
                case 3 -> mostrarEstadoEstudiante();
                case 4 -> verificarPertenencia();
                case 5 -> listarPorCurso();
                case 6 -> resumenCursos();
                case 7 -> calculadora();
                case 0 -> System.out.println("\n  Hasta pronto!");
            }
        } while (opcion != 0);
    }

    // ── Opciones ─────────────────────────────────────────────────────

    public void mostrarCursos() {
        lineaDecorativa();
        System.out.println("  Cursos disponibles:");
        CURSOS_VALIDOS.forEach(c -> System.out.println("    - " + c));
    }

    public void mostrarEstudiantes() {
        lineaDecorativa();
        System.out.println("  Estudiantes registrados:");
        base.getEstudiantes()
                .stream()
                .sorted((a, b) -> a.nombre().compareToIgnoreCase(b.nombre()))
                .forEach(e -> System.out.println("    " + e));
    }

    public void mostrarEstadoEstudiante() {
        lineaDecorativa();
        System.out.println("  Estudiantes registrados:");
        base.getEstudiantes()
                .stream()
                .sorted((a, b) -> a.nombre().compareToIgnoreCase(b.nombre()))
                .forEach(e -> System.out.println("    - " + e.nombre() + " (" + e.curso() + ")"));

        String nombre = leerNombreNoVacio("\n  Ingrese el nombre del estudiante: ");

        base.buscar(nombre).ifPresentOrElse(
                e -> System.out.printf("%n  %s | nota: %.1f | %s | %s%n",
                        e.nombre(), e.nota(), e.curso(), e.estado().toUpperCase()),
                () -> System.out.println("\n  No se encontro un estudiante con ese nombre.")
        );
    }

    public void verificarPertenencia() {
        lineaDecorativa();
        System.out.println("  Estudiantes registrados:");
        base.getEstudiantes()
                .stream()
                .sorted((a, b) -> a.nombre().compareToIgnoreCase(b.nombre()))
                .forEach(e -> System.out.println("    - " + e.nombre() + " (" + e.curso() + ")"));

        String nombre = leerNombreNoVacio("\n  Nombre del estudiante: ");
        String curso  = leerCursoValido();

        if (base.perteneceACurso(nombre, curso)) {
            System.out.printf("%n  Si, %s pertenece al curso %s.%n", nombre, curso);
        } else {
            System.out.printf("%n  No, %s no pertenece al curso %s.%n", nombre, curso);
        }
    }

    public void listarPorCurso() {
        lineaDecorativa();
        String curso = leerCursoValido();

        List<Estudiante> lista = base.estudiantesDeCurso(curso);
        System.out.println("\n  Estudiantes de " + curso + " (ordenados por nota):");
        lista.forEach(e -> System.out.println("  " + e));
        System.out.printf("  Promedio del curso: %.2f%n", base.promedioDeCurso(curso));
    }

    public void resumenCursos() {
        lineaDecorativa();
        System.out.println("  Resumen de cursos:");
        base.resumenCursos().forEach(c -> System.out.println("  " + c));
    }

    public void calculadora() {
        lineaDecorativa();
        double a  = leerDouble("  Ingrese el primer numero: ");
        double b  = leerDouble("  Ingrese el segundo numero: ");
        String op = leerOperador();

        Calculadora.calcular(a, b, op).ifPresentOrElse(
                resultado -> System.out.printf("%n  Resultado: %.2f%n", resultado),
                () -> System.out.println("\n  Division por cero no permitida.")
        );
    }

    // ── Metodos de lectura validada ──────────────────────────────────

    private int leerOpcion(int min, int max) {
        while (true) {
            String linea = sc.nextLine().trim();
            try {
                int valor = Integer.parseInt(linea);
                if (valor >= min && valor <= max) return valor;
                System.out.printf("  Opcion fuera de rango (%d-%d), intente de nuevo: ", min, max);
            } catch (NumberFormatException e) {
                System.out.print("  Solo numeros, intente de nuevo: ");
            }
        }
    }

    private String leerNombreNoVacio(String mensaje) {
        System.out.print(mensaje);
        while (true) {
            String valor = sc.nextLine().trim();
            if (!valor.isEmpty()) return valor;
            System.out.print("  El nombre no puede estar vacio, intente de nuevo: ");
        }
    }

    private static String normalizar(String s) {
        return s.trim().toLowerCase().replaceAll("\s+", "");
    }

    /// Ignora mayusculas y espacios: "base de datos" == "basededatos" == "Bases de Datos"
    private String leerCursoValido() {
        System.out.println("  Cursos disponibles:");
        CURSOS_VALIDOS.forEach(c -> System.out.println("    - " + c));
        System.out.print("  Ingrese el curso: ");
        while (true) {
            String entrada = normalizar(sc.nextLine());
            String encontrado = CURSOS_VALIDOS.stream()
                    .filter(c -> normalizar(c).equals(entrada))
                    .findFirst()
                    .orElse(null);
            if (encontrado != null) return encontrado;
            System.out.print("  Curso no reconocido, intente de nuevo: ");
        }
    }

    private String leerOperador() {
        System.out.print("  Operacion (+, -, *, /): ");
        while (true) {
            String valor = sc.nextLine().trim();
            if (Set.of("+", "-", "*", "/").contains(valor)) return valor;
            System.out.print("  Operador invalido, use +  -  *  /: ");
        }
    }

    private double leerDouble(String mensaje) {
        System.out.print(mensaje);
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.print("  Numero invalido, intente de nuevo: ");
            }
        }
    }

    private void lineaDecorativa() {
        System.out.println("\n  =====================================");
    }
}