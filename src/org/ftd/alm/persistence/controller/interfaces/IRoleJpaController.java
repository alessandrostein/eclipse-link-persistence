package org.ftd.alm.persistence.controller.interfaces;

import java.util.List;
import javax.persistence.EntityManager;
import org.ftd.alm.persistence.controller.exceptions.NonexistentEntityException;
import org.ftd.alm.persistence.entity.Role;

/**
 *
 * @author Fabio Tavares Dippold
 * 
 */
public interface IRoleJpaController {
    
    public EntityManager getEntityManager();
    public void create(Role role);
    public void edit(Role role) throws NonexistentEntityException, Exception ;
    public void destroy(Long id) throws NonexistentEntityException;
    public List<Role> findRoleEntities();
    public List<Role> findRoleEntities(int maxResults, int firstResult);
    public Role findRole(Long id);
    public int getRoleCount();
    
}
