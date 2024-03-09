package com.example.school_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.school_system.commom.R;
import com.example.school_system.entity.Messages;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

public interface MessagesService extends IService<Messages> {
    R addMsg(int sid);

    R getAllNot(HttpServletRequest request);

    R markAsRead(Messages[] messages);
}
