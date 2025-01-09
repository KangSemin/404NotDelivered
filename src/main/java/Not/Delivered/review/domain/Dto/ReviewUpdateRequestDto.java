package Not.Delivered.review.domain.Dto;

import jakarta.validation.constraints.Size;

public record ReviewUpdateRequestDto(
    @Size(min = 1, max = 5, message = "별점은 1점부터 5점까지 가능합니다.")
    Long starPoint,
    @Size(max = 200,message = "200자 이내로 작성해주세요.")
    String reviewContent
) {

}
