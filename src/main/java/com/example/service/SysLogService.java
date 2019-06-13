package com.example.service;

import com.example.entity.SysLog;
import com.example.repository.SysLogRepository;
import com.example.util.TimeUtil;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author deray.wang
 * @date 2019/5/7 16:41
 */
@Service
public class SysLogService {
    @Autowired
    SysLogRepository sysLogRepository;

    public SysLog saveLog(SysLog sysLog) {
        Preconditions.checkNotNull(sysLog, "Object is null");
        Long rid = sysLog.getId();
        if ((null == rid || rid == 0) && sysLog.getCreateTime() == null) {
            sysLog.setCreateTime(TimeUtil.nowTimestamp());
        }

        SysLog log = sysLogRepository.save(sysLog);

        return log;
    }

    public Page<SysLog> getPageList(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
        //PageHelper.offsetPage(rule.getOffset(), rule.getLimit(), CamelCaseUtil.toUnderlineName(rule.getSort())+" "+rule.getOrder());
        return sysLogRepository.findAll(pageable);
    }

    /*
     * 删除日志
     */
    public void delete(Long id) {
        sysLogRepository.delete(id);
    }
}
