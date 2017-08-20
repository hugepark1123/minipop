/**
 *
 *
 * @author : hugepar1123
 * @date : 2017-08-19
 * @since : 
 */
package hugepark.toy.minipop.commons;

import lombok.Getter;

@Getter
public enum HttpRequestExceptionType {
	EXPECTED("Expected"), UNEXPECTED("Unexpected");
	
	private String value;
	
	private HttpRequestExceptionType(String value) {
		this.value = value;
	}
}
