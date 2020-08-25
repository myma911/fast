package cn.aaron911.pay.demo.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("")
    public String index() {
        logger.error("欢迎使用 IJPay -By Javen  交流群：723992875");
        return "index.html";
    }

    @RequestMapping("/toWxH5Pay")
    public String toWxH5Pay() {
        return "wxh5pay.html";
    }

    @RequestMapping("/toWxPay")
    public String toWxPay() {
        return "wxpay.html";
    }

    @RequestMapping("/toWxSuPay")
    public String toWxSuPay() {
        return "wxsubpay.html";
    }

    @RequestMapping(value = "/payInputMoney")
    public ModelAndView payInputMoney() {
        ModelAndView mav = new ModelAndView("pay_input_money.html");
        mav.addObject("content", "xxx");
        return mav;
    }

    @RequestMapping(value = "/payKeyBoard")
    public String payKeyBoard() {
        return "pay_keyboard.html";
    }

    @RequestMapping(value = "/paySelectMoney")
    public String paySelectMoney() {
        return "pay_select_money.html";
    }

    @RequestMapping("/success")
    public String success() {
        return "success.html";
    }

    @RequestMapping(value = "/ss/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String pa(@PathVariable("id") Integer id) {
        return "id>" + id;
    }

    @RequestMapping(value = "/xx", method = RequestMethod.GET)
    @ResponseBody
    public String param(@RequestParam("id") Integer xx) {
        return "id>" + xx;
    }

    @RequestMapping(value = "/xxx", method = RequestMethod.GET)
    @ResponseBody
    public String param2(@RequestParam(value = "id", required = false, defaultValue = "2") Integer xx) {
        return "id>" + xx;
    }

}
