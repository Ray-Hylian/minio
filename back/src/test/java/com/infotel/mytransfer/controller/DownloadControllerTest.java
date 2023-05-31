package com.infotel.mytransfer.controller;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infotel.mytransfer.dto.BucketObjectInfoDTO;
import com.infotel.mytransfer.dto.ObjectDownloadRequestDTO;
import com.infotel.mytransfer.dto.ObjectDownloadResponseDTO;
import com.infotel.mytransfer.dto.ObjectInfoDTO;
import com.infotel.mytransfer.entities.BucketObjectInfoEntity;
import com.infotel.mytransfer.entities.ObjectDownloadEntity;
import com.infotel.mytransfer.entities.ObjectInfoEntity;
import com.infotel.mytransfer.mapper.BucketObjectInfoMapper;
import com.infotel.mytransfer.mapper.ObjectDownloadMapper;
import com.infotel.mytransfer.mapper.ObjectInfoMapper;
import com.infotel.mytransfer.service.DataObjectService;
import com.infotel.mytransfer.service.DownloadService;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class DownloadControllerTest {

	@MockBean
	private DataObjectService objectService;
	
	@MockBean
	private DownloadService downloadService;
	
	@MockBean
	private BucketObjectInfoMapper bucketObjectInfoMapper;
	
	@MockBean
	private ObjectDownloadMapper objectDownloadMapper;
	
	@MockBean
	private ObjectInfoMapper objectInfoMapper;

	@Autowired
	MockMvc mockMvc;
		
	List<BucketObjectInfoEntity> listEntities = new ArrayList<>();
	List<BucketObjectInfoDTO> listDto = new ArrayList<>();
	
	List<ObjectDownloadEntity> listObjectDlEntities= new ArrayList<>();
	List<ObjectDownloadRequestDTO> listObjectDlRequestDto = new ArrayList<>();
	ObjectDownloadEntity objectDownloadEntity = new ObjectDownloadEntity(1L, UUID.randomUUID().toString(), "bucket", "path");
	ZonedDateTime zdt = ZonedDateTime.now();	
	ZonedDateTime zdt2 = ZonedDateTime.now();
	Map<String, String> map = new HashMap<>();
	ObjectInfoEntity objectInfoEntity = new ObjectInfoEntity("etag", 1L,zdt,zdt2, false, map, "versionId", "contentType");
	ObjectInfoDTO objectInfoDto = new ObjectInfoDTO("name", "contentType", 1L);
	ObjectDownloadRequestDTO obrequestDto = new ObjectDownloadRequestDTO("bucket", "path");
	ObjectDownloadResponseDTO obresponseDto = new ObjectDownloadResponseDTO();
	
	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * {@link DownloadController#getFiles(String)}
	 * Get file available from a bucket
	 * @throws Exception
	 */
	@Test
	public void getFilesTest() throws Exception {
		
		Mockito.when(objectService.getListObjects(Mockito.any())).thenReturn(listEntities);
		Mockito.when(bucketObjectInfoMapper.mapToDTO(Mockito.anyList())).thenReturn(listDto);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/list-files").contentType(MediaType.APPLICATION_JSON)
				 .param("bucket", "new-bucket")).andExpect(MockMvcResultMatchers.status().isOk());
		
		Mockito.verify(objectService, Mockito.times(1)).getListObjects(Mockito.anyString());
	}
	
	/**
	 * {@link DownloadController#getRequestFile(ObjectDownloadRequestDTO)}
	 * Get file from object request
	 * @throws Exception
	 */
	@Test
	public void getRequestFileTest() throws Exception {
		
		Mockito.when(downloadService.createDownloadRequest(Mockito.any(), Mockito.any())).thenReturn(objectDownloadEntity);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/request-file").contentType(MediaType.APPLICATION_JSON)
				 .content(asJsonString(obrequestDto))).andExpect(MockMvcResultMatchers.status().isOk());
		
		Mockito.verify(downloadService, Mockito.times(1)).createDownloadRequest(Mockito.anyString(), Mockito.anyString());
	}
	
	/**
	 * {@link DownloadController#fileInfo(String)}
	 * Get file informations with a uuid
	 * @throws Exception 
	 */
	@Test
	public void fileInfoTest() throws Exception {
		
		Mockito.when(downloadService.getObjectDownloadRequest(Mockito.anyString())).thenReturn(objectDownloadEntity);
		Mockito.when(objectService.getObjectInfo(Mockito.any(), Mockito.any())).thenReturn(objectInfoEntity);
		Mockito.when(objectInfoMapper.mapToDTO(Mockito.any(ObjectInfoEntity.class))).thenReturn(objectInfoDto);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/file-info/" + UUID.randomUUID().toString()).contentType(MediaType.APPLICATION_JSON)
				 ).andExpect(MockMvcResultMatchers.status().isOk());
		
		Mockito.verify(downloadService, Mockito.times(1)).getObjectDownloadRequest(Mockito.any());
		Mockito.verify(objectService, Mockito.times(1)).getObjectInfo(Mockito.any(), Mockito.any());
	}
	
	/**
	 * {@link DownloadController#downloadFile(String)}
	 * Donwload file with a uuid
	 * @throws Exception 
	 */
	@Test
	public void downloadFileTest() throws Exception {
		
		Mockito.when(downloadService.getObjectDownloadRequest(Mockito.anyString())).thenReturn(objectDownloadEntity);
		Mockito.when(objectInfoMapper.mapToDTO(Mockito.any(ObjectInfoEntity.class))).thenReturn(objectInfoDto);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/download/" + UUID.randomUUID().toString()).contentType(MediaType.APPLICATION_JSON)
				 ).andExpect(MockMvcResultMatchers.status().isOk());
		
		Mockito.verify(downloadService, Mockito.times(1)).getObjectDownloadRequest(Mockito.any());
	}
}
