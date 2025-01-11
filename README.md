# 404NotDelivered

404NotDelivered는 Spring Boot와 JPA를 기반으로 한 학습용 배달앱 프로젝트입니다. 이 프로젝트는 회원가입, 로그인, 로그아웃과 같은 인증 기능과 함께 사용자, 가게, 메뉴 관리, 주문, 리뷰, 댓글 등의 기능을 구현하고 있습니다.

## 목차
- [소개](#소개)
- [기능](#기능)
  - [인증](#인증)
  - [사용자 관리](#사용자-관리)
  - [가게 관리](#가게-관리)
  - [메뉴 관리](#메뉴-관리)
  - [주문 관리](#주문-관리)
  - [리뷰 관리](#리뷰-관리)
  - [댓글 관리](#댓글-관리)
  - [공통 기능](#공통-기능)
    - [JWT 인증](#jwt-인증)
    - [예외 처리](#예외-처리)
    - [로깅](#로깅)
    - [엔티티 공통 속성](#엔티티-공통-속성)
- [시작하기](#시작하기)
- [API 문서](#api-문서)
  - [회원가입](#회원가입)
  - [로그인](#로그인)
  - [로그아웃](#로그아웃)
  - [사용자 API](#사용자-api)
  - [가게 API](#가게-api)
  - [메뉴 API](#메뉴-api)
  - [주문 API](#주문-api)
  - [리뷰 API](#리뷰-api)
  - [댓글 API](#댓글-api)
- [서비스](#서비스)
  - [AuthService](#authservice)
  - [UserService](#userservice)
  - [ShopService](#shopservice)
  - [MenuService](#menuservice)
  - [PurchaseService](#purchaseservice)
  - [ReviewService](#reviewservice)
  - [CommentService](#commentservice)
  - [LogoutService](#logoutservice)
- [공통 모듈](#공통-모듈)
  - [JWT 설정](#jwt-설정)
  - [예외 처리](#예외-처리-1)
  - [로깅](#로깅-1)
  - [엔티티 공통 속성](#엔티티-공통-속성-1)
  - [공통 응답 DTO](#공통-응답-dto)
- [라이선스](#라이선스)

## 소개

이 프로젝트는 Spring Boot를 사용하여 개발된 학습용 배달 애플리케이션입니다. 기본적인 사용자 인증 기능을 포함하며, 사용자 관리, 가게 관리, 메뉴 관리, 주문 관리, 리뷰 관리, 댓글 관리 등의 기능을 제공합니다. 또한 JWT 인증, 예외 처리, 로깅 등 다양한 공통 기능을 포함하고 있습니다.

## 기능

### 인증

- **회원가입**: 새로운 사용자를 등록합니다.
- **로그인**: 사용자를 인증하고 JWT 토큰을 발급합니다.
- **로그아웃**: JWT 토큰을 블랙리스트에 추가하여 무효화합니다.

### 사용자 관리

- **회원 정보 조회**: 사용자의 정보를 조회합니다.
- **회원 정보 수정**: 사용자의 정보를 수정합니다.
- **회원 탈퇴**: 사용자를 탈퇴 처리합니다.

### 가게 관리

- **가게 등록**: 새로운 가게를 등록합니다 (사장님만 가능).
- **가게 조회**: 전체 가게 목록 또는 특정 가게를 조회합니다.
- **가게 수정**: 가게 정보를 수정합니다 (사장님만 가능).
- **가게 삭제**: 가게를 삭제(폐점)합니다 (사장님만 가능).

### 메뉴 관리

- **메뉴 추가**: 가게에 새로운 메뉴를 추가합니다 (사장님만 가능).
- **메뉴 수정**: 메뉴 정보를 수정합니다 (사장님만 가능).
- **메뉴 삭제**: 메뉴를 삭제(판매 중지)합니다 (사장님만 가능).

### 주문 관리

- **주문 생성**: 사용자(NORMAL_USER)가 주문을 생성합니다.
- **주문 취소**: 사용자(NORMAL_USER)가 주문을 취소합니다 (대기중인 주문만 가능).
- **주문 상태 변경**: 사장님(OWNER)은 조리 상태를 변경하고, 라이더(RIDER)는 배달 상태를 변경합니다.
- **주문 조회**: 사용자, 사장님, 라이더가 자신의 주문을 조회합니다.

### 리뷰 관리

- **리뷰 작성**: 사용자(NORMAL_USER)가 리뷰를 작성합니다.
- **리뷰 수정**: 사용자(NORMAL_USER)가 작성한 리뷰를 수정합니다.
- **리뷰 삭제**: 사용자(NORMAL_USER)가 작성한 리뷰를 삭제합니다.
- **리뷰 조회**: 특정 가게의 리뷰 목록을 조회합니다.

### 댓글 관리

- **댓글 작성**: 사장님(OWNER)이 리뷰에 대한 댓글을 작성합니다.
- **댓글 수정**: 사장님(OWNER)이 작성한 댓글을 수정합니다.
- **댓글 삭제**: 사장님(OWNER)이 작성한 댓글을 삭제합니다.

### 공통 기능

#### JWT 인증

- **JwtConfig**: JWT 토큰의 생성, 파싱 및 유효성 검사를 처리합니다.
- **JwtFilter**: JWT 토큰을 검증하여 인증된 요청만 접근하도록 필터링합니다.
- **SecurityConfig**: JWT 필터를 애플리케이션의 필터 체인에 등록합니다.
- **PasswordEncoder**: 사용자 비밀번호를 해싱하고 검증합니다.

#### 예외 처리

- **GlobalExceptionHandler**: 전역 예외 처리기로, 각종 예외 상황에 대해 일관된 API 응답을 제공합니다.

#### 로깅

- **PurchaseTrace**: AOP를 활용하여 구매 관련 로직에 대한 로깅을 처리합니다.

#### 엔티티 공통 속성

- **BaseTime**: 생성일자와 수정일자를 자동으로 관리하기 위한 엔티티입니다.
- **Address**: 공통 주소 정보를 관리하기 위한 임베디드 엔티티입니다.

## 시작하기

로컬에서 애플리케이션을 실행하려면 다음 단계를 따라주세요.

[애플리케이션 클론 및 실행 방법에 대한 지침]

## API 문서

### 회원가입

**엔드포인트**: `POST /signup`

새로운 사용자를 등록하고 JWT 토큰을 반환합니다.

**요청 바디:**

- `email` (string): 사용자의 이메일 주소.
- `password` (string): 사용자의 비밀번호.
- `username` (string): 사용자의 이름.
- `userStatus` (enum): 사용자의 역할/상태 (`NORMAL_USER`, `OWNER`, `RIDER`).
- `address` (object): 사용자의 주소.

### 로그인

**엔드포인트**: `POST /login`

사용자를 인증하고 JWT 토큰을 반환합니다.

**요청 바디:**

- `email` (string): 사용자의 이메일 주소.
- `password` (string): 사용자의 비밀번호.

### 로그아웃

**엔드포인트**: `POST /logout`

JWT 토큰을 무효화하여 사용자를 로그아웃합니다.

**헤더:**

- `Authorization` (string): Bearer 토큰.

### 사용자 API

**회원 정보 조회**

- **엔드포인트**: `GET /users`
- **설명**: 현재 로그인한 사용자의 정보를 조회합니다.

**회원 정보 수정**

- **엔드포인트**: `PATCH /users`
- **설명**: 현재 로그인한 사용자의 정보를 수정합니다.
- **요청 바디**: 수정할 사용자 정보 (`userName`, `email`, `password`, `phoneNumber`, `address`, `userStatus`).

**회원 탈퇴**

- **엔드포인트**: `DELETE /users`
- **설명**: 현재 로그인한 사용자를 탈퇴 처리합니다.

### 가게 API

**가게 등록**

- **엔드포인트**: `POST /shops`
- **설명**: 새로운 가게를 등록합니다 (사장님만 가능).
- **요청 바디**: 가게 정보 (`shopName`, `introduce`, `address`, `phoneNumber`, `openTime`, `closeTime`, `minOrderPrice`).

**가게 조회**

- **엔드포인트**:
  - 전체 가게 조회: `GET /shops`
  - 특정 가게 조회: `GET /shops/{shopId}`
- **설명**: 전체 가게 목록 또는 특정 가게를 조회합니다.
- **쿼리 파라미터**: `shopName` (선택 사항)

**가게 수정**

- **엔드포인트**: `PATCH /shops/{shopId}`
- **설명**: 가게 정보를 수정합니다 (사장님만 가능).
- **요청 바디**: 수정할 가게 정보 (`shopName`, `introduce`, `address`, `phoneNumber`, `openTime`, `closeTime`, `minOrderPrice`).

**가게 삭제**

- **엔드포인트**: `DELETE /shops/{shopId}`
- **설명**: 가게를 삭제(폐점)합니다 (사장님만 가능).

### 메뉴 API

**메뉴 추가**

- **엔드포인트**: `POST /menus`
- **설명**: 가게에 새로운 메뉴를 추가합니다 (사장님만 가능).
- **요청 바디**: 메뉴 정보 (`shopId`, `menuName`, `price`).

**메뉴 수정**

- **엔드포인트**: `PATCH /menus/{menuId}`
- **설명**: 메뉴 정보를 수정합니다 (사장님만 가능).
- **요청 바디**: 수정할 메뉴 정보 (`shopId`, `menuName`, `price`).

**메뉴 삭제**

- **엔드포인트**: `DELETE /menus/{shopId}/{menuId}`
- **설명**: 메뉴를 삭제(판매 중지)합니다 (사장님만 가능).

### 주문 API

**주문 생성**

- **엔드포인트**: `POST /normalUser/purchases`
- **설명**: 사용자가 새로운 주문을 생성합니다.
- **요청 바디**: 주문 정보 (`shopId`, `menuId`).

**주문 취소**

- **엔드포인트**: `DELETE /normalUser/purchases/{purchaseId}`
- **설명**: 사용자가 주문을 취소합니다 (대기중인 주문만 가능).

**주문 조회**

- **엔드포인트**:
  - 사용자: `GET /normalUser/purchases` 또는 `GET /normalUser/purchases/{purchaseId}`
  - 사장님: `GET /owner/purchases` 또는 `GET /owner/purchases/{purchaseId}`
  - 라이더: `GET /rider/purchases` 또는 `GET /rider/purchases/{purchaseId}`
- **설명**: 자신의 주문 목록 또는 상세 정보를 조회합니다.

**주문 상태 변경**

- **엔드포인트**:
  - 사장님: `PATCH /owner/purchases/{purchaseId}`
  - 라이더: `PATCH /rider/purchases/{purchaseId}`
- **설명**: 사장님은 주문의 조리 상태를 변경하고, 라이더는 배달 상태를 변경합니다.
- **요청 바디**: 변경할 주문 상태 (`purchaseStatus`).

### 리뷰 API

**리뷰 작성**

- **엔드포인트**: `POST /reviews`
- **설명**: 사용자가 리뷰를 작성합니다.
- **요청 바디**: 리뷰 정보 (`starPoint`, `reviewContent`, `purchaseId`).

**리뷰 수정**

- **엔드포인트**: `PATCH /reviews/{reviewId}`
- **설명**: 사용자가 리뷰를 수정합니다.
- **요청 바디**: 수정할 리뷰 정보 (`starPoint`, `reviewContent`).

**리뷰 삭제**

- **엔드포인트**: `DELETE /reviews/{reviewId}`
- **설명**: 사용자가 리뷰를 삭제합니다.

**리뷰 조회**

- **엔드포인트**: `GET /reviews/shops/{shopId}`
- **설명**: 특정 가게의 리뷰 목록을 조회합니다.
- **쿼리 파라미터**: `minStarPoint`, `maxStarPoint` (별점 필터, 선택 사항)

### 댓글 API

**댓글 작성**

- **엔드포인트**: `POST /reviews/{reviewId}/comments`
- **설명**: 사장님이 리뷰에 댓글을 작성합니다.
- **요청 바디**: 댓글 내용 (`commentContent`).

**댓글 수정**

- **엔드포인트**: `PATCH /reviews/{reviewId}/comments/{commentId}`
- **설명**: 사장님이 댓글을 수정합니다.
- **요청 바디**: 수정할 댓글 내용 (`commentContent`).

**댓글 삭제**

- **엔드포인트**: `DELETE /reviews/{reviewId}/comments/{commentId}`
- **설명**: 사장님이 댓글을 삭제합니다.

## 서비스

### AuthService

사용자 인증 및 등록 로직을 처리합니다.

- **signUpUser**: 새로운 사용자를 등록하고, 비밀번호를 암호화하여 저장합니다.
- **login**: 사용자의 이메일과 비밀번호를 검증하고 JWT 토큰을 생성합니다.

### UserService

사용자 정보 조회, 수정, 탈퇴 로직을 처리합니다.

- **getUserById**: 사용자 정보를 조회합니다.
- **updateUser**: 사용자 정보를 수정합니다.
- **deleteUser**: 사용자를 탈퇴 처리합니다.

### ShopService

가게 등록, 조회, 수정, 삭제 로직을 처리합니다.

- **createShop**: 새로운 가게를 등록합니다.
- **findAllShop**: 전체 가게 목록을 조회합니다.
- **findByShopId**: 특정 가게를 조회합니다.
- **updateShop**: 가게 정보를 수정합니다.
- **deleteShop**: 가게를 삭제(폐점)합니다.

### MenuService

메뉴 추가, 수정, 삭제 로직을 처리합니다.

- **createMenu**: 가게에 새로운 메뉴를 추가합니다.
- **updateMenu**: 메뉴 정보를 수정합니다.
- **deleteMenu**: 메뉴를 삭제(판매 중지)합니다.

### PurchaseService

주문 생성, 취소, 조회, 상태 변경 로직을 처리합니다.

- **createPurchase**: 사용자가 새로운 주문을 생성합니다.
- **cancelPurchase**: 사용자가 주문을 취소합니다.
- **getPurchaseListForUser**: 사용자의 주문 목록을 조회합니다.
- **getPurchaseForUser**: 사용자의 주문 상세 정보를 조회합니다.
- **getPurchaseListForOwner**: 사장님의 가게에 대한 주문 목록을 조회합니다.
- **getPurchaseForOwner**: 사장님의 가게에 대한 주문 상세 정보를 조회합니다.
- **updatePurchaseStatusByOwner**: 사장님이 주문의 조리 상태를 변경합니다.
- **getPurchaseListForRider**: 라이더가 배달할 수 있는 주문 목록을 조회합니다.
- **getPurchaseForRider**: 라이더의 주문 상세 정보를 조회합니다.
- **updatePurchaseStatusByRider**: 라이더가 주문의 배달 상태를 변경합니다.

### ReviewService

리뷰 작성, 수정, 삭제, 조회 로직을 처리합니다.

- **createReview**: 사용자가 리뷰를 작성합니다.
- **updateReview**: 사용자가 리뷰를 수정합니다.
- **deleteReview**: 사용자가 리뷰를 삭제합니다.
- **getShopReview**: 특정 가게의 리뷰 목록을 조회합니다.

### CommentService

댓글 작성, 수정, 삭제 로직을 처리합니다.

- **createComment**: 사장님이 리뷰에 댓글을 작성합니다.
- **updateComment**: 사장님이 댓글을 수정합니다.
- **deleteComment**: 사장님이 댓글을 삭제합니다.

### LogoutService

Redis를 활용하여 JWT 토큰을 블랙리스트에 추가함으로써 로그아웃을 처리합니다.

- **addToBlacklist**: 토큰을 블랙리스트에 추가합니다.
- **isBlacklisted**: 토큰이 블랙리스트에 있는지 확인합니다.

## 공통 모듈

### JWT 설정

**JwtConfig**

JWT 토큰의 생성, 유효성 검사 및 토큰에서 정보 추출을 담당합니다.

- **generateToken**: 사용자 ID와 상태를 기반으로 JWT 토큰을 생성합니다.
- **getUserIdFromToken**: 토큰에서 사용자 ID를 추출합니다.
- **getUserStatusFromToken**: 토큰에서 사용자 상태를 추출합니다.
- **getExpiration**: 토큰의 만료 시간을 계산합니다.
- **validateToken**: 토큰의 유효성을 검증합니다.

**JwtFilter**

JWT 토큰을 검증하여 인증된 사용자만 접근할 수 있도록 필터링합니다.

- 필터는 `/login`, `/signup` 경로를 제외하고 모든 요청에 대해 토큰을 확인합니다.
- 유효한 토큰인 경우 요청에 사용자 정보를 설정하고 다음 필터로 전달합니다.

**SecurityConfig**

JWT 필터를 애플리케이션의 필터 체인에 등록합니다.

### 예외 처리

**GlobalExceptionHandler**

애플리케이션 전역에서 발생하는 예외를 처리하고 일관된 API 응답을 제공합니다.

- 다양한 예외 상황에 대해 적절한 HTTP 상태 코드와 메시지를 반환합니다.
- 로그를 통해 예외 정보를 기록합니다.

### 로깅

**PurchaseTrace**

AOP를 활용하여 구매 관련 컨트롤러의 메서드 실행 전후에 로깅을 수행합니다.

- 구매 요청에 대한 상세한 로그를 생성하여 추적 및 디버깅에 활용합니다.
- 요청한 사용자, 요청 URL, 구매 ID, 상점 ID, 구매 상태 등을 로그에 포함합니다.

### 엔티티 공통 속성

**BaseTime**

엔티티의 생성 시간과 수정 시간을 자동으로 관리하기 위한 추상 클래스입니다.

- **createdAt**: 생성 시간.
- **lastModifiedAt**: 수정 시간.

**Address**

사용자와 가게 등의 주소 정보를 관리하기 위한 임베디드 엔티티입니다.

- **city**: 시.
- **state**: 군/구.
- **street**: 읍/면/동.
- **detailAddress1**: 상세 주소 1.
- **detailAddress2**: 상세 주소 2.

### 공통 응답 DTO

**ApiResponse**

API 응답의 형식을 일관되게 관리하기 위한 제네릭 클래스입니다.

- **status**: HTTP 상태 코드.
- **message**: 응답 메시지.
- **data**: 응답 데이터.

## 라이선스

[라이선스 정보 추가]
