package com.example.school_system.controller;

import com.example.school_system.commom.R;
import com.example.school_system.entity.Messages;
import com.example.school_system.entity.User;
import com.example.school_system.service.MessagesService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Messages")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    /***
     * 新增通知
     * @param sid
     * @return
     */
    @GetMapping ("/addMsg/{sid}")
    @ResponseBody
    public R addMsg(@PathVariable int sid){
        return messagesService.addMsg(sid);
    }


    /***
     * 获取未读信息
     * @return
     */
    @GetMapping ("/getAllNot")
    @ResponseBody
    public R getAllNot(HttpServletRequest request){
        return messagesService.getAllNot(request);
    }

    /***
     * 修改状态为已读
     * @return
     */
    @PostMapping ("/markAsRead")
    @ResponseBody
    public R markAsRead(@RequestBody Messages[] messages){
        return messagesService.markAsRead(messages);
    }
    
}
