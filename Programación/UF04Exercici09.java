/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package curso.uf04exercicis;
import java.util.Scanner;
/**
 * UF04 Exercici 8: Programa que demana l'edat i ens diu si som majors o menors d'edat
 */
public class UF04Exercici09 {

    public static void main(String[] args) {

        int edat;
        Scanner entrada = new Scanner(System.in);

        System.out.print("Introdueix l'edad: ");
        edat = entrada.nextInt();

        if (edat >= 18) {
            System.out.println("Eres major d'edat");
        } else {
            System.out.println("Eres menor d'edat");
        }
        
        entrada.close();
    }
}
