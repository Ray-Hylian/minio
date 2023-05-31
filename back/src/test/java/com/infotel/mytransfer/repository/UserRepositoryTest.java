package com.infotel.mytransfer.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import com.infotel.mytransfer.entities.UserEntity;

@ActiveProfiles("test")
@DataJpaTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryTest {
	
	@Autowired(required=true)
	private UserRepository userRepository;
	
	@BeforeEach
	public void init() {
		UserEntity user = new UserEntity();
		user.setId(1L);
		user.setUuid(UUID.randomUUID().toString());
		user.setName("Florentin");
		user.setEmail("florentin.leclercq@infotel.com");
		userRepository.save(user);
	}
	
	@AfterEach
    public void cleanUpEach(){
		userRepository.deleteAll();
    }

	@Test
	public void getUsersContainingTest(){
		
		List<UserEntity> test = userRepository.findAll();
		String s = "flo";
		test = userRepository.getUsersContaining(s);
		assertEquals("Florentin", test.get(0).getName());

	}
}
