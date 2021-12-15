package com.codegym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ListController {

    @GetMapping("/test")
    public String showList(Model model){
        List<String> list = new ArrayList<>();
        list.add("anh");
        list.add("binh");
        list.add("dung");
        list.add("duy");
        list.add("huong");
        model.addAttribute("list",list);
        return "/showlist";
    }
}
