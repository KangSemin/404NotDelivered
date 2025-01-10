package Not.Delivered.purchase.service;

import Not.Delivered.menu.domain.Menu;
import Not.Delivered.menu.repository.MenuRepository;
import Not.Delivered.purchase.domain.Purchase;
import Not.Delivered.purchase.domain.PurchaseStatus;
import Not.Delivered.purchase.dto.PurchaseCreateDto;
import Not.Delivered.purchase.dto.PurchaseDto;
import Not.Delivered.purchase.repository.PurchaseRepository;
import Not.Delivered.shop.domain.Shop;
import Not.Delivered.shop.repository.ShopRepository;
import Not.Delivered.user.domain.User;
import Not.Delivered.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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

  // 주문 생성
  @Transactional
  public PurchaseDto createPurchase(Long userId, PurchaseCreateDto purchaseCreateDto) {
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

  // NORMAL_USER용 메서드
  @Transactional(readOnly = true)
  public List<PurchaseDto> getPurchasesForUser(Long userId, String purchaseStatus) {

    PurchaseStatus status = PurchaseStatus.valueOf(purchaseStatus);
    List<Purchase> purchaseList = purchaseRepository.findByPurchaseUser_UserIdAndPurchaseStatus(userId, status);

    return purchaseList.stream()
        .map(PurchaseDto::convertToDto)
        .collect(Collectors.toList());
  }


  @Transactional(readOnly = true)
  public PurchaseDto getPurchaseForUser(Long userId, Long purchaseId) {
    Purchase purchase = purchaseRepository.findByPurchaseIdAndPurchaseUser_UserId(purchaseId, userId)
        .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));
    return PurchaseDto.convertToDto(purchase);
  }

  // OWNER용 메서드
  @Transactional(readOnly = true)
  public List<PurchaseDto> getPurchaseListForOwner(Long ownerId, String purchaseStatus) {

    PurchaseStatus status = PurchaseStatus.valueOf(purchaseStatus);
    List<Purchase> purchaseList = purchaseRepository.findByShop_OwnerUser_UserIdAndPurchaseStatus(ownerId, status)
          .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));

    return purchaseList.stream()
        .map(PurchaseDto::convertToDto)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public PurchaseDto getPurchaseForOwner(Long ownerId, Long purchaseId) {

    Purchase purchase = purchaseRepository.findByPurchaseIdAndShop_OwnerUser_UserId(purchaseId, ownerId)
        .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));

    return PurchaseDto.convertToDto(purchase);
  }

  @Transactional
  public PurchaseDto updatePurchaseStatusByOwner(Long ownerId, Long purchaseId, PurchaseStatus newStatus) {

    Purchase purchase = purchaseRepository.findById(purchaseId)
        .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));

    // 주문이 해당 OWNER의 가게에 속해있는지 확인
    if (!purchase.isOwnedByThisUserId(ownerId)) {
      throw new AccessDeniedException("해당 주문에 대한 권한이 없습니다.");
    }

    purchase.changeStatus(newStatus);
    return PurchaseDto.convertToDto(purchaseRepository.save(purchase));
  }

  // RIDER용 메서드
  public List<PurchaseDto> getPurchaseListForRider(Long riderId, String purchaseStatus) {

    //    purchaseStatus null여부는 controller에서 global로 잡힌다.
    PurchaseStatus status = PurchaseStatus.valueOf(purchaseStatus);
    List<Purchase> purchaseList = purchaseRepository.findByPurchaseStatus(status);

    return purchaseList.stream()
        .map(PurchaseDto::convertToDto)
        .collect(Collectors.toList());
  }

  public PurchaseDto getPurchaseForRider(Long riderId, Long purchaseId) {
    Purchase purchase = purchaseRepository.findById(purchaseId)
        .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));
    return PurchaseDto.convertToDto(purchase);
  }

  @Transactional
  public PurchaseDto updatePurchaseStatusByRider(Long riderId, Long purchaseId, PurchaseStatus newStatus) {
    Purchase purchase = purchaseRepository.findById(purchaseId)
        .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));

//    유저의 라이더 여부: 세션에서 검증됨
    User rider = userRepository.findById(riderId)
        .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));

    // 배달 가능한 주문인지 확인 (예: COOKED 상태여야 함)
    if (purchase.getPurchaseStatus() != PurchaseStatus.COOKED && purchase.getDeliveringUser() == null) {
      throw new IllegalStateException("배달할 수 있는 주문이 아닙니다.");
    }
    // 배송자 설정
    if (newStatus == PurchaseStatus.DELIVERING) {
      purchase.setDeliveringUser(rider);
    }

    // 상태 변경
    purchase.changeStatus(newStatus);
    return PurchaseDto.convertToDto(purchaseRepository.save(purchase));
  }


}