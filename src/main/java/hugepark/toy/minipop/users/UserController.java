/**
 *
 *
 * @author : hugepark1123
 * @date : 2017-08-16
 * @since : 
 */
package hugepark.toy.minipop.users;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hugepark.toy.minipop.commons.ApiError;
import hugepark.toy.minipop.commons.ApiError.FieldError;
import hugepark.toy.minipop.commons.ApiError.GlobalError;
import hugepark.toy.minipop.users.UserDto.Request;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@PostMapping("/users")
	public ResponseEntity<?> createUser(
			@RequestBody
			@Valid
			Request.Create dto, BindingResult result) {
		
		if(result.hasErrors()) {
			List<FieldError> fieldErrors = result.getFieldErrors().stream()
				.map(error ->
					new FieldError(
							error.getField(),
							error.getCode(),
							error.getRejectedValue(),
							error.getDefaultMessage()))
				.collect(Collectors.toList());
			List<GlobalError> globalErrors = result.getGlobalErrors().stream()
					.map(error -> 
						new GlobalError(
							error.getCode(), 
							error.getDefaultMessage()))
					.collect(Collectors.toList());
			ApiError error = new ApiError();
			error.setFieldErrors(fieldErrors);
			error.setGlobalErrors(globalErrors);
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
	
	@GetMapping("/users")
	public ResponseEntity<?> getUsers(Pageable pageable) {
		Page<User> page = service.findAll(pageable);
		
		if(page.hasContent() == false) {
			return ResponseEntity.noContent().build();
		}
		
		List<UserDto.Reponse> content = page.getContent().stream()
			.map(user -> {
				UserDto.Reponse dto = new UserDto.Reponse();
				BeanUtils.copyProperties(user, dto);
				return dto;
			})
			.collect(Collectors.toList());
		
		PageImpl<UserDto.Reponse> result = new PageImpl<>(content, pageable, page.getTotalElements());
		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id) {
		Optional<User> users = service.findOne(id);
		
		if(users.isPresent() == false)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(users.get());
	}
	
	@GetMapping(value="/users", params="username")
	public ResponseEntity<?> getUser(@RequestParam String username) {
		Optional<User> users = service.findByUsername(username);
		
		if(users.isPresent() == false)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(users.get());
	}
	
	@ExceptionHandler(UserDuplicatedException.class)
	public ResponseEntity<?> handleUserDuplicatedException(UserDuplicatedException e) {
		FieldError fieldError = new FieldError(
				"loginId",
				"Duplicated",
				e.getLoginId(),
				"duplicated.user");
		ApiError error = new ApiError();
		error.setFieldErrors(Arrays.asList(fieldError));
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}
}
