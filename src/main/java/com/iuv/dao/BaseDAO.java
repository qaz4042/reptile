package com.iuv.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;

/**
 * @author lzpeng
 * @version 2009-1-10
 */
public interface BaseDAO<T, PK extends Serializable> {

    void save(T entity);

    void delete(T entity);

    void delete(PK id);

    List<T> findAll();

    // Page<T> findAll(Page<T> page);

    /**
     * Gets the object by ID .
     */
    T get(final PK id);

    /**
     * Press the HQL Query object list .
     *
     * @param hql    hqlStatement
     * @param values Number of variable parameters
     */
    List find(String hql, Object... values);

    /**
     * Press the HQL query paging . No support for automatically gets the total number of results ,User further execution of the query .
     *
     * @param page
     *            Paging parameters .Including the pageSize and firstResult .
     * @param hql
     *            hqlStatement .
     * @param values
     *            Number of variable parameters .
     *
     * @return Paging query results ,Comes with a results list and all query parameters .
     */
    //  Page<T> find(Page<T> page, String hql, Object... values);

    /**
     * Press the HQL query only object .
     */
    Object findUnique(String hql, Object... values);

    /**
     * Press the HQL query results Intger class shape .
     */
    Integer findInt(String hql, Object... values);

    /**
     * According to the results of the HQL Query type long .
     */
    Long findLong(String hql, Object... values);

    /**
     * According to the Criterion query object list .
     *
     * @param criterion Number of variable Criterion .
     */
    List<T> findByCriteria(Criterion... criterion);

    /**
     * According to the Criterion paging query .
     *
     * @param page
     *            Paging parameters .Including the pageSize, firstResult, orderBy, asc, autoCount .
     *            Where firstResult can be directly specified ,You can also specify pageNo . autoCountSpecifies whether dynamic gets total number of results .
     *
     * @param criterion
     *            Number of variable Criterion .
     * @return Paging query results .Comes with a results list and all query parameters .
     */
    // Page<T> findByCriteria(Page page, Criterion... criterion);

    /**
     * Find a list of objects by property .
     */
    List<T> findByProperty(String propertyName, Object value);

    /**
     * Find unique object by property .
     */
    T findUniqueByProperty(String propertyName, Object value);

    /**
     * Depending on the query function and argument list to create a Query object ,Subsequent to processing ,The auxiliary function .
     */
    Query createQuery(String queryString, Object... values);

    /**
     * According to the Criterion conditions create Criteria ,Subsequent to processing ,The auxiliary function .
     */
    Criteria createCriteria(Criterion... criterions);

    /**
     * Determine the object's property value is unique within the database .
     */
    boolean isPropertyUnique(String propertyName, Object newValue,
                             Object orgValue);

    /**
     * Through this query count query to obtain the total number of objects .
     *
     * @return pageThe totalCount property in the object is assigned .
     */
    // int countQueryResult(Page<T> page, Criteria c);
}