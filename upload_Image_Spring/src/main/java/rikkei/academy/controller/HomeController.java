package rikkei.academy.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
@PropertySource("classpath:upload.properties") // Đọc cấu hình từ file upload.properties
public class HomeController {

	@Value("${upload-path}") // Đọc giá trị từ cấu hình upload.properties
	private String pathUpload;

	@GetMapping
	public String home() {
		return "home"; // Trả về trang home.jsp
	}

	@PostMapping("/upload")
	public String uploadFile(@RequestParam("urls") List<MultipartFile> images, Model model) {
		File file = new File(pathUpload);
		if (!file.exists()) {
			file.mkdirs(); // Tạo thư mục upload nếu chưa tồn tại
		}

		List<String> listUrl = new ArrayList<>();
		for (MultipartFile m : images) {
			String fileName = m.getOriginalFilename(); // Lấy tên gốc của file
			listUrl.add(fileName); // Thêm tên file vào danh sách

			try {
				FileCopyUtils.copy(m.getBytes(), new File(pathUpload + fileName)); // Copy file vào thư mục upload
			} catch (IOException e) {
				throw new RuntimeException(e); // Xử lý lỗi nếu có vấn đề trong việc copy
			}
		}
		model.addAttribute("images", listUrl); // Thêm danh sách tên file vào model
		return "images"; // Trả về trang images.jsp và hiển thị danh sách tên file đã upload
	}
}
