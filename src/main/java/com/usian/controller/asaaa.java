package com.usian.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.usian.entity.Student;
import com.usian.entity.User;
import com.usian.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class asaaa {
    @Autowired
    private StudentService studentService;
    @RequestMapping("/p")
    public String p(){
        return "login";
    }
    @RequestMapping("/login")
    public String c(User user, Model model, HttpServletRequest request){
        if(studentService.login(user)){
            request.getSession().setAttribute("u",user);
            return "redirect:index";
        }else{
            model.addAttribute("s","用户名或密码错误");
            return "login";
        }
    }
    @RequestMapping("/index")
    public String o(String firsttime, String lasttime, String name,@RequestParam(defaultValue = "2")int pageSize, @RequestParam(defaultValue = "1")int pageNum, Model model){
        PageHelper.startPage(pageNum,pageSize);
        List<Student> l=studentService.getAll(name,firsttime,lasttime);
        PageInfo<Student> pageInfo=new PageInfo<>(l);
        model.addAttribute("stu",pageInfo);
        model.addAttribute("firsttime",firsttime);
        model.addAttribute("lasttime",lasttime);
        model.addAttribute("name",name);
        return "index";
    }
    @RequestMapping("/add")
    public String add(Model model){
        return "add";
    }
    @RequestMapping("/addStudent")
    public String b(@ModelAttribute Student student){
        studentService.add(student);
        return "redirect:index";
    }
    @RequestMapping("/del")
    public String del(Integer id){
        studentService.delete(id);
        return "redirect:index";
    }
    @RequestMapping("/update")
    public String update(String id,Model model){
        Student student=studentService.getStudent(id);
        model.addAttribute("s",student);
        return "update";
    }
    @RequestMapping("/updateStudent")
    public String updateStudent(@ModelAttribute Student student){
        studentService.update(student);
        return "redirect:index";
    }
}
