package cn.ele.core.pojo.user;

import java.io.Serializable;
import java.util.Date;

public class RolePermissionLink extends RolePermissionLinkKey implements Serializable {
    private String description;

    private Date crtime;

    private Date uptime;

    private static final long serialVersionUID = 1L;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCrtime() {
        return crtime;
    }

    public void setCrtime(Date crtime) {
        this.crtime = crtime;
    }

    public Date getUptime() {
        return uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", description=").append(description);
        sb.append(", crtime=").append(crtime);
        sb.append(", uptime=").append(uptime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
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
        RolePermissionLink other = (RolePermissionLink) that;
        return (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()))
            && (this.getPermissionId() == null ? other.getPermissionId() == null : this.getPermissionId().equals(other.getPermissionId()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getCrtime() == null ? other.getCrtime() == null : this.getCrtime().equals(other.getCrtime()))
            && (this.getUptime() == null ? other.getUptime() == null : this.getUptime().equals(other.getUptime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        result = prime * result + ((getPermissionId() == null) ? 0 : getPermissionId().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getCrtime() == null) ? 0 : getCrtime().hashCode());
        result = prime * result + ((getUptime() == null) ? 0 : getUptime().hashCode());
        return result;
    }
}