package com.example.makesecuritfreeinflearn.config.auth;

import com.example.makesecuritfreeinflearn.model.User;
import com.example.makesecuritfreeinflearn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessingUrl("/loginProc)
// /loginProc 요청이 오면 자동으로 UserDetailsService 타입으로 IOC 되어 있는 loadUserByUsername 함수가 실행된다.
@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;

	// 함수 종료시 AuthenticationPrincipal 어노테이션이 만들어진다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(" loadUserByUsername 함수 호출됨 ");
		User user = userRepository.findByUsername(username);
		if(user == null) {
			return null;
		}
		return new PrincipalDetails(user);
	}

}
