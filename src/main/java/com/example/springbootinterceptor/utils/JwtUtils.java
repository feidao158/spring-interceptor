package com.example.springbootinterceptor.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springbootinterceptor.pojo.User;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JwtUtils {

//    token有效期 一个月
    public final static long TIME = 1000 * 60 * 60 * 24 * 30L;

//    最大未登录可重新生成时间
    public final static long MAX_REBUILD_TIME = 1000 * 60 * 60 * 24 * 7L;

    public static String generateJwt(User user){

        long time = user.getLastLoginTime().getTime();
        int millisecond = getMillisecond(time);

        try {
            Algorithm algorithm = Algorithm.HMAC256(user.getPassword());
            String jwt = JWT.create()
                    .withClaim("id", user.getId())
                    .withClaim("lastLoginTime",user.getLastLoginTime().getTime()-millisecond)
                    .withExpiresAt(new Date(System.currentTimeMillis() + TIME))
                    .sign(algorithm);
            Claim lastLoginTime = JWT.decode(jwt).getClaim("lastLoginTime");

            return jwt;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getId(String token){
        DecodedJWT decode = JWT.decode(token);
        int username = decode.getClaim("id").asInt();
        return username;
    }

    public static Date getExpiresDate(String token){
        DecodedJWT decode = JWT.decode(token);
        return decode.getExpiresAt();
    }

    public static boolean verifyJwt(String token,User user){
        int id = getId(token);
        try {
            Algorithm algorithm = Algorithm.HMAC256(user.getPassword());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("id", id)
                    .withClaim("lastLoginTime",user.getLastLoginTime().getTime())
                    .build();
            verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * new Date().getTime()获取的时间戳会包含毫秒 而数据库保存的datetime不包含毫秒
     * generateJwt（）方法中的lastLoginTime 是new()出来的
     * 而verifyJwt（）中的方法是从数据库查询出来的 这样会造成lastLoginTime不一致而导致token验证失败
     * 此方法可获取 new Date().getTime()中的毫秒数 并在generateJwt（）中减去毫秒数
     * @param time
     * @return
     */
    public static int getMillisecond(long time){
        long convert =0;
        int ans = 0;

        while (ans<3){
            convert = convert * 10 + (time%10);
            ans++;
            time/=10;
        }
        ans = 0;
        while (convert != 0) {
            ans = (int) (ans * 10 + (convert % 10));
            convert /= 10;
        }
        return ans;
    }


}
