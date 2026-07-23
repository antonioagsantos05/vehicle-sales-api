package br.com.fiap.vehiclesales.exception;
import jakarta.servlet.http.HttpServletRequest; import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime; import java.util.*; import java.util.stream.Collectors;
@RestControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler(NotFoundException.class) ResponseEntity<ApiError> notFound(NotFoundException e,HttpServletRequest r){return build(HttpStatus.NOT_FOUND,"RESOURCE_NOT_FOUND",e.getMessage(),r,Map.of());}
 @ExceptionHandler(BusinessException.class) ResponseEntity<ApiError> conflict(BusinessException e,HttpServletRequest r){return build(HttpStatus.CONFLICT,"BUSINESS_RULE_VIOLATION",e.getMessage(),r,Map.of());}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ApiError> validation(MethodArgumentNotValidException e,HttpServletRequest r){Map<String,String> f=e.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(x->x.getField(),x->Objects.requireNonNullElse(x.getDefaultMessage(),"inválido"),(a,b)->a)); return build(HttpStatus.BAD_REQUEST,"VALIDATION_ERROR","Dados inválidos.",r,f);}
 @ExceptionHandler(Exception.class) ResponseEntity<ApiError> generic(Exception e,HttpServletRequest r){return build(HttpStatus.INTERNAL_SERVER_ERROR,"INTERNAL_ERROR","Erro interno inesperado.",r,Map.of());}
 private ResponseEntity<ApiError> build(HttpStatus s,String c,String m,HttpServletRequest r,Map<String,String> f){return ResponseEntity.status(s).body(new ApiError(LocalDateTime.now(),s.value(),s.getReasonPhrase(),c,m,r.getRequestURI(),f));}
}
