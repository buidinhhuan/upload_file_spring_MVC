package rikkei.academy.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

// Đánh dấu đây là một lớp cấu hình Spring
@Configurable
@EnableWebMvc // Kích hoạt cấu hình cho Spring Web MVC
@ComponentScan(basePackages = {"rikkei.academy"}) // Quét các thành phần trong gói "rikkei.academy"
@PropertySource("classpath:upload.properties") // Đọc cấu hình từ file upload.properties
public class WebConfig implements WebMvcConfigurer, ApplicationContextAware {

	// Đọc giá trị từ file properties
	@Value("${upload-path}")
	private String pathUpload;

	private ApplicationContext applicationContext;

	// Ghi đè phương thức từ ApplicationContextAware để nhận ApplicationContext
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	// Cấu hình ViewResolver để xác định nơi lưu trữ các file JSP và cách trả về chúng
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/"); // Tiền tố cho đường dẫn JSP
		resolver.setSuffix(".jsp"); // Hậu tố cho đường dẫn JSP
		return resolver;
	}

	// Cấu hình CommonsMultipartResolver để hỗ trợ upload file
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setMaxUploadSizePerFile(52428800); // Kích thước tối đa cho mỗi file upload (50MB)
		return resolver;
	}

	// Cấu hình các resource handler để xử lý các request tới các tài nguyên như ảnh, CSS...
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/image/**", "/css/**")
				.addResourceLocations("file:" + pathUpload, "classpath:/assets/css/");
		// Đăng ký đường dẫn /image/** để truy cập vào các file ảnh trong thư mục cấu hình pathUpload
		// Đăng ký đường dẫn /css/** để truy cập vào các file CSS trong thư mục classpath:/assets/css/
	}

}
