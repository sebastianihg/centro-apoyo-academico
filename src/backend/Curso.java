package backend;
/// Resumen de un curso de la base de conocimiento. Es un record (inmutable)
///  agrupa el nombre del curso, cuantos estudiantes tiene y su promedio.
/// Se construye a partir de los datos usando Stream
public record Curso(String nombre, int cantidad, double promedio) {
    @Override
    public String toString() {
        return String.format("%-16s | %2d estudiantes | promedio: %.2f",
                nombre, cantidad, promedio);
    }
}