package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.Ret;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@Api(value = "登录", tags = "登录")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {

    /**
     * 登录
     */
    @PostMapping("login")
    public Ret login() {

        return Ret.ok().data("token", "admin");
    }

    /**
     * 用户信息
     * @return
     */
    @GetMapping("info")
    public Ret info() {

        return Ret.ok()
                .data("roles", "[admin]")
                .data("name", "admin")
                .data("avatar", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602586526500&di=522e7c1444fa79a415a069acc3957748&imgtype=0&src=http%3A%2F%2Fimg.soogif.com%2Fmini%2FXBHwOEsUslP3voJjpAxiZgyeb6YpzDfu.gif%3Fscope%3D96weixin");
    }
}
