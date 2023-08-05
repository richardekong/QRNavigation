package com.team1.qrnavigationproject.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;


@Service
public class QRImageServiceImpl implements QRImageService {

    //Referenced from
    @Override
    public File makeQRImage(String imageName, String text) throws WriterException, IOException {
        //create a byte matrix for the qr code and encode a desired text
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix matrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200, hintMap);

        //create a buffered image to hold the qrcode
        final String fileType = "png";
        final int matrixWidth = matrix.getWidth();
        final int matrixHeight = matrix.getHeight();

        final File imageFile = new File("%s.%s".formatted(imageName,fileType));
        BufferedImage image = new BufferedImage(matrixWidth, matrixHeight, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixHeight);
        graphics.setColor(Color.BLACK);
        //paint and save the image using the matrix
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixHeight; j++) {
                if (matrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        ImageIO.write(image, fileType,imageFile);
        return imageFile;
    }
}
