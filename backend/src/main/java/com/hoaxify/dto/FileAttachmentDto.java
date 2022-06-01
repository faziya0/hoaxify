package com.hoaxify.dto;

import com.hoaxify.entity.FileAttachment;

import lombok.Data;
@Data
public class FileAttachmentDto {
	private String name;
	private String fileType;
	public FileAttachmentDto(FileAttachment fileAttachment) {
		this.name=fileAttachment.getName();
		this.fileType=fileAttachment.getFileType();
	}

}
