package Not.Delivered.purchase.domain;

public enum PurchaseStatus {
  PENDING(null, "COOKING"),         // 대기중
  COOKING("PENDING", "COOKED"),     // 조리중
  COOKED("COOKING", "DELIVERING"),  // 조리완료
  DELIVERING("COOKED", "DELIVERED"),// 배달중
  DELIVERED("DELIVERING", null);    // 배달완료

  private final String previousStatus;
  private final String nextStatus;

  PurchaseStatus(String previousStatus, String nextStatus) {
    this.previousStatus = previousStatus;
    this.nextStatus = nextStatus;
  }

  public PurchaseStatus getPreviousStatus() {
    if (previousStatus == null) {
      return null;
    }
    return PurchaseStatus.valueOf(previousStatus);
  }

  public PurchaseStatus getNextStatus() {
    if (nextStatus == null) {
      return null;
    }
    return PurchaseStatus.valueOf(nextStatus);
  }

  // 특정 상태로의 전환이 가능한지 확인하는 메서드
  public boolean canTransitionTo(PurchaseStatus targetStatus) {
    return targetStatus == this.getNextStatus() || targetStatus == this.getPreviousStatus();
  }
}