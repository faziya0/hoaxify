package com.hoaxify.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hoaxify.dto.HoaxSubmitDto;
import com.hoaxify.entity.FileAttachment;
import com.hoaxify.entity.Hoax;
import com.hoaxify.entity.User;
import com.hoaxify.repository.FileAttachmentRepository;
import com.hoaxify.repository.HoaxRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HoaxService {
	
	private final HoaxRepository hoaxifyRepository;
	private final UserService userService;
	private final FileAttachmentRepository fileAttachmentRepository;
	private final FileService fileService;
	
	public void saveHoax(HoaxSubmitDto hoaxSubmitDto,User user) {
		Hoax hoax = new Hoax();
		hoax.setContent(hoaxSubmitDto.getContent());
		hoax.setUser(user);
		hoax.setTimestap(new Date());
		hoaxifyRepository.save(hoax);
		Optional<FileAttachment> fileAttachmentOptional = fileAttachmentRepository.findById(hoaxSubmitDto.getAttachmentId());
		if(fileAttachmentOptional.isPresent()) {
			FileAttachment fileAttachment=fileAttachmentOptional.get();
			fileAttachment.setHoax(hoax);
			fileAttachmentRepository.save(fileAttachment);
		}
		
		

	}
	
	public Page<Hoax> getAllHoaxes(Pageable pageable,String username){
		
		if(username!=null) {
			User user=userService.getUser(username);
			Specification<Hoax> specIsUser=IsUser(user);
			Page<Hoax> page=hoaxifyRepository.findAll(specIsUser, pageable);
			return page;
		}
		Page<Hoax> page=hoaxifyRepository.findAll(pageable);
		return page;
	}
	
	
	public Page<Hoax> getOldHoaxes(int id,Pageable pageable,String username){
		Specification<Hoax> specIdLessThan = idLessThan(id);
		
		if(username!=null) {
			User user=userService.getUser(username);
			Specification<Hoax> specIdLessThanAndUser= specIdLessThan.and(IsUser(user));
			Page<Hoax> page=hoaxifyRepository.findAll(specIdLessThanAndUser,pageable);
			return page;
		}
		Page<Hoax> page=hoaxifyRepository.findAll(specIdLessThan, pageable);
		return page;
	}


	
	public long getNewHoaxCount(int id,String username) {
		Specification<Hoax> specIdGreaterThan=idGreaterThan(id);
		if(username!=null) {
			User user=userService.getUser(username);
			Specification<Hoax> specIdGreaterThanAndUser= specIdGreaterThan.and(IsUser(user));
			return hoaxifyRepository.count(specIdGreaterThanAndUser);
		}
		return hoaxifyRepository.count(specIdGreaterThan);
	}
	
	
	public List<Hoax> getNewHoaxes(int id,String username,Sort sort){
		Specification<Hoax> specIdGreaterThan=idGreaterThan(id);
		if(username!=null) {
			User user=userService.getUser(username);
			Specification<Hoax> specIdGreaterThanAndUser= specIdGreaterThan.and(IsUser(user));
			return hoaxifyRepository.findAll(specIdGreaterThanAndUser, sort);
		}
		return hoaxifyRepository.findAll(specIdGreaterThan, sort);
	}
	
	
	public Specification<Hoax> idLessThan(int id){
		return  (root,query,criteriaBuilder)->{
			return criteriaBuilder.lessThan(root.get("id"), id);
		};
		
	}
	
	public Specification<Hoax> IsUser(User user){
		return (root,query,criteriaBuilder)->{
			return criteriaBuilder.equal(root.get("user"), user);
		};
	}
	
	public Specification<Hoax> idGreaterThan(int id){
		return (root,query,criteriaBuilder)->{
			return criteriaBuilder.greaterThan(root.get("id"), id);
		};
	}
	
	public void deleteHoax(int id) {
		Optional<Hoax> optionalHoax=hoaxifyRepository.findById(id);
		Hoax hoaxdb=optionalHoax.get();
		FileAttachment fileAttachment=hoaxdb.getFileAttachment();
		if(fileAttachment!=null) {
			String imageName=fileAttachment.getName();
			fileService.deleteFileAttachment(imageName);
			
		}
		hoaxifyRepository.deleteById(id);
	}
	
	public List<Hoax> findAllHoaxByUser(User user){
		return hoaxifyRepository.findAll(IsUser(user));
	}
	
	
	
	
	
	





}


