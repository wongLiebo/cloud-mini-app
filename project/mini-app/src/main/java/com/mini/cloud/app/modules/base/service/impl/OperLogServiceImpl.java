package com.mini.cloud.app.modules.base.service.impl;

import com.mini.cloud.app.modules.base.entity.OperLog;
import com.mini.cloud.app.modules.base.mapper.OperLogMapper;
import com.mini.cloud.app.modules.base.service.OperLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author twang
 * @since 2020-09-07
 */
@Service
public class OperLogServiceImpl extends ServiceImpl<OperLogMapper, OperLog> implements OperLogService {

}
