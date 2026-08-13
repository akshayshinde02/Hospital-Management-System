package com.prescription.service.impl;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.prescription.exception.PrescriptionException;
import com.prescription.model.Prescription;
import com.prescription.model.PrescriptionMedicine;

@Service
public class PdfService {
    
     public byte[] generatePrescriptionPdf(Prescription prescription) throws PrescriptionException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Document document = new Document();

        PdfWriter.getInstance(document, outputStream);

        document.open();

        document.add(new Paragraph("Hospital Management System"));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Prescription"));
        document.add(new Paragraph("----------------------------"));

        document.add(new Paragraph("Doctor Id : " + prescription.getUserId()));
        document.add(new Paragraph("Patient Id : " + prescription.getPatientId()));

        for(PrescriptionMedicine p : prescription.getMedicines()){
            document.add(new Paragraph("Medicine : " + p.getMedicineName()));
            document.add(new Paragraph("Dosage : " + p.getDosage()));
            document.add(new Paragraph("Instructions : " + p.getInstruction()));
        }
        document.close();

        return outputStream.toByteArray();
    }
}
