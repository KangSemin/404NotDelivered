package Not.Delivered.purchase.domain;

public enum PurchaseStatus {
	PENDING,       // 대기중
	COOKING,       // 조리중
	COOKED,        // 조리완료
	DELIVERING,    // 배달중
	DELIVERED      // 배달완료
}