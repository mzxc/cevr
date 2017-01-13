package com.gomyck.component.core.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;

import com.gomyck.component.util.PageUtil;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 
 * 通用持久层工具类
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-17]
 * @see #BaseDao
 * @since 2.1
 */
@SuppressWarnings("rawtypes")
public class BaseDao
{
    @Autowired
    @Qualifier("sqlClient")
    public SqlMapClient sqlSession;
    
    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;
    
    private SessionFactory getSessionFactory()
    {
        return this.sessionFactory;
    }
    
    public Session getSession()
    {
        return this.sessionFactory.getCurrentSession();
    }
    
    public void save(final Object entity)
    {
        getSession().save(entity);
    }
    
    public void saveOrUpdate(final Object entity)
    {
        getSession().saveOrUpdate(entity);
    }
    
    public void delete(final Object entity)
    {
        getSession().delete(entity);
    }
    
    public void update(final Object entity)
    {
        getSession().update(entity);
    }
    
    public List findAll(final Class clazz)
    {
        return getSession().createCriteria(clazz).list();
        
    }
    
    /**
     * 
     * 执行sql语句更新
     * 
     * @param clazz 表对应实体类型
     * @param map 字段_更新值
     * @param sqlWhere 动态拼接的sql语句
     * @return 更新条数
     * @see [类、类#方法、类#成员]
     */
    public int update(final Class clazz, final Map<String, String> map, final String sqlWhere)
    {
        final StringBuffer sql = new StringBuffer();
        sql.append(" UPDATE ").append(getTableName(clazz)).append(" SET ");
        final Set<String> keySet = map.keySet();
        final Iterator<String> it = keySet.iterator();
        while (it.hasNext())
        {
            final String column = it.next();
            sql.append(column).append(" = '").append(map.get(column) + "'");
            if (it.hasNext())
            {
                sql.append(",");
            }
        }
        sql.append(" WHERE 1=1 ");
        sql.append(sqlWhere);
        return executeNativeSql(sql.toString());
    }
    
    /**
     * 
     * 根据ID删除记录(字段名必须是ID)
     * 
     * @param clazz 表对应实体类型
     * @param bills 格式为: '1','2','3'
     * @return 删除的条数
     * @see [类、类#方法、类#成员]
     */
    public int deleteByIds(final Class clazz, final String bills)
    {
        final StringBuffer sql = new StringBuffer();
        sql.append(" DELETE FROM ").append(getTableName(clazz)).append(" WHERE ");
        sql.append(" ID IN (").append(bills).append(") ");
        return executeNativeSql(sql.toString());
    }
    
    /**
     * 
     * 根据主键删除记录
     * 
     * @param clazz 表对应实体类型
     * @param id 主键值
     * @return 删除的条数
     * @see [类、类#方法、类#成员]
     */
    public int deleteByPrimaryKey(final Class clazz, final Serializable id)
    {
        return deleteByProperty(clazz, getSessionFactory().getClassMetadata(clazz).getIdentifierPropertyName(), id.toString());
    }
    
    /**
     * 
     * 根据字段名来删除
     * 
     * @param clazz 表对应实体类型
     * @param propertyName 字段名
     * @param propertyValue 字段名对应的值
     * @return 删除的条数
     * @see [类、类#方法、类#成员]
     */
    public int deleteByProperty(final Class clazz, final String propertyName, final String propertyValue)
    {
        final String sql = " DELETE FROM " + getTableName(clazz) + " WHERE " + propertyName + " = '" + propertyValue + "'";
        return executeNativeSql(sql);
    }
    
    /**
     * 
     * 根据字段名来删除
     * 
     * @param clazz 表对应实体类型
     * @param propertys 字段集合 key为字段名 value为字段对应的值 删除条件为AND
     * @return 删除条数
     * @see [类、类#方法、类#成员]
     */
    public int deleteByPropertys(final Class clazz, final Map<String, Object> propertys)
    {
        if (propertys.size() < 1)
        {
            return -1;
        }
        final StringBuffer sql = new StringBuffer();
        sql.append(" DELETE FROM ").append(getTableName(clazz)).append(" WHERE 1=1");
        for (final String propertyName : propertys.keySet())
        {
            sql.append(" AND ").append(propertyName).append(" = ");
            if (propertys.get(propertyName) instanceof String)
            {
                sql.append("'").append(propertys.get(propertyName)).append("'");
            }
            else
            {
                sql.append(propertys.get(propertyName));
            }
        }
        return executeNativeSql(sql.toString());
    }
    
    /**
     * 
     * 根据字段名来删除
     * 
     * @param clazz 表对应实体类型
     * @param propertys 字段集合 key为字段名 value为字段对应的值 删除条件为AND
     * @param sqlWhere 拼接的sql语句
     * @return 删除得到条数
     * @see [类、类#方法、类#成员]
     */
    public int deleteByPropertysAndSqlStr(final Class clazz, final Map<String, Object> propertys, final String sqlWhere)
    {
        if (propertys.size() < 1)
        {
            return -1;
        }
        final StringBuffer sql = new StringBuffer();
        sql.append(" DELETE FROM ").append(getTableName(clazz)).append(" WHERE 1=1");
        for (final String propertyName : propertys.keySet())
        {
            sql.append(" AND ").append(propertyName).append(" = ");
            if (propertys.get(propertyName) instanceof String)
            {
                sql.append("'").append(propertys.get(propertyName)).append("'");
            }
            else
            {
                sql.append(propertys.get(propertyName));
            }
        }
        sql.append(" " + sqlWhere);
        return executeNativeSql(sql.toString());
    }
    
    /**
     * 
     * 查询_Criteria
     * 
     * @param clazz 表对应实体类型
     * @param criteria DetachedCriteria类型的条件
     * @return 查询结果LIST
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public List findByCriteria(final Class clazz, final DetachedCriteria dcriteria)
    {
        final Criteria exeCriteria = dcriteria.getExecutableCriteria(getSession());
        final Cache cache = (Cache)clazz.getAnnotation(Cache.class);
        if (cache != null)
        {
            exeCriteria.setCacheable(true);
            exeCriteria.setCacheRegion(cache.region());
        }
        return exeCriteria.list();
    }
    
    /**
     * 
     * 查询_Criteria
     * 
     * @param clazz 表对应实体类型
     * @param criteria DetachedCriteria类型的条件
     * @param begin 起始行游标
     * @param rows 查询体量
     * @return 查询结果LIST
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public List findByCriteria(final Class clazz, final DetachedCriteria dcriteria, final int begin, final int rows)
    {
        final Criteria exeCriteria = dcriteria.getExecutableCriteria(getSession());
        exeCriteria.setFirstResult(begin);
        exeCriteria.setMaxResults(rows);
        final Cache cache = (Cache)clazz.getAnnotation(Cache.class);
        if (cache != null)
        {
            exeCriteria.setCacheable(true);
            exeCriteria.setCacheRegion(cache.region());
        }
        return exeCriteria.list();
    }
    
    /**
     * 
     * 根据主键查询
     * 
     * @param clazz 表对应实体类型
     * @param id 主键值
     * @return 返回的结果
     * @see [类、类#方法、类#成员]
     */
    public Object findByPrimaryKey(final Class clazz, final Serializable id)
    {
        return getSession().get(clazz, id);
    }
    
    /**
     * 
     * 根据字段查询结果
     * 
     * @param clazz 表对应实体类型
     * @param propertyName 字段名
     * @param propertyValue 对应的值
     * @return 查询结果LIST
     * @see [类、类#方法、类#成员]
     */
    public List findByProperty(final Class clazz, final String propertyName, final Object propertyValue)
    {
        return findByCriteria(clazz, DetachedCriteria.forClass(clazz).add(Restrictions.eq(propertyName, propertyValue)));
    }
    
    /**
     * 
     * 查询_criteria
     * 
     * @param clazz 表对应实体类型
     * @param criteria DetachedCriteria查询条件
     * @param pageProxy 分页信息
     * @return 查询结果LIST
     * @see [类、类#方法、类#成员]
     */
    public PageUtil findByCriteria(final Class clazz, final DetachedCriteria criteria, final PageUtil pageUtil)
    {
        final int limit = pageUtil.getPageSize();
        pageUtil.setTotalCount(findCountByCriteria(clazz, criteria).intValue());
        final int beginRowNum = PageUtil.getStartOfPage(pageUtil.getPageIndex(), pageUtil.getPageSize());
        final List list = findByCriteria(clazz, criteria, beginRowNum, limit);
        pageUtil.setRecords(list);
        return pageUtil;
    }
    
    /**
     * 
     * 模版查询
     * 
     * @param clazz 表对应实体类型
     * @param entity 模版实体
     * @param from 起始游标
     * @param limit 行数
     * @return 查询结果LIST
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public List findByExample(final Class clazz, final Class entity, final int from, final int limit)
    {
        final Criteria criteria = getSession().createCriteria(clazz).add(Example.create(entity));
        criteria.setFirstResult(from);
        criteria.setFetchSize(limit);
        final Cache cache = (Cache)clazz.getAnnotation(Cache.class);
        if (cache != null)
        {
            criteria.setCacheable(true);
            criteria.setCacheRegion(cache.region());
        }
        return criteria.list();
    }
    
    /**
     * 
     * 根据字段查询结果
     * 
     * @param clazz 表对应实体类型
     * @param properties 字段集合key为字段名 value为字段值
     * @return 查询结果LIST
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public List findByProperties(final Class clazz, final Map<String, Object> properties)
    {
        final Criteria criteria = getSession().createCriteria(clazz);
        for (final String propName : properties.keySet())
        {
            criteria.add(Restrictions.eq(propName, properties.get(propName)));
        }
        final Cache cache = (Cache)clazz.getAnnotation(Cache.class);
        if (cache != null)
        {
            criteria.setCacheable(true);
            criteria.setCacheRegion(cache.region());
        }
        return criteria.list();
    }
    
    /**
     * 
     * 根据字段查询结果
     * 
     * @param clazz 表对应实体类型
     * @param properties 字段集合key为字段名 value为字段值
     * @param pageProxy 分页信息
     * @return 查询结果LIST
     * @see [类、类#方法、类#成员]
     */
    public PageUtil findByProperties(final Class clazz, final Map<String, Object> properties, final PageUtil pageUtil)
    {
        final DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        for (final String propName : properties.keySet())
        {
            criteria.add(Restrictions.eq(propName, properties.get(propName)));
        }
        findCountByCriteria(clazz, criteria);
        return findByCriteria(clazz, criteria, pageUtil);
    }
    
    /**
     * 
     * 查询所有
     * 
     * @param clazz 表对应实体类型
     * @param pageProxy 分页信息
     * @return PageUtil
     * @see [类、类#方法、类#成员]
     */
    public PageUtil findAll(final Class clazz, final PageUtil pageUtil)
    {
        return findByCriteria(clazz, DetachedCriteria.forClass(clazz), pageUtil);
    }
    
    /**
     * 
     * 查询总计录数
     * 
     * @param clazz 表对应实体类型
     * @return 记录数
     * @see [类、类#方法、类#成员]
     */
    public Long findAllCount(final Class clazz)
    {
        return findCountByCriteria(clazz, DetachedCriteria.forClass(clazz));
    }
    
    /**
     * 
     * 根据sql语句查询
     * 
     * @param clazz 表对应实体类型
     * @param toClass 结果转型的class
     * @param sql sql语句
     * @param columns 字段类型 key为字段名,type为转型类型
     * @return 查询结果LIST
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public List findByNativeSQL(final Class clazz, final Class toClass, final String sql, final Map<String, Type> columns)
    {
        final SQLQuery query = getSession().createSQLQuery(sql);
        final Cache cache = (Cache)clazz.getAnnotation(Cache.class);
        if (cache != null)
        {
            query.setCacheable(true);
            query.setCacheRegion(cache.region());
        }
        final Set<String> keys = columns.keySet();
        for (final String name : keys)
        {
            query.addScalar(name, columns.get(name));
        }
        if (toClass != null)
        {
            query.setResultTransformer(new AliasToBeanResultTransformer(toClass));
        }
        return query.list();
    }
    
    /**
     * 
     * 根据条件查询条数,之后设置projection为null方便后续加入排序或者连接查询
     * 
     * @param clazz 表对应实体类型
     * @param criteria DetachedCriteria查询条件
     * @return 总条数
     * @see [类、类#方法、类#成员]
     */
    public Long findCountByCriteria(final Class clazz, final DetachedCriteria criteria)
    {
        final Long count = (Long)findByCriteria(clazz, criteria.setProjection(Projections.rowCount())).get(0);
        criteria.setProjection(null);
        criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        return count;
    }
    
    /**
     * 
     * 模版查询
     * 
     * @param clazz 表对应实体类型
     * @param entity 实体
     * @return 记录数
     * @see [类、类#方法、类#成员]
     */
    public Long findCountByExample(final Class clazz, final Class entity)
    {
        return (Long)findByCriteria(clazz, DetachedCriteria.forClass(clazz).add(Example.create(entity)).setProjection(Projections.rowCount())).get(0);
    }
    
    /**
     * 本方法只适用于有关系映射的实体间 连接
     * 
     * @param clazz 表对应实体类型
     * @param foreignProp 关联表的属性 参照entity类中的属性
     * @param joinType 连接方式,具体值通过Criteria接口中的常量获得
     * @param selfCriterions 本表条件
     * @param otherCriterions 连表条件
     * @return 查询结果LIST
     * @see [类、类#方法、类#成员]
     */
    public List findListJoinOtherByCriteria(final Class clazz, final String foreignProp, final String alias, final JoinType joinType, final List<Criterion> selfCriterions, final List<Criterion> otherCriterions)
    {
        final DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        if (selfCriterions != null)
        {
            for (final Criterion criterion : selfCriterions)
            {
                criteria.add(criterion);
            }
        }
        final DetachedCriteria criteria2 = criteria.createCriteria(foreignProp, alias, joinType);
        if (otherCriterions != null)
        {
            for (final Criterion criterion : otherCriterions)
            {
                criteria2.add(criterion);
            }
        }
        return findByCriteria(clazz, criteria2);
    }
    
    /**
     * 
     * 根据投影查询 projection 可指定查询列 ,统计,去重复
     * 
     * @param clazz 表对应实体类型
     * @param criteria DetachedCriteria查询条件
     * @param projection 投影
     * @param pageProxy 分页信息
     * @return 查询结果PageUtil
     * @see [类、类#方法、类#成员]
     */
    public PageUtil findByCriteriaProjectionsPage(final Class clazz, final DetachedCriteria criteria, final Projection projection, final PageUtil pageUtil)
    {
        final int maxResults = pageUtil.getPageSize();
        pageUtil.setTotalCount(findCountByCriteria(clazz, criteria).intValue());
        criteria.setProjection(projection);
        criteria.setResultTransformer(Transformers.aliasToBean(clazz));
        final int beginRowNum = PageUtil.getStartOfPage(pageUtil.getPageIndex(), pageUtil.getPageSize());
        pageUtil.setRecords(findByCriteria(clazz, criteria, beginRowNum, maxResults));
        return pageUtil;
    }
    
    /**
     * 
     * 查询指定列映射到实体
     * 
     * @param clazz 表对应实体类型
     * @param criteria DetachedCriteria查询条件
     * @param columnsAndAlias 装配字段名称,如果数据库字段与实体类属性名不一样则需要投影
     * @return 查询结果LIST
     * @see [类、类#方法、类#成员]
     */
    public List findSomeColumn(final Class clazz, final DetachedCriteria criteria, final Map<String, String> columnsAndAlias)
    {
        final ProjectionList projectionList = Projections.projectionList();
        for (final String key : columnsAndAlias.keySet())
        {
            projectionList.add(Projections.property(key).as(columnsAndAlias.get(key)));
        }
        criteria.setProjection(projectionList);
        criteria.setResultTransformer(Transformers.aliasToBean(clazz));
        return findByCriteria(clazz, criteria);
    }
    
    /**
     * 
     * 查询指定列映射到实体
     * 
     * @param clazz 表对应实体类型
     * @param criteria DetachedCriteria查询条件
     * @param columnsAndAlias 装配字段名称,如果数据库字段与实体类属性名不一样则需要投影
     * @param pageProxy 分页信息
     * @return 查询结果PageUtil
     * @see [类、类#方法、类#成员]
     */
    public PageUtil findSomeColumn(final Class clazz, final DetachedCriteria criteria, final Map<String, String> columnsAndAlias, final PageUtil pageUtil)
    {
        final ProjectionList projectionList = Projections.projectionList();
        for (final String key : columnsAndAlias.keySet())
        {
            projectionList.add(Projections.property(key).as(columnsAndAlias.get(key)));
        }
        final int maxResults = pageUtil.getPageSize();
        pageUtil.setTotalCount(findCountByCriteria(clazz, criteria).intValue());
        criteria.setProjection(projectionList);
        criteria.setResultTransformer(Transformers.aliasToBean(clazz));
        final int beginRowNum = PageUtil.getStartOfPage(pageUtil.getPageIndex(), pageUtil.getPageSize());
        pageUtil.setRecords(findByCriteria(clazz, criteria, beginRowNum, maxResults));
        return pageUtil;
    }
    
    /**
     * 
     * 查询指定列映射到实体
     * 
     * @param clazz 表对应实体类型
     * @param criteria DetachedCriteria查询条件
     * @param columnsAndAlias 装配字段名必须与实体属性名相同
     * @return 查询结果PageUtil
     * @see [类、类#方法、类#成员]
     */
    public List findSomeColumn(final Class clazz, final DetachedCriteria criteria, final String[] columnsAndAlias)
    {
        final ProjectionList projectionList = Projections.projectionList();
        for (final String string : columnsAndAlias)
        {
            projectionList.add(Projections.property(string).as(string));
        }
        criteria.setProjection(projectionList);
        criteria.setResultTransformer(Transformers.aliasToBean(clazz));
        return findByCriteria(clazz, criteria);
    }
    
    /**
     * 
     * 查询指定列映射到实体
     * 
     * @param clazz 表对应实体类型
     * @param criteria DetachedCriteria查询条件
     * @param columnsAndAlias 装配字段名必须与实体属性名相同
     * @param pageProxy 分页信息
     * @return 查询结果PageUtil
     * @see [类、类#方法、类#成员]
     */
    public PageUtil findSomeColumn(final Class clazz, final DetachedCriteria criteria, final String[] columnsAndAlias, final PageUtil pageUtil)
    {
        final ProjectionList projectionList = Projections.projectionList();
        for (final String string : columnsAndAlias)
        {
            projectionList.add(Projections.property(string).as(string));
        }
        final int maxResults = pageUtil.getPageSize();
        pageUtil.setTotalCount(findCountByCriteria(clazz, criteria).intValue());
        criteria.setProjection(projectionList);
        criteria.setResultTransformer(Transformers.aliasToBean(clazz));
        final int beginRowNum = PageUtil.getStartOfPage(pageUtil.getPageIndex(), pageUtil.getPageSize());
        pageUtil.setRecords(findByCriteria(clazz, criteria, beginRowNum, maxResults));
        return pageUtil;
    }
    
    /**
     * 
     * 查询指定列映射到实体
     * 
     * @param clazz 表对应实体类型
     * @param criteria DetachedCriteria查询条件
     * @param columnsAndAlias 装配字段名必须与实体属性名相同
     * @param pageProxy 分页信息
     * @param order 排序
     * @return 查询结果LIST
     * @see [类、类#方法、类#成员]
     */
    public List findSomeColumn(final Class clazz, final DetachedCriteria dcriteria, final String[] columnsAndAlias, final PageUtil pageUtil, final Order order)
    {
        final ProjectionList projectionList = Projections.projectionList();
        for (final String string : columnsAndAlias)
        {
            projectionList.add(Projections.property(string).as(string));
        }
        final int maxResults = pageUtil.getPageSize();
        pageUtil.setTotalCount(findCountByCriteria(clazz, dcriteria).intValue());
        dcriteria.addOrder(order);
        dcriteria.setProjection(projectionList);
        dcriteria.setResultTransformer(Transformers.aliasToBean(clazz));
        final int beginRowNum = PageUtil.getStartOfPage(pageUtil.getPageIndex(), pageUtil.getPageSize());
        return findByCriteria(clazz, dcriteria, beginRowNum, maxResults);
    }
    
    /**
     * 
     * 执行sql语句
     * 
     * @param sql sql语句
     * @return 执行条数
     * @see [类、类#方法、类#成员]
     */
    public Integer executeNativeSql(final String sql)
    {
        return getSession().createSQLQuery(sql).executeUpdate();
    }
    
    /**
     * 
     * 创建Hql预处理语句
     * 
     * @param hql hql
     * @param values 传入的参数
     * @return Query对象
     * @see [类、类#方法、类#成员]
     */
    public Query createQuery(final String hql, final Object... values)
    {
        Assert.hasText(hql);
        final Query query = getSession().createQuery(hql);
        for (int i = 0; i < values.length; i++)
        {
            query.setParameter(i, values[i]);
        }
        return query;
    }
    
    /**
     * 
     * 创建sql预处理语句
     * 
     * @param sql sql
     * @param values 传入的参数
     * @return Query对象
     * @see [类、类#方法、类#成员]
     */
    public Query createSqlQuery(final String sql, final Object... values)
    {
        Assert.hasText(sql);
        final Query query = getSession().createSQLQuery(sql);
        for (int i = 0; i < values.length; i++)
        {
            query.setParameter(i, values[i]);
        }
        return query;
    }
    
    /**
     * 
     * 查询放入缓存
     * 
     * @param hql hql
     * @param values 传入的参数
     * @return Query对象
     * @see [类、类#方法、类#成员]
     */
    public Query createQueryByCache(final String hql, final Object... values)
    {
        Assert.hasText(hql);
        final Query query = getSession().createQuery(hql);
        for (int i = 0; i < values.length; i++)
        {
            query.setParameter(i, values[i]);
        }
        query.setCacheable(true);
        return query;
    }
    
    /**
     * 
     * 分页查询
     * 
     * @param hql hql
     * @param pageNo 当前页
     * @param pageSize 查询体量
     * @param values 传入的参数
     * @return 分页对象
     * @see [类、类#方法、类#成员]
     */
    public PageUtil findPageByQuery(final String hql, final int pageNo, final int pageSize, final Object... values)
    {
        
        final String countQueryString = " SELECT COUNT(*) FROM( " + hql + " ) x ";
        final int totalCount = ((Long)createQuery(countQueryString, values).uniqueResult()).intValue();
        if (totalCount < 1)
        {
            return new PageUtil(new ArrayList(), 0, 0, pageSize);
        }
        final int start = PageUtil.getStartOfPage(pageNo, pageSize);
        final Query query = createQuery(hql, values);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        final List records = query.setFirstResult(start).setMaxResults(pageSize).list();
        return new PageUtil(records, totalCount, pageNo, pageSize);
    }
    
    /**
     * 
     * 分页查询
     * 
     * @param sql sql
     * @param pageNo 当前页
     * @param pageSize 查询体量
     * @param values 传入的参数
     * @return 分页对象
     * @see [类、类#方法、类#成员]
     */
    public PageUtil findPageBySqlQuery(final String sql, final int pageNo, final int pageSize, final Object... values)
    {
        
        final String countQueryString = " SELECT COUNT(*) FROM( " + sql + " ) x ";
        final int totalCount = ((BigInteger)createSqlQuery(countQueryString, values).uniqueResult()).intValue();
        if (totalCount < 1)
        {
            return new PageUtil(new ArrayList(), 0, 0, pageSize);
        }
        final int start = PageUtil.getStartOfPage(pageNo, pageSize);
        final Query query = createSqlQuery(sql, values);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        final List records = query.setFirstResult(start).setMaxResults(pageSize).list();
        return new PageUtil(records, totalCount, pageNo, pageSize);
    }
    
    /**
     * 
     * 刷新
     * 
     * @see [类、类#方法、类#成员]
     */
    public void flush()
    {
        getSession().flush();
    }
    
    /**
     * 
     * 清除
     * 
     * @see [类、类#方法、类#成员]
     */
    public void clear()
    {
        getSession().clear();
    }
    
    /**
     * 
     * 获取实体对应的表名
     * 
     * @param clazz 表对应实体类型
     * @return 表名
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public String getTableName(final Class clazz)
    {
        final Entity entity = (Entity)clazz.getAnnotation(Entity.class);
        return " " + entity.name() + " ";
    }
    
    /**
     * 
     * 缓存
     * 
     * @return 缓存
     * @see [类、类#方法、类#成员]
     */
    public org.hibernate.Cache getCache()
    {
        return getSessionFactory().getCache();
    }
}
