package com.example.chuyen_de_1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/test")
public class TestController {
    @GetMapping("/hello/{name}")
    public String hello(Model mode, @PathVariable("name") String name) {
        mode.addAttribute("name", name);
        return "test";
    }

    @GetMapping("/newpage/{name}")
    public String newpage(@PathVariable("name") String name, Model model) {
        model.addAttribute("name", name);
        return "newpage";
    }

    @GetMapping("/from1")
    public String from1(Model model) {
        return "from1";
    }

    @GetMapping("/hello2")
    public String hello2(@RequestParam(name = "value2") String name, Model model) {
        System.out.println(name);
        model.addAttribute("name", name);
        return "hello";
    }
}
