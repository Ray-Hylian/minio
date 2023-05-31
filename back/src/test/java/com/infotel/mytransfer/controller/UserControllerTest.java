package com.infotel.mytransfer.controller;

import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
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
import com.infotel.mytransfer.dto.UserDTO;
import com.infotel.mytransfer.entities.UserEntity;
import com.infotel.mytransfer.mapper.UserMapper;
import com.infotel.mytransfer.service.DataObjectService;
import com.infotel.mytransfer.service.impl.UserServiceImpl;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerTest {

	@MockBean
	UserServiceImpl userService;
	
	@MockBean
	DataObjectService objectService;
	
	@MockBean
	UserMapper userMapper;

	@Autowired
	MockMvc mockMvc;

	UserDTO userDTO = new UserDTO();
	UserEntity userEntity = new UserEntity();
	List<UserEntity> listEntities = new ArrayList<>();
	String filter = "Flo";
	
	List<UserDTO> listDTO = new ArrayList<>();

	@BeforeEach
	public void init() {
		userDTO.setUuid(UUID.randomUUID().toString());
		userDTO.setName("Florentin");
		userDTO.setEmail("florentin.leclercq@infotel.com");
		
		userEntity.setUuid(userDTO.getUuid());
		userEntity.setName("Florentin");
		userEntity.setEmail("florentin.leclercq@infotel.com");
		
		listEntities.add(userEntity);
		listDTO.add(userDTO);
	}
	
	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Test for {@link UserController#createUser(UserDTO)}<br/>
	 * Creating a user
	 * @throws Exception
	 */
	@Test
	public void createUserTest() throws Exception {
		
		 Mockito.when(userService.createUser(Mockito.any())).thenReturn(userEntity);
		 Mockito.when(objectService.createBucket(anyString())).thenReturn(true);
		 Mockito.when(userMapper.mapToEntity(Mockito.any(UserDTO.class))).thenReturn(userEntity);
	
		 mockMvc.perform(MockMvcRequestBuilders.post("/create-user").contentType(MediaType.APPLICATION_JSON)
				 .content(asJsonString(userDTO))).andExpect(MockMvcResultMatchers.status().isOk());
		 
		 Mockito.verify(userService, Mockito.times(1)).createUser(userEntity);
		 Mockito.verify(objectService, Mockito.times(1)).createBucket(Mockito.anyString());
	}
	
	/**
	 * Test for {@link UserController#searchUsers(String)}<br/>
	 *  Searching a user from letters 
	 * @throws Exception
	 */
	@Test
	public void searchUsersTest() throws Exception {
		
		Mockito.when(userService.getUsersContaining(Mockito.any())).thenReturn(listEntities);
		Mockito.when(userMapper.mapToDTO(Mockito.anyList())).thenReturn(listDTO);
		
		
		mockMvc.perform(MockMvcRequestBuilders.post("/search-users").contentType(MediaType.APPLICATION_JSON)
				 .content(filter)).andExpect(MockMvcResultMatchers.content().string(asJsonString(Arrays.asList(userDTO))));
	
		Mockito.verify(userService, Mockito.times(1)).getUsersContaining(Mockito.anyString());
		
	}

}
