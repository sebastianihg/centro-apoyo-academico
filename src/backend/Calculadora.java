package backend;

import java.util.Optional;

/// Calculadora con las operaciones aritmeticas basicas (+, -, *, /).
/// Las operaciones son funciones puras y la division se maneja de forma
/// funcional con Optional.
public final class Calculadora {

    // Clase de utilidad, no instanciable.
    private Calculadora() { }

    public static double sumar(double a, double b)       { return a + b; }
    public static double restar(double a, double b)      { return a - b; }
    public static double multiplicar(double a, double b) { return a * b; }

    /// Division  retorna Optional vacio si el divisor es 0.
    public static Optional<Double> dividir(double a, double b) {
        return b == 0 ? Optional.empty() : Optional.of(a / b);
    }

    /// Aplica la operacion indicada sobre los dos operandos.
    public static Optional<Double> calcular(double a, double b, String operacion) {
        return switch (operacion) {
            case "+" -> Optional.of(sumar(a, b));
            case "-" -> Optional.of(restar(a, b));
            case "*" -> Optional.of(multiplicar(a, b));
            case "/" -> dividir(a, b);
            default  -> Optional.empty();
        };
    }
}