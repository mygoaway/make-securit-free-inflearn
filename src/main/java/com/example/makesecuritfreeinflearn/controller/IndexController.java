package com.example.makesecuritfreeinflearn.controller;

import com.example.makesecuritfreeinflearn.config.auth.PrincipalDetails;
import com.example.makesecuritfreeinflearn.model.User;
import com.example.makesecuritfreeinflearn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

@Controller
public class IndexController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/test/login")
	public @ResponseBody String testLogin(Authentication authentication,
										  @AuthenticationPrincipal PrincipalDetails userDetails // 세션정보를 획들이 가능하다.
	) {
		System.out.println("/test/login ======================");

		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("authentication = " +principalDetails.getUser());

		System.out.println(userDetails.getUser());
		return "세션 정보 확인하기";
	}


	@GetMapping("/test/oauth/login")
	public @ResponseBody String testOauthLogin(Authentication authentication,
											   @AuthenticationPrincipal PrincipalDetails oAuth2User// 세션정보를 획들이 가능하다.
	) {
		System.out.println("/test/oauth/login ======================");

		OAuth2User user = (OAuth2User) authentication.getPrincipal();
		System.out.println("oAuth2User = " +user.getAttributes());
		// super.loadUser(userRequest).getAttributes()

		System.out.println("oAuth2User = " + oAuth2User.getAttributes());

		return "OAuth 세션 정보 확인하기";
	}

	@GetMapping({ "", "/" })
	public @ResponseBody String index() {
		return "인덱스 페이지입니다.";
	}

	// OAuth 로그인 / 일반 로그인을 해도 PrincipalDetails로 받을 수 있음
	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principal) {
		System.out.println("Principal : " + principal.getUser());
		// iterator 순차 출력 해보기
		Iterator<? extends GrantedAuthority> iter = principal.getAuthorities().iterator();
		while (iter.hasNext()) {
			GrantedAuthority auth = iter.next();
			System.out.println(auth.getAuthority());
		}

		return "유저 페이지입니다.";
	}

	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "어드민 페이지입니다.";
	}
	
	//@PostAuthorize("hasRole('ROLE_MANAGER')")
	//@PreAuthorize("hasRole('ROLE_MANAGER')")
	@Secured("ROLE_MANAGER")
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "매니저 페이지입니다.";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/join")
	public String join() {
		return "join";
	}

	@PostMapping("/joinProc")
	public String joinProc(User user) {
		System.out.println("회원가입 진행 : " + user);
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole("ROLE_USER");
		userRepository.save(user);
		return "redirect:/";
	}
}
