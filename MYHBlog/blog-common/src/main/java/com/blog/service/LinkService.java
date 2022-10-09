package com.blog.service;

import com.blog.domain.Link;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.ResponseResult;
import com.blog.domain.dto.AddLinkDto;
import com.blog.domain.dto.UpdateLinkDto;

/**
* @author MYH
* @description 针对表【t_link(友链)】的数据库操作Service
* @createDate 2022-09-27 17:09:48
*/
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult pageLinkList(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addLink(AddLinkDto addLinkDto);

    ResponseResult getLinkById(Long id);

    ResponseResult updateLink(UpdateLinkDto updateLinkDto);

    ResponseResult deleteLink(Long id);
}
