package org.ftd.alm.persistence.controller.interfaces;

import java.util.List;
import javax.persistence.EntityManager;
import org.ftd.alm.persistence.controller.exceptions.NonexistentEntityException;
import org.ftd.alm.persistence.entity.Role;
import org.ftd.alm.persistence.entity.User;
import org.ftd.alm.persistence.entity.UserRole;

/**
 *
 * @author Fabio Tavares Dippold
 * 
 */
public interface IUserJpaController {
    
    public EntityManager getEntityManager();
    public void create(User user);
    public void edit(User user) throws NonexistentEntityException, Exception ;
    public void destroy(Long id) throws NonexistentEntityException;
    public List<User> findUserEntities();
    public List<User> findUserEntities(int maxResults, int firstResult);
    public User findUser(Long id);
    public int getUserCount();
    
    /*
     * RELATIONSHIP WITH ENTITY ROLE...
     */    
    public List<UserRole> findUserRolesEntities(User user);
    public boolean UserHasThatRole(User user, Role role);
    
}
