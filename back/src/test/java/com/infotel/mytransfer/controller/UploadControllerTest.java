package com.infotel.mytransfer.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import com.infotel.mytransfer.dto.MetadataDTO;
import com.infotel.mytransfer.service.DataObjectService;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UploadControllerTest {
	
	@MockBean
	private DataObjectService objectService;
	
	@Mock
	private MultipartFile mockFileMultipart;
	
	@Autowired
	MockMvc mockMvc;
	
	/**
	 * Tests for uploading a file to the controller.
	 * Success means it was uploaded successfully.
	 */
	@Test
	public void testUploadFileSuccess() throws Exception {
		 Mockito.when(objectService.uploadObject(Mockito.anyString(), Mockito.anyCollection(), Mockito.any())).thenReturn(true);

		 MockMultipartFile mockFile = new MockMultipartFile("file", "test".getBytes());
		 
		 mockMvc.perform(MockMvcRequestBuilders.multipart("/upload").file(mockFile).param("bucket", "bucket-name")).andExpect(MockMvcResultMatchers.status().isOk());
		 
		 verify(objectService, times(1)).uploadObject(Mockito.anyString(), Mockito.anyCollection(), Mockito.any());
	}
	
	/**
	 * Tests for an error while uploading file.
	 * Success means it wasn't uploaded.
	 */
	@Test
	public void testUploadFileInternalServerError() throws Exception {
		Mockito.when(objectService.uploadObject(Mockito.anyString(), Mockito.anyCollection(), Mockito.any())).thenReturn(false);
		
		MockMultipartFile mockFile = new MockMultipartFile("file", "test".getBytes());
		
		mockMvc.perform(MockMvcRequestBuilders.multipart("/upload").file(mockFile).param("bucket", "bucket-name")).andExpect(MockMvcResultMatchers.content().string("\"INTERNAL_SERVER_ERROR\""));
		
		verify(objectService, times(1)).uploadObject(Mockito.anyString(), Mockito.anyCollection(), Mockito.any());
	}
	
	/**
	 * Tests for an error while reading input files.
	 * Success means files weren't read.
	 */
	@Test
	public void testUploadFileIOException() throws Exception {
		Mockito.when(mockFileMultipart.getInputStream()).thenThrow(IOException.class);
		
		MockMultipartFile mockFile = new MockMultipartFile("file", "test".getBytes());
		
		mockMvc.perform(MockMvcRequestBuilders.multipart("/upload").file(mockFile).param("bucket", "bucket-name")).andExpect(MockMvcResultMatchers.content().string("\"INTERNAL_SERVER_ERROR\""));
		
		verify(objectService, times(1)).uploadObject(Mockito.anyString(), Mockito.anyCollection(), Mockito.any());
	}
	
	/**
	 * Tests for uploading without giving a file.
	 * Success means BAD_REQUEST was raised successfully.
	 */
	@Test
	public void testUploadFileBadRequest() throws Exception {
		Mockito.when(objectService.uploadObject(Mockito.anyString(), Mockito.anyCollection(), Mockito.any(MetadataDTO[].class))).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders.multipart("/upload").param("bucket", "bucket-name")).andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		verify(objectService, Mockito.never()).uploadObject(Mockito.anyString(), Mockito.anyCollection(), Mockito.any());
	}

}
