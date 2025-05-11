package com.clinic.project.Adapters.Controllers;

import com.clinic.project.Adapters.Dto.AppointmentRequestDto;
import com.clinic.project.Domain.Model.Appointment;
import com.clinic.project.Domain.Model.AppointmentStatus;
import com.clinic.project.Domain.Model.AppointmentType;
import com.clinic.project.Domain.Model.User;
import com.clinic.project.Domain.Services.AppointmentService;
import com.clinic.project.Domain.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @PostMapping
    public Appointment createAppointment(@RequestBody AppointmentRequestDto appointmentRequestDto) {

        User patient = userService.getUserById(appointmentRequestDto.getPatientId());
        User doctor = userService.getUserById(appointmentRequestDto.getDoctorId());

        AppointmentType appointmentType;
        try {
            appointmentType = AppointmentType.valueOf(appointmentRequestDto.getAppointmentType());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid appointment type: " + appointmentRequestDto.getAppointmentType());
        }

        // Create a new appointment
        Appointment appointment = new Appointment();
        appointment.setDateTime(appointmentRequestDto.getDateTime());
        appointment.setStatus(appointmentRequestDto.getStatus());
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setType(appointmentType);

        // Save and return the appointment
        return appointmentService.createAppointment(appointment);
    }

    @GetMapping("/patient/{username}")
    public List<Appointment> getAppointmentsByPatient(@PathVariable String username) {
        User patient = userService.getUserByUsername(username);
        return appointmentService.getAppointmentsByPatient(patient);
    }

    @GetMapping("/doctor/{username}")
    public List<Appointment> getAppointmentsByDoctor(@PathVariable String username) {
        User doctor = userService.getUserByUsername(username);
        return appointmentService.getAppointmentsByDoctor(doctor);
    }


    @PutMapping("/{id}/status")
    public Appointment updateAppointmentStatus(@PathVariable Long id, @RequestParam AppointmentStatus status) {
        return appointmentService.updateStatus(id, status);
    }

    //all appointments
    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/status/{status}")
    public List<Appointment> getAppointmentsByStatus(@PathVariable AppointmentStatus status) {
        return appointmentService.getAppointmentsByStatus(status);
    }

}
