import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GasPump {

    private BlockingQueue<Object> tocilica = new LinkedBlockingQueue<>();

    public GasPump() {
        customerCount = 0;
        tocilica.add(new Object());
    }

    /**
     * Staje u red.
     */
    public void access() {
        customerCount++;
        try {
            tocilica.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Odlazi.
     */
    public void leave() {
        tocilica.add(new Object());
        customerCount--;
    }

    /**
     * vraca broj musterija koje stoje u redu, ukljucujuci i onoga ko
     * je zauzeo terminal za tocenje goriva.
     */
    public synchronized int getCustomerCount() {
        return customerCount;
    }

    /**
     * broj musterija koje stoje u redu, ukljucujuci
     * i onoga ko trenutno koristi terminal za tocenje.
     */
    public int customerCount;
}
