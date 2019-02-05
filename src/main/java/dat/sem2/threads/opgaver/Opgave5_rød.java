/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat.sem2.threads.opgaver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Mads Egevang Jensen
 */
public class Opgave5_rød {
    
    public static void main( String[] args ) {
        ExecutorService workingJack = Executors.newSingleThreadExecutor();
//        for ( int count = 0; count < 25; count++ ) {
//                            System.out.println( "Hello "+ count + " to us" );

            workingJack.submit( () -> {
                // Det er en rød opgave at forklare hvad denne fejl skyldes
                // Fjern udkommenteringen i næste linje
                for ( int count = 0; count < 25; count++ ) {
                System.out.println( "Hello "+ count + " to us" );
                }
            } );
//        }
        workingJack.shutdown();
    }
}

/* Svar:
    Den anonyme funktion, der bliver kaldt efter workingJack.submit, skaber en 
    syntax error, fordi en local variable, i dette tilfælde count, i en anonym 
    funktion, er nødt til at være "effectively final", menende at den ikke vil
    blive ændret på. 
    
    Dette skyldes, at den tråd, der skaber den anonyme funktion, også kaldet 
    "lambda", kan give den videre til en anden tråd, og derefter ændre
    på den lokale variabel, hvilket strider imod lambda principielle funktion, 
    at kunne overrække specifikke funktioner til specifikke threads (?)
    
    Vores løsning på fejlen, er at enten flytte sout funktionen ud af den 
    anonyme funktion, eller at flytte for-loopet ind i den anynyme funktion,
    således, at den samme thread både står for at ændre den lokale variabel og
    at kalde den lokale variabel.
*/

