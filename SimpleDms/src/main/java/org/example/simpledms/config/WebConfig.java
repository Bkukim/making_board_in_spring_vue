package org.example.simpledms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * packageName : org.example.simpledms.config
 * fileName : WebConfig
 * author : PC
 * date : 2024-04-02
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-02         PC          최초 생성
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {// 얘가 cors를 허용하는 함수를 가진다.


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로를 허용한다.
                .allowedOrigins("http://localhost:8080")// 이 port의 접근의 위의 경로는 다 허용한다.
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name()
                        );// 이 방식들은 다 허용한다. 여기서 특정 방식을 빼면 그것들은 허용하지 않고 해킹으로 인식
    }
}
