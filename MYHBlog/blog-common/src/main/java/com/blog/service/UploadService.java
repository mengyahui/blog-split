package com.blog.service;

import com.blog.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/*
 * @author 孟亚辉
 * @time 2022/9/29 21:50
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
