/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package av1_aa2_enfonsarlaflota;

import java.util.Scanner;

/**
 * Versi� simplificada del joc Enfonsar La Flota. Es tracta de una versi� player
 * vs pc, on nom�s jugar� l'usuari hum�. El joc t� un tauler de 10x10 i es
 * coloquen automaticament (random) les posicions dels baixells. Compta amb 3
 * nivells de dificultat.
 *
 * @author Manel
 */
public class AV1_AA2_EnfonsarLaFlota {

    /**
     * FUNCTION:Funci� principal del joc. Es l'encarregada de cridar a
     * mostrarMenu() i jugar_partida.
     *
     * @param args
     */
    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);
        int llanxes = 0, vaixells = 0, cuirassats = 0, portavions = 0;
        int maxIntents = 0;
        int min = 1, max = 3;
        int files = 10, columnes = 10;
        String dades = " ";

        mostrarMenu();
        switch (damana_dades_entre_max_min(dades, min, max)) {
            case 1:
                llanxes = 5;
                vaixells = 3;
                cuirassats = 1;
                portavions = 1;
                maxIntents = 50;
                break;
            case 2:
                llanxes = 5;
                vaixells = 3;
                cuirassats = 1;
                portavions = 1;
                maxIntents = 30;
                break;
            case 3:
                llanxes = 1;
                vaixells = 1;
                cuirassats = 0;
                portavions = 0;
                maxIntents = 10;
                break;
        }
        System.out.println(llanxes + " " + maxIntents);
        jugar_partida(files, columnes, llanxes, vaixells, cuirassats, portavions, maxIntents);

    }

    /**
     * FUNTION: funci� generadora de men� principal. A partir de la resposta del
     * jugador se estableixen el nombre de vaixells
     *
     * @param no necesario
     */
    private static void mostrarMenu() {

        String levelOne = "5 llanxes, 3 vaixells, 1 cuirassat i 1 portaavions (50 trets).";
        String levelTwo = "2 llanxes, 3 vaixells, 1 cuirassat i 1 portaavions (30 trets).";
        String levelThree = "1 llanxa i 1 vaixell (10 trets).";

        System.out.println("===== BENVINGUTS A AFONAR LA FLOTA ====="
                + "\n 1. F�cil. " + levelOne + "\n 2. Mitj�. " + levelTwo + "\n 3. Dificil. "
                + levelThree + "\n\t Quin nivell vas a triar??");
    }

    /**
     * FUNTION: Funci� principal de control del joc. Amb ella es crea el tauler,
     * i la distribuci� de vaixells conforme al nivell de dificultat elegit
     *
     * @param files nombre de files del taules
     * @param columnes nombre de columnes del tauler
     * @param llanxes nombre de llanxes segons nivell
     * @param vaixells nombre de vaixells segons nivell
     * @param cuirassats nombre de cuirassats segons nivell
     * @param portavions nombre de portavions segons nivell
     * @param maxIntents nombre m�xim d'intetns per a guanyar
     */
    private static void jugar_partida(int files, int columnes, int llanxes,
            int vaixells, int cuirassats, int portavions, int maxIntents) {
        //creem boolean per saber si mostrar tauler o no
        boolean veureTot = false;
        int intents = 0;
        char[][] tauler = new char[files][columnes];
        System.out.println("Inici del joc. Benvingut comandant!");

        tauler = crear_tauler(files, columnes);
        inserir_barcos(tauler, llanxes, vaixells, cuirassats, portavions);

        while (intents < maxIntents && !veureTot) {
            mostra_tauler(tauler, false); // Mostra el tauler sense revelar la posici� dels barcos
            int[] coordenades = demanar_coordenades_tret(tauler, files, columnes);
            processa_tret(tauler, files, columnes);
            intents++;
            veureTot = !queden_barcos(tauler); // Comprova si queden barcos
        }

        mostra_tauler(tauler, true); // Mostra el tauler amb tots els barcos
        if (veureTot) {
            System.out.println("Has guanyat la partida!");
        } else {
            System.out.println("Has perdut. No queden m�s intents.");
        }

    }

    /**
     * FUNTION: Funci� emprada generar un tauler buit donada un nombre X de
     * posicions
     *
     * @param tamany nombre files y columnes a generar
     * @return tauler amb '-' en totes les posiciones
     */
    private static char[][] crear_tauler(int files, int columnes) {
        char[][] tauler = new char[files][columnes];
        for (int i = 0; i < tauler.length; i++) {
            for (int j = 0; j < tauler[i].length; j++) {
                tauler[i][j] = '-';
            }
        }
        return tauler;
    }

    /**
     * Funci� que es cridada per a mostrar el tauler. Fa us d'una cridada
     * recursiva per a mostrar les l�nies
     *
     *
     * @param tauler
     * @param veureTot
     */
    public static void mostra_tauler(char[][] tauler, boolean veureTot) {
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < tauler.length; i++) {
            mostra_fila(tauler, i, 0, veureTot); // Cridada recursiva per cada fila
        }
    }

    /**
     * PROCEDURE: procediment per a generar les files del taules recursivament.
     *
     * @param tauler
     * @param i longitud fila
     * @param j variable numerica de columna
     * @param mT boolean control mostral todo tablero o no
     */
    private static void mostra_fila(char[][] tauler, int i, int j, boolean mT) {
        if (j >= tauler[i].length) {
            System.out.println(" "); // Fin de la fila
            return;
        }
        // Per cada posici� de columna = 0 en la fila imprimim la lletra que toque
        if (j == 0) {
            // el valor de la lletra 65 es la lletra A del unicode
            int lletra = 65 + i;
            System.out.print((char) lletra + " ");
        }

        char casella = tauler[i][j];
        if (!mT && esBaixell(casella)) {
            System.out.print("-");
        } else {
            System.out.print(casella + " ");
        }

        mostra_fila(tauler, i, j + 1, mT);
    }

    /**
     * FUNCI�: Funci� de comprobaci� per esbrinar si un tipus de casella
     * representa vaixell o no
     *
     * @param casella
     * @return
     */
    private static boolean esBaixell(char casella) {
        char[] tipusVaixells = {'L', 'B', 'C', 'P'};
        for (char tipoVaixell : tipusVaixells) {
            if (casella == tipoVaixell) {
                return true;
            }
        }
        return false;
    }

    /**
     * PROCEDURE: Procediment per a inserir a un vaixell si es compleixen les
     * especificacions
     *
     * @param tauler
     * @param llanxes
     * @param vaixells
     * @param cuirassats
     * @param portavions
     */
    public static void inserir_barcos(char[][] tauler, int llanxes, int vaixells, int cuirassats, int portavions) {
        // Iteraci� per a cada tipus de vaixell del joc
        for (int i = 0; i < llanxes; i++) {
            if (!inserir_barco(tauler, 1, 'L')) {
                System.out.println("No es posible insertar una llanxa.");
            }
        }
        for (int i = 0; i < vaixells; i++) {
            if (!inserir_barco(tauler, 3, 'B')) {
                System.out.println("No es posible insertar un vaixell.");
            }
        }
        for (int i = 0; i < cuirassats; i++) {
            if (!inserir_barco(tauler, 4, 'C')) {
                System.out.println("No es posible insertar un cuirassat.");
            }
        }
        for (int i = 0; i < portavions; i++) {
            if (!inserir_barco(tauler, 5, 'P')) {
                System.out.println("No es posible insertar un portaavions.");
            }
        }
    }

    /**
     * FUNCTION: Funci� per a insertar un baixell en una coordenada aleatoria.
     *
     * @param tauler tauler de joc
     * @param mida mida del vaixell
     * @param tipus lletra que identifica el vaixell
     * @return True si es posible False si += 100 intentos
     */
    public static boolean inserir_barco(char[][] tauler, int mida, char tipus) {
        int intentos = 0;
        while (intentos < 100) {
            int[] coordenada = coordenada_aleatoria(tauler, mida);
            if (es_pot_inserir_barco(tauler, mida, coordenada[0], coordenada[1])) {
                for (int i = 0; i < mida; i++) {
                    tauler[coordenada[0]][coordenada[1] + i] = tipus;
                }
                return true;
            }
            intentos++;
        }
        return false;
    }

    /**
     * FUNCTION: Funci� emprada per comprobar que es pot inserir un vaixell,
     * primer es comproba que capiga al tauler si cap, es comproba que no siga
     * una casella ja ocupada.
     *
     * @param tauler tauler de joc 10x10
     * @param mida mida del vaixell
     * @param f files del tauler
     * @param c columnes del tauler
     * @return true si es`pot incerir false si no es incerir
     */
    public static boolean es_pot_inserir_barco(char[][] tauler, int mida, int f, int c) {
        int columnes = tauler[0].length;
        if (c + mida > columnes) {
            return false;
        }

        for (int i = 0; i < mida; i++) {
            if (tauler[f][c + i] != '-') {
                return false;
            }
        }
        return true;
    }

    /**
     * FUNTION: Funci� per demanar les coordenades al jugador hum� del tret. En 
     * la petici� de les files es demana un valor en lletra que es convertira en
     * un valor int per pasar a la funci� demana_dades_entre_max_min
     * @param tauler
     * @param files
     * @param columnes
     * @return
     */
    public static int[] demanar_coordenades_tret(char[][] tauler, int files, int columnes) {
        Scanner entrada = new Scanner(System.in);
        int tret[] = {0, 0}, min = 0, max = 0, valorLletra;
        char conversion;
        String dades;
        min = 0;
        max = files - 1;
        System.out.print("Indica la fila del proper tret(A-J):\n ");
        dades = entrada.nextLine();
        conversion = dades.charAt(0);
        valorLletra = (int)conversion-65;
        if (valorLletra >= min && valorLletra <= max){
        dades = String.valueOf(valorLletra);
        tret[0] = damana_dades_entre_max_min(dades, min, max);
        }else{
        System.out.println("Ep. Soles es valen lletres entre la A i ");}
        min = 0;
        max = columnes - 1;
        System.out.print("Indica la columna del proper tret (0-9):\n ");
        dades = entrada.nextLine();
        tret[1] = damana_dades_entre_max_min(dades, min, max);
        return tret;
    }

    /**
     * PROCEDURE: Procediment encarregat de processar els trets del jugador
     * Controla la logica de aigua o tocat
     *
     * @param tauler
     * @param row
     * @param column
     */
    public static void processa_tret(char[][] tauler, int row, int column) {
        switch (tauler[row][column]) {
            case '-':
                System.out.println("Aigua!");
                tauler[row][column] = 'A';
                break;
            case 'L':
            case 'B':
            case 'C':
            case 'P':
                System.out.println("Tocat!");
                tauler[row][column] = 'X';
                break;
            default:
                System.out.println("Ep! Has tornat a disparar a un tret anterior");
                break;
        }
    }

    /**
     * FUNCTION: Funci� que genera una posici� aleatoria al tauler.
     *
     * @param tauler
     * @param mida
     * @return
     */
    public static int[] coordenada_aleatoria(char[][] tauler, int mida) {
        int files = tauler.length;
        int columnes = tauler[0].length;
        int fila = (int) (Math.random() * files);
        int columna = (int) (Math.random() * (columnes - mida + 1));

        return new int[]{fila, columna};
    }

    /**
     * FUNCTION: Funci� per replegar les dades de l'usuari i comprobar que
     * siguen valides
     *
     * @param dades
     * @param min
     * @param max
     * @return
     */
    private static int damana_dades_entre_max_min(String dades, int min, int max) {
        Scanner scanner = new Scanner(System.in);
        int valor;

        while (true) {
            //System.out.print(dades);
            while (!scanner.hasNextInt()) {
                System.out.println("Aix� no �s un n�mero. Intenta-ho de nou:");
                scanner.next();
                System.out.print(dades);
            }
            valor = scanner.nextInt();

            if (valor >= min && valor <= max) {
                return valor;
            } else {
                System.out.println("El n�mero ha de ser entre " + min + " i " + max + ".");
            }
        }
    }
/**
 * FUNCTION: Funci� que recorre el tauler buscant caselles amb lletra corresponent
 * a vaixell. 
 * @param tauler
 * @return false si no queden, True si es troba casella baixell
 */
    public static boolean queden_barcos(char[][] tauler) {
        for (int fila = 0; fila < tauler.length; fila++) {
            for (int columna = 0; columna < tauler[fila].length; columna++) {
                char casella = tauler[fila][columna];
                if (esBaixell(casella)) {
                    return true;
                }
            }
        }
        return false;
    }

}
