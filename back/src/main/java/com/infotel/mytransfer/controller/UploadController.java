package com.infotel.mytransfer.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infotel.mytransfer.dto.MetadataDTO;
import com.infotel.mytransfer.entities.DataObjectEntity;
import com.infotel.mytransfer.service.DataObjectService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UploadController {
	
	private static Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Autowired
	private DataObjectService objectService;

	@PostMapping(value = "/upload")
	public HttpStatus uploadFile(@RequestParam("bucket") String bucket, @RequestParam("file") MultipartFile[] files, @RequestParam(value = "metadata", required = false) String[] metadata) {
		List<DataObjectEntity> objects = new ArrayList<>();
		for (int i = 0; i < files.length; i++) {
			MultipartFile mf = files[i];
			try {
				InputStream is = mf.getInputStream();
				objects.add(new DataObjectEntity(mf.getResource().getFilename(), is, is.available(), mf.getContentType()));
			} catch (IOException e) {
				logger.error(String.format("Unable to upload object to MinIO (bucket=%s, object=%s)", bucket, mf.getName()), e);
				return HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		
		MetadataDTO[] metadataDTO = null;
		if(metadata != null) {
			try {
				metadataDTO = MAPPER.readValue(Arrays.toString(metadata), MetadataDTO[].class);
			} catch (JsonProcessingException e) {
				logger.error(String.format("Unable to map metadata values (metadata=%s)", Arrays.toString(metadata)), e);
			}
		}
		
		boolean isFileUploaded = objectService.uploadObject(bucket, objects, metadataDTO);
		return isFileUploaded ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
	}
	
}
