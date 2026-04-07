package com.eetrust.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

/**
 * 同步数据增量备份表
 * @TableName t_sync_data_incre_bak
 */
public class TSyncDataIncreBak {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 1 组织 2 用户
     */
    private Integer type;

    /**
     * 组织/用户唯一标识
     */
    private String uniqueField;

    /**
     * 父级唯一标识
     */
    private String parentCode;

    /**
     * 组织/用户名称
     */
    private String name;

    /**
     * 同步内容 xml
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 0 待同步 1 同步失败 -1 同步停止
     */
    private Integer state;

    /**
     * 同步返回详细
     */
    private String result;

    /**
     * 失败次数
     */
    private Integer errorCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUniqueField() {
        return uniqueField;
    }

    public void setUniqueField(String uniqueField) {
        this.uniqueField = uniqueField;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TSyncDataIncreBak other = (TSyncDataIncreBak) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
                && (this.getUniqueField() == null ? other.getUniqueField() == null : this.getUniqueField().equals(other.getUniqueField()))
                && (this.getParentCode() == null ? other.getParentCode() == null : this.getParentCode().equals(other.getParentCode()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
                && (this.getResult() == null ? other.getResult() == null : this.getResult().equals(other.getResult()))
                && (this.getErrorCount() == null ? other.getErrorCount() == null : this.getErrorCount().equals(other.getErrorCount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getUniqueField() == null) ? 0 : getUniqueField().hashCode());
        result = prime * result + ((getParentCode() == null) ? 0 : getParentCode().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getResult() == null) ? 0 : getResult().hashCode());
        result = prime * result + ((getErrorCount() == null) ? 0 : getErrorCount().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", uniqueField=").append(uniqueField);
        sb.append(", parentCode=").append(parentCode);
        sb.append(", name=").append(name);
        sb.append(", content=").append(content);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", state=").append(state);
        sb.append(", result=").append(result);
        sb.append(", errorCount=").append(errorCount);
        sb.append("]");
        return sb.toString();
    }
}
