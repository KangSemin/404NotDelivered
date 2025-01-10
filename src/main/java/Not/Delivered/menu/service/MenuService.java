package Not.Delivered.menu.service;

import Not.Delivered.menu.domain.Menu;
import Not.Delivered.menu.dto.MenuCreateRequestDto;
import Not.Delivered.menu.dto.MenuCreateResponseDto;
import Not.Delivered.menu.dto.MenuUpdateRequestDto;
import Not.Delivered.menu.dto.MenuUpdateResponseDto;
import Not.Delivered.menu.repository.MenuRepository;
import Not.Delivered.shop.domain.Shop;
import Not.Delivered.shop.service.ShopService;
import Not.Delivered.user.domain.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {

  private final MenuRepository menuRepository;
  private final ShopService shopService;

  public MenuCreateResponseDto createMenu(
      Long userId, UserStatus userRole, MenuCreateRequestDto dto) {

    if (!userRole.equals(UserStatus.OWNER)) {
      throw new IllegalArgumentException("사장님만 사용할 수 있는 기능입니다.");
    }

    Shop foundShop = shopService.foundAndValidate(userId, dto.getShopId());

    Menu newMenu =
        Menu.builder().shop(foundShop).menuName(dto.getMenuName()).price(dto.getPrice()).build();

    Menu savedMenu = menuRepository.save(newMenu);

    return MenuCreateResponseDto.builder()
        .menuName(savedMenu.getMenuName())
        .price(savedMenu.getPrice())
        .build();
  }

  @Transactional
  public MenuUpdateResponseDto updateMenu(
      Long userId, UserStatus userRole, Long menuId, MenuUpdateRequestDto dto) {
    if (!userRole.equals(UserStatus.OWNER)) {
      throw new IllegalArgumentException("사장님만 사용할 수 있는 기능입니다.");
    }

    shopService.foundAndValidate(userId, dto.getShopId());

    Menu foundMenu =
        menuRepository
            .findById(menuId)
            .orElseThrow(() -> new IllegalArgumentException("Menu not found"));

    foundMenu.updateMenuInfo(dto);

    return MenuUpdateResponseDto.builder()
        .menuName(foundMenu.getMenuName())
        .price(foundMenu.getPrice())
        .build();
  }

  @Transactional
  public void deleteMenu(Long userId, UserStatus userRole, Long shopId, Long menuId) {
    if (!userRole.equals(UserStatus.OWNER)) {
      throw new IllegalArgumentException("사장님만 사용할 수 있는 기능입니다.");
    }

    shopService.foundAndValidate(userId, shopId);

    Menu foundMenu =
        menuRepository
            .findById(menuId)
            .orElseThrow(() -> new IllegalArgumentException("Menu not found"));

    shopService.foundAndValidate(userId, shopId);
    foundMenu.deletedMenu();
  }
}
