package Not.Delivered.common.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Address {

  private String city;

  private String state;

  private String street;

  private String detailAddress1;

  private String detailAddress2;

  public Address() {
  }

  public Address(String city, String state, String street, String detailAddress1,
      String detailAddress2) {
    this.city = city;
    this.state = state;
    this.street = street;
    this.detailAddress1 = detailAddress1;
    this.detailAddress2 = detailAddress2;
  }
}
