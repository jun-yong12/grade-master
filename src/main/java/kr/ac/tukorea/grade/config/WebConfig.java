package kr.ac.tukorea.grade.config;

import kr.ac.tukorea.grade.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/**
 * WebMvc 설정
 * - LoginInterceptor 등록
 * - CookieLocaleResolver (i18n 쿠키 기반 언어 전환)
 * - LocaleChangeInterceptor (?lang=en / ?lang=ko)
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver("lang");
        resolver.setDefaultLocale(Locale.KOREAN);
        return resolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // i18n 인터셉터 (모든 경로)
        registry.addInterceptor(localeChangeInterceptor());

        // 로그인 체크 인터셉터 (보호 경로)
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/students/**", "/grades/**")
                .excludePathPatterns("/login", "/logout", "/css/**", "/js/**");
    }
}
