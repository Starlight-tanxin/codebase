package com.tx.controller;

import com.tx.annotation.AuthRole;
import com.tx.annotation.RoleType;
import com.tx.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @AuthRole(role = RoleType.ALL)
    @RequestMapping(value = "/showAll",method = RequestMethod.GET)
    public Object showAll(){
        return userMapper.selectAll();
    }


    @AuthRole(role = RoleType.FINANCE)
    @RequestMapping(value = "/a",method = RequestMethod.GET)
    public Object A(){
        return "a";
    }

    @AuthRole(role = RoleType.FINANCE)
    @RequestMapping(value = "/b",method = RequestMethod.GET)
    public Object B(){
        return "b";
    }


    @AuthRole(role = RoleType.ADMIN)
    @RequestMapping(value = "/c",method = RequestMethod.GET)
    public Object C(){
        return "c";
    }

}
