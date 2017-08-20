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

@RestControllerAdvice
@ResponseStatus
public class UnexpectedExceptionHandler {
	
	final private HttpRequestExceptionType type = HttpRequestExceptionType.UNEXPECTED;
	
	@ExceptionHandler(Exception.class)
	public ApiError handleError(HttpServletRequest req, Exception e)
			throws RuntimeException {

		if (AnnotationUtils.findAnnotation(
				e.getClass(),
				ResponseStatus.class) != null)
			throw new RuntimeException();
		
		return ApiError.build(req, HttpStatus.INTERNAL_SERVER_ERROR, e, type);
	}
}
