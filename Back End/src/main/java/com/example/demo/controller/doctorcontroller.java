package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.doctor;
import com.example.demo.repository.doctorRepo;
import com.example.demo.response.response;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/consult/")
public class doctorcontroller {

	@Autowired
	private doctorRepo consultRepository;
	


	// create users rest api
	@PostMapping("/saveconsult")
	public doctor createUsers(@RequestBody doctor consult) {
		return consultRepository.save(consult);
	}

	// get all Users rest api
	@GetMapping("/getconsult")
	private List<doctor> getAllConsult() {
		return consultRepository.findAll();
	}

	// get User by Id rest api
	@GetMapping("/getconsult/{id}")
	public ResponseEntity<doctor> getconsultById(@PathVariable Long id) {
		doctor consult = consultRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Doctors not exist with id : " + id));
		return ResponseEntity.ok(consult);
	}

	// update User rest api
	@PutMapping("/getconsult/{id}")
	public ResponseEntity<doctor> updateConsult(@PathVariable Long id, @RequestBody doctor consult) {
		doctor consult2 = consultRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Doctors not exist with id : " + id));
		consult2.setName(consult.getName());
		consult2.setEmail(consult.getEmail());
		consult2.setStart_time(consult.getStart_time());
		consult2.setEnd_time(consult.getEnd_time());
		consult2.setNic(consult.getNic());
		consult2.setPassword(consult.getPassword());

		doctor updateConsult = consultRepository.save(consult2);
		return ResponseEntity.ok(updateConsult);
	}

	// delete User rest api
	@DeleteMapping("/getconsult/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteConsult(@PathVariable Long id) {
		doctor consult = consultRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Doctors not exist with id : " + id));

		consultRepository.delete(consult);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	// Users Login
		@PostMapping("/login")
		public ResponseEntity<Object> login(@RequestBody doctor loginForm) {
			String name = loginForm.getName();
			String password = loginForm.getPassword();

			doctor consults = consultRepository.findByEmailAndPassword(name, password);
			if (consults != null) {
				// User authenticated successfully
				return response.responseBuilder("Consult Login Successfully.", HttpStatus.OK,
						consultRepository.findByEmailAndPassword(name, password));

			} else {
				// Invalid credentials
				Map<String, String> errorResponse = new HashMap<>();
				errorResponse.put("message", "Request Consult Not Found");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
			}
		}

	
	
	
	@PostMapping("/filter-by-start-time")
    public List<doctor> filterByStartTime(@RequestBody Map<String, String> requestBody) {
        String startTime = requestBody.get("start_time");
        return consultRepository.findByStartTimeGreaterThanOrEqual(startTime);
    }

    @PostMapping("/filter-by-end-time")
    public List<doctor> filterByEndTime(@RequestBody Map<String, Integer> requestBody) {
        Integer endTime = requestBody.get("end_time");
        return consultRepository.findByEndTimeLessThanOrEqual(endTime);
    }
   
   
}
	


