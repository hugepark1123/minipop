/**
 *
 *
 * @author : hugepar1123
 * @date : 2017-08-17
 * @since : 
 */
package hugepark.toy.minipop.commons;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiError {
	private String code;
	private String name;
	private String message;
	private Date time;

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
}
