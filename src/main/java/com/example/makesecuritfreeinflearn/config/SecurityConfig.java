package com.example.makesecuritfreeinflearn.config;

import com.example.makesecuritfreeinflearn.config.auth.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


//  1. 코드 받기(인증) / 2. 엑세스 토큰(권한) / 3. 사용자 프로필 정보를 가져옴 / 4-1. 그 정보를 토대로 회원가입을 자동으로 진행시키기도 함
// 4-2 (이메일, 전화번호, 이름 아이디), 쇼핑몰 -> (집주소) / 백화점몰 ->(등급)

@Configuration // IoC 빈(bean)을 등록
@EnableWebSecurity // 필터 체인 관리 시작 어노테이션
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		http.authorizeRequests()
			//.antMatchers("/user/**").authenticated()
			//.antMatchers("/user/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
			//.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
			//.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll()

		.and()
			.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/loginProc")
			.defaultSuccessUrl("/")

		.and()
			.oauth2Login()
			.loginPage("/login")
			.userInfoEndpoint()
			.userService(principalOauth2UserService)// 구글 로그인이 완료된 뒤의 후처리가 필요함. Tip. 코드를 받는게 아니고 엑세스 토큰 + 사용자 프로필 정보를 받는다
			;
	}
}





