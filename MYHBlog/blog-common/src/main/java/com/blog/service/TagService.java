package com.blog.service;

import com.blog.domain.ResponseResult;
import com.blog.domain.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author MYH
* @description 针对表【t_tag(标签)】的数据库操作Service
* @createDate 2022-09-30 20:39:25
*/
public interface TagService extends IService<Tag> {

    ResponseResult pageTagList(Integer pageNum, Integer pageSize, String name, String remark);

    ResponseResult addTag(Tag tag);

    ResponseResult deleteTag(Long id);

    ResponseResult selectTagById(Long id);

    ResponseResult updateTag(Tag tag);

    ResponseResult listAllTag();
}
