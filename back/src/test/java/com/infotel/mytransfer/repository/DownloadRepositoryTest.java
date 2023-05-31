package com.infotel.mytransfer.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import com.infotel.mytransfer.entities.ObjectDownloadEntity;

@ActiveProfiles("test")
@DataJpaTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class DownloadRepositoryTest {
		
	@Autowired(required=true)
	private DownloadRepository downloadRepository;
	
	@BeforeEach
	public void init() {
		
		ObjectDownloadEntity object = new ObjectDownloadEntity();
		object.setId(1L);
		object.setUuid("123");
		object.setBucket("Bucket");
		object.setPath("Path");
		downloadRepository.save(object);
		
		ObjectDownloadEntity object2 = new ObjectDownloadEntity();
		object2.setId(1L);
		object2.setUuid("123-456");
		object2.setBucket("Bucket2");
		object2.setPath("Path2");
		downloadRepository.save(object2);
		
	}
	
	@AfterEach
    public void cleanUpEach(){
		downloadRepository.deleteAll();
    }

	@Test
	public void findDistinctByUuidTest(){
		ObjectDownloadEntity objectInc = downloadRepository.findDistinctByUuid("123-456");
		
		assertEquals("Path2", objectInc.getPath());
	}

}
