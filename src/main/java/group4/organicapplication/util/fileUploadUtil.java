/**
*@author Admin
*@date Jul 28, 2023
*@version 17.0
*
*/
package group4.organicapplication.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class fileUploadUtil {
	 public static void saveFile(String uploadDir, String fileName,
	            MultipartFile multipartFile) throws IOException {
	        Path uploadPath = Paths.get(uploadDir);
	         
	        if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
	        }
	         
	        try (InputStream inputStream = multipartFile.getInputStream()) {
	            Path filePath = uploadPath.resolve(fileName);
	            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	        } catch (IOException ioe) {        
	            throw new IOException("Could not save image file: " + fileName, ioe);
	        }      
	    }
	 public static void deleteFile(File file) throws IOException {
		 if(file.listFiles() != null) {
		 for (File subfile : file.listFiles()) {
		
	          if (subfile.isDirectory()) {
	                deleteFile(subfile);
	            }
	            subfile.delete();
	        }
	 
}
		 }
	 }
