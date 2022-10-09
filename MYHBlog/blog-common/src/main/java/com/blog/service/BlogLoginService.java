package com.blog.service;

import com.blog.domain.ResponseResult;
import com.blog.domain.User;

/*
 * @author 孟亚辉
 * @time 2022/9/27 17:52
 */

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
