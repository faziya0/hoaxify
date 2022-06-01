package com.hoaxify.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hoaxify.entity.FileAttachment;
import com.hoaxify.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FileController {
	
	private final FileService fileService;
	 
	@PostMapping("api/1.0/hoax-attachments")
	public FileAttachment saveHoaxAttachment(MultipartFile image){
		FileAttachment fileAttachment=fileService.saveHoaxAttachment(image);
		return fileAttachment;
	}

}
