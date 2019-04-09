import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;


public class Main {

    public static void main(String[] args) {
        System.out.println("hello world");

        try {
            String sourceDir = "/Users/quadrixm/Bar/Java/pdf2image/test_pdf_1.pdf"; // Pdf files are read from this folder
            String destinationDir = "/Users/quadrixm/Bar/Java/pdf2image/Converted_PdfFiles_to_Image/"; // converted images from pdf document are saved here

            File sourceFile = new File(sourceDir);
            File destinationFile = new File(destinationDir);
            if (!destinationFile.exists()) {
                destinationFile.mkdir();
                System.out.println("Folder Created -> "+ destinationFile.getAbsolutePath());
            }
            if (sourceFile.exists()) {
                System.out.println("Images copied to Folder: "+ destinationFile.getName());
                PDDocument document = PDDocument.load(sourceFile);
                PDPageTree pdPageTree = document.getDocumentCatalog().getPages();
                System.out.println("Total files to be converted -> "+ pdPageTree.getCount());

                PDFRenderer pdfRenderer = new PDFRenderer(document);

                String fileName = sourceFile.getName().replace(".pdf", "");
                int pageNumber = 1;
                for (int page = 0; page < document.getNumberOfPages(); ++page) {

                    BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
//                    String fileName = OUTPUT_DIR + "image-" + page + ".png";
//                    ImageIO.write(image, fileName, 300);


//                    BufferedImage image = page.();
                    File outputfile = new File(destinationDir + fileName +"_"+ pageNumber +".png");
                    System.out.println("Image Created -> "+ outputfile.getName());
                    ImageIO.write(image, "png", outputfile);
                    pageNumber++;
                }
                document.close();
                System.out.println("Converted Images are saved at -> "+ destinationFile.getAbsolutePath());
            } else {
                System.err.println(sourceFile.getName() +" File not exists");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
