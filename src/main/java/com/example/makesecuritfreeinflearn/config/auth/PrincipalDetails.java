package com.example.makesecuritfreeinflearn.config.auth;

import com.example.makesecuritfreeinflearn.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// 시큐리티가 /loginPrc 주소 요청이오면 낚아채서 로그인을 진행시킨다.
// 로그인을 진행이 완료가 되면 session을 만들어줍니다.(Security ContextHolder)
// 오브젝트 => Authentication 객체
// Authentication 안에 User 정보가 있어야 됨
// User 오브젝트타입 =>  UserDetails 타입 객체

// Security Session => Authentication => UserDetails


// Authentication 객체에 저장할 수 있는 유일한 타입
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

	private User user; // 콤포지션
	private Map<String, Object> attributes; // OAuth2User 정보

	// 일반 로그인
	public PrincipalDetails(User user) {
		super();
		this.user = user;
	}

	// OAuth 로그인
	public PrincipalDetails(User user, Map<String, Object> attributes) {
		super();
		this.user = user;
		this.attributes = attributes;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collet = new ArrayList<GrantedAuthority>();
		collet.add(()->{ return user.getRole();});
		return collet;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		return null;
	}
}
