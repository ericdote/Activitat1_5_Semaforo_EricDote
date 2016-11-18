package practica15semaforsericdote;

import java.util.concurrent.Semaphore;

public class Practica15SemaforsEricDote {

    public static float saldo = 1500;

    public static void main(String[] args) {
        /**
         * Creem el semaphore
         */
        final Semaphore semafor = new Semaphore(1, true);
        /**
         * Creem un Runnable per fer els ingressos
         */
        final Runnable ingressos = new Runnable() {
            /**
             * En aquest metode donem pas a l'accio de ingressar diners No
             * permitim cap accio més amb acquire() Lliberem el bloqueig amb
             * relase()
             */
            public void run() {
                try {
                    semafor.acquire();
                    ingressar(50);
                    System.out.println("Saldo actual: " + llegirSaldo());
                    semafor.release();
                } catch (Exception e) {

                }
            }
        };
        /**
         * Creem un Runnable per poder extreure diners
         */
        final Runnable treure = new Runnable() {
            /**
             * Aquest run dona pas a l'accio de retirar diners.
             */
            public void run() {
                try {
                    semafor.acquire();
                    treure(50);
                    System.out.println(Thread.currentThread().getName() + " " + llegirSaldo());
                    semafor.release();
                } catch (Exception e) {
                }
            }
        };

        /**
         * Realitzem un bucle per recorrer els threads que em creat i aixi
         * executarlos
         */
        for (int i = 0; i < 10; i++) {
            new Thread(ingressos).start();
            new Thread(treure).start();
        }
    }

    /**
     * A aquest metode es realitza l'operacio d'ingressar els diners. El process
     * es agafar el saldo que hi ha actualment, sumar-li els diners que volem
     * sumar, I el saldo sera igual a la suma(aux). Per ultim es guarda el salgo
     * amb la crida del metode guardarSaldo.
     *
     * @param diners
     */
    public static void ingressar(float diners) {
        float aux, saldo;
        aux = llegirSaldo();
        aux += diners;
        saldo = aux;
        guardarSaldo(saldo);
    }

    /**
     * A aquest metode es realitza l'operacio de treure diners. El process es
     * identic al de ingressar, treient que en comptes de sumar diners es resta.
     *
     * @param diners
     */
    public static void treure(float diners) {
        float aux;
        aux = llegirSaldo();
        aux = aux - diners;
        saldo = aux;
        guardarSaldo(saldo);
    }

    /**
     * Aquest metode llegeix el saldo actual.
     *
     * @return
     */
    public static float llegirSaldo() {
        return saldo;
    }

    /**
     * Aquest metode guarda el saldo actualitzat.
     *
     * @param saldoMod
     */
    public static void guardarSaldo(float saldoMod) {
        saldo = saldoMod;
    }
}
