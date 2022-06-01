package com.hoaxify.dto;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class HoaxSubmitDto {
	@Size(message="{hoaxify.validation.constraints.Size.hoaxify.message}", min=1,max=1000)
	private String content;
	private int attachmentId;

}
