package com.hoaxify.controller;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hoaxify.constraint.CurrentUser;
import com.hoaxify.dto.HoaxSubmitDto;
import com.hoaxify.dto.response.GenericResponse;
import com.hoaxify.dto.HoaxDto;
import com.hoaxify.entity.Hoax;
import com.hoaxify.entity.User;
import com.hoaxify.repository.UserRepository;
import com.hoaxify.service.HoaxService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HoaxController {
	private final HoaxService hoaxService;
	private final UserRepository userRepository;

	
	@PostMapping("api/1.0/hoaxes")
	public ResponseEntity<?> saveHoax(@Valid @RequestBody HoaxSubmitDto hoax,@CurrentUser User user){
		String username=user.getUsername();
		User userInDB=userRepository.findByUsername(username);
		
			hoaxService.saveHoax(hoax,userInDB);
			return ResponseEntity.ok(new GenericResponse("hoax is saved"));
		
		
	
		}
	
	@GetMapping({"api/1.0/hoaxes","api/1.0/users/{username}/hoaxes"})
	public Page<HoaxDto> getAllHoaxes(@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable,
			@PathVariable(required=false) String username){
		if(username!=null) {
			return hoaxService.getAllHoaxes(pageable, username).map(HoaxDto::new);}
		Page<HoaxDto> pageHoax=hoaxService.getAllHoaxes(pageable,username).map(HoaxDto::new);
		return pageHoax;
		
	
		}
	@GetMapping({"api/1.0/hoaxes/{id:[0-9]+}","api/1.0/users/{username}/hoaxes/{id:[0-9]+}"})
	public ResponseEntity<?> getHoaxesRelative(@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable,
			@PathVariable int id,
			@PathVariable(required=false) String username,
			@RequestParam(name="count", defaultValue="false",required=false)boolean count,
			@RequestParam(name="direction",defaultValue="before") String direction){
		if(username!=null) {
			if(count) {
				long countHoax=hoaxService.getNewHoaxCount(id,username);
				HashMap<String,Long> response=new HashMap<>();
				response.put("count", countHoax);
				return ResponseEntity.ok(response);
			}
			if(direction.equals("after")) {
				List<Hoax> listHoax=hoaxService.getNewHoaxes(id, username, pageable.getSort());
				List<HoaxDto> listHoaxVM =listHoax.stream().map(HoaxDto::new).collect(Collectors.toList());
				return ResponseEntity.ok(listHoaxVM);
			}
			
			Page<HoaxDto> page=hoaxService.getOldHoaxes(id,pageable, username).map(HoaxDto::new);
			return ResponseEntity.ok(page);
		}
		if(count) {
			long idCount=hoaxService.getNewHoaxCount(id,username);
			HashMap<String,Long> response=new HashMap<>();
			response.put("count", idCount);
			return ResponseEntity.ok(response);
		}
		if(direction.equals("after")) {
			List<Hoax>listhoax=hoaxService.getNewHoaxes(id,username, pageable.getSort());
			List<HoaxDto> listHoaxVM=listhoax.stream().map(HoaxDto::new).collect(Collectors.toList());
			return ResponseEntity.ok(listHoaxVM);
		}
		Page<HoaxDto> page=hoaxService.getOldHoaxes(id, pageable,username).map(HoaxDto::new);
		return ResponseEntity.ok(page);
		}
		
	@DeleteMapping("api/1.0/hoaxes/{id:[0-9]+}")
	@PreAuthorize("@hoaxSecurityService.isAllowedDelete(#id,#user)")
	public ResponseEntity<?> deleteHoax(@PathVariable int id,@CurrentUser User user){
		hoaxService.deleteHoax(id);
	 return ResponseEntity.ok(new GenericResponse("hoax is removed"));
	 
	}
	
}

	

