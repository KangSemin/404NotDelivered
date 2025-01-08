package Not.Delivered.user.service;

public class TempUserNotFoundException extends RuntimeException {

  public TempUserNotFoundException(String message) {
    super(message);
  }
}
