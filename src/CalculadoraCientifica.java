import java.util.Scanner;

public class CalculadoraCientifica {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Resultados resultados = new Resultados();

    public static void main(String[] args) {
        Menu menu = new Menu(scanner, resultados);
        menu.iniciar();
        scanner.close();
    }
}

// Operacion.java
interface Operacion {
    double calcular(double... operandos);
    String getNombre();
}

// OperacionBasica.java
abstract class OperacionBasica implements Operacion {
    private final String nombre;

    protected OperacionBasica(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getNombre() {
        return nombre;
    }
}

// Operaciones específicas
class Suma extends OperacionBasica {
    public Suma() {
        super("Suma");
    }

    @Override
    public double calcular(double... operandos) {
        return operandos[0] + operandos[1];
    }
}

class Resta extends OperacionBasica {
    public Resta() {
        super("Resta");
    }

    @Override
    public double calcular(double... operandos) {
        return operandos[0] - operandos[1];
    }
}

class Multiplicacion extends OperacionBasica {
    public Multiplicacion() {
        super("Multiplicación");
    }

    @Override
    public double calcular(double... operandos) {
        return operandos[0] * operandos[1];
    }
}

class Division extends OperacionBasica {
    public Division() {
        super("División");
    }

    @Override
    public double calcular(double... operandos) {
        if (operandos[1] == 0) {
            throw new ArithmeticException("División por cero no permitida.");
        }
        return operandos[0] / operandos[1];
    }
}

class Potencia extends OperacionBasica {
    public Potencia() {
        super("Potencia");
    }

    @Override
    public double calcular(double... operandos) {
        return potenciaRecursiva(operandos[0], (int) operandos[1]);
    }

    private double potenciaRecursiva(double base, int exponente) {
        if (exponente == 0) return 1;
        if (exponente < 0) return 1 / potenciaRecursiva(base, -exponente);
        return base * potenciaRecursiva(base, exponente - 1);
    }
}

// Menu.java
class Menu {
    private final Scanner scanner;
    private final Resultados resultados;

    public Menu(Scanner scanner, Resultados resultados) {
        this.scanner = scanner;
        this.resultados = resultados;
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = obtenerOpcion();
            procesarOpcion(opcion);
        } while (opcion != 5);
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n=== Calculadora Científica ===");
        System.out.println("1. Suma");
        System.out.println("2. Resta");
        System.out.println("3. Multiplicación");
        System.out.println("4. División");
        System.out.println("5. Potencia");
        System.out.println("6. Ver resultados almacenados");
        System.out.println("7. Salir");
    }

    private int obtenerOpcion() {
        System.out.print("Seleccione una opción: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, ingrese un número válido.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private void procesarOpcion(int opcion) {
        Operacion operacion = null;

        switch (opcion) {
            case 1 -> operacion = new Suma();
            case 2 -> operacion = new Resta();
            case 3 -> operacion = new Multiplicacion();
            case 4 -> operacion = new Division();
            case 5 -> operacion = new Potencia();
            case 6 -> resultados.mostrarResultados();
            case 7 -> System.out.println("¡Gracias por usar la calculadora científica!");
            default -> System.out.println("Opción no válida.");
        }

        if (operacion != null) realizarOperacion(operacion);
    }

    private void realizarOperacion(Operacion operacion) {
        System.out.println("\n=== " + operacion.getNombre() + " ===");
        System.out.print("Ingrese el primer número: ");
        double num1 = scanner.nextDouble();

        System.out.print("Ingrese el segundo número: ");
        double num2 = scanner.nextDouble();

        try {
            double resultado = operacion.calcular(num1, num2);
            System.out.println("Resultado: " + resultado);
            resultados.almacenarResultado(resultado);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

// Resultados.java
class Resultados {
    private final double[] resultados;
    private int indice;

    public Resultados() {
        resultados = new double[100];
        indice = 0;
    }

    public void almacenarResultado(double resultado) {
        if (indice < resultados.length) {
            resultados[indice++] = resultado;
        } else {
            System.out.println("Error: Memoria llena, no se pueden almacenar más resultados.");
        }
    }

    public void mostrarResultados() {
        System.out.println("\n=== Resultados Almacenados ===");
        for (int i = 0; i < indice; i++) {
            System.out.println((i + 1) + ": " + resultados[i]);
        }
        if (indice == 0) {
            System.out.println("No hay resultados almacenados.");
        }
    }
}
