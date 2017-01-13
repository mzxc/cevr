package com.gomyck.business.model.entity.welcome;

// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * BizActivate entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "biz_activate")
public class BizActivate implements java.io.Serializable
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7341908038244417467L;
    
    private Integer id;
    
    private Integer userId;
    
    private String validateCode;
    
    private String cancleFlag;
    
    private Date singTime;
    
    // Constructors
    
    /** default constructor */
    public BizActivate()
    {
    }
    
    /** minimal constructor */
    public BizActivate(final Integer id, final Integer userId)
    {
        this.id = id;
        this.userId = userId;
    }
    
    /** full constructor */
    public BizActivate(final Integer id, final Integer userId, final String validateCode, final String cancleFlag, final Date singTime)
    {
        this.id = id;
        this.userId = userId;
        this.validateCode = validateCode;
        this.cancleFlag = cancleFlag;
        this.singTime = singTime;
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
    
    @Column(name = "userId", nullable = false)
    public Integer getUserId()
    {
        return this.userId;
    }
    
    public void setUserId(final Integer userId)
    {
        this.userId = userId;
    }
    
    @Column(name = "validateCode", length = 50)
    public String getValidateCode()
    {
        return this.validateCode;
    }
    
    public void setValidateCode(final String validateCode)
    {
        this.validateCode = validateCode;
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
    
    @Column(name = "singTime", length = 19)
    public Date getSingTime()
    {
        return this.singTime;
    }
    
    public void setSingTime(final Date singTime)
    {
        this.singTime = singTime;
    }
    
}