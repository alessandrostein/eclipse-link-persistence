/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ftd.alm.persistence.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.ftd.alm.persistence.controller.exceptions.NonexistentEntityException;
import org.ftd.alm.persistence.controller.exceptions.PreexistingEntityException;
import org.ftd.alm.persistence.entity.UserRole;

/**
 *
 * @author ftd
 */
public class UserRoleJpaController implements Serializable {

    public UserRoleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserRole userRole) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(userRole);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUserRole(userRole.getUserId()) != null) {
                throw new PreexistingEntityException("UserRole " + userRole + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserRole userRole) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            userRole = em.merge(userRole);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = userRole.getUserId();
                if (findUserRole(id) == null) {
                    throw new NonexistentEntityException("The userRole with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserRole userRole;
            try {
                userRole = em.getReference(UserRole.class, id);
                userRole.getUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userRole with id " + id + " no longer exists.", enfe);
            }
            em.remove(userRole);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserRole> findUserRoleEntities() {
        return findUserRoleEntities(true, -1, -1);
    }

    public List<UserRole> findUserRoleEntities(int maxResults, int firstResult) {
        return findUserRoleEntities(false, maxResults, firstResult);
    }

    private List<UserRole> findUserRoleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserRole.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public UserRole findUserRole(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserRole.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserRoleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserRole> rt = cq.from(UserRole.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
