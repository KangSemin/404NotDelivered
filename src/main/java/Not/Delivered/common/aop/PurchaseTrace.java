package Not.Delivered.common.aop;


import Not.Delivered.common.dto.ApiResponse;
import Not.Delivered.purchase.dto.PurchaseDto;
import Not.Delivered.purchase.dto.PurchaseOfRiderDto;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class PurchaseTrace {

  private final Logger logger = LoggerFactory.getLogger(PurchaseTrace.class.getName());

  @Around("execution(public * Not.Delivered.purchase.controller.PurchaseOwnerController.*(..))||"
      + "execution(public * Not.Delivered.purchase.controller.PurchaseNormalUserController.*(..))")
  public Object purchaseLogging(ProceedingJoinPoint joinPoint) throws Throwable {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

    if("GET".equalsIgnoreCase(request.getMethod())){
      return joinPoint.proceed();
    }

    Object result = joinPoint.proceed();
    logger.info(getLogMessage(request, result));

    return result;
  }

  private String getLogMessage(HttpServletRequest request, Object result) {
    StringBuilder logMessage = new StringBuilder();

    String purchaseId = ((PurchaseDto)((ApiResponse) ((ResponseEntity) result)
        .getBody()).getData()).getPurchaseId().toString();
    String shopId = ((PurchaseDto)((ApiResponse) ((ResponseEntity) result)
        .getBody()).getData()).getShopId().toString();
    String purchaseStatus = ((PurchaseDto)((ApiResponse) ((ResponseEntity) result)
        .getBody()).getData()).getPurchaseStatus().toString();

    logMessage.append("[purchase log ").append(LocalDateTime.now()).append("] : requestUser = ")
        .append(request.getAttribute("userId")).append(", requestURL = ")
        .append(request.getRequestURI()).append(", purchase ID = ").append(purchaseId)
        .append(", shop ID = ").append(shopId).append(", purchaseStatus = ").append(purchaseStatus);

    return logMessage.toString();
  }

  @Around("execution(public * Not.Delivered.purchase.controller.PurchaseRiderController.*(..))")
  public Object purchaseRiderLogging(ProceedingJoinPoint joinPoint) throws Throwable {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

    if("GET".equalsIgnoreCase(request.getMethod())){
      return joinPoint.proceed();
    }

    Object result = joinPoint.proceed();
    logger.info(getRiderLogMessage(request,result));

    return result;
  }

  private String getRiderLogMessage(HttpServletRequest request, Object result) {
    StringBuilder logMessage = new StringBuilder();

    String purchaseId = ((PurchaseOfRiderDto)((ApiResponse) ((ResponseEntity) result)
        .getBody()).getData()).getPurchaseId().toString();
    String shopId = ((PurchaseOfRiderDto)((ApiResponse) ((ResponseEntity) result)
        .getBody()).getData()).getShopId().toString();
    String purchaseStatus = ((PurchaseOfRiderDto)((ApiResponse) ((ResponseEntity) result)
        .getBody()).getData()).getPurchaseStatus().toString();

    logMessage.append("[purchase log ").append(LocalDateTime.now()).append("] : requestUser = ")
        .append(request.getAttribute("userId")).append(", requestURL = ")
        .append(request.getRequestURI()).append(", purchase ID = ").append(purchaseId)
        .append(", shop ID = ").append(shopId).append(", purchaseStatus = ").append(purchaseStatus);

    return logMessage.toString();
  }
}


