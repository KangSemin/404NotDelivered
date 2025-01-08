package Not.Delivered.common.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtConfig jwtConfig;
	private final String BEARER = "Bearer ";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String requestURI = request.getRequestURI();

		// 인증이 필요없는 경로는 통과
		if (isPermitAllPath(requestURI)) {
			filterChain.doFilter(request, response);
			return;
		}

		// Authorization 헤더 확인
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith(BEARER)) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 없거나 유효하지 않습니다.");
			return;
		}


		try {
			String token = authHeader.substring(BEARER.length());
			if (jwtConfig.validateToken(token)) {
				Long userId = jwtConfig.getUserIdFromToken(token);
				request.setAttribute("userId", userId);
				filterChain.doFilter(request, response);
			} else {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 처리 중 오류가 발생했습니다.");
		}
	}

	private boolean isPermitAllPath(String requestURI) {
//		return requestURI.equals("/users/login") ||
//				requestURI.equals("/users/signup");
		return true;
	}

}
