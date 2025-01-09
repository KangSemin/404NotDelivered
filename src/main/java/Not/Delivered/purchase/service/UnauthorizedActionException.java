package Not.Delivered.purchase.service;

public class UnauthorizedActionException extends RuntimeException {

  public UnauthorizedActionException(String message) {
    super(message);
  }
}
