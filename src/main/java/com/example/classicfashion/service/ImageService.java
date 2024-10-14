package com.example.classicfashion.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.classicfashion.model.Image;
import com.example.classicfashion.repository.ImageRepository;

@Service
public class ImageService {
	private final ImageRepository imageRepository;

	public ImageService(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}

	public void save(Image Image) {
		imageRepository.save(Image);
	}

	public void deleteById(Long Id) {
		imageRepository.deleteById(Id);
	}
	public List<Image> findAll(){
		return imageRepository.findAll();
	}
	private final String UPLOAD_DIR = "uploads" + File.separator;

	public List<Image> uploadImages(MultipartFile[] files) {
	    List<Image> savedImages = new ArrayList<>();

	    if (files != null && files.length > 0) {
	        for (MultipartFile file : files) {
	            if (!file.isEmpty()) {
	                try {
	                    // Tạo tên file với thời gian hiện tại để đảm bảo tính duy nhất
	                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

	                    // Đường dẫn upload cho file
	                    Path uploadPath = Paths.get(UPLOAD_DIR + fileName);
	                    
	                    // Tạo thư mục nếu chưa tồn tại
	                    File uploadDir = new File(UPLOAD_DIR);
	                    if (!uploadDir.exists()) {
	                        uploadDir.mkdirs();
	                    }

	                    // Lưu file vào thư mục
	                    Files.write(uploadPath, file.getBytes());

	                    // Lưu thông tin ảnh vào database
	                    Image image = new Image();
	                    image.setImgLink("/" + UPLOAD_DIR.replace(File.separator, "/") + fileName); // Chuẩn hóa đường dẫn URL
	                    imageRepository.save(image);
	                    savedImages.add(image);

	                } catch (IOException e) {
	                    // Ghi log hoặc in ra console để dễ debug
	                    System.err.println("Error when saving file: " + e.getMessage());
	                }
	            }
	        }
	    }
	    return savedImages; 
	}
}
