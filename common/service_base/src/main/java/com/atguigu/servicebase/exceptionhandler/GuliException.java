package com.atguigu.servicebase.exceptionhandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException{

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "描述信息")
    private String msg;

    @Override
    public String toString() {
        return "GuliException{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }
}
