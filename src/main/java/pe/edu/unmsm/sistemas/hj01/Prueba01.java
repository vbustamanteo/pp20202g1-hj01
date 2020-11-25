/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.sistemas.hj01;

import com.google.common.base.Stopwatch;
import edu.rice.hj.Module0;
import edu.rice.hj.Module1;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author VictorHugo
 */
public class Prueba01 {
    private static final int DIM = 99900000;
    private static long X[] = new long[DIM];
    private static long suma1, suma2, suma;

    public static void main(String[] args) {
        Random ran = new Random();
        for( int i=0; i<DIM; i++ ) {
            X[i] = ran.nextInt(1000);
        }
        
        Stopwatch reloj = Stopwatch.createUnstarted();
        
        reloj.start();
        suma = calcularSuma(X, 0, DIM);
        reloj.stop();
        System.out.println( "la suma secuencial es: " + suma );
        System.out.println(
           "Tardó " + reloj.elapsed(TimeUnit.NANOSECONDS) + 
                   " nano "
        );
        
        reloj.start();
        Module0.launchHabaneroApp(() -> {
            Module0.finish(() -> {
                Module1.async(() -> {
                    suma1 = calcularSuma( X, 0, DIM/2 );
                });
                suma2 = calcularSuma( X, DIM/2, DIM );
            });
            suma = suma1 + suma2;
        });
        reloj.stop();
        System.out.println( "la suma es: " + suma );
        System.out.println(
           "Tardó " + reloj.elapsed(TimeUnit.NANOSECONDS) + 
                   " nano "
        );
    }
    
    private static long calcularSuma( long X[], int ini, int fin ) {
        long suma = 0;
        for( int i=ini; i<fin; i++ ) {
            suma = suma + X[i];
        }
        return suma;
    }
    
}
