package com.prescription.controller;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.prescription.exception.PrescriptionException;
import com.prescription.model.Prescription;
import com.prescription.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/prescription")
@Slf4j
@RequiredArgsConstructor
public class PrescriptionController {
    
    private final PrescriptionService prescriptionService;

    @PostMapping("/create-prescription/{appointmentId}")
    public ResponseEntity<Prescription> createPrescription(@RequestHeader("Authorization") String token,
            @RequestBody Prescription prescription, @PathVariable Long appointmentId) throws PrescriptionException{

        String METHOD = "createPrescription";
        log.info("inside controller{}",METHOD);

        Prescription savedPrescription = prescriptionService.createPrescription(token, prescription,appointmentId);

        return new ResponseEntity<>(savedPrescription, HttpStatus.CREATED);
    }

    @GetMapping("/get-prescription/{prescriptionId}")
    public ResponseEntity<Prescription> getPrescription(@RequestHeader("Authorization") String token, @PathVariable Long prescriptionId) throws PrescriptionException {
        
        String METHOD = "getPrescription";
        log.info("inside controller {}",METHOD);

        Prescription prescription = prescriptionService.getSinglePrescription(token, prescriptionId);

        return new ResponseEntity<>(prescription, HttpStatus.OK);
    }

    @GetMapping("/get-prescriptions/{patientId}")
    public ResponseEntity<List<Prescription>> getPrescriptions(@RequestHeader("Authorization") String token, @PathVariable Long patientId) throws PrescriptionException {
        
        String METHOD = "getPrescriptions";
        log.info("inside controller {}",METHOD);

        List<Prescription> prescriptions = prescriptionService.getAllPatientPrescriptions(token,patientId);

        return new ResponseEntity<>(prescriptions, HttpStatus.OK);
    }

    @PutMapping("/update-prescription")
    public ResponseEntity<Prescription> updatePrescription(@RequestHeader("Authorization") String token,@RequestBody Prescription prescription) throws PrescriptionException{

        String METHOD="updatePrescription";
        log.info("inside controller {}",METHOD);

        Prescription updatedPrescription = prescriptionService.updatePrescription(token, prescription);

        return new ResponseEntity<>(updatedPrescription, HttpStatus.OK);
    }

    @DeleteMapping("/remove-prescription/{prescriptionId}")
    public ResponseEntity<String> removePrescription(@RequestHeader("Authorization") String token, @PathVariable Long prescriptionId) throws PrescriptionException{

        String METHOD="removePrescription";
        log.info("inside controller {}",METHOD);

        prescriptionService.removePrescription(token, prescriptionId);

        return ResponseEntity.ok("Prescription deleted successfully");
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadPrescription(@RequestHeader("Authorization") String token, @PathVariable Long id) 
    throws PrescriptionException{
        
        byte[] pdf = prescriptionService.downloadPrescription(token, id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=prescription.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
    
    
}
