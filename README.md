# 404NotDelivered 🚀

404NotDelivered는 Spring Boot와 JPA를 기반으로 한 학습용 배달앱 프로젝트입니다. 회원가입, 로그인, 로그아웃 등의 인증 기능과 사용자 관리, 가게 관리, 메뉴 관리, 주문, 리뷰, 댓글 기능을 제공합니다.

## 목차 📑
- [소개 🚀](#소개-)
- [기능 💡](#기능-)
  - [인증 🔐](#인증-)
  - [사용자 관리 👤](#사용자-관리-)
  - [가게 관리 🏪](#가게-관리-)
  - [메뉴 관리 🍽](#메뉴-관리-)
  - [주문 관리 🛒](#주문-관리-)
  - [리뷰 관리 📝](#리뷰-관리-)
  - [댓글 관리 💬](#댓글-관리-)
  - [공통 기능 ⚙️](#공통-기능-)
- [시작하기 ▶️](#시작하기-)
- [API 문서 📖](#api-문서-)
- [서비스 🛠](#서비스-)
- [공통 모듈 📦](#공통-모듈-)
- [라이선스 📄](#라이선스-)

## 소개 🚀

이 프로젝트는 Spring Boot를 사용하여 개발된 학습용 배달 애플리케이션입니다. 기본적인 인증과 다양한 관리 기능을 포함하고 있으며, JWT 인증, 예외 처리, 로깅 등 공통 기능을 제공합니다.

## 기능 💡

### 인증 🔐

- **회원가입**: 새로운 사용자 등록
- **로그인**: 사용자 인증 및 JWT 토큰 발급
- **로그아웃**: JWT 토큰 무효화

### 사용자 관리 👤

- **정보 조회**: 사용자 정보 조회
- **정보 수정**: 사용자 정보 수정
- **탈퇴**: 사용자 탈퇴 처리

### 가게 관리 🏪

- **가게 등록**: 새로운 가게 등록 (사장님)
- **가게 조회**: 가게 목록 및 상세 조회
- **가게 수정**: 가게 정보 수정 (사장님)
- **가게 삭제**: 가게 폐점 처리 (사장님)

### 메뉴 관리 🍽

- **메뉴 추가**: 메뉴 추가 (사장님)
- **메뉴 수정**: 메뉴 정보 수정 (사장님)
- **메뉴 삭제**: 메뉴 삭제 (사장님)

### 주문 관리 🛒

- **주문 생성**: 사용자 주문 생성
- **주문 취소**: 주문 취소 (대기중인 주문)
- **주문 상태 변경**: 조리 및 배달 상태 변경 (사장님, 라이더)
- **주문 조회**: 주문 내역 및 상세 조회

### 리뷰 관리 📝

- **리뷰 작성**: 사용자 리뷰 작성
- **리뷰 수정**: 리뷰 수정
- **리뷰 삭제**: 리뷰 삭제
- **리뷰 조회**: 가게 리뷰 목록 조회

### 댓글 관리 💬

- **댓글 작성**: 사장님이 리뷰에 댓글 작성
- **댓글 수정**: 댓글 수정
- **댓글 삭제**: 댓글 삭제

### 공통 기능 ⚙️

- **JWT 인증**: 토큰 기반 인증 처리
- **예외 처리**: 전역 예외 처리 및 일관된 응답
- **로깅**: AOP 기반의 로깅 처리
- **공통 엔티티**: 생성, 수정 시간 자동 관리

## 시작하기 ▶️

[애플리케이션 클론 및 실행 방법]

## API 문서 📖

API에 대한 자세한 정보는 [API 문서](docs/API.md)를 참고하세요.

- **회원가입**: `POST /signup`
- **로그인**: `POST /login`
- **로그아웃**: `POST /logout`
- **사용자 정보**: `GET /users`, `PATCH /users`, `DELETE /users`
- **가게**: `POST /shops`, `GET /shops`, `PATCH /shops/{shopId}`, `DELETE /shops/{shopId}`
- **메뉴**: `POST /menus`, `PATCH /menus/{menuId}`, `DELETE /menus/{shopId}/{menuId}`
- **주문**: `POST /normalUser/purchases`, `DELETE /normalUser/purchases/{purchaseId}`, `GET /normalUser/purchases`, `PATCH /owner/purchases/{purchaseId}`, `PATCH /rider/purchases/{purchaseId}`
- **리뷰**: `POST /reviews`, `PATCH /reviews/{reviewId}`, `DELETE /reviews/{reviewId}`, `GET /reviews/shops/{shopId}`
- **댓글**: `POST /reviews/{reviewId}/comments`, `PATCH /reviews/{reviewId}/comments/{commentId}`, `DELETE /reviews/{reviewId}/comments/{commentId}`

## 서비스 🛠

- **AuthService**: 인증 로직 처리
- **UserService**: 사용자 정보 관리
- **ShopService**: 가게 정보 관리
- **MenuService**: 메뉴 정보 관리
- **PurchaseService**: 주문 처리
- **ReviewService**: 리뷰 관리
- **CommentService**: 댓글 관리
- **LogoutService**: 로그아웃 처리

## 공통 모듈 📦

- **JWT 설정**: 토큰 생성 및 검증
- **예외 처리**: 전역 예외 처리기
- **로깅**: 구매 로직 로깅
- **공통 엔티티**: `BaseTime` (생성/수정 시간), `Address` (주소 정보)
- **공통 응답 DTO**: `ApiResponse` (통일된 응답 형식)

## 라이선스 📄

[라이선스 정보]
