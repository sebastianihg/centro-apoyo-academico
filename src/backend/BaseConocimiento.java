package backend;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/// Base de conocimiento del sistema(se guarad la lista de estudiantes y resuelve las consultas academicas con la API Stream())
public class BaseConocimiento {
    //todos los estudiantes registrados.
    private final List<Estudiante> estudiantes;
    // Constructor para carga la base de conocimiento al crearse el objeto.
    public BaseConocimiento() {
        this.estudiantes = cargarEstudiantes();
    }

    /// Define los 32 estudiantes de la base de conocimiento igual pueden ser ams
    private List<Estudiante> cargarEstudiantes() {
        return List.of(
                new Estudiante("Ana",          "Prolog",          6.5),
                new Estudiante("Luis",         "Prolog",          3.4),
                new Estudiante("Maria",        "Java",            5.8),
                new Estudiante("Pedro",        "Java",            4.0),
                new Estudiante("Camila",       "Python",          6.9),
                new Estudiante("Diego",        "Python",          2.8),
                new Estudiante("Valentina",    "Algoritmos",      5.5),
                new Estudiante("Matias",       "Algoritmos",      3.9),
                new Estudiante("Javiera",      "Base de datos",  6.1),
                new Estudiante("Sebastian",    "Base de datos",  4.7),
                new Estudiante("Fernanda",     "Redes",           3.2),
                new Estudiante("Cristobal",    "Redes",           5.0),
                new Estudiante("Catalina",     "Prolog",          4.5),
                new Estudiante("Benjamin",     "Prolog",          6.8),
                new Estudiante("Isidora",      "Java",            3.7),
                new Estudiante("Vicente",      "Java",            5.2),
                new Estudiante("Antonia",      "Python",          4.9),
                new Estudiante("Tomas",        "Python",          6.3),
                new Estudiante("Florencia",    "Algoritmos",      2.5),
                new Estudiante("Joaquin",      "Algoritmos",      6.0),
                new Estudiante("Martina",      "Base de datos",  5.6),
                new Estudiante("Felipe",       "Base de datos",  3.1),
                new Estudiante("Constanza",    "Redes",           6.7),
                new Estudiante("Agustin",      "Redes",           4.2),
                new Estudiante("Trinidad",     "Prolog",          5.9),
                new Estudiante("Maximiliano",  "Java",            4.8),
                new Estudiante("Emilia",       "Python",          3.5),
                new Estudiante("Nicolas",      "Algoritmos",      5.3),
                new Estudiante("Sofia",        "Base de datos",  6.4),
                new Estudiante("Gabriel",      "Redes",           2.9),
                new Estudiante("Amanda",       "Java",            7.0),
                new Estudiante("Ignacio",      "Python",          4.1)
        );
    }

    /// Getter de la lista completa de estudiantes.
    public List<Estudiante> getEstudiantes() {
        return this.estudiantes;
    }

    /// Verificion si un estudiante pertenece a un curso.
    public boolean perteneceACurso(String nombre, String curso) {
        return estudiantes.stream()
                .filter(e -> e.nombre().equalsIgnoreCase(nombre))
                .anyMatch(e -> e.curso().equalsIgnoreCase(curso));
    }

    /// Determina con Stream + filter + anyMatch si un estudiante esta aprobado (nota >= 4.0).
    public boolean estaAprobado(String nombre) {
        return estudiantes.stream()
                .filter(e -> e.nombre().equalsIgnoreCase(nombre))
                .anyMatch(e -> e.nota() >= 4.0);
    }

    /// Determina si un estudiante esta reprobado (nota < 4.0).
    public boolean estaReprobado(String nombre) {
        return estudiantes.stream()
                .filter(e -> e.nombre().equalsIgnoreCase(nombre))
                .anyMatch(e -> e.nota() < 4.0);
    }

    /// Busca un estudiante por su nombre con Stream + filter + findFirst.
    public Optional<Estudiante> buscar(String nombre) {
        return estudiantes.stream()
                .filter(e -> e.nombre().equalsIgnoreCase(nombre))
                .findFirst();
    }

    /// Lista con Stream los estudiantes de un curso, ordenados por nota descendente.
    public List<Estudiante> estudiantesDeCurso(String curso) {
        return estudiantes.stream()
                .filter(e -> e.curso().equalsIgnoreCase(curso))
                .sorted(Comparator.comparingDouble(Estudiante::nota).reversed())
                .toList();
    }

    /// Calcula con Stream el promedio de notas de un curso
    public double promedioDeCurso(String curso) {
        return estudiantes.stream()
                .filter(e -> e.curso().equalsIgnoreCase(curso))
                .mapToDouble(Estudiante::nota)
                .average()
                .orElse(0.0);
    }

    /// Genera un resumen por curso  para cada curso para calcula la cantidad de estudiantes y su promedio, y lo devuelve como records Curso.
    public List<Curso> resumenCursos() {
        Map<String, List<Estudiante>> porCurso = estudiantes.stream()
                .collect(Collectors.groupingBy(Estudiante::curso));

        return porCurso.entrySet().stream()
                .map(entrada -> new Curso(
                        entrada.getKey(),
                        entrada.getValue().size(),
                        entrada.getValue().stream()
                                .mapToDouble(Estudiante::nota)
                                .average()
                                .orElse(0.0)))
                .sorted(Comparator.comparing(Curso::nombre))
                .toList();
    }
}