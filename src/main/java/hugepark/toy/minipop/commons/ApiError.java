/**
 *
 *
 * @author : hugepar1123
 * @date : 2017-08-17
 * @since : 
 */
package hugepark.toy.minipop.commons;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter @Setter @ToString(exclude= {"globalErrors", "fieldErrors"})
public class ApiError {
	private String uri;
	private String code;
	private String name;
	private String message;

	private List<GlobalError> globalErrors = new ArrayList<>();
	private List<FieldError> fieldErrors = new ArrayList<>();
	
	@AllArgsConstructor
	@Getter @Setter
	public static class FieldError {
		private String field;
		private String code;
		private Object rejectedValue;
		private String message;
	}
	
	@AllArgsConstructor
	@Getter @Setter
	public static class GlobalError {
		private String code;
		private String message;
	}
	
	public static ApiError build(HttpServletRequest req, HttpStatus status, Exception e, HttpRequestExceptionType type) {
		final ApiError apiError = new ApiError();
		apiError.setUri(req.getRequestURI());
		apiError.setCode(String.valueOf(status.value()));
		apiError.setName(status.name());
		apiError.setMessage(e.getLocalizedMessage());

		log.error("{} exception occured. "
				+ "request URI : {}, "
				+ "HttpStatus Code : {}, "
				+ "HttpStatus Name : {}, "
				+ "Exception message : {}",
				type.getValue(),
				apiError.getUri(),
				apiError.getCode(),
				apiError.getName(),
				apiError.getMessage());
		
		return apiError;
	}

	public static ApiError build(HttpStatus badRequest, HttpRequestExceptionType expected, BindingResult result) {
		final ApiError apiError = new ApiError();
		List<GlobalError> globalErrors = result.getGlobalErrors().stream()
				.map(error -> 
					new GlobalError(
						error.getCode(), 
						error.getDefaultMessage()))
				.collect(Collectors.toList());
		apiError.setGlobalErrors(globalErrors);
		
		List<FieldError> fieldErrors = result.getFieldErrors().stream()
			.map(error ->
				new FieldError(
						error.getField(),
						error.getCode(),
						error.getRejectedValue(),
						error.getDefaultMessage()))
			.collect(Collectors.toList());
		apiError.setFieldErrors(fieldErrors);
		return apiError;
	}
}
