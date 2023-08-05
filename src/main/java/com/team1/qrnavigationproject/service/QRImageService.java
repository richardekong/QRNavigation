package com.team1.qrnavigationproject.service;

import com.google.zxing.WriterException;

import java.io.File;
import java.io.IOException;

public interface QRImageService {
    File makeQRImage(String fileName, String text) throws WriterException, IOException;
}
