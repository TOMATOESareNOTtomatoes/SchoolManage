//package com.fanqie.utils;
//
//@Data
//@TableName(value = "cc_admin")
//public class Admin implements Serializable {
//    /**
//     * 管理员表id
//     */
//    @TableId(value = "id",type = IdType.AUTO)
//    private Integer id;
//
//    /**
//     * 管理员id
//     */
//    private String adminId;
//
//    /**
//     * 管理员用户名
//     */
//    private String adminUsername;
//
//    /**
//     * 管理员密码
//     */
//    private String adminPassword;
//
//    /**
//     * 管理员真实姓名
//     */
//    private String adminRealname;
//
//    /**
//     * 管理员密钥
//     */
//    private String salt;
//
//    /**
//     * 角色id
//     */
//    private String roleId;
//
//    /**
//     * 部门id
//     */
//    private String deptId;
//
//    /**
//     * 管理员性别
//     */
//    private String adminSex;
//
//    /**
//     * 管理员联系方式
//     */
//    private String adminTelephone;
//
//    /**
//     * 管理员年龄
//     */
//    private Integer adminAge;
//
//    /**
//     * 逻辑删除0（false）未删除，1（true）已删除
//     */
//    private Boolean isDeleted;
//
//    /**
//     * 创建时间
//     */
//    private Date gmtCreate;
//
//    /**
//     * 修改时间
//     */
//    private Date gmtModified;
//
//    private static final long serialVersionUID = 1L;
//
//    @Override
//    public boolean equals(Object that) {
//        if (this == that) {
//            return true;
//        }
//        if (that == null) {
//            return false;
//        }
//        if (getClass() != that.getClass()) {
//            return false;
//        }
//        Admin other = (Admin) that;
//        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
//            && (this.getAdminId() == null ? other.getAdminId() == null : this.getAdminId().equals(other.getAdminId()))
//            && (this.getAdminUsername() == null ? other.getAdminUsername() == null : this.getAdminUsername().equals(other.getAdminUsername()))
//            && (this.getAdminPassword() == null ? other.getAdminPassword() == null : this.getAdminPassword().equals(other.getAdminPassword()))
//            && (this.getAdminRealname() == null ? other.getAdminRealname() == null : this.getAdminRealname().equals(other.getAdminRealname()))
//            && (this.getSalt() == null ? other.getSalt() == null : this.getSalt().equals(other.getSalt()))
//            && (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()))
//            && (this.getDeptId() == null ? other.getDeptId() == null : this.getDeptId().equals(other.getDeptId()))
//            && (this.getAdminSex() == null ? other.getAdminSex() == null : this.getAdminSex().equals(other.getAdminSex()))
//            && (this.getAdminTelephone() == null ? other.getAdminTelephone() == null : this.getAdminTelephone().equals(other.getAdminTelephone()))
//            && (this.getAdminAge() == null ? other.getAdminAge() == null : this.getAdminAge().equals(other.getAdminAge()))
//            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
//            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
//            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()));
//    }
//
//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
//        result = prime * result + ((getAdminId() == null) ? 0 : getAdminId().hashCode());
//        result = prime * result + ((getAdminUsername() == null) ? 0 : getAdminUsername().hashCode());
//        result = prime * result + ((getAdminPassword() == null) ? 0 : getAdminPassword().hashCode());
//        result = prime * result + ((getAdminRealname() == null) ? 0 : getAdminRealname().hashCode());
//        result = prime * result + ((getSalt() == null) ? 0 : getSalt().hashCode());
//        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
//        result = prime * result + ((getDeptId() == null) ? 0 : getDeptId().hashCode());
//        result = prime * result + ((getAdminSex() == null) ? 0 : getAdminSex().hashCode());
//        result = prime * result + ((getAdminTelephone() == null) ? 0 : getAdminTelephone().hashCode());
//        result = prime * result + ((getAdminAge() == null) ? 0 : getAdminAge().hashCode());
//        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
//        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
//        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
//        return result;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(getClass().getSimpleName());
//        sb.append(" [");
//        sb.append("Hash = ").append(hashCode());
//        sb.append(", id=").append(id);
//        sb.append(", adminId=").append(adminId);
//        sb.append(", adminUsername=").append(adminUsername);
//        sb.append(", adminPassword=").append(adminPassword);
//        sb.append(", adminRealname=").append(adminRealname);
//        sb.append(", salt=").append(salt);
//        sb.append(", roleId=").append(roleId);
//        sb.append(", deptId=").append(deptId);
//        sb.append(", adminSex=").append(adminSex);
//        sb.append(", adminTelephone=").append(adminTelephone);
//        sb.append(", adminAge=").append(adminAge);
//        sb.append(", isDeleted=").append(isDeleted);
//        sb.append(", gmtCreate=").append(gmtCreate);
//        sb.append(", gmtModified=").append(gmtModified);
//        sb.append(", serialVersionUID=").append(serialVersionUID);
//        sb.append("]");
//        return sb.toString();
//    }
//}
//