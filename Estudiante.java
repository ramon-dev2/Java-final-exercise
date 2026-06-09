class Estudiante {

    String mes;
    String curso;
    String nombre;
    String apellido;

    int matematica;
    int lengua;
    int naturales;
    int sociales;

    public double calcularPromedio() {
        int cantidadMaterias = 4;

        try {
            return (matematica + lengua + naturales + sociales) / cantidadMaterias;
        } catch (ArithmeticException e) {
            return 0;
        }
    }
}