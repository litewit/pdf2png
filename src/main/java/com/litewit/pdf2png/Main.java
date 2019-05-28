package com.litewit.pdf2png;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


public class Main {

    public static void main(String[] args) {

        Long currentTimeMillis = System.currentTimeMillis();

        File dir = new File(".");
        File[] filesList = dir.listFiles();
        if (filesList != null) {
            for (File file : filesList) {
                if (file.isFile() && getFileExtension(file.getName()).equalsIgnoreCase("pdf")) {
                    convert(file);
                }
            }
        }

        long diff = System.currentTimeMillis() - currentTimeMillis;

        System.out.print(diff);

    }

    private static String getFileExtension(String fileName) {
        if (fileName == null || fileName.equals(""))
            return "undefined";
        int dotIndex = fileName.lastIndexOf(".");
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    private static void convert(File sourceFile) {
        try {
            String destinationDir = sourceFile.getName().replace(".pdf", "") + "/"; // converted images from pdf document are saved here

            File destinationFile = new File(destinationDir);
            if (!destinationFile.exists()) {
                boolean fileCreated = destinationFile.mkdir();
                if (fileCreated)
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
                    File outputFile = new File(destinationDir + fileName +"_"+ pageNumber +".png");
                    System.out.println("Image Created -> "+ outputFile.getName());
                    ImageIO.write(image, "png", outputFile);
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
