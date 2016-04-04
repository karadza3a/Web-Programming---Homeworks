import java.util.Random;

public class Customer extends Thread {

    public Customer(GasStation station, int fuelType, String name) {
        this.station = station;
        this.fuelType = fuelType;
        this.name = name;
    }

    public void run() {

        // 1. musterija se voza gradom

        try {
            Thread.sleep(new Random(System.currentTimeMillis()).nextInt(700));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(System.currentTimeMillis() + name + " dosao na pumpu, tip " + fuelType);

        // 2. dolazi u na pumpu, izabere svoj terminal za tocenje i staje u red
        GasPump gp = station.getGasPump(fuelType);
        gp.access();

        System.out.println(System.currentTimeMillis() + name + " toci gorivo");

        // 3. toci gorivo
        try {
            Thread.sleep(new Random(System.currentTimeMillis()).nextInt(700));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(System.currentTimeMillis() + name + " napusta");
        // 4. napusta pumpu (podrazumeva se da je platio ;)
        gp.leave();
    }

    private GasStation station;
    private int fuelType;
    private String name;
}