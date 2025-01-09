package Not.Delivered.common.interceptor;

import Not.Delivered.user.domain.UserStatus;
import ch.qos.logback.core.joran.spi.HttpUtil.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthorizationInterceptor implements HandlerInterceptor {


  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    UserStatus userStatus = UserStatus.valueOf((String) request.getAttribute("userStatus"));

    if (userStatus == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 에러
      response.getWriter().write("Unauthorized: Missing userStatus");
      return false;
    }

    switch (userStatus) {
      case OWNER:
        return !isUriPermitOnlyDriver(request, response);
      case RIDER:
        return !isUriPermitOnlyOwner(request, response);
      case NORMAL_USER:
        return !isUriPermitOnlyDriver(request, response) && !isUriPermitOnlyOwner(request,
            response);
    }

    // 정상적으로 통과
    return true;
  }

  private boolean isUriPermitOnlyOwner(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    String uri = request.getRequestURI();
    if (pathMatcher.match("/shops", uri) && !request.getMethod().equals("GET") ||
        pathMatcher.match("/menus", uri) && !request.getMethod().equals("GET") ||
        pathMatcher.match("/orders/{orderId}", uri) && request.getMethod().equals("DELETE") ||
        pathMatcher.match("/shops/{shopId}/orders", uri) ||
        pathMatcher.match("/orders/{orderId}/complete", uri) ||
        pathMatcher.match("/reviews/{reviewId}/comments", uri)
    ) {
      return true;
    }

    response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 에러
    response.getWriter().write("Forbidden: You do not have access to this resource.");
    return false;
  }

  private boolean isUriPermitOnlyDriver(HttpServletRequest request, HttpServletResponse response)
      throws Exception {

    String uri = request.getRequestURI();

    if (pathMatcher.match("/orders/{orderId}/deliver", uri) ||
        pathMatcher.match("/orders", uri) && request.getMethod().equals("GET")) {
      return true;
    }

    response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 에러
    response.getWriter().write("Forbidden: You do not have access to this resource.");
    return false;
  }

}
