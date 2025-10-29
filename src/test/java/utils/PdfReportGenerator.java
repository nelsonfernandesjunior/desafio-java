package utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PdfReportGenerator {

    private static List<String> screenshotPaths = new ArrayList<>();
    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
    private static final Font BOLD_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);

    public static void addScreenshot(String screenshotPath) {
        if (screenshotPath != null && !screenshotPath.isEmpty() && new File(screenshotPath).exists()) {
            screenshotPaths.add(screenshotPath);
            System.out.println("📸 Screenshot adicionado ao PDF: " + screenshotPath);
        }
    }

    public static void generatePdfReport(String testName, String status) {
        if (screenshotPaths.isEmpty()) {
            System.out.println("⚠️  Nenhum screenshot válido para gerar PDF");
            return;
        }

        Document document = new Document();
        FileOutputStream fos = null;

        try {
            // Cria diretório se não existir
            File pdfDir = new File("evidencias/pdf-reports");
            if (!pdfDir.exists()) {
                boolean created = pdfDir.mkdirs();
                System.out.println("📁 Diretório criado: " + created);
            }

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = testName.replace(" ", "_").replace("/", "_") + "_" + timestamp + ".pdf";
            String pdfPath = new File(pdfDir, fileName).getAbsolutePath();

            fos = new FileOutputStream(pdfPath);
            PdfWriter.getInstance(document, fos);
            document.open();

            // Cabeçalho
            Paragraph title = new Paragraph("Relatório de Teste Automatizado", TITLE_FONT);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Informações do teste
            document.add(new Paragraph("Teste: " + testName, BOLD_FONT));
            document.add(new Paragraph("Data: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()), NORMAL_FONT));
            document.add(new Paragraph("Status: " + status, BOLD_FONT));
            document.add(new Paragraph("Total de Evidências: " + screenshotPaths.size(), NORMAL_FONT));
            document.add(new Paragraph(" ")); // linha em branco

            // Adiciona screenshots
            for (int i = 0; i < screenshotPaths.size(); i++) {
                String screenshotPath = screenshotPaths.get(i);
                File screenshotFile = new File(screenshotPath);

                if (screenshotFile.exists()) {
                    try {
                        // Título da etapa
                        Paragraph stepTitle = new Paragraph("Etapa " + (i + 1) + ":", BOLD_FONT);
                        stepTitle.setSpacingBefore(10);
                        document.add(stepTitle);

                        // Imagem
                        Image image = Image.getInstance(screenshotPath);
                        image.scaleToFit(500, 500); // Redimensiona se necessário
                        image.setAlignment(Element.ALIGN_CENTER);
                        document.add(image);

                        // Nome do arquivo
                        document.add(new Paragraph("Arquivo: " + screenshotFile.getName(), NORMAL_FONT));
                        document.add(new Paragraph(" ")); // linha em branco

                    } catch (Exception e) {
                        System.err.println("❌ Erro ao adicionar imagem ao PDF: " + e.getMessage());
                        document.add(new Paragraph("❌ Erro ao carregar evidência: " + screenshotFile.getName()));
                    }
                } else {
                    System.out.println("⚠️  Arquivo não encontrado: " + screenshotPath);
                }
            }

            System.out.println("📄 PDF gerado com sucesso: " + pdfPath);

        } catch (DocumentException | IOException e) {
            System.err.println("❌ Erro ao gerar PDF: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    System.err.println("❌ Erro ao fechar arquivo: " + e.getMessage());
                }
            }
            // Limpa lista para próximo teste
            screenshotPaths.clear();
        }
    }

    public static void clearScreenshots() {
        screenshotPaths.clear();
        System.out.println("🧹 Lista de screenshots limpa");
    }

    public static int getScreenshotCount() {
        return screenshotPaths.size();
    }
}