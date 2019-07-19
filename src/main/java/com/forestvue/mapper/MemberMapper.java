package com.forestvue.mapper;

import com.forestvue.domain.AuthVO;
import com.forestvue.domain.MemberVO;

import java.util.ArrayList;

public interface MemberMapper {
    public MemberVO read(String username);
    public ArrayList<MemberVO> readAll();
    public void write(MemberVO vo);
    public void grant(AuthVO vo);
    public void deleteUserAll();
    public void deleteUserAuthAll();
}
