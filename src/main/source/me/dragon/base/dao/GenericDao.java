package me.dragon.base.dao;

import me.dragon.base.core.Page;
import me.dragon.base.core.ReflectionUtils;
import me.dragon.base.core.ToBeanResultTransformSafety;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * Created by dragon on 4/4/2017.
 */
@Repository
@Transactional
public class GenericDao<T, PK extends Serializable> {

    protected Logger logger = Logger.getLogger(getClass());

    protected SessionFactory sessionFactory;

    protected Class<T> entityClass;


    /**
     * 用于Dao层子类使用的构造函数.
     * 通过子类的泛型定义取得对象类型Class.
     * eg.
     * public class UserDao extends SimpleHibernateDao<User, Long>
     */
    public GenericDao() {
        this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
    }

    /**
     * 用于用于省略Dao层, 在Service层直接使用通用SimpleHibernateDao的构造函数.
     * 在构造函数中定义对象类型Class.
     * eg.
     * SimpleHibernateDao<User, Long> userDao = new SimpleHibernateDao<User, Long>(sessionFactory, User.class);
     */
    public GenericDao(final SessionFactory sessionFactory, final Class<T> entityClass) {
        this.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
    }

    /**
     * 取得sessionFactory.
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * 采用@Autowired按类型注入SessionFactory, 当有多个SesionFactory的时候Override本函数.
     */
    @Autowired
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * 取得当前Session.
     */
    public Session getSession() {
        Session session = sessionFactory.getCurrentSession();
        //Session session = sessionFactory.openSession();
        return session;
    }

    /**
     * 保存新增或修改的对象.
     */
    public void save(final T entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().saveOrUpdate(entity);
        logger.debug("save entity: " +  entity);
    }

    /**
     * 删除对象.
     *
     * @param entity 对象必须是session中的对象或含id属性的transient对象.
     */
    public void delete(final T entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().delete(entity);
        logger.debug("delete entity: " + entity);
    }

    /**
     * 按id删除对象.
     */
    public void delete(final PK id) {
        Assert.notNull(id, "id不能为空");
        delete(get(id));
        logger.debug("delete entity " + entityClass.getSimpleName() +",id is " + id);
    }

    /**
     * 按id获取对象.
     */
    public T get(final PK id) {
        Assert.notNull(id, "id不能为空");
        return (T) getSession().get(entityClass, id);
    }

    /**
     * 按id获取对象.
     */
    public T load(final PK id) {
        Assert.notNull(id, "id不能为空");
        return (T) getSession().load(entityClass, id);
    }

    /**
     *	获取全部对象.
     */
    public List<T> getAll() {
        return find();
    }


    /**
     * 按属性查找对象列表,匹配方式为相等.
     */
    public List<T> findByProperty(final String propertyName, final Object value) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = Restrictions.eq(propertyName, value);
        return find(criterion);
    }

    /**
     * 按属性查找唯一对象,匹配方式为相等.
     */
    public T findUniqueByProperty(final String propertyName, final Object value) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = Restrictions.eq(propertyName, value);
        return (T) createCriteria(criterion).uniqueResult();
    }


    /**
     * 按id列表获取对象.
     */
    public List<T> findByIds(List<PK> ids) {
        return find(Restrictions.in(getIdName(), ids));
    }

    /**
     * 按HQL查询对象列表.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    public <X> List<X> find(final String hql, final Object... values) {
        return createQuery(hql, values).list();
    }

    /**
     * 按HQL查询对象列表.
     *
     * @param values 命名参数,按名称绑定.
     */
    public <X> List<X> find(final String hql, final Map<String, ?> values) {
        return createQuery(hql, values).list();
    }

    /**
     * 按HQL查询唯一对象.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    public <X> X findUnique(final String hql, final Object... values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

    /**
     * 按HQL查询唯一对象.
     *
     * @param values 命名参数,按名称绑定.
     */
    public <X> X findUnique(final String hql, final Map<String, ?> values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

    /**
     * 执行HQL进行批量修改/删除操作.
     */
    public int batchExecute(final String hql, final Object... values) {
        return createQuery(hql, values).executeUpdate();
    }

    /**
     * 执行HQL进行批量修改/删除操作.
     * @return 更新记录数.
     */
    public int batchExecute(final String hql, final Map<String, ?> values) {
        return createQuery(hql, values).executeUpdate();
    }

    /**
     * 根据查询HQL与参数列表创建Query对象.
     *
     * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    public Query createQuery(final String queryString, final Object... values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    /**
     * 根据查询HQL与参数列表创建Query对象.
     *
     * @param values 命名参数,按名称绑定.
     */
    public Query createQuery(final String queryString, final Map<String, ?> values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            query.setProperties(values);
        }
        return query;
    }

    /**
     * 按Criteria查询对象列表.
     *
     * @param criterions 数量可变的Criterion.
     */
    public List<T> find(final Criterion... criterions) {
        List<T> rets = createCriteria(criterions).list();
        return rets;
    }

    /**
     * 按Criteria查询唯一对象.
     *
     * @param criterions 数量可变的Criterion.
     */
    public T findUnique(final Criterion... criterions) {
        return (T) createCriteria(criterions).uniqueResult();
    }

    /**
     * 根据Criterion条件创建Criteria.
     *
     * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
     *
     * @param criterions 数量可变的Criterion.
     */
    public Criteria createCriteria(final Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(entityClass);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    public Criteria createCriteria(List<Criterion> criterions) {
        Criteria criteria = getSession().createCriteria(entityClass);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    public Criteria createCriteria(List<Criterion> criterions, List<Order> orders) {
        Criteria criteria = getSession().createCriteria(entityClass);
        for (Criterion c : criterions) {
            criteria.add(c);
        }

        for (Order c : orders) {
            criteria.addOrder(c);
        }
        return criteria;
    }

    /**
     * 初始化对象.
     * 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化.
     * 只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性.
     * 如需初始化关联属性,可实现新的函数,执行:
     * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
     * Hibernate.initialize(user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
     */
    public void initEntity(T entity) {
        Hibernate.initialize(entity);
    }

    /**
     * @see #initEntity(Object)
     */
    public void initEntity(List<T> entityList) {
        for (T entity : entityList) {
            Hibernate.initialize(entity);
        }
    }

    /**
     * Flush当前Session.
     */
    public void flush() {
        getSession().flush();
    }

    /**
     * 为Query添加distinct transformer.
     */
    public Query distinct(Query query) {
        query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return query;
    }

    /**
     * 为Criteria添加distinct transformer.
     */
    public Criteria distinct(Criteria criteria) {
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria;
    }

    /**
     * 取得对象的主键名.
     */
    public String getIdName() {
        ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
        return meta.getIdentifierPropertyName();
    }
    /**
     * map 可为集合参数
     * @param hql
     * @param map
     * @return
     */
    public List<T> findByMap(String hql,Map<String,?> map){
        Query query = getSession().createQuery(hql);
        if (map != null) {
            Set<String> keySet = map.keySet();
            for (String string : keySet) {
                Object obj = map.get(string);
                //这里考虑传入的参数是什么类型，不同类型使用的方法不同
                if(obj instanceof Collection<?>){
                    query.setParameterList(string, (Collection<?>)obj);
                }else if(obj instanceof Object[]){
                    query.setParameterList(string, (Object[])obj);
                }else{
                    query.setParameter(string, obj);
                }
            }
        }
        return query.list();
    }

    /**
     * 得到对象分页列表(1.1版)
     *
     * @param sql
     * @param firstResult
     * @param maxResult
     * @param pojoClass
     * @return
     */
    public List findListWithSQL( String sql, final int firstResult,
                                 int maxResult,  Class pojoClass) {
        SQLQuery query = getSession().createSQLQuery(sql);

        if (firstResult >= 0) {
            query.setFirstResult(firstResult);
        }

        if (maxResult > 0) {
            query.setMaxResults(maxResult);
        }

        List resultList = null;

        if (pojoClass != null || pojoClass != java.util.HashMap.class) {
            resultList = query.setResultTransformer(
                    new ToBeanResultTransformSafety(pojoClass)).list();
        }

        query = null;

        return resultList;

    }

    /**
     * 统计记录数(1.1版)
     */
    public int countWithSQL(String sql) {
        return this.count(sql, false);
    }

    /**
     * 用 count(*) from 进行统计(1.1版)
     *
     * @param queryStr
     * @param isHSQL
     *            true:HSQL;false:SQL
     * @return
     */
    private int count(String queryStr, final boolean isHSQL) {
        String sql = queryStr;
        String result = "0";
        Session session = getSession();
        if (isHSQL) {
            result = String.valueOf(session.createQuery(sql)
                    .iterate().next());
        } else {
            result = String.valueOf(session.createSQLQuery(sql)
                    .uniqueResult());
        }

        return new Integer(result).intValue();
    }

    /**
     * 分页查询(1.1版)
     */
    @SuppressWarnings("unchecked")
    public Page findPageWithSQL(String sql, int pageSize, int pageNum,
                                Class modeClass) {
        Page page = new Page();
        page.setPageSize(pageSize);
        page.setPageNo(pageNum);

        String countSql = new StringBuffer("select count(*) as count from (")
                .append(sql).append(") count_temp ").toString();
        int count = countWithSQL(countSql);

        if (count < 1) {
            List list = new ArrayList();
            page.setResult(list);
            page.setTotalCount(0);
            return page;
        }

        pageSize = pageSize > 0 ? pageSize : 15;
        pageNum = pageNum > 0 ? pageNum : 1;

        int firstResult = (pageNum - 1) * pageSize;// 第一条是1
        int maxResult = pageSize;

        List resultList = findListWithSQL(sql, firstResult, maxResult, modeClass);

        page.setResult(resultList==null?new ArrayList():resultList);
        page.setTotalCount(count);
        return page;

    }

    /**
     * 分页查询(1.1版)
     */
    public Page findPageMapWithSQL(String sql, int pageSize, int pageNumber) {
        return findPageWithSQL(sql, pageSize, pageNumber, java.util.HashMap.class);
    }
}

