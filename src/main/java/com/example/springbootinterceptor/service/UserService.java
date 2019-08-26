package com.example.springbootinterceptor.service;
import com.example.springbootinterceptor.dao.UserRepository;
import com.example.springbootinterceptor.pojo.JsonResult;
import com.example.springbootinterceptor.pojo.User;
import com.example.springbootinterceptor.utils.JwtUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public JsonResult findUserById(Integer id){
        User user = userRepository.findById(id).get();
        return new JsonResult(200,"success",user);
    }

    public JsonResult login(User user){
        User byUsername = userRepository.findByUsername(user.getUsername());
        JsonResult ok = JsonResult.ok();
        if (byUsername!=null){

            String s1 = DigestUtils.md5Hex(user.getPassword() + byUsername.getSalt());
            if (byUsername.getPassword().equals(s1)){
                byUsername.setLastLoginTime(new Date());
                userRepository.save(byUsername);
                String s = JwtUtils.generateJwt(byUsername);
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("token",s);
                ok.setData(hashMap);
                return ok;
            }
            ok.setStatus(405);
            ok.setMsg("账号或密码错误");
            return ok;

        }
        ok.setStatus(405);
        ok.setMsg("账号不存在");
        return ok;
    }

    public JsonResult findAllUserInfo(){
        JsonResult jsonResult = new JsonResult(200,"success",null);
        List<User> all = userRepository.findAll();
        jsonResult.setData(all);
        return jsonResult;
    }

}
