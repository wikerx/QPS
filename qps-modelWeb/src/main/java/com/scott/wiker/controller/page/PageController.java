package com.scott.wiker.controller.page;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author : Mr.薛
 * @version : V1.0
 * @className : PageController
 * @description :
 * @date : 2021/2/2 0002 上午 11:25
 * @status : 编写
 **/
@Slf4j
@Controller
//@RequestMapping(value = "/page")
public class PageController {

    /**
     * 测试页面
     * @param model
     * @return
     */
    @RequestMapping(value = {"index","index.html"})
    public String result(Model model){
        return "index";
    }
}
