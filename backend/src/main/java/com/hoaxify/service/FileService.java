package com.hoaxify.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hoaxify.configuration.AppConfiguration;
import com.hoaxify.entity.FileAttachment;
import com.hoaxify.entity.User;
import com.hoaxify.repository.FileAttachmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class FileService {
	
  private final AppConfiguration appConfiguration;
  private final FileAttachmentRepository fileAttachmentRepository;

   
	public String writebase64EncodedStringToFile(String image) throws IOException {
		String filename=generateRandomName();
		File file =new File(appConfiguration.getProfileStoragePath()+"/"+filename);
		OutputStream outputStream=new FileOutputStream(file);
		byte[]decodedbytearray=Base64.getDecoder().decode(image);
		outputStream.write(decodedbytearray);
		outputStream.close();
		return filename;
	}
	
	public String generateRandomName() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	public void deleteProfileImage(String image) {
		if(image==null) {
			return;
		}
		String filePath=appConfiguration.getProfileStoragePath()+"/"+image;
		try {
			Files.deleteIfExists(Paths.get(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void deleteFileAttachment(String image) {
		String filePath=appConfiguration.getAttachmentStoragePath()+"/"+image;
		try {
			Files.deleteIfExists(Paths.get(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String detectImage(String image) {
		byte[]decodedbytearray=Base64.getDecoder().decode(image);
		Tika tika = new Tika();
		return tika.detect(decodedbytearray);
	}
	public String detectImage(byte[] byteArray) {
		Tika tika = new Tika();
		return tika.detect(byteArray);
	}
	
	public FileAttachment saveHoaxAttachment(MultipartFile multipartFile) {
		String filename=generateRandomName();
		File file =new File(appConfiguration.getAttachmentStoragePath()+"/"+filename);
		FileAttachment fileAttachment = new FileAttachment();
		try {
			OutputStream outputStream=new FileOutputStream(file);
			outputStream.write(multipartFile.getBytes());
			outputStream.close();
			
			fileAttachment.setName(filename);
			fileAttachment.setTimestamp(new Date());
			String fileType= detectImage(multipartFile.getBytes());
			fileAttachment.setFileType(fileType);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileAttachmentRepository.save(fileAttachment);
		
		
	}
	
	@Scheduled(fixedRate=24*60*60*1000)
	public void cleanUpStorage() {
		Date twentyfourhour = new Date(System.currentTimeMillis()-(24*60*60*1000));
		List<FileAttachment> fileAttachmentlist=fileAttachmentRepository.findByTimestampBeforeAndHoaxIsNull(twentyfourhour);
		for(FileAttachment file:fileAttachmentlist) {
			deleteFileAttachment(file.getName());
			fileAttachmentRepository.deleteById(file.getId());
		}
	}
	
	public void deleteAllStoredFilesForUser(User user) {
		List<FileAttachment>fileAttachmentList= fileAttachmentRepository.findByHoaxUser(user);
		for(FileAttachment fileAttachment:fileAttachmentList) {
		deleteFileAttachment(fileAttachment.getName());
		}
		deleteProfileImage(user.getImage());
	}

}
