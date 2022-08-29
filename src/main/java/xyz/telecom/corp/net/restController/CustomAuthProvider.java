package xyz.telecom.corp.net.restController;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthProvider implements AuthenticationProvider{

	@Autowired
	private UserDetailsService userDetailsService;
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		final String username = (authentication.getPrincipal() == null)?"NONE_PROVIDED":authentication.getName();
		if(StringUtils.isEmpty(username)) {
			throw new BadCredentialsException("Invalid login details");
		}
		UserDetails user = null;
		try {
			user = userDetailsService.loadUserByUsername(username);
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid login details");
			// TODO: handle exception
		}
		return createSuccessFullAuth(authentication, user);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	public Authentication createSuccessFullAuth(final Authentication authentication,final UserDetails user) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
		token.setDetails(authentication.getDetails());
		return token;
	}

}
