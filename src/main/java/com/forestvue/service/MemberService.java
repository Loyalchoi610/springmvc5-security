package com.forestvue.service;

import com.forestvue.domain.MemberListDTO;
import com.forestvue.domain.MemberVO;
import com.forestvue.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j
@Service
@AllArgsConstructor
public class MemberService {

    private MemberMapper mapper;

    public MemberListDTO getUsers(){
        MemberListDTO memberListDTO = new MemberListDTO();
        memberListDTO.setMembers(mapper.readAll());
        return memberListDTO;
    }
    public MemberVO getUserByName(String username){
        return mapper.read(username);
    }


}
