package com.mini.cloud.app.modules.base.service.impl;

import com.mini.cloud.app.modules.base.entity.CarInfo;
import com.mini.cloud.app.modules.base.mapper.CarInfoMapper;
import com.mini.cloud.app.modules.base.service.CarInfoService;
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
public class CarInfoServiceImpl extends ServiceImpl<CarInfoMapper, CarInfo> implements CarInfoService {

}
