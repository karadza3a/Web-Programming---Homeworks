import java.util.LinkedList;
import java.util.Queue;

public class Theatre {

    Queue<Guest> q = new LinkedList<>();

    public Theatre(String name, int counterCount) {
        this.name = name;
        counters = new boolean[counterCount];
    }

    /**
     * zauzimanje saltera ako je slobodan
     */
    public synchronized int approachCounter(Guest guest) {
        int index = getCounter();

        while (index == -1) {
            try {
                this.wait();
                index = getCounter();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        counters[index] = true;
        return index;
    }

    /**
     * oslobadjanje saltera
     */
    public synchronized void leaveCounter(Guest guest, int index) {
        counters[index] = false;
        this.notify();
    }

    /**
     * vraca redni broj prvog slobodnog saltera; vraca -1 ako nema slobodnih
     * saltera
     */
    private int getCounter() {
        for (int i = 0; i < counters.length; i++)
            if (counters[i] == false)
                return i;
        return -1;
    }

    /**
     * naziv pozorista
     */
    public String name;

    /**
     * niz saltera kojima pristupaju gosti; ako je vrednost i-tog elementa niza
     * false, i-ti salter je slobodan
     */
    public boolean[] counters;
}