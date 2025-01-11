package Not.Delivered.purchase.service;

import Not.Delivered.menu.domain.Menu;
import Not.Delivered.menu.repository.MenuRepository;
import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.purchase.domain.PurchaseStatus;
import Not.Delivered.purchase.dto.PurchaseCreateDto;
import Not.Delivered.purchase.dto.PurchaseDto;
import Not.Delivered.purchase.dto.PurchaseOfRiderDto;
import Not.Delivered.purchase.repository.PurchaseRepository;
import Not.Delivered.shop.domain.Shop;
import Not.Delivered.shop.repository.ShopRepository;
import Not.Delivered.user.domain.User;
import Not.Delivered.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

  // NORMAL_USER용 메서드
  // 주문 생성
  @Transactional
  public PurchaseDto createPurchase(Long userId, PurchaseCreateDto purchaseCreateDto) {

    User user = userRepository.findByUserIdAndIsWithdrawalFalse(userId)
        .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

    Shop shop = shopRepository.findByShopIdAndIsClosing(purchaseCreateDto.getShopId())
        .orElseThrow(() -> new EntityNotFoundException("가게를 찾을 수 없습니다."));

    Menu menu = menuRepository.findById(purchaseCreateDto.getMenuId())
        .orElseThrow(() -> new EntityNotFoundException("메뉴를 찾을 수 없습니다."));

    // 메뉴 가격이 가게의 최소 주문 금액 이상인지 확인
    if (menu.getPrice() < shop.getMinOrderPrice()) {
      throw new IllegalArgumentException("주문 금액이 가게의 최소 주문 금액보다 적습니다.");
    }

    // 현재 시간이 가게의 영업 시간 내인지 확인
    LocalTime now = LocalTime.now();
    if (!isWithinOperatingHours(now, shop.getOpenTime(), shop.getCloseTime())) {
      throw new IllegalArgumentException("현재는 가게의 영업 시간이 아닙니다.");
    }

    Purchase purchase = Purchase.builder()
        .purchaseUser(user)
        .shop(shop)
        .menu(menu)
        .purchaseStatus(PurchaseStatus.PENDING)
        .build();

    return PurchaseDto.convertToDto(purchaseRepository.save(purchase));
  }

  // 주문 취소 - NORMAL_USER
  @Transactional
  public void cancelPurchase(Long userId, Long purchaseId) {
    Purchase purchase = purchaseRepository.findById(purchaseId)
        .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));

    if (!purchase.isOwnedByThisUserId(userId)) {
      throw new AccessDeniedException("해당 주문을 취소할 권한이 없습니다.");
    }

    purchase.cancel();
    purchaseRepository.save(purchase);
  }

  @Transactional(readOnly = true)
  public List<PurchaseDto> getPurchaseListForUser(Long userId, PurchaseStatus purchaseStatus) {

    List<Purchase> purchaseList;

    if (purchaseStatus == null) {
      purchaseList = purchaseRepository.findByPurchaseUser_UserIdAndIsCancelledFalse(userId);
    } else {
      purchaseList = purchaseRepository.findByPurchaseUser_UserIdAndPurchaseStatusAndIsCancelledFalse(userId, purchaseStatus);
    }

    return purchaseList.stream()
        .map(PurchaseDto::convertToDto)
        .collect(Collectors.toList());
  }


  @Transactional(readOnly = true)
  public PurchaseDto getPurchaseForUser(Long userId, Long purchaseId) {
    Purchase purchase = purchaseRepository.findByPurchaseIdAndPurchaseUser_UserIdAndIsCancelledFalse(purchaseId, userId)
        .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));
    return PurchaseDto.convertToDto(purchase);
  }

  // OWNER용 메서드
  @Transactional(readOnly = true)
  public List<PurchaseDto> getPurchaseListForOwner(Long ownerId, PurchaseStatus purchaseStatus) {

    List<Purchase> purchaseList;

    if (purchaseStatus == null) {
      purchaseList = purchaseRepository.findByShop_OwnerUser_UserIdAndIsCancelledFalse(ownerId);
    } else {
      purchaseList = purchaseRepository.findByShop_OwnerUser_UserIdAndPurchaseStatusAndIsCancelledFalse(ownerId, purchaseStatus);
    }

    return purchaseList.stream()
        .map(PurchaseDto::convertToDto)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public PurchaseDto getPurchaseForOwner(Long ownerId, Long purchaseId) {

    Purchase purchase = purchaseRepository.findByPurchaseIdAndShop_OwnerUser_UserIdAndIsCancelledFalse(purchaseId, ownerId)
        .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));

    return PurchaseDto.convertToDto(purchase);
  }

  @Transactional
  public PurchaseDto updatePurchaseStatusByOwner(Long ownerId, Long purchaseId, PurchaseStatus newStatus) {

    Purchase purchase = purchaseRepository.findById(purchaseId)
        .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));

    // 주문이 해당 OWNER의 가게에 속해있는지 확인
    if (!purchase.isShopOwnedByThisUserId(ownerId)) {
      throw new AccessDeniedException("해당 주문에 대한 권한이 없습니다.");
    }

    purchase.changeStatus(newStatus);
    return PurchaseDto.convertToDto(purchaseRepository.save(purchase));
  }

  // RIDER용 메서드
  @Transactional(readOnly = true)
  public List<PurchaseOfRiderDto> getPurchaseListForRider(Long riderId, PurchaseStatus purchaseStatus) {
    List<Purchase> purchaseList;

    if (purchaseStatus == null) {
      // 라이더가 조회할 수 있는 모든 주문 조회
      // 배달 가능한 주문 (COOKED 상태이며 라이더가 지정되지 않음)
      List<Purchase> availableOrders = purchaseRepository.findByPurchaseStatusAndDeliveringUserIsNullAndIsCancelledFalse(PurchaseStatus.COOKED);

      // 해당 라이더가 배달 중이거나 완료한 주문
      List<Purchase> riderOrders = purchaseRepository.findByDeliveringUser_UserIdAndIsCancelledFalse(riderId);

      purchaseList = new ArrayList<>();
      purchaseList.addAll(availableOrders);
      purchaseList.addAll(riderOrders);
    } else {

      if (purchaseStatus == PurchaseStatus.COOKED) {
        purchaseList = purchaseRepository.findByPurchaseStatusAndDeliveringUserIsNullAndIsCancelledFalse(purchaseStatus);
      } else if (purchaseStatus == PurchaseStatus.DELIVERING || purchaseStatus == PurchaseStatus.DELIVERED) {
        purchaseList = purchaseRepository.findByPurchaseStatusAndDeliveringUser_UserIdAndIsCancelledFalse(purchaseStatus, riderId);
      } else {
        throw new IllegalArgumentException("해당 주문 상태에 대한 처리가 정의되지 않았습니다.");
      }
    }

    return purchaseList.stream()
        .map(PurchaseOfRiderDto::convertToDto)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public PurchaseOfRiderDto getPurchaseForRider(Long riderId, Long purchaseId) {
    Purchase purchase = purchaseRepository.findById(purchaseId)
        .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));

    if (purchase.getPurchaseStatus() == PurchaseStatus.COOKED && purchase.getDeliveringUser() == null) {
      // 배달 가능한 주문
      return PurchaseOfRiderDto.convertToDto(purchase);
    } else if (purchase.getDeliveringUser() != null && purchase.getDeliveringUser().getUserId().equals(riderId)) {
      // 해당 라이더가 배달 중이거나 완료한 주문
      return PurchaseOfRiderDto.convertToDto(purchase);
    } else {
      throw new AccessDeniedException("해당 주문에 대한 권한이 없습니다.");
    }
  }

  @Transactional
  public PurchaseOfRiderDto updatePurchaseStatusByRider(Long riderId, Long purchaseId, PurchaseStatus newStatus) {
    Purchase purchase = purchaseRepository.findById(purchaseId)
        .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));

    User rider = userRepository.findById(riderId)
        .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));

    PurchaseStatus currentStatus = purchase.getPurchaseStatus();

    if (currentStatus == PurchaseStatus.COOKED && newStatus == PurchaseStatus.DELIVERING) {
      // 주문 상태 COOKED -> DELIVERING으로 변경하고 라이더를 할당
      if (purchase.getDeliveringUser() == null) {
        purchase.setDeliveringUser(rider);
        purchase.changeStatus(newStatus);
      } else {
        throw new IllegalStateException("이미 다른 라이더가 배달 중입니다.");
      }
    } else if (currentStatus == PurchaseStatus.DELIVERING && newStatus == PurchaseStatus.DELIVERED) {
      // 배달 중인 라이더가 주문 상태를 DELIVERED로 변경
      if (purchase.getDeliveringUser().getUserId().equals(riderId)) {
        purchase.changeStatus(newStatus);
      } else {
        throw new AccessDeniedException("해당 주문에 대한 권한이 없습니다.");
      }
    } else {
      throw new IllegalStateException("주문 상태를 해당 상태로 변경할 수 없습니다.");
    }

    return PurchaseOfRiderDto.convertToDto(purchaseRepository.save(purchase));
  }

  private boolean isWithinOperatingHours(LocalTime currentTime, LocalTime openTime, LocalTime closeTime) {
    if (openTime.equals(closeTime)) {
      // 24시간 영업
      return true;
    } else if (openTime.isBefore(closeTime)) {
      // 같은 날에 영업 종료
      return !(currentTime.isBefore(openTime) || currentTime.isAfter(closeTime));
    } else {
      // 자정을 넘어 다음 날에 영업 종료하는 경우
      return !(currentTime.isBefore(openTime) && currentTime.isAfter(closeTime));
    }
  }


}
