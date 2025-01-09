package Not.Delivered.menu.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuUpdateResponseDto {

  private final String menuName;

  private final Integer price;

  @Builder
  public MenuUpdateResponseDto(String menuName, Integer price) {
    this.menuName = menuName;
    this.price = price;
  }
}
