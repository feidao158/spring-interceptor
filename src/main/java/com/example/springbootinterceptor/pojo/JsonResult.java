package com.example.springbootinterceptor.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult {

    private Integer status;

    private String msg;

    private Object data;


    public static JsonResult ok(){
        return new JsonResult(200,"success",null);
    }

}
