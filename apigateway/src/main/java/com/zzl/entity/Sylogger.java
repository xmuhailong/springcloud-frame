package com.zzl.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Sylogger {
    private String syslogid;

    private String sysreqip;

    private String sysrequrl;

    private String syssvrnam;

    private String sysmtdnam;

    private String sysreqpam;

    private Date sysreqtim;

    private String sysrsprst;

    private String sysfalcod;

    private Date sysrsptim;

    public String getSyslogid() {
        return syslogid;
    }

    public void setSyslogid(String syslogid) {
        this.syslogid = syslogid == null ? null : syslogid.trim();
    }

    public String getSysreqip() {
        return sysreqip;
    }

    public void setSysreqip(String sysreqip) {
        this.sysreqip = sysreqip == null ? null : sysreqip.trim();
    }

    public String getSysrequrl() {
        return sysrequrl;
    }

    public void setSysrequrl(String sysrequrl) {
        this.sysrequrl = sysrequrl == null ? null : sysrequrl.trim();
    }

    public String getSyssvrnam() {
        return syssvrnam;
    }

    public void setSyssvrnam(String syssvrnam) {
        this.syssvrnam = syssvrnam == null ? null : syssvrnam.trim();
    }

    public String getSysmtdnam() {
        return sysmtdnam;
    }

    public void setSysmtdnam(String sysmtdnam) {
        this.sysmtdnam = sysmtdnam == null ? null : sysmtdnam.trim();
    }

    public String getSysreqpam() {
        return sysreqpam;
    }

    public void setSysreqpam(String sysreqpam) {
        this.sysreqpam = sysreqpam == null ? null : sysreqpam.trim();
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getSysreqtim() {
        return sysreqtim;
    }

    public void setSysreqtim(Date sysreqtim) {
        this.sysreqtim = sysreqtim;
    }

    public String getSysrsprst() {
        return sysrsprst;
    }

    public void setSysrsprst(String sysrsprst) {
        this.sysrsprst = sysrsprst == null ? null : sysrsprst.trim();
    }

    public String getSysfalcod() {
        return sysfalcod;
    }

    public void setSysfalcod(String sysfalcod) {
        this.sysfalcod = sysfalcod == null ? null : sysfalcod.trim();
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getSysrsptim() {
        return sysrsptim;
    }

    public void setSysrsptim(Date sysrsptim) {
        this.sysrsptim = sysrsptim;
    }
}