package com.forestvue.security;


import com.forestvue.domain.MemberVO;
import com.forestvue.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private MemberMapper memberMapper;

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        MemberVO vo = memberMapper.read(userName);
        if(vo==null)  throw new UsernameNotFoundException("no user found");
        return new CustomUser(vo);
    }
}
