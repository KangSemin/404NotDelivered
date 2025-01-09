package Not.Delivered.menu.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuCreateResponseDto {

  private final String menuName;

  private final Integer price;

  @Builder
  public MenuCreateResponseDto(String menuName, Integer price) {
    this.menuName = menuName;
    this.price = price;
  }
}
