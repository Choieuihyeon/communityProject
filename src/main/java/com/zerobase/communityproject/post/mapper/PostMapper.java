package com.zerobase.communityproject.post.mapper;

import com.zerobase.communityproject.post.dto.PostDto;
import com.zerobase.communityproject.post.model.PostParam;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {

	long selectListCount(PostParam parameter);

	List<PostDto> selectList(PostParam parameter);

}
