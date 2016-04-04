import java.util.Random;

public class Guest extends Thread {

	/** konstruktor */
	public Guest(String name, Theatre theatre) {
		this.name = name;
		this.theatre = theatre;
	}

	/** opisuje ponasanje gosta */
	public void run() {
		System.out.println( name + " usao u pozoriste.");
		int counterIndex = theatre.approachCounter(this);
		System.out.println( name + " stao za salter " + counterIndex);
		try {
			Thread.sleep(new Random(System.currentTimeMillis()).nextInt(700));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		theatre.leaveCounter(this, counterIndex);
		System.out.println( name + " kupio kartu na salteru " + counterIndex);
	}

	/** ime gosta */
	public String name;

	/** pozoriste u koje se ide */
	private Theatre theatre;

}