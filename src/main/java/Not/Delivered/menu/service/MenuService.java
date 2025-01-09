package Not.Delivered.menu.service;

import Not.Delivered.menu.domain.Menu;
import Not.Delivered.menu.dto.MenuCreateRequestDto;
import Not.Delivered.menu.dto.MenuCreateResponseDto;
import Not.Delivered.menu.repository.MenuRepository;
import Not.Delivered.shop.domain.Shop;
import Not.Delivered.shop.service.ShopService;
import Not.Delivered.user.domain.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
