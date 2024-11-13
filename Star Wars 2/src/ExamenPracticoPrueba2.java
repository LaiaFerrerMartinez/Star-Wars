import java.util.Random;
import java.util.Scanner;

public class ExamenPracticoPrueba2 {

    private static final int MAX_FILA_TABLERO = 10;

    private static final int MAX_COLUMNA_TABLERO = 10;

    private static char [][] tableroJugador1 = new char[MAX_FILA_TABLERO][MAX_COLUMNA_TABLERO];

    private static char [][] tableroJugador2 = new char[MAX_FILA_TABLERO][MAX_COLUMNA_TABLERO];

    private static int filaPers, columnaPers,filaYoda, columnaYoda, filaVader, columnaVader;

    private static int filaComprobacion, columnaComprobacion;

    private static int vidas1 = 3;

    private static int vidas2 = 3;

    private static int vidas;

    private static int jugador;

    private static boolean ganar = false;

    private static boolean movimientoFila;

    private static boolean movimientoColumna;

    // Relleno tablero con casillas libres
    private static void rellenarTablerosL () {
        for (int i = 0; i < MAX_FILA_TABLERO; i++) {
            for (int j = 0; j < MAX_COLUMNA_TABLERO; j++) {
                tableroJugador1 [i][j] = 'L';
                tableroJugador2 [i][j] = 'L';
            }
        }
    }

    // Coloco los personajes dependiendo del número de veces que hay que ponerlos en el tablero 1
    private static void colocarPersonajesTablero (char pers, int num, char[][] tablero) {
        Random aleatorio = new Random();
        int filaTablero;
        int columnaTablero;
        for (int i = 0; i < num; i++) {
            do {
                filaTablero = aleatorio.nextInt(9);
                columnaTablero = aleatorio.nextInt(9);
            } while (tablero[filaTablero][columnaTablero] != 'L');
            tablero[filaTablero][columnaTablero] = pers;
            if (pers=='Y') {
                filaYoda = filaTablero;
                columnaYoda = columnaTablero;
            }
            if (pers=='V') {
                filaVader = filaTablero;
                columnaVader = columnaTablero;
            }
        }
    }

    private static void colocarCasillaFinal (char[][] tablero) {
        tablero[MAX_FILA_TABLERO-1][MAX_COLUMNA_TABLERO-1] = 'F';
    }

    // Imprime el tablero que le pidas
    private static void imprimirTablero (char tablero[][]) {
        for (int i = 0; i < MAX_FILA_TABLERO; i++) {
            for (int j = 0; j < MAX_COLUMNA_TABLERO; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void desplazamiento (int fila, int columna) {
        Scanner lector = new Scanner(System.in);
        char[][] tablero;
        if (jugador == 1) {
            tablero = tableroJugador1;
        } else {
            tablero = tableroJugador2;
        }
        switch (tablero[fila][columna]) {
            case 'L':
                filaPers = fila;
                columnaPers = columna;
                if (jugador == 1) {
                    tablero [filaYoda][columnaYoda] = 'L';
                } else {
                    tablero [filaVader][columnaVader] = 'L';
                }
                break;
            case 'D', 'R':
                filaPers = fila;
                columnaPers = columna;
                vidas --;
                System.out.println("Te quedan " + vidas1 + " vidas.");
                if (jugador == 1) {
                    tablero [filaYoda][columnaYoda] = 'L';
                } else {
                    tablero [filaVader][columnaVader] = 'L';
                }
                break;
            case 'M':
                System.out.println("El muro no te deja desplazarte.");
                break;
            case 'F':
                filaPers = fila;
                columnaPers = columna;
                System.out.println("El jugador " + jugador + "ha ganado.");
                if (jugador == 1) {
                    tablero [filaYoda][columnaYoda] = 'L';
                } else {
                    tablero [filaVader][columnaVader] = 'L';
                }
                ganar = true;
                break;
            case 'P':
                System.out.println("Dime a qué casilla quieres ir.");
                System.out.print("Fila (0 - 9): ");
                filaPers = lector.nextInt();
                System.out.print("Columna (0 - 9): ");
                columnaPers = lector.nextInt();
                desplazamiento(filaPers, columnaPers);
                break;

        }
    }

    private static void movD (int casillas) {
        if ((columnaPers + casillas) >= MAX_COLUMNA_TABLERO) {
            columnaComprobacion = columnaPers + casillas - MAX_COLUMNA_TABLERO;
        } else {
            columnaComprobacion = columnaPers + casillas;
        }
        movimientoColumna = true;
    }

    private static void movA (int casillas) {
        if ((columnaPers - casillas) < 0) {
            columnaComprobacion = columnaPers - casillas + MAX_COLUMNA_TABLERO;
        } else {
            columnaComprobacion = columnaPers - casillas;
        }
        movimientoColumna = true;
    }

    private static void movW (int casillas) {
        if ((filaPers - casillas) < 0) {
            filaComprobacion = filaPers - casillas + MAX_FILA_TABLERO;
        } else {
            filaComprobacion = filaPers - casillas;
        }
        movimientoFila = true;
    }

    private static void movS (int casillas) {
        if ((filaPers + casillas) >= MAX_FILA_TABLERO) {
            filaComprobacion = filaPers + casillas - MAX_FILA_TABLERO;
        } else {
            filaComprobacion = filaPers + casillas;
        }
        movimientoFila = true;
    }

    private static void movQ (int casillas) {
        movA(casillas);
        movW(casillas);
    }

    private static void movR (int casillas) {
        movD(casillas);
        movW(casillas);
    }

    private static void movE (int casillas) {
        movA(casillas);
        movS(casillas);
    }

    private static void movB (int casillas) {
        movD(casillas);
        movS(casillas);
    }

    private static void movimientos () {
        Scanner lectorCas = new Scanner(System.in);

        // Pido el número de casillas
        int casillas;
        do {
            System.out.println("Dime el número de casillas que te quieres desplazar.");
            casillas = lectorCas.nextInt();
        } while (casillas > 3 || casillas <= 0);


        // Pido la direccion del desplazamiento
        Scanner lector = new Scanner(System.in);
        System.out.println("Dime la dirección de desplazamiento.");
        String direccion = lector.nextLine();
        movimientoFila = false;
        movimientoColumna = false;
        switch (direccion) {
            case "D":
                movD(casillas);
                break;
            case "A":
                movA(casillas);
                break;
            case "W":
                movW(casillas);
                break;
            case "S":
                movS(casillas);
                break;
            case "Q":
                movQ(casillas);
                break;
            case "R":
                movR(casillas);
                break;
            case "E":
                movE(casillas);
                break;
            case "B":
                movB(casillas);
                break;
        }
        if (movimientoFila && movimientoColumna) {
            desplazamiento (filaComprobacion, columnaComprobacion);
        } else if (movimientoFila) {
            desplazamiento(filaComprobacion, columnaPers);
        } else if (movimientoColumna) {
            desplazamiento(filaPers, columnaComprobacion);
        }
    }

    private static void jugar () {
        System.out.println("Utiliza el teclado WASD y QREB.");
        System.out.println("D: derecha; A: izquierda; W: arriba; S: abajo; Q: diagonal izquierda y arriba; " +
                "R: diagonal derecha arriba; E: diagonal izquierda abajo; B: diagonal derecha abajo.");
        jugador = 1;
        do {
            switch (jugador) {
                case 1:
                    System.out.println("Jugador 1:");
                    imprimirTablero(tableroJugador1);
                    System.out.println();
                    vidas = vidas1;
                    filaPers = filaYoda;
                    columnaPers = columnaYoda;
                    movimientos();
                    tableroJugador1[filaPers][columnaPers] = 'Y';
                    imprimirTablero(tableroJugador1);
                    vidas1 = vidas;
                    filaYoda = filaPers;
                    columnaYoda = columnaPers;
                    break;
                case 2:
                    System.out.println("Jugador 2:");
                    imprimirTablero(tableroJugador2);
                    System.out.println();
                    vidas = vidas2;
                    filaPers = filaVader;
                    columnaPers = columnaVader;
                    movimientos();
                    tableroJugador2[filaPers][columnaPers] = 'V';
                    imprimirTablero(tableroJugador2);
                    filaVader = filaPers;
                    columnaVader = columnaPers;
                    vidas2 = vidas;
                    break;
            }
            if (jugador == 1) {
                jugador = 2;
            } else if (jugador == 2) {
                jugador = 1;
            }
            System.out.println();
        } while (vidas > 0 && ganar==false);
    }

    public static void main(String[] args) {
        rellenarTablerosL();
        colocarPersonajesTablero('Y', 1, tableroJugador1);
        colocarPersonajesTablero('D', 5, tableroJugador1);
        colocarPersonajesTablero('M', 5, tableroJugador1);
        colocarPersonajesTablero('P', 5, tableroJugador1);
        colocarCasillaFinal(tableroJugador1);
        colocarPersonajesTablero('V', 1, tableroJugador2);
        colocarPersonajesTablero('R', 5, tableroJugador2);
        colocarPersonajesTablero('M', 5, tableroJugador2);
        colocarPersonajesTablero('P', 5, tableroJugador2);
        colocarCasillaFinal(tableroJugador2);
        jugar();
    }
}