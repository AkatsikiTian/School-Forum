package com.usst.background.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usst.background.entity.Category;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface CategoryMapper extends BaseMapper<Category> {
    @Update("alter table category AUTO_INCREMENT = 1")
    Integer updateautoid();

    @Select("select count(*) from article where category_id=#{id}")
    Integer countid(Long id);
}
