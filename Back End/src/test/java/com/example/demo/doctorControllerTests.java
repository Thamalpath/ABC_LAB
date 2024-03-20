package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.doctor;
import com.example.demo.repository.doctorRepo;
@SpringBootTest
public class doctorControllerTests {
	@Autowired
	doctorRepo consultRepo;

	@Test
	public void testConsultSave() {
		doctor consult0 = new doctor();
		consult0.setId(1L);
		consult0.setName("Test Doctor");
		consult0.setEmail("Test@gmail.com");
		consult0.setNic("NIC");
		consult0.setStart_time("7.00");
		consult0.setEnd_time("9.00");
		consult0.setPassword("Test");
		consultRepo.save(consult0);
		assertNotNull(consultRepo.findById(1L).get());
	}

	@Test
	public void testReadAllConsult() {
		List<doctor> list1 = consultRepo.findAll();
		assertThat(list1).size().isGreaterThan(0);
	}

	@Test
	public void testUpdateConsult() {
		doctor consult0 = consultRepo.findById(1L).get();
		consult0.setEmail("Test0123@gmail.com");
		consultRepo.save(consult0);
		assertNotEquals("Keyboard", consultRepo.findById(1L).get().getEmail());
	}

	@Test
	public void testDelete() {
		consultRepo.deleteById(2L);
		assertThat(consultRepo.existsById(2L)).isFalse();
	}
}
