package Not.Delivered.menu.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MenuUpdateRequestDto {

  @NotNull(message = "가게 ID는 필수 입력 값입니다.")
  private final Long shopId;

  private final String menuName;

  private final Integer price;
}
