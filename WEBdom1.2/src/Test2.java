public class Test2 {

    /**
     * Pokrece primer: ima jedna pumpa i 50 nervoznih vozaca.
     */
    public static void main(String[] args) {
        GasStation station = new GasStation();
        for (int i = 0; i < 9; i++)
            new Customer(station, (int) Math.round(Math.random() * 3), "Vozac " + i).start();
    }
}
