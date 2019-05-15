package com.zzl.mapper;

import com.zzl.entity.Sylogger;

public interface SyloggerMapper {
    int deleteByPrimaryKey(String syslogid);

    int insert(Sylogger record);

    int insertSelective(Sylogger record);

    Sylogger selectByPrimaryKey(String syslogid);

    int updateByPrimaryKeySelective(Sylogger record);

    int updateByPrimaryKey(Sylogger record);
}