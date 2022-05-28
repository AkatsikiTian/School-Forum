package com.usst.background.controller;

import com.usst.background.entity.SysUser;
import com.usst.background.entity.Tag;
import com.usst.background.service.TagService;
import com.usst.background.vo.Result;
import com.usst.background.vo.SysUserVo;
import com.usst.background.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping
    public Result findbyPage(@RequestParam Integer currentPage, Integer pageSize){
        return Result.succ(tagService.findbypage(currentPage,pageSize));
    }

    @PostMapping
    public Result save(@RequestBody TagVo tagVo){
        int result;
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagVo,tag);
        System.out.println(tag);
        if (tagVo.getId()!=null){
            tag.setId(Long.valueOf(tagVo.getId()));
            result =tagService.update(tag);
        }else{
            result=tagService.save(tag);
        }
        return Result.succ(result);
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable Long id){
        int count = tagService.count(id);
        if (count>0){
            return Result.fail(500,"当前标签下有文章，删除失败");
        }
        return Result.succ(tagService.delete(id));
    }

    @GetMapping("/find")
    public Result findbyInput(@RequestParam Integer currentPage,Integer pageSize,String select,String input){
        return Result.succ(tagService.findbyinput(currentPage,pageSize,select,input));
    }
}
