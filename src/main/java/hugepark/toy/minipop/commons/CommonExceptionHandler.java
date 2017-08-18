/**
 *
 *
 * @author : hugepar1123
 * @date : 2017-08-18
 * @since : 
 */
package hugepark.toy.minipop.commons;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
@ResponseStatus
public class CommonExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ApiError handleError(HttpServletRequest req, Exception exception)
			throws RuntimeException {

		if (AnnotationUtils.findAnnotation(exception.getClass(),
				ResponseStatus.class) != null)
			throw new RuntimeException();

		log.error("Request: {} raised: {}, reqInfo: {}", req.getRequestURI(), exception.getLocalizedMessage());
		
		ApiError error = new ApiError();
		error.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		error.setName(HttpStatus.INTERNAL_SERVER_ERROR.name());
		error.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		
		return error;
	}
}
