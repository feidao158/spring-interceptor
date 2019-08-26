package com.example.springbootinterceptor.interceptor;

import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.springbootinterceptor.pojo.JsonResult;
import com.example.springbootinterceptor.pojo.User;
import com.example.springbootinterceptor.service.UserService;
import com.example.springbootinterceptor.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        response.setContentType("application/json;charset=UTF-8");
        JsonResult jsonResult = new JsonResult();
        ObjectMapper objectMapper = new ObjectMapper();
        int id = 0;
        if (token==null){

            jsonResult.setStatus(406);
            jsonResult.setMsg("请先登录");
            String s = objectMapper.writeValueAsString(jsonResult);
            response.getWriter().write(s);
            return false;
        }
        try {
             id = JwtUtils.getId(token);
        }catch (JWTDecodeException e){
            jsonResult.setStatus(407);
            jsonResult.setMsg("非法token！");
            String s = objectMapper.writeValueAsString(jsonResult);
            response.getWriter().write(s);
            return false;
        }

        User data = (User) userService.findUserById(id).getData();

        try {
            JwtUtils.verifyJwt(token,data);
        }catch (TokenExpiredException e){
            Date expiresDate = JwtUtils.getExpiresDate(token);
            expiresDate.setTime(expiresDate.getTime() + JwtUtils.MAX_REBUILD_TIME);
            if (expiresDate.getTime() >System.currentTimeMillis()){
                JsonResult login = userService.login(data);
                login.setStatus(600);
                String s = objectMapper.writeValueAsString(login);
                response.getWriter().write(s);
                return false;
            }
            jsonResult.setStatus(909);
            jsonResult.setMsg("token过期 请重新登陆");
            String s = objectMapper.writeValueAsString(jsonResult);
            response.getWriter().write(s);
            return false;
        }catch (InvalidClaimException e){
            jsonResult.setStatus(908);
            jsonResult.setMsg("你的账号已在别处登陆 请重新登陆或修改密码");
            String s = objectMapper.writeValueAsString(jsonResult);
            response.getWriter().write(s);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
