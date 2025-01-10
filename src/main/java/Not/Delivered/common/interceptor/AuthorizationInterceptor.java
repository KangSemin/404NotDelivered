package Not.Delivered.common.interceptor;

import Not.Delivered.user.domain.UserStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthorizationInterceptor implements HandlerInterceptor {

  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    String userStatusValue = (String) request.getAttribute("userStatus");
    if (userStatusValue == null) {
      setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Missing userStatus");
      return false;
    }

    UserStatus userStatus;
    try {
      userStatus = UserStatus.valueOf(userStatusValue);
    } catch (IllegalArgumentException e) {
      setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid userStatus");
      return false;
    }

    // NORMAL_USER 권한 확인
    if (isNormalUserApi(request)) {
      return true;
    }

    // OWNER 전용 API 확인
    if (userStatus == UserStatus.OWNER && isOwnerApi(request)) {
      return true;
    }

    // RIDER 전용 API 확인
    if (userStatus == UserStatus.RIDER && isRiderApi(request)) {
      return true;
    }

    // 접근 권한 없음
    setErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "Forbidden: Access denied");
    return false;
  }

  private boolean isNormalUserApi(HttpServletRequest request) {
    // NORMAL_USER에게 허용된 API

    Map<String,String> normalUserApis = new HashMap<>();

    normalUserApis.put("/users",null);
    normalUserApis.put("/users/**",null);
    normalUserApis.put("/reviews","Post");
    normalUserApis.put("/reviews/{reviewId}",null);
    normalUserApis.put("/reviews/{reviewId}/comments","Get");
    normalUserApis.put("/shops","Get");
    normalUserApis.put("/shops/*","Get");
    normalUserApis.put("/orders/{orderId}","Get");
//    normalUserApis.put("/purchases/normalUser","Get");
//    normalUserApis.put("/purchases/{purchaseId}","Get");
//    normalUserApis.put("/purchases","Post");
//    normalUserApis.put("/purchases/{orderId}","Delete");
    normalUserApis.put("/normalUser/**",null); // 추가함

    return isUriMatching(request, normalUserApis);
  }

  private boolean isOwnerApi(HttpServletRequest request) {
    // OWNER 전용 API

    Map<String,String> ownerApis = new HashMap<>();

    ownerApis.put("/menus","Post");
    ownerApis.put("/menus/**",null);
    ownerApis.put("/reviews/{reviewId}/comments", "Post");
    ownerApis.put("/reviews/{reviewId}/comments/*", null);
    ownerApis.put("/shops","Post");
    ownerApis.put("/shops/**",null);
    ownerApis.put("/owner/purchases/**",null); // 수정
//    ownerApis.put("/purchases/owner*",null);
//    ownerApis.put("/purchases/owner/**",null);
    
    return isUriMatching(request, ownerApis);
  }

  private boolean isRiderApi(HttpServletRequest request) {
    // RIDER 전용 API
    Map<String,String> riderApis = new HashMap<>();

    riderApis.put("/purchases/rider*",null);
    riderApis.put("/rider/purchases/**",null); // 추가


    return isUriMatching(request, riderApis);
  }

  private boolean isUriMatching(HttpServletRequest request, Map<String,String> allowedApis) {
    String uri = request.getRequestURI();
    for (String allowedUri : allowedApis.keySet()) {
      String method = allowedApis.get(allowedUri);
      if (pathMatcher.match(allowedUri, uri) && (method == null || request.getMethod().equalsIgnoreCase(method))) {
        return true;
      }
    }
    return false;
  }

  private void setErrorResponse(HttpServletResponse response, int status, String message) throws Exception {
    response.setStatus(status);
    response.getWriter().write(message);
  }
}
