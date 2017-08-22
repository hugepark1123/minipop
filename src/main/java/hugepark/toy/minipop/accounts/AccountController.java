/**
 *
 *
 * @author : hugepark1123
 * @date : 2017-08-16
 * @since : 
 */
package hugepark.toy.minipop.accounts;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hugepark.toy.minipop.accounts.AccountDto.Request;
import hugepark.toy.minipop.commons.ApiError;
import hugepark.toy.minipop.commons.HttpRequestExceptionType;
import hugepark.toy.minipop.utils.Utils;

@RestController
@RequestMapping("/api")
public class AccountController {
	
	@Autowired
	private AccountService service;
	
	@PostMapping("/users")
	public ResponseEntity<?> createUser (
			@RequestBody
			@Valid
			Request.Create dto, BindingResult result) 
	throws Exception {
		if(result.hasErrors()) {
			ApiError error = ApiError.build(
					HttpStatus.BAD_REQUEST, 
					HttpRequestExceptionType.EXPECTED, 
					result);
			return ResponseEntity.badRequest().body(error);
		}
		
		Optional<Account> account = service.createUser(dto);
		
		if(account.isPresent() == false) {
			throw new Exception("User creation failed");
		}
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(Utils.copyBean(
						account.get(), 
						AccountDto.Reponse.class));
	}
	
	@GetMapping("/users")
	public ResponseEntity<?> getUsers(Pageable pageable) {
		Page<Account> page = service.findAll(pageable);
		
		if(page.hasContent() == false) {
			return ResponseEntity.noContent().build();
		}
		
		List<AccountDto.Reponse> content = page.getContent().stream()
			.map(account -> {
				AccountDto.Reponse dto = new AccountDto.Reponse();
				BeanUtils.copyProperties(account, dto);
				return dto;
			})
			.collect(Collectors.toList());
		
		PageImpl<AccountDto.Reponse> result = new PageImpl<>(content, pageable, page.getTotalElements());
		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id) {
		Optional<Account> account = service.findOne(id);
		
		if(account.isPresent() == false)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(
				Utils.copyBean(
						account.get(),
						AccountDto.Reponse.class));
	}
	
	@GetMapping(value="/users", params="username")
	public ResponseEntity<?> getUser(@RequestParam String username) {
		Optional<Account> account = service.findByUsername(username);
		
		if(account.isPresent() == false)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(
				Utils.copyBean(
						account.get(),
						AccountDto.Reponse.class));
	}
	
//	@ExceptionHandler(UserDuplicatedException.class)
//	public ResponseEntity<?> handleUserDuplicatedException(UserDuplicatedException e) {
//		FieldError fieldError = new FieldError(
//				"loginId",
//				"Duplicated",
//				e.getLoginId(),
//				"duplicated.user");
//		ApiError error = new ApiError();
//		error.setFieldErrors(Arrays.asList(fieldError));
//		
//		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
//	}
}
