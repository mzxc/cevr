package com.gomyck.business.model.entity.welcome;
// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * BizUser entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "biz_user")
public class BizUser implements java.io.Serializable
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7895163600457998430L;
    
    private Integer id;
    
    private String realsurname;
    
    private String realname;
    
    private String userName;
    
    private String password;
    
    private String email;
    
    private String sex;
    
    private Integer powerId;
    
    private String deleteFlag;
    
    private String cancleFlag;
    
    private String holdeFlag;
    
    private Date lastLoginTime;
    
    // Constructors
    
    /** default constructor */
    public BizUser()
    {
    }
    
    /** minimal constructor */
    public BizUser(final String userName, final String password, final String email)
    {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }
    
    /** full constructor */
    public BizUser(final String realsurname, final String realname, final String userName, final String password, final String email, final String sex, final Integer powerId, final String deleteFlag, final String cancleFlag, final String holdeFlag, final Date lastLoginTime)
    {
        this.realsurname = realsurname;
        this.realname = realname;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.sex = sex;
        this.powerId = powerId;
        this.deleteFlag = deleteFlag;
        this.cancleFlag = cancleFlag;
        this.holdeFlag = holdeFlag;
        this.lastLoginTime = lastLoginTime;
    }
    
    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId()
    {
        return this.id;
    }
    
    public void setId(final Integer id)
    {
        this.id = id;
    }
    
    @Column(name = "realsurname", length = 10)
    public String getRealsurname()
    {
        return this.realsurname;
    }
    
    public void setRealsurname(final String realsurname)
    {
        this.realsurname = realsurname;
    }
    
    @Column(name = "realname", length = 10)
    public String getRealname()
    {
        return this.realname;
    }
    
    public void setRealname(final String realname)
    {
        this.realname = realname;
    }
    
    @Column(name = "userName", nullable = false, length = 100)
    public String getUserName()
    {
        return this.userName;
    }
    
    public void setUserName(final String userName)
    {
        this.userName = userName;
    }
    
    @Column(name = "password", nullable = false, length = 50)
    public String getPassword()
    {
        return this.password;
    }
    
    public void setPassword(final String password)
    {
        this.password = password;
    }
    
    @Column(name = "email", nullable = false, length = 100)
    public String getEmail()
    {
        return this.email;
    }
    
    public void setEmail(final String email)
    {
        this.email = email;
    }
    
    @Column(name = "sex", length = 10)
    public String getSex()
    {
        return this.sex;
    }
    
    public void setSex(final String sex)
    {
        this.sex = sex;
    }
    
    @Column(name = "powerId")
    public Integer getPowerId()
    {
        return this.powerId;
    }
    
    public void setPowerId(final Integer powerId)
    {
        this.powerId = powerId;
    }
    
    @Column(name = "deleteFlag", length = 10)
    public String getDeleteFlag()
    {
        return this.deleteFlag;
    }
    
    public void setDeleteFlag(final String deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }
    
    @Column(name = "cancleFlag", length = 10)
    public String getCancleFlag()
    {
        return this.cancleFlag;
    }
    
    public void setCancleFlag(final String cancleFlag)
    {
        this.cancleFlag = cancleFlag;
    }
    
    @Column(name = "holdeFlag", length = 10)
    public String getHoldeFlag()
    {
        return this.holdeFlag;
    }
    
    public void setHoldeFlag(final String holdeFlag)
    {
        this.holdeFlag = holdeFlag;
    }
    
    @Column(name = "lastLoginTime", length = 19)
    public Date getLastLoginTime()
    {
        return this.lastLoginTime;
    }
    
    public void setLastLoginTime(final Date lastLoginTime)
    {
        this.lastLoginTime = lastLoginTime;
    }
    
}