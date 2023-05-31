package com.infotel.mytransfer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.infotel.mytransfer.dto.BucketObjectInfoDTO;
import com.infotel.mytransfer.dto.ObjectDownloadRequestDTO;
import com.infotel.mytransfer.dto.ObjectDownloadResponseDTO;
import com.infotel.mytransfer.dto.ObjectInfoDTO;
import com.infotel.mytransfer.entities.ObjectDownloadEntity;
import com.infotel.mytransfer.mapper.BucketObjectInfoMapper;
import com.infotel.mytransfer.mapper.ObjectDownloadMapper;
import com.infotel.mytransfer.mapper.ObjectInfoMapper;
import com.infotel.mytransfer.service.DataObjectService;
import com.infotel.mytransfer.service.DownloadService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class DownloadController {

	@Autowired
	private DataObjectService objectService;

	@Autowired
	private DownloadService downloadService;

	@Autowired
	private BucketObjectInfoMapper bucketObjectInfoMapper;
	@Autowired
	private ObjectDownloadMapper objectDownloadMapper;
	@Autowired
	private ObjectInfoMapper objectInfoMapper;
	
	@GetMapping(value = "/list-files")
	public List<BucketObjectInfoDTO> getFiles(@RequestParam(value = "bucket") String bucket) {
		return bucketObjectInfoMapper.mapToDTO(objectService.getListObjects(bucket));
	}

	@PostMapping("/request-file")
	public ResponseEntity<ObjectDownloadResponseDTO> getRequestFile(@RequestBody ObjectDownloadRequestDTO objectRequestDTO) {
		ObjectDownloadResponseDTO objectDownloadResponse = objectDownloadMapper.mapToDTO(
				downloadService.createDownloadRequest(objectRequestDTO.getBucket(), objectRequestDTO.getPath()));
		return ResponseEntity.ok(objectDownloadResponse);
	}
	
	@GetMapping(value = "/file-info/{uuid}")
	public ResponseEntity<ObjectInfoDTO> fileInfo(@PathVariable("uuid") String uuid) {
		ObjectDownloadEntity objectDownloadEntity = downloadService.getObjectDownloadRequest(uuid);
		if(objectDownloadEntity != null) {
			ObjectInfoDTO dto = objectInfoMapper.mapToDTO(objectService.getObjectInfo(objectDownloadEntity.getBucket(), objectDownloadEntity.getPath()));
			String path = objectDownloadEntity.getPath().contains("/") ? objectDownloadEntity.getPath().substring(objectDownloadEntity.getPath().lastIndexOf('/')) : objectDownloadEntity.getPath();
			dto.setName(path);
			return ResponseEntity.ok().body(dto);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/download/{uuid}")
	public ResponseEntity<StreamingResponseBody> downloadFile(@PathVariable("uuid") String uuid) {
		ObjectDownloadEntity fileDownloadEntity = downloadService.getObjectDownloadRequest(uuid);
		if(fileDownloadEntity != null) {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(downloadService.downloadSignedObject(fileDownloadEntity));
		}
		return ResponseEntity.notFound().build();
	}
	
}
