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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import hugepark.toy.minipop.users.UserDuplicatedException;

@RestControllerAdvice
public class ExpectedExceptionHandler {
	
	final private HttpRequestExceptionType type = HttpRequestExceptionType.EXPECTED;
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiError handleMethodArgTypeMismatch(HttpServletRequest req, MethodArgumentTypeMismatchException e) {
		if (AnnotationUtils.findAnnotation(e.getClass(),
				ResponseStatus.class) != null)
			throw new RuntimeException();
		
		return ApiError.build(req, HttpStatus.BAD_REQUEST, e, type);
	}
	
	@ExceptionHandler(UserDuplicatedException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ApiError handleUserDuplication(HttpServletRequest req, UserDuplicatedException e) {
		if (AnnotationUtils.findAnnotation(e.getClass(),
				ResponseStatus.class) != null)
			throw new RuntimeException();
			
		return ApiError.build(req, HttpStatus.CONFLICT, e, type);
	}
}