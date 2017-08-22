/**
 * 
 * 
 * @author : hugepark1123
 * @date : 2017-08-17
 * @since : 
 */
package hugepark.toy.minipop.accounts;

import lombok.Getter;

@Getter
public class UserDuplicatedException extends RuntimeException {

	private static final long serialVersionUID = 8984837209807642968L;
	
	public UserDuplicatedException(String message) {
		super(message);
	}

}
