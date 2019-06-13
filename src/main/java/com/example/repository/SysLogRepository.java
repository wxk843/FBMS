package com.example.repository;

import com.example.entity.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author deray.wang
 * @date 2019/5/7 16:55
 */
public interface SysLogRepository extends JpaRepository<SysLog,Long> {

}
