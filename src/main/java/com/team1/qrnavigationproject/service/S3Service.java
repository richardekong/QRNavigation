package com.team1.qrnavigationproject.service;

import com.amazonaws.services.s3.model.S3ObjectInputStream;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface S3Service {
   S3ObjectInputStream getS3Object(String fileName);

   String putObjectInS3(File file) throws IOException;


}
