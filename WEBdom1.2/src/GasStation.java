public class GasStation {

  public GasStation() {
    diesel = new GasPump();
    normal = new GasPump();
    leadfree = new GasPump();
    superb = new GasPump();
  }

  /** vraca terminal odgovarajuceg tipa */
  public GasPump getGasPump(int type) {
    switch (type) {
      case 0:
        return diesel;
      case 1:
        return normal;
      case 2:
        return leadfree;
      case 3:
        return superb;
      default:
        return null;
    }
  }

  /** za svaki tip goriva po jedan terminal za tocenje */
  private GasPump diesel;
  private GasPump normal;
  private GasPump leadfree;
  private GasPump superb;
}  