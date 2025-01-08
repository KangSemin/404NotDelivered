package Not.Delivered.common.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtFilter jwtFilter;

  @Bean
  public FilterRegistrationBean<JwtFilter> jwtFilterBean() {
    FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(jwtFilter);
    registrationBean.addUrlPatterns("/*");
    return registrationBean;
  }


}
