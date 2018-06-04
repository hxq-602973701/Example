package com.java1234.dal.entity.main.sys.menu;

import com.java1234.dal.entity.base.BaseEntity;
import com.java1234.dal.utils.SequenceRule;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "s_menu")
@SequenceRule
public class Menu extends BaseEntity {
    @Id
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "menu_parent_id")
    private Long menuParentId;

    @Column(name = "page_id")
    private String pageId;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_pinyin")
    private String menuPinyin;

    @Column(name = "menu_title")
    private String menuTitle;

    @Column(name = "menu_icon")
    private String menuIcon;

    @Column(name = "menu_image")
    private String menuImage;

    @Column(name = "menu_url")
    private String menuUrl;

    @Column(name = "menu_roles")
    private Long menuRoles;

    @Column(name = "menu_memo")
    private String menuMemo;

    @Column(name = "ranking")
    private String ranking;

    @Column(name = "menu_show")
    private Boolean menuShow;

    @Column(name = "smart_link")
    private Integer smartLink;

    /**
     * 删除标志
     */
    @Column(name = "del_flag")
    private Boolean delFlag;

    /**
     * 创建人
     */
    @Column(name = "create_uid")
    private Long createUid;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改人
     */
    @Column(name = "modified_uid")
    private Long modifiedUid;

    /**
     * 修改时间
     */
    @Column(name = "modified_time")
    private Date modifiedTime;

    //=====================================================

    /**
     * 子菜单
     */
    @Transient
    private List<Menu> menuList;

    /**
     * 菜单高亮
     */
    @Transient
    private Boolean menuActive;

    /**
     * @return menu_id
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * @param menuId
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    /**
     * @return menu_parent_id
     */
    public Long getMenuParentId() {
        return menuParentId;
    }

    /**
     * @param menuParentId
     */
    public void setMenuParentId(Long menuParentId) {
        this.menuParentId = menuParentId;
    }

    /**
     * @return page_id
     */
    public String getPageId() {
        return pageId;
    }

    /**
     * @param pageId
     */
    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    /**
     * @return menu_name
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * @param menuName
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * @return menu_pinyin
     */
    public String getMenuPinyin() {
        return menuPinyin;
    }

    /**
     * @param menuPinyin
     */
    public void setMenuPinyin(String menuPinyin) {
        this.menuPinyin = menuPinyin;
    }

    /**
     * @return menu_title
     */
    public String getMenuTitle() {
        return menuTitle;
    }

    /**
     * @param menuTitle
     */
    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    /**
     * @return menu_icon
     */
    public String getMenuIcon() {
        return menuIcon;
    }

    /**
     * @param menuIcon
     */
    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    /**
     * @return menu_image
     */
    public String getMenuImage() {
        return menuImage;
    }

    /**
     * @param menuImage
     */
    public void setMenuImage(String menuImage) {
        this.menuImage = menuImage;
    }

    /**
     * @return menu_url
     */
    public String getMenuUrl() {
        return menuUrl;
    }

    /**
     * @param menuUrl
     */
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    /**
     * @return menu_roles
     */
    public Long getMenuRoles() {
        return menuRoles;
    }

    /**
     * @param menuRoles
     */
    public void setMenuRoles(Long menuRoles) {
        this.menuRoles = menuRoles;
    }

    /**
     * @return menu_memo
     */
    public String getMenuMemo() {
        return menuMemo;
    }

    /**
     * @param menuMemo
     */
    public void setMenuMemo(String menuMemo) {
        this.menuMemo = menuMemo;
    }

    /**
     * @return ranking
     */
    public String getRanking() {
        return ranking;
    }

    /**
     * @param ranking
     */
    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    /**
     * @return menu_show
     */
    public Boolean getMenuShow() {
        return menuShow;
    }

    /**
     * @param menuShow
     */
    public void setMenuShow(Boolean menuShow) {
        this.menuShow = menuShow;
    }

    /**
     * @return smart_link
     */
    public Integer getSmartLink() {
        return smartLink;
    }

    /**
     * @param smartLink
     */
    public void setSmartLink(Integer smartLink) {
        this.smartLink = smartLink;
    }

    /**
     * 获取删除标志
     *
     * @return del_flag - 删除标志
     */
    public Boolean getDelFlag() {
        return delFlag;
    }

    /**
     * 设置删除标志
     *
     * @param delFlag 删除标志
     */
    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取创建人
     *
     * @return create_uid - 创建人
     */
    public Long getCreateUid() {
        return createUid;
    }

    /**
     * 设置创建人
     *
     * @param createUid 创建人
     */
    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改人
     *
     * @return modified_uid - 修改人
     */
    public Long getModifiedUid() {
        return modifiedUid;
    }

    /**
     * 设置修改人
     *
     * @param modifiedUid 修改人
     */
    public void setModifiedUid(Long modifiedUid) {
        this.modifiedUid = modifiedUid;
    }

    /**
     * 获取修改时间
     *
     * @return modified_time - 修改时间
     */
    public Date getModifiedTime() {
        return modifiedTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifiedTime 修改时间
     */
    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public Boolean getMenuActive() {
        return menuActive;
    }

    public void setMenuActive(Boolean menuActive) {
        this.menuActive = menuActive;
    }
}