package com.mini.cloud.app.modules.base.service.impl;

import com.mini.cloud.app.modules.base.entity.User;
import com.mini.cloud.app.modules.base.mapper.UserMapper;
import com.mini.cloud.app.modules.base.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author twang
 * @since 2020-09-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
