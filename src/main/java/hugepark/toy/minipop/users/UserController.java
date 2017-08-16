/**
 *
 *
 * @author : hugepark1123
 * @date : 2017-08-16
 * @since : 
 */
package hugepark.toy.minipop.users;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hugepark.toy.minipop.commons.ErrorResponse;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@PostMapping("/users")
	public ResponseEntity<?> createUser(
			@RequestBody
			@Valid
			UserDto.Create dto, BindingResult result) {
		
		if(result.hasErrors()) {
			ErrorResponse error = new ErrorResponse();
			error.setCode("bad.request");
			error.setMessage(
					result.getAllErrors().stream()
					.map(msg -> msg.getDefaultMessage())
					.collect(Collectors.joining(",")));
			return ResponseEntity.badRequest().body(error);
		}
		
		Optional<User> user = service.createUser(dto);
		
		if(user.isPresent() == false) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		UserDto.Reponse response = new UserDto.Reponse();
		BeanUtils.copyProperties(user.get(), response);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
