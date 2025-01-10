package Not.Delivered.review.domain.Dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewCreateRequestDto(
    @Min(value = 1)
    @Max(value = 5)
    @NotBlank(message = "별점은 비울 수 없습니다.")
    Long starPoint,
    @Size(max = 200,message = "200자 이내로 작성해주세요.")
    @NotBlank(message = "내용은 비울 수 없습니다.")
    String reviewContent,
    @NotNull
    Long purchaseId
) {
}
