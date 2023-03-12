package com.zerobase.communityproject.member.mapper;

import com.zerobase.communityproject.member.dto.MemberDto;
import com.zerobase.communityproject.member.model.MemberParam;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

	long selectListCount(MemberParam parameter);

	List<MemberDto> selectList(MemberParam parameter);

}
