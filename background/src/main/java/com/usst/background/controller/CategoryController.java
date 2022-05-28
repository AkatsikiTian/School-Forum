package com.usst.background.controller;

import com.usst.background.entity.Category;
import com.usst.background.entity.Tag;
import com.usst.background.service.CategoryService;
import com.usst.background.service.TagService;
import com.usst.background.vo.CategoryVo;
import com.usst.background.vo.Result;
import com.usst.background.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public Result findbyPage(@RequestParam Integer currentPage, Integer pageSize){
        return Result.succ(categoryService.findbypage(currentPage,pageSize));
    }

    @PostMapping
    public Result save(@RequestBody CategoryVo categoryVo){
        int result;
        Category category = new Category();
        BeanUtils.copyProperties(categoryVo,category);
        System.out.println(category);
        if (categoryVo.getId()!=null){
            category.setId(Long.valueOf(categoryVo.getId()));
            result =categoryService.update(category);
        }else{
            result=categoryService.save(category);
        }
        return Result.succ(result);
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable Long id){
        int count = categoryService.count(id);
        if (count>0){
            return Result.fail(500,"当前标签下有文章，删除失败");
        }
        return Result.succ(categoryService.delete(id));
    }

    @GetMapping("/find")
    public Result findbyInput(@RequestParam Integer currentPage,Integer pageSize,String select,String input){
        return Result.succ(categoryService.findbyinput(currentPage,pageSize,select,input));
    }
}
