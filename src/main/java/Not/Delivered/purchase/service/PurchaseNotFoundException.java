package Not.Delivered.purchase.service;

public class PurchaseNotFoundException extends RuntimeException {

  public PurchaseNotFoundException(String message) {
    super(message);
  }
}
