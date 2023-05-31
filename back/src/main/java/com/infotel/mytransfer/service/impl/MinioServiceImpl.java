package com.infotel.mytransfer.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.infotel.mytransfer.dto.MetadataDTO;
import com.infotel.mytransfer.entities.BucketObjectInfoEntity;
import com.infotel.mytransfer.entities.DataObjectEntity;
import com.infotel.mytransfer.entities.ObjectInfoEntity;
import com.infotel.mytransfer.service.DataObjectService;

import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.Result;
import io.minio.SetBucketVersioningArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Item;
import io.minio.messages.VersioningConfiguration;
import io.minio.messages.VersioningConfiguration.Status;

@Service
public class MinioServiceImpl implements DataObjectService {

	private static Logger logger = LoggerFactory.getLogger(MinioServiceImpl.class);

	private MinioClient minioClient;
	
	public MinioServiceImpl() {
		this.minioClient = MinioClient.builder().endpoint("0.0.0.0", 9000, false)
				.credentials("minioadmin", "minioadmin").build();
	}

	@Override
	public boolean uploadObject(String bucket, Collection<DataObjectEntity> dataObjects, MetadataDTO[] metadata) {
		Objects.requireNonNull(bucket, "bucket must not be null");
		Objects.requireNonNull(dataObjects, "dataObjects must not be null");

		Map<String, String> metadataMap = new HashMap<>();
		
		if(metadata != null) {
			for (int i = 0; i < metadata.length; i++) {
				MetadataDTO m = metadata[i];
				metadataMap.put(m.getKey(), m.getValue());
			}
		}
		
		for(DataObjectEntity e : dataObjects) {
			try {
				minioClient.putObject(PutObjectArgs.builder().bucket(bucket)
						.object(e.getPath()).contentType(e.getContentType()).userMetadata(metadataMap).stream(e.getInputStream(), e.getSize(), -1).build());
			} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException
					| InternalException | InvalidResponseException | NoSuchAlgorithmException | ServerException
					| XmlParserException | IllegalArgumentException | IOException e1) {
				logger.error(String.format("Unable to upload an object from multiple to MinIO (bucket=%s, object=%s)", bucket, e.getPath()), e1);
				return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean createBucket(String bucketName) {
		Objects.requireNonNull(bucketName, "bucketName must not be null");

		MakeBucketArgs.Builder makeBucket = MakeBucketArgs.builder().bucket(bucketName);
		SetBucketVersioningArgs.Builder setVersioning = SetBucketVersioningArgs.builder().bucket(bucketName).config(new VersioningConfiguration(Status.ENABLED, false));
		try {
			this.minioClient.makeBucket(makeBucket.build());
			this.minioClient.setBucketVersioning(setVersioning.build());
		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
				| IllegalArgumentException | IOException e) {
			logger.error(String.format("Unable to create bucket (bucket=%s)", bucketName), e);
			return false;
		}
		return true;

	}
	
	@Override
	public List<BucketObjectInfoEntity> getListObjects(String bucketName) {
		Iterable<Result<Item>> results =  minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
		List<BucketObjectInfoEntity> list = new ArrayList<>();
		results.forEach(r -> {
			try {
				list.add(new BucketObjectInfoEntity(r.get().objectName()));
			} catch (InvalidKeyException | ErrorResponseException | IllegalArgumentException | InsufficientDataException
					| InternalException | InvalidResponseException | NoSuchAlgorithmException | ServerException
					| XmlParserException | IOException e) {
				logger.error(String.format("Unable to list objects (bucket=%s)", bucketName), e);
			}
		});
		return list;
	}
	
	@Override
	public ObjectInfoEntity getObjectInfo(String bucketName, String path) {
		try {
			StatObjectResponse stat = minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(path).build());
			return new ObjectInfoEntity(stat.etag(), stat.size(), stat.lastModified(), stat.retentionRetainUntilDate(), stat.deleteMarker(), stat.userMetadata(), stat.versionId(), stat.contentType());
		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
				| IllegalArgumentException | IOException e) {
			logger.error(String.format("Unable to read object size (bucket=%s, path=%s)", bucketName, path), e);
		}
		return null;
	}

	@Override
	public InputStream getObject(String bucketName, String path) {
		try {
			return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(path).build());
		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
				| IllegalArgumentException | IOException e) {
			logger.error(String.format("Unable to fetch object on MinIO (bucket=%s, path=%s)", bucketName, path), e);
		}
		return null;
	}
	
}
