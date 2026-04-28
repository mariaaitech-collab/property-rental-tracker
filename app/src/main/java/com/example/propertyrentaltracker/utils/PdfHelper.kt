package com.example.propertyrentaltracker.utils

import android.content.Context
import android.os.Environment
import com.example.propertyrentaltracker.data.db.entity.Payment
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.properties.TextAlignment
import java.io.File

object PdfHelper {

    fun generateReceipt(context: Context, payment: Payment): File? {
        try {
            val fileName = "Receipt_${payment.paymentId}_${System.currentTimeMillis()}.pdf"
            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)

            val writer = PdfWriter(file)
            val pdfDoc = PdfDocument(writer)
            val document = Document(pdfDoc)

            // Add Header
            val header = Paragraph("Property Rental Tracker")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(24f)
                .setBold()
            document.add(header)

            val subHeader = Paragraph("Rent Receipt")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(18f)
            document.add(subHeader)

            document.add(Paragraph("\n"))

            // Add Details
            document.add(Paragraph("Receipt No: ${payment.paymentId}"))
            document.add(Paragraph("Date: ${java.util.Date(payment.paymentDate)}"))
            document.add(Paragraph("Tenant ID: ${payment.tenantId}")) // Could be joined with tenant name
            document.add(Paragraph("Rent Month: ${payment.monthYear}"))
            document.add(Paragraph("\n"))

            document.add(Paragraph("Amount Paid: PKR ${payment.amountPaid}").setBold().setFontSize(16f))
            document.add(Paragraph("Payment Method: ${payment.paymentMethod}"))
            document.add(Paragraph("Status: ${payment.paymentStatus}"))

            if (payment.notes.isNotEmpty()) {
                document.add(Paragraph("\nNotes: ${payment.notes}"))
            }

            document.add(Paragraph("\n\n-----------------------------"))
            document.add(Paragraph("Authorized Signature").setTextAlignment(TextAlignment.RIGHT))

            document.close()
            return file
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
