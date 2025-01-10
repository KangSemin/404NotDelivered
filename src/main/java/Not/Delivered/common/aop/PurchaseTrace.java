package Not.Delivered.common.aop;


import Not.Delivered.common.dto.ApiResponse;
import Not.Delivered.purchase.dto.PurchaseDto;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
      + "execution(public * Not.Delivered.purchase.controller.PurchaseNormalUserController.*(..))||"
      + "execution(public * Not.Delivered.purchase.controller.PurchaseRiderController.*(..))")
  public Object purchaseLogging(ProceedingJoinPoint joinPoint) throws Throwable {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

//    if("GET".equalsIgnoreCase(request.getMethod())){
//      return joinPoint.proceed();
//    }

    Object result = joinPoint.proceed();
    logger.info(getLogMessage(request, result));

    return result;
  }

  private String getLogMessage(HttpServletRequest request, Object result) {
    StringBuilder logMessage = new StringBuilder();

    String purchaseId = ((PurchaseDto) ((ArrayList) ((ApiResponse) ((ResponseEntity) result)
        .getBody()).getData()).get(0)).getPurchaseId().toString();
    String shopId = ((PurchaseDto) ((ArrayList) ((ApiResponse) ((ResponseEntity) result)
        .getBody()).getData()).get(0)).getShopId().toString();
    String purchaseStatus = ((PurchaseDto) ((ArrayList) ((ApiResponse) ((ResponseEntity) result)
        .getBody()).getData()).get(0)).getPurchaseStatus().toString();

    logMessage.append("[purchase log ").append(LocalDateTime.now()).append("] : requestUser = ")
        .append(request.getAttribute("userId")).append(", requestURL = ")
        .append(request.getRequestURI()).append(", purchase ID = ").append(purchaseId)
        .append(", shop ID = ").append(shopId).append(", purchaseStatus = ").append(purchaseStatus);

    return logMessage.toString();
  }
}


