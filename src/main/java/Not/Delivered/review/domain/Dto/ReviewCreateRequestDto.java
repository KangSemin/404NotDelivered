package Not.Delivered.review.domain.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewCreateRequestDto(
    @Size(min = 1, max = 5, message = "별점은 1점부터 5점까지 가능합니다.")
    @NotBlank(message = "별점은 비울 수 없습니다.")
    Long starPoint,
    @Size(max = 200,message = "200자 이내로 작성해주세요.")
    @NotBlank(message = "내용은 비울 수 없습니다.")
    String reviewContent,
    @NotNull
    Long purchaseId
) {
}
