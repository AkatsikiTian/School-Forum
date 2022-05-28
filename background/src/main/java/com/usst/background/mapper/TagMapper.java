package com.usst.background.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usst.background.entity.Tag;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TagMapper extends BaseMapper<Tag> {
    @Update("alter table tag AUTO_INCREMENT = 1")
    Integer updateautoid();

    @Select("select count(*) from article_tag where tag_id=#{id}")
    Integer countid(Long id);

}
