package Not.Delivered.menu.dto;

import Not.Delivered.menu.domain.Menu;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuReadResponseDto {

  private final String menuName;

  private final Integer price;

  @Builder
  public MenuReadResponseDto(String menuName, Integer price) {
    this.menuName = menuName;
    this.price = price;
  }

  public static List<MenuReadResponseDto> toDtoList(List<Menu> menuList) {
    return menuList.stream()
        .map(
            menu ->
                MenuReadResponseDto.builder()
                    .menuName(menu.getMenuName())
                    .price(menu.getPrice())
                    .build())
        .toList();
  }
}
