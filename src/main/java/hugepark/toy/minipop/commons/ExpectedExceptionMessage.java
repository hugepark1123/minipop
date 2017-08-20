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

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter @Setter @ToString(exclude= {"globalErrors", "fieldErrors"})
public class ExpectedExceptionMessage {
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
	
	public static ExpectedExceptionMessage build(HttpServletRequest req, HttpStatus status, Exception e) {
		ExpectedExceptionMessage error = new ExpectedExceptionMessage();
		error.setUri(req.getRequestURI());
		error.setCode(String.valueOf(status.value()));
		error.setName(status.name());
		error.setMessage(e.getLocalizedMessage());
		
		log.error(error.toString());
		return error;
	}
}
