package com.example.QNA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//매퍼 생성 후 서비스에서 사용시 Bean스캔 실패
//해결을 위해 메인에 직접 컴포넌트 스캔
@ComponentScan(basePackages = {"com.example.QNA", "mapper"})
public class QnaApplication {

	public static void main(String[] args) {
		SpringApplication.run(QnaApplication.class, args);
	}

}
