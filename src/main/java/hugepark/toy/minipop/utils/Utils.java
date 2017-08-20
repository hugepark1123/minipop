/**
 *
 *
 * @author : hugepar1123
 * @date : 2017-08-19
 * @since : 
 */
package hugepark.toy.minipop.utils;

import org.springframework.beans.BeanUtils;

public class Utils {
	public static <T, R> R copyBean(T source, Class<R> clazz) {
		R instance = BeanUtils.instantiate(clazz);
		BeanUtils.copyProperties(source, instance);
		return instance;
	}
 }
