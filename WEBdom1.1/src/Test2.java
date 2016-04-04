import java.util.Random;

public class Test2 {
	public static void main(String[] args) {
		Theatre t = new Theatre("SNP", 3);
		for (int i = 0; i < 15; i++) {
			new Guest("Musterija " + i, t).start();
			try {
				Thread.sleep(new Random(System.currentTimeMillis()).nextInt(100) );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
