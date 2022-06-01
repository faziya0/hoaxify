package com.hoaxify.dto;

import javax.validation.constraints.Size;

import com.hoaxify.constraint.FileType;

import lombok.Data;

@Data

public class UserUpdateDto {
@Size(message="{hoaxify.validation.constraints.Size.displayname.message}",min=4,max=200)
private String displayName;
@FileType(types= {"jpeg","png"})
private String image;
}
