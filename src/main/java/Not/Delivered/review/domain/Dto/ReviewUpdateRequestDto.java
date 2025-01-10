package Not.Delivered.review.domain.Dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record ReviewUpdateRequestDto(
    @Min(value = 1)
    @Max(value = 5)
    Long starPoint,
    @Size(max = 200,message = "200자 이내로 작성해주세요.")
    String reviewContent
) {

}
