package com.shx.law.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by xuan on 2017/7/2.
 */
@Entity
public class LawItem {
    @Id(autoincrement = true)
    private Long id;
    //名称
    private String law_name;
    private String issue_no;
    //专业
    private String major;
    //类型名称
    private String type_name;
    //类型编码
    private String type_code;
    //文件访问路经
    private String file_path;
    //状态（-1为无效 0为有效）
    private Integer status;
    private Date create_time;
    private String update_time;
    //摘要
    private String description;

    @Generated(hash = 1583732423)
    public LawItem(Long id, String law_name, String issue_no, String major,
            String type_name, String type_code, String file_path, Integer status,
            Date create_time, String update_time, String description) {
        this.id = id;
        this.law_name = law_name;
        this.issue_no = issue_no;
        this.major = major;
        this.type_name = type_name;
        this.type_code = type_code;
        this.file_path = file_path;
        this.status = status;
        this.create_time = create_time;
        this.update_time = update_time;
        this.description = description;
    }

    @Generated(hash = 801481384)
    public LawItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLaw_name() {
        return law_name;
    }

    public void setLaw_name(String law_name) {
        this.law_name = law_name;
    }

    public String getIssue_no() {
        return issue_no;
    }

    public void setIssue_no(String issue_no) {
        this.issue_no = issue_no;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getType_code() {
        return type_code;
    }

    public void setType_code(String type_code) {
        this.type_code = type_code;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
