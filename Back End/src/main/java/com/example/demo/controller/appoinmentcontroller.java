package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.appoinment;
import com.example.demo.model.doctor;
import com.example.demo.repository.appoinmentRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/appoinment/")
public class appoinmentcontroller {
	@Autowired
	private appoinmentRepo appoinmentRepo;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	// create users rest api
		@PostMapping("/saveappoinment")
		public appoinment createUsers(@RequestBody appoinment appoinment) {
			return appoinmentRepo.save(appoinment);
		}

		// get all Users rest api
		@GetMapping("/getappoinment")
		private List<appoinment> getAllConsult() {
			return appoinmentRepo.findAll();
		}
		
		// delete User rest api
		@DeleteMapping("/getappoinment/{id}")
		public ResponseEntity<Map<String, Boolean>> deleteConsult(@PathVariable Long id) {
			appoinment appoinment = appoinmentRepo.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("appoinment not exist with id : " + id));

			appoinmentRepo.delete(appoinment);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return ResponseEntity.ok(response);
		}
		
		   @PostMapping("/appointments/{id}/approve")
		    public ResponseEntity<String> approveAppointment(@PathVariable Long id) {
		        Optional<appoinment> optionalAppointment = appoinmentRepo.findById(id);
		        if (optionalAppointment.isPresent()) {
		        	appoinment appointment = optionalAppointment.get();
		            appointment.setStatus("Approved"); // Assuming you have an 'approved' flag in your Appointment entity
		            appoinmentRepo.save(appointment);

		            sendEmailToPatient(appointment); // Pass the appointment object to sendEmailToPatient method
		            return ResponseEntity.ok("Appointment approved successfully.");
		        } else {
		            return ResponseEntity.notFound().build();
		        }
		    }

		    private void sendEmailToPatient(appoinment appointment) {
		        SimpleMailMessage mailMessage = new SimpleMailMessage();
		        mailMessage.setSubject("Doctor Approved Your Appointment Successfully!");
		        mailMessage.setTo(appointment.getMail());
		        mailMessage.setFrom("docfinder.xyz@gmail.com");

		        String emailContent = "Dear " + appointment.getU_name() + ",\n\n"
		                + "We are pleased to inform you that your appointment has been approved.\n\n"
		                + "Here are the details:\n" + "Doctor Name: " + appointment.getC_name() + "\n" 
		                + "Doctor Available Date: " + appointment.getTime() + "\n"
		                + "If you have any questions or need to make changes, please don't hesitate to contact us.\n\n"
		                + "Thank you for choosing ABC Laboratories.\n\n" + "Sincerely,\n" + "The ABC Laboratories Team";

		        mailMessage.setText(emailContent);

		        javaMailSender.send(mailMessage);
		    }
		    
		    @PostMapping("/appointments/{id}/reject")
		    public ResponseEntity<String> rejectAppointment(@PathVariable Long id) {
		        Optional<appoinment> optionalAppointment = appoinmentRepo.findById(id);
		        if (optionalAppointment.isPresent()) {
		        	appoinment appointment = optionalAppointment.get();
		            appointment.setStatus("Rejected"); // Assuming you have a 'status' field in your Appointment entity
		            appoinmentRepo.save(appointment);

		            sendRejectionEmailToPatient(appointment); // Pass the appointment object to sendRejectionEmailToPatient method
		            return ResponseEntity.ok("Appointment rejected successfully.");
		        } else {
		            return ResponseEntity.notFound().build();
		        }
		    }

		    private void sendRejectionEmailToPatient(appoinment appointment) {
		        SimpleMailMessage mailMessage = new SimpleMailMessage();
		        mailMessage.setSubject("Appointment Rejected by Doctor");
		        mailMessage.setTo(appointment.getMail());
		        mailMessage.setFrom("docfinder.xyz@gmail.com");

		        String emailContent = "Dear " + appointment.getU_name() + ",\n\n"
		                + "We regret to inform you that your appointment has been rejected.\n\n"
		                + "Here are the details:\n" + "Doctor Name: " + appointment.getC_name() + "\n" 
		                + "Appointment Date: " + appointment.getTime() + "\n"
		                + "If you have any concerns or need further assistance, please feel free to contact us.\n\n"
		                + "Thank you for considering ABC Laboratories.\n\n" + "Sincerely,\n" + "The ABC Laboratories Team";

		        mailMessage.setText(emailContent);

		        javaMailSender.send(mailMessage);
		    }

		    
		    
		    @PostMapping("/consultant-name/{name}")
		    public List<appoinment> filterByCounsultName(@PathVariable String name) {
		        //String CName = requestBody.get("c_name");
		        return appoinmentRepo.findByCounsultName(name);
		    }
		    
}
