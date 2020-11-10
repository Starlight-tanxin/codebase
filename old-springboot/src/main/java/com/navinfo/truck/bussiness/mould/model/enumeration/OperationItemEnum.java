package com.navinfo.truck.bussiness.mould.model.enumeration;

/**
 * <p>操作历史枚举</p>
 *
 * @author TanXin
 * @date 2020/11/5 8:36
 */
public enum OperationItemEnum {

    ADD("新建"),

    UPDATE("编辑"),

    DELETE("删除");


    private String itemName;

    OperationItemEnum(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }
}
