package com.hoaxify.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hoaxify.entity.FileAttachment;
import com.hoaxify.entity.User;

public interface FileAttachmentRepository extends JpaRepository<FileAttachment,Integer>{

	List<FileAttachment> findByTimestampBeforeAndHoaxIsNull(Date date);
	List<FileAttachment> findByHoaxUser(User user);
}
