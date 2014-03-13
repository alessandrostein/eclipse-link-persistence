package org.ftd.alm.persistence.test;

/**
 * JAVA API CLASSES...
 */
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * APP CLASSES...
 */
import org.ftd.alm.persistence.controller.RoleJpaController;
import org.ftd.alm.persistence.controller.UserJpaController;
import org.ftd.alm.persistence.controller.UserRoleJpaController;
import org.ftd.alm.persistence.controller.exceptions.NonexistentEntityException;
import org.ftd.alm.persistence.controller.interfaces.IRoleJpaController;
import org.ftd.alm.persistence.controller.interfaces.IUserJpaController;
import org.ftd.alm.persistence.entity.Role;
import org.ftd.alm.persistence.entity.User;
import org.ftd.alm.persistence.entity.UserRole;

/**
 *
 * @author Fabio Tavares Dippold
 *
 */

public class DoQualityTest {
    private static final String PERSISTENCE_UNIT_NAME = "alm-PU";
    
    public static void main(String[] args) {
        try {
//            doResetStandartConfig();
            showAppMasterData();
        } catch (NonexistentEntityException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        
    }

    /**
     * SETUP APP...
     */
    private static void doResetStandartConfig() throws NonexistentEntityException, Exception {
        doRemoveAllUserRoles();
        doRemoveAllRole();
        doRemoveAllUser();
        doAddUsersTest("Fabio,Galateo,Lui,Noa,Dore,Gift,Uxita,Danger");
        doAddRolesTest("admin,user,manager,director,publish,reader,aprover,project-manager");
        doAddAllRoleToUsers(getAllUser());
        
    }

    private static void showAppMasterData() throws Exception {
        listAllUser();
        listAllRole();
        listAllUserRoles();
    }

    /**
     * USER ENTITY TESTS...
     */
    private static List<User> getAllUser() {
        System.out.println("\nInvocando o método getAllUser...");
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        IUserJpaController controller = new UserJpaController(factory);
        List<User> lst = controller.findUserEntities();

        return lst;
    }    
    
    private static void listAllUser() {
        System.out.println("\nInvocando o método listAllUser...");
        List<User> lst = getAllUser();
        User o;
        for (int i = 0; i < lst.size(); i++) {
            o = (User) lst.get(i);
            System.out.println(o);
        }

    }

    private static void doAddUsersTest(String collectionNamesSeparatedByAComma) {
        String nomes[] = collectionNamesSeparatedByAComma.split(",");
        for (String nome : nomes) {
            doAddUserTest(nome);
        }
    }

    private static void doAddUserTest(String someName) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        IUserJpaController controller = new UserJpaController(factory);
        em.getTransaction().begin();
        User o = new User();
        o.setName(someName);
        controller.create(o);
        em.getTransaction().commit();
        em.close();
    }

    private static void doRemoveAllUser() throws NonexistentEntityException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        IUserJpaController controller = new UserJpaController(factory);
        em.getTransaction().begin();

        List<User> lst = controller.findUserEntities();
        User o;
        for (int i = 0; i < lst.size(); i++) {
            o = (User) lst.get(i);
            controller.destroy(o.getId());
        }

        em.getTransaction().commit();
        em.close();
    }

    /**
     * ROLE ENTITY TESTS...
     */
    private static void listAllRole() {
        System.out.println("\nInvocando o método listAllRole...");
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        IRoleJpaController controller = new RoleJpaController(factory);
        List<Role> lst = controller.findRoleEntities();
        Role o;
        for (int i = 0; i < lst.size(); i++) {
            o = (Role) lst.get(i);
            System.out.println(o);
        }

    }

    private static void doAddRolesTest(String collectionNamesSeparatedByAComma) {
        String nomes[] = collectionNamesSeparatedByAComma.split(",");
        for (String nome : nomes) {
            doAddRoleTest(nome);
        }
    }

    private static void doAddRoleTest(String someName) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        IRoleJpaController controller = new RoleJpaController(factory);
        em.getTransaction().begin();
        Role o = new Role();
        o.setName(someName);
        controller.create(o);
        em.getTransaction().commit();
        em.close();
    }

    private static void doRemoveAllRole() throws NonexistentEntityException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        IRoleJpaController controller = new RoleJpaController(factory);
        em.getTransaction().begin();

        List<Role> lst = controller.findRoleEntities();
        Role o;
        for (int i = 0; i < lst.size(); i++) {
            o = (Role) lst.get(i);
            controller.destroy(o.getId());
        }

        em.getTransaction().commit();
        em.close();
    }

    /**
     * USER-ROLE ENTITY TESTS...
     */    
    private static void listAllUserRoles() {
        System.out.println("\nInvocando o método listAllUserRoles...");
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        UserRoleJpaController controller = new UserRoleJpaController(factory);
        List<UserRole> lst = controller.findUserRoleEntities();
        UserRole o;
        for (int i = 0; i < lst.size(); i++) {
            o = (UserRole) lst.get(i);
            System.out.println(o);
        }

    }  
    
    private static void doRemoveAllUserRoles() throws NonexistentEntityException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        UserRoleJpaController controller = new UserRoleJpaController(factory);
        em.getTransaction().begin();

        List<UserRole> lst = controller.findUserRoleEntities();
        UserRole o;
        for (int i = 0; i < lst.size(); i++) {
            o = (UserRole) lst.get(i);
            controller.destroy(o.getId());
        }

        em.getTransaction().commit();
        em.close();        
    }
    
    private static void doAddAllRoleToUsers(List<User> users) throws Exception {
        User u;
        for (int i=0; i<users.size(); i++) {
            u = (User) users.get(i);
            doAddAllRoleToUser(u);
        }
    }
    
    private static void doAddAllRoleToUser(User user) throws Exception {
        System.out.println("Invocando o método doAddAllRoleToUser... " + user.getName());
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        IRoleJpaController roleController = new RoleJpaController(factory);
        List<Role> lst = roleController.findRoleEntities();
        
        UserRoleJpaController userRoleController = new UserRoleJpaController(factory);
        
        Role role;
        UserRole userRole;
        for (int i = 0; i < lst.size(); i++) {
            role = (Role) lst.get(i);
            userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(role.getId());
            userRoleController.create(userRole);       
        }        
    }
}
