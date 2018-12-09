package com.tx.annotation;

public enum RoleType {
    /**
     * 管理员
     */
    ADMIN,
    /**
     * 财务
     */
    FINANCE,
    /**
     * 不关心角色，鉴权
     */
    ALL,
    /**
     * 开放
     */
    OPEN;

    /**
     *
     * @param str 枚举值相同的字符串
     * @return
     */
    public static RoleType strGet(String str) {
        try {
            return RoleType.valueOf(str.trim().toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}
