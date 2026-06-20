package backend;
/// Representa a un estudiante del Centro de Apoyo Academico.
/// Se modela como un RECORD: es inmutable por definicion (campos final y sin
/// setters), siguiendo el principio de inmutabilidad de la programacion funcional.
/// Sus accesores son nombre(), curso() y nota().
public record Estudiante(String nombre, String curso, double nota) {

    /// Funcion  sobre el estudiante
    public boolean aprobado() {
        return this.nota >= 4.0;
    }

    /// Devuelve el estado academico como texto.
    public String estado() {
        return aprobado() ? "Aprobado" : "Reprobado";
    }
    @Override
    public String toString() {
        return String.format("%-14s | %-16s | nota: %.1f | %s",
                nombre, curso, nota, estado());
    }
}