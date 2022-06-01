package com.hoaxify.constraint;

import java.util.Arrays;
import java.util.stream.Collectors;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import com.hoaxify.service.FileService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class FileTypeValidator implements ConstraintValidator<FileType,String>{

private final FileService fileService;

String[] types;

	@Override
public void initialize(FileType constraintAnnotation) {
	types=constraintAnnotation.types();
}



	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value==null || value.isEmpty()) {return true;}
		String image=fileService.detectImage(value);
		for(String type:this.types) {
		 if(image.contains(type)) {
			 return true;
		 }
		}
		String supportedTypes=Arrays.stream(types).collect(Collectors.joining(", "));
		context.disableDefaultConstraintViolation();
		HibernateConstraintValidatorContext hibernateConstraintValidatorContext=context.unwrap(HibernateConstraintValidatorContext.class);
		hibernateConstraintValidatorContext.addMessageParameter("types", supportedTypes);
		hibernateConstraintValidatorContext.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addConstraintViolation();
			
		return false;
	}

}
