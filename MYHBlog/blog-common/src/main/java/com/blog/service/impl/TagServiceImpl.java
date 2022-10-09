package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.ResponseResult;
import com.blog.domain.Tag;
import com.blog.domain.vo.PageVo;
import com.blog.domain.vo.TagVo;
import com.blog.service.TagService;
import com.blog.mapper.TagMapper;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author MYH
 * @description 针对表【t_tag(标签)】的数据库操作Service实现
 * @createDate 2022-09-30 20:39:25
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
        implements TagService {

    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, String name, String remark) {
        // 分页查询
        LambdaQueryWrapper<Tag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.hasText(name), Tag::getName, name);
        lambdaQueryWrapper.eq(StringUtils.hasText(remark), Tag::getRemark, remark);
        Page<Tag> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);
        // 封装数据
        List<Tag> tagList = page.getRecords();
        PageVo pageVo = new PageVo(tagList, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(Tag tag) {
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectTagById(Long id) {
        Tag tag = getOne(new LambdaQueryWrapper<Tag>().eq(Tag::getId, id));
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult updateTag(Tag tag) {
        LambdaUpdateWrapper<Tag> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Tag::getId,tag.getId());
        update(tag,lambdaUpdateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        List<Tag> tagList = list();
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tagList, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }
}




