package com.clinic.project.Adapters.Controllers;

import com.clinic.project.Domain.Model.Appointment;
import com.clinic.project.Domain.Model.AppointmentStatus;
import com.clinic.project.Domain.Model.User;
import com.clinic.project.Domain.Services.AppointmentService;
import com.clinic.project.Domain.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment) {
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
}
