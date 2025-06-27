package com.eetrust.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

/**
 *
 * @TableName t_sync_data_incre
 */
public class TSyncDataIncre {
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

    /**
     * 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 1 组织 2 用户
     */
    public Integer getType() {
        return type;
    }

    /**
     * 1 组织 2 用户
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 组织/用户唯一标识
     */
    public String getUniqueField() {
        return uniqueField;
    }

    /**
     * 组织/用户唯一标识
     */
    public void setUniqueField(String uniqueField) {
        this.uniqueField = uniqueField;
    }

    /**
     * 父级唯一标识
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * 父级唯一标识
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    /**
     * 组织/用户名称
     */
    public String getName() {
        return name;
    }

    /**
     * 组织/用户名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 同步内容 xml
     */
    public String getContent() {
        return content;
    }

    /**
     * 同步内容 xml
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 0 待同步 1 同步失败 -1 同步停止
     */
    public Integer getState() {
        return state;
    }

    /**
     * 0 待同步 1 同步失败 -1 同步停止
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 同步返回详细
     */
    public String getResult() {
        return result;
    }

    /**
     * 同步返回详细
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * 失败次数
     */
    public Integer getErrorCount() {
        return errorCount;
    }

    /**
     * 失败次数
     */
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
        TSyncDataIncre other = (TSyncDataIncre) that;
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