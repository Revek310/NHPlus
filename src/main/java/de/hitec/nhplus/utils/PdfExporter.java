package de.hitec.nhplus.utils;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.pdf.PdfDocument;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.model.Treatment;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Class for exporting database entries to pdf
 */
public class PdfExporter {


    /**
     * calls a file chooser dialog and exports a patient to the selected destination
     * @param patient that you want to export
     * @param dest export destination
     */
    public static void exportPatientToPdf(Patient patient, String dest) {
        try {
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Patient Information"));
            document.add(new Paragraph("Patient Number: " + patient.getPid()));
            document.add(new Paragraph("Name: " + patient.getFirstName()));
            document.add(new Paragraph("Surname: " + patient.getSurname()));
            document.add(new Paragraph("Date of Birth: " + patient.getDateOfBirth()));
            document.add(new Paragraph("Care level: " + patient.getCareLevel()));
            document.add(new Paragraph("Room: " + patient.getRoomNumber()));

            document.close();

            System.out.println("PDF created successfully!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * calls a file chooser dialog and exports a treatment to the selected destination
     * @param treatment that you want to export
     * @param dest export destination
     */
    public static void exportTreatmentToPdf(Treatment treatment, String dest) {
        try {
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Patient Information"));
            document.add(new Paragraph("Treatment Number: " + treatment.getTid()));
            document.add(new Paragraph("Patient Number: " + treatment.getPid()));
            document.add(new Paragraph("Caregiver Number: " + treatment.getCid()));
            document.add(new Paragraph("Date: " + treatment.getDate()));
            document.add(new Paragraph("Begin: " + treatment.getBegin()));
            document.add(new Paragraph("End: " + treatment.getEnd()));
            document.add(new Paragraph("Description: " + treatment.getDescription()));

            document.close();

            System.out.println("PDF created successfully!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}