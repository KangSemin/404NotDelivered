package Not.Delivered.purchase.controller;

import Not.Delivered.common.dto.ApiResponse;
import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.purchase.dto.PurchaseCreateDto;
import Not.Delivered.purchase.service.AccessDeniedException;
import Not.Delivered.purchase.service.PurchaseService;
import Not.Delivered.user.domain.UserStatus;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/normalUser/purchases")
public class PurchaseNormalUserController {

  private final PurchaseService purchaseService;

  // 주문 생성 - NORMAL_USER
  @PostMapping
  public ResponseEntity<ApiResponse<Purchase>> createPurchase(
      @RequestAttribute Long userId,
      @RequestAttribute UserStatus userStatus,
      @Valid @RequestBody PurchaseCreateDto purchaseCreateDto) {

    if (userStatus != UserStatus.NORMAL_USER) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body(ApiResponse.error(HttpStatus.FORBIDDEN, "일반 사용자만 주문을 생성할 수 있습니다."));
    }

    Purchase newPurchase = purchaseService.createPurchase(userId, purchaseCreateDto);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(HttpStatus.CREATED, "주문이 성공적으로 생성되었습니다.", newPurchase));
  }

  // 주문 취소 - NORMAL_USER (PENDING 상태에서만)
  @DeleteMapping("/{purchaseId}")
  public ResponseEntity<ApiResponse<Void>> cancelPurchase(
      @RequestAttribute Long userId,
      @RequestAttribute UserStatus userStatus,
      @PathVariable Long purchaseId) {

    if (userStatus != UserStatus.NORMAL_USER) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body(ApiResponse.error(HttpStatus.FORBIDDEN, "일반 사용자만 주문을 취소할 수 있습니다."));
    }

    try {
      purchaseService.cancelPurchase(userId, purchaseId);
      return ResponseEntity.ok(
          ApiResponse.success(HttpStatus.OK, "주문이 성공적으로 취소되었습니다.", null)
      );
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(ApiResponse.error(HttpStatus.BAD_REQUEST, e.getMessage()));
    } catch (AccessDeniedException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body(ApiResponse.error(HttpStatus.FORBIDDEN, e.getMessage()));
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(ApiResponse.error(HttpStatus.NOT_FOUND, e.getMessage()));
    }
  }

  // 주문 목록 조회 - NORMAL_USER 본인의 주문만 조회
  @GetMapping
  public ResponseEntity<ApiResponse<List<Purchase>>> getUserPurchases(
      @RequestAttribute Long userId,
      @RequestAttribute UserStatus userStatus,
      @RequestParam(required = false) String purchaseStatus) {

    if (userStatus != UserStatus.NORMAL_USER) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body(ApiResponse.error(HttpStatus.FORBIDDEN, "일반 사용자만 자신의 주문 목록을 조회할 수 있습니다."));
    }

    List<Purchase> purchases = purchaseService.getPurchasesForUser(userId, purchaseStatus);

    return ResponseEntity.ok(
        ApiResponse.success(HttpStatus.OK, "주문 목록 조회에 성공했습니다.", purchases)
    );
  }

  // 주문 상세 조회 - NORMAL_USER 본인의 주문만 조회
  @GetMapping("/{purchaseId}")
  public ResponseEntity<ApiResponse<Purchase>> getUserPurchase(
      @RequestAttribute Long userId,
      @RequestAttribute UserStatus userStatus,
      @PathVariable Long purchaseId) {

    if (userStatus != UserStatus.NORMAL_USER) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body(ApiResponse.error(HttpStatus.FORBIDDEN, "일반 사용자만 자신의 주문을 조회할 수 있습니다."));
    }

    Optional<Purchase> purchaseOptional = purchaseService.getPurchaseForUser(userId, purchaseId);

    if (purchaseOptional.isPresent()) {
      return ResponseEntity.ok(
          ApiResponse.success(HttpStatus.OK, "주문 조회에 성공했습니다.", purchaseOptional.get())
      );
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(ApiResponse.error(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."));
    }
  }
}