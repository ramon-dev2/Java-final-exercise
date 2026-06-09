import java.io.*;
import java.util.*;

public class SistemaCalificaciones {

    static Scanner scanner = new Scanner(System.in);
    static final String ARCHIVO = "calificaciones.txt";

    public static void main(String[] args) {

        String opcion = "";

        do {
            mostrarMenu();
            opcion = scanner.nextLine();

            try {
                if (opcion.equals("1")) {
                    registrarCalificaciones();
                } else if (opcion.equals("2")) {
                    reporteCalificaciones();
                } else if (opcion.equals("3")) {
                    System.out.println("Saliendo del sistema...");
                } else {
                    System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error, pero el programa no se cerrará.");
            }

        } while (!opcion.equals("3"));
    }

    public static void mostrarMenu() {
        System.out.println("====================================");
        System.out.println("COLEGIO DIOS ES BUENO");
        System.out.println("SISTEMA DE CALIFICACIONES");
        System.out.println("====================================");
        System.out.println("1- Registro de calificaciones");
        System.out.println("2- Reporte calificaciones por mes");
        System.out.println("3- Salir");
        System.out.println("====================================");
        System.out.print("Elija la opción deseada y pulse <ENTER>: ");
    }

    public static void registrarCalificaciones() {

        System.out.print("Digite el mes: ");
        String mes = scanner.nextLine();

        System.out.print("Digite el curso: ");
        String curso = scanner.nextLine();

        System.out.print("Digite el nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Digite el apellido: ");
        String apellido = scanner.nextLine();

        int matematica = pedirNota("Matemática");
        int lengua = pedirNota("Lengua");
        int naturales = pedirNota("Naturales");
        int sociales = pedirNota("Sociales");

        try {
            FileWriter fw = new FileWriter(ARCHIVO, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(mes + ";" + curso + ";" + nombre + ";" + apellido + ";" +
                    matematica + ";" + lengua + ";" + naturales + ";" + sociales);

            bw.newLine();
            bw.close();

            System.out.println("Calificaciones guardadas correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar en el archivo.");
        }
    }

    public static int pedirNota(String materia) {
        int nota = 0;
        boolean valido = false;

        while (!valido) {
            try {
                System.out.print("Digite la nota de " + materia + ": ");
                nota = Integer.parseInt(scanner.nextLine());

                if (nota >= 0 && nota <= 100) {
                    valido = true;
                } else {
                    System.out.println("La nota debe estar entre 0 y 100.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Debe digitar un número válido.");
            }
        }

        return nota;
    }

    public static void reporteCalificaciones() {

        System.out.print("Digite el mes del reporte: ");
        String mesBuscado = scanner.nextLine();

        System.out.print("Digite el curso: ");
        String cursoBuscado = scanner.nextLine();

        ArrayList<Estudiante> estudiantes = new ArrayList<>();

        try {
            FileReader fr = new FileReader(ARCHIVO);
            BufferedReader br = new BufferedReader(fr);

            String linea;

            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(";");

                String mes = datos[0];
                String curso = datos[1];

                if (mes.equalsIgnoreCase(mesBuscado) && curso.equalsIgnoreCase(cursoBuscado)) {

                    Estudiante estudiante = new Estudiante();

                    estudiante.mes = datos[0];
                    estudiante.curso = datos[1];
                    estudiante.nombre = datos[2];
                    estudiante.apellido = datos[3];
                    estudiante.matematica = Integer.parseInt(datos[4]);
                    estudiante.lengua = Integer.parseInt(datos[5]);
                    estudiante.naturales = Integer.parseInt(datos[6]);
                    estudiante.sociales = Integer.parseInt(datos[7]);

                    estudiantes.add(estudiante);
                }
            }

            br.close();

        } catch (FileNotFoundException e) {
            System.out.println("No existe el archivo de calificaciones todavía.");
            return;
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
            return;
        }

        estudiantes.sort(Comparator.comparing(e -> e.apellido));

        System.out.println();
        System.out.println("Colegio Dios es bueno.");
        System.out.println("Reporte de Calificaciones de " + mesBuscado);
        System.out.println("Curso: " + cursoBuscado);
        System.out.println("==========================================================");
        System.out.println("Nombre Apellido Matemática Lengua Naturales Sociales Promedio Literal");
        System.out.println("=====================================================================");

        for (Estudiante e : estudiantes) {
            double promedio = e.calcularPromedio();
            String literal = obtenerLiteral(promedio);

            System.out.println(
                    e.nombre + " " +
                    e.apellido + " " +
                    e.matematica + " " +
                    e.lengua + " " +
                    e.naturales + " " +
                    e.sociales + " " +
                    promedio + " " +
                    literal
            );
        }

        System.out.println("----------------------------------------------------------");
        System.out.println("Total de estudiantes: " + estudiantes.size());
        System.out.println();
    }

    public static String obtenerLiteral(double promedio) {

        if (promedio >= 90 && promedio <= 100) {
            return "A";
        } else if (promedio >= 80 && promedio < 90) {
            return "B";
        } else if (promedio >= 70 && promedio < 80) {
            return "C";
        } else {
            return "D";
        }
    }
}

