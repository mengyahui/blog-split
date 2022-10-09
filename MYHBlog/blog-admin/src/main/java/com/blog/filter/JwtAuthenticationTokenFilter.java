package com.blog.filter;

import com.alibaba.fastjson.JSON;
import com.blog.domain.LoginUser;
import com.blog.domain.ResponseResult;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.utils.JwtUtil;
import com.blog.utils.RedisCache;
import com.blog.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/*
 * @author 孟亚辉
 * @time 2022/9/28 20:30
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中的 token
        String token = request.getHeader("token");
        // 判断是否获取到 token
        if (!StringUtils.hasText(token)) {
            // 获取不到说明该接口不需要登录，直接放行即可。
            filterChain.doFilter(request, response);
            return;
        }
        // 解析获取 userId
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            // token 超时 or token 不合法
            // 需要重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject();
        // 从 redis 中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("login:" + userId);
        // 获取不到登录信息，提示用户重新登录
        if (Objects.isNull(loginUser)) {
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        // 存入 SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
