package com.chen.server.service.impl;

import com.chen.server.pojo.Oplog;
import com.chen.server.mapper.OplogMapper;
import com.chen.server.service.OplogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author CHEN
 * @since 2022-09-29
 */
@Service
public class OplogServiceImpl extends ServiceImpl<OplogMapper, Oplog> implements OplogService {

}
