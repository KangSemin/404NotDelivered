package Not.Delivered.purchase.service;

import Not.Delivered.menu.domain.Menu;
import Not.Delivered.menu.repository.MenuRepository;
import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.purchase.domain.PurchaseStatus;
import Not.Delivered.purchase.dto.PurchaseCreateDto;
import Not.Delivered.purchase.repository.PurchaseRepository;
import Not.Delivered.shop.domain.Shop;
import Not.Delivered.shop.repository.ShopRepository;
import Not.Delivered.user.domain.User;

import Not.Delivered.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;


import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // 의존성 주입
public class PurchaseService {

  private final PurchaseRepository purchaseRepository;
  private final UserRepository userRepository;
  private final ShopRepository shopRepository;
  private final MenuRepository menuRepository;

  // 주문 생성
  @Transactional
  public Purchase createPurchase(Long userId, PurchaseCreateDto purchaseCreateDto) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

    Shop shop = shopRepository.findById(purchaseCreateDto.getShopId())
        .orElseThrow(() -> new EntityNotFoundException("가게를 찾을 수 없습니다."));

    Menu menu = menuRepository.findById(purchaseCreateDto.getMenuId())
        .orElseThrow(() -> new EntityNotFoundException("메뉴를 찾을 수 없습니다."));

    Purchase purchase = Purchase.builder()
        .purchaseUser(user)
        .shop(shop)
        .menu(menu)
        .purchaseStatus(PurchaseStatus.PENDING)
        .build();

    return purchaseRepository.save(purchase);
  }

  // 주문 취소 - NORMAL_USER
  @Transactional
  public void cancelPurchase(Long userId, Long purchaseId) {
    Purchase purchase = purchaseRepository.findById(purchaseId)
        .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));

    if (!purchase.getPurchaseUser().getUserId().equals(userId)) {
      throw new AccessDeniedException("해당 주문을 취소할 권한이 없습니다.");
    }

    purchase.cancel();
    purchaseRepository.save(purchase);
  }

  // NORMAL_USER용 메서드
  @Transactional(readOnly = true)
  public List<Purchase> getPurchasesForUser(Long userId, String purchaseStatus) {
    if (purchaseStatus != null) {
      PurchaseStatus status = PurchaseStatus.valueOf(purchaseStatus);
      return purchaseRepository.findByPurchaseUser_UserIdAndPurchaseStatus(userId, status);
    }
    return purchaseRepository.findByPurchaseUser_UserId(userId);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Purchase> getPurchaseForUser(Long userId, Long purchaseId) {
    return purchaseRepository.findByPurchaseIdAndPurchaseUser_UserId(purchaseId, userId);
  }

  // OWNER용 메서드
  @Override
  @Transactional(readOnly = true)
  public List<Purchase> getPurchasesForOwner(Long ownerId, String purchaseStatus) {
    if (purchaseStatus != null) {
      PurchaseStatus status = PurchaseStatus.valueOf(purchaseStatus);
      return purchaseRepository.findByShop_UserId_UserIdAndPurchaseStatus(ownerId, status);
    }
    return purchaseRepository.findByShop_UserId_UserId(ownerId);
  }

  @Transactional(readOnly = true)
  public Optional<Purchase> getPurchaseForOwner(Long ownerId, Long purchaseId) {
    return purchaseRepository.findByPurchaseIdAndShop_UserId_UserId(purchaseId, ownerId);
  }

  @Transactional
  public Purchase updatePurchaseStatusByOwner(Long ownerId, Long purchaseId, PurchaseStatus newStatus) {
    Purchase purchase = purchaseRepository.findById(purchaseId)
        .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));

    // 주문이 해당 OWNER의 가게에 속해있는지 확인
    if (!purchase.getShop().getUserId().getUserId().equals(ownerId)) {
      throw new AccessDeniedException("해당 주문에 대한 권한이 없습니다.");
    }

    // OWNER는 다음 상태나 이전 상태로만 변경 가능
    if (!purchase.getPurchaseStatus().canTransitionTo(newStatus)) {
      throw new IllegalStateException("현재 상태에서 해당 상태로 전환할 수 없습니다.");
    }

    // 상태 변경
    purchase.changeStatus(newStatus);
    return purchaseRepository.save(purchase);
  }

  // RIDER용 메서드
  @Transactional(readOnly = true)
  public List<Purchase> getPurchasesForRider(Long riderId, String purchaseStatus) {
    if (purchaseStatus != null) {
      PurchaseStatus status = PurchaseStatus.valueOf(purchaseStatus);
      return purchaseRepository.findByPurchaseStatus(status);
    }
    return purchaseRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<Purchase> getPurchaseForRider(Long riderId, Long purchaseId) {
    return purchaseRepository.findById(purchaseId);
  }

  @Transactional
  public Purchase updatePurchaseStatusByRider(Long riderId, Long purchaseId, PurchaseStatus newStatus) {
    Purchase purchase = purchaseRepository.findById(purchaseId)
        .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));

    // 배달 가능한 주문인지 확인 (예: COOKED 상태여야 함)
    if (purchase.getPurchaseStatus() != PurchaseStatus.COOKED && purchase.getDeliveringUser() == null) {
      throw new IllegalStateException("배달할 수 있는 주문이 아닙니다.");
    }

    // RIDER는 다음 상태나 이전 상태로만 변경 가능
    if (!purchase.getPurchaseStatus().canTransitionTo(newStatus)) {
      throw new IllegalStateException("현재 상태에서 해당 상태로 전환할 수 없습니다.");
    }

    // 배송자 설정
    if (newStatus == PurchaseStatus.DELIVERING) {
      User rider = userRepository.findById(riderId)
          .orElseThrow(() -> new EntityNotFoundException("라이더를 찾을 수 없습니다."));
      purchase.setDeliveringUser(rider);
    }

    // 상태 변경
    purchase.changeStatus(newStatus);
    return purchaseRepository.save(purchase);
  }
}