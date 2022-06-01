package com.hoaxify.dto;


import com.hoaxify.entity.Hoax;

import lombok.Data;

@Data
public class HoaxDto {
	private String content;
	private long timestap;
	private int id;
	private UserDto user;
	private FileAttachmentDto fileAttachment;
	
	public HoaxDto(Hoax hoax) {
		this.setId(hoax.getId());
		this.setContent(hoax.getContent());
		this.setTimestap(hoax.getTimestap().getTime());
		this.setUser(new UserDto(hoax.getUser()));
		if(hoax.getFileAttachment() != null) {			
			this.fileAttachment = new FileAttachmentDto(hoax.getFileAttachment());
		}
		}
	


}
