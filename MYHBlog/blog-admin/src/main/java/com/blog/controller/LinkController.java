package com.blog.controller;

/*
 * @author 孟亚辉
 * @time 2022/10/5 22:35
 */

import com.blog.domain.ResponseResult;
import com.blog.domain.dto.AddLinkDto;
import com.blog.domain.dto.UpdateLinkDto;
import com.blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult pageLinkList(Integer pageNum, Integer pageSize, String name, String status) {
        return linkService.pageLinkList(pageNum, pageSize, name, status);
    }

    @PostMapping
    public ResponseResult addLink(@RequestBody AddLinkDto addLinkDto){
        return linkService.addLink(addLinkDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getLinkById(@PathVariable("id") Long id){
        return linkService.getLinkById(id);
    }

    @PutMapping
    public ResponseResult updateLink(@RequestBody UpdateLinkDto updateLinkDto){
        return linkService.updateLink(updateLinkDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable("id") Long id){
        return linkService.deleteLink(id);
    }
}
