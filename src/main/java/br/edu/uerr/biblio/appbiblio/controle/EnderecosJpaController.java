/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uerr.biblio.appbiblio.controle;

import br.edu.uerr.biblio.appbiblio.controle.exceptions.IllegalOrphanException;
import br.edu.uerr.biblio.appbiblio.controle.exceptions.NonexistentEntityException;
import br.edu.uerr.biblio.appbiblio.controle.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.edu.uerr.biblio.appbiblio.modelo.Endereco;
import java.util.ArrayList;
import java.util.List;
import br.edu.uerr.biblio.appbiblio.modelo.Campus;
import br.edu.uerr.biblio.appbiblio.modelo.Enderecos;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jklac
 */
public class EnderecosJpaController implements Serializable {

    public EnderecosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Enderecos enderecos) throws PreexistingEntityException, Exception {
        if (enderecos.getEnderecoList() == null) {
            enderecos.setEnderecoList(new ArrayList<Endereco>());
        }
        if (enderecos.getCampusList() == null) {
            enderecos.setCampusList(new ArrayList<Campus>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Endereco> attachedEnderecoList = new ArrayList<Endereco>();
            for (Endereco enderecoListEnderecoToAttach : enderecos.getEnderecoList()) {
                enderecoListEnderecoToAttach = em.getReference(enderecoListEnderecoToAttach.getClass(), enderecoListEnderecoToAttach.getId());
                attachedEnderecoList.add(enderecoListEnderecoToAttach);
            }
            enderecos.setEnderecoList(attachedEnderecoList);
            List<Campus> attachedCampusList = new ArrayList<Campus>();
            for (Campus campusListCampusToAttach : enderecos.getCampusList()) {
                campusListCampusToAttach = em.getReference(campusListCampusToAttach.getClass(), campusListCampusToAttach.getId());
                attachedCampusList.add(campusListCampusToAttach);
            }
            enderecos.setCampusList(attachedCampusList);
            em.persist(enderecos);
            for (Endereco enderecoListEndereco : enderecos.getEnderecoList()) {
                Enderecos oldEnderecosIdOfEnderecoListEndereco = enderecoListEndereco.getEnderecosId();
                enderecoListEndereco.setEnderecosId(enderecos);
                enderecoListEndereco = em.merge(enderecoListEndereco);
                if (oldEnderecosIdOfEnderecoListEndereco != null) {
                    oldEnderecosIdOfEnderecoListEndereco.getEnderecoList().remove(enderecoListEndereco);
                    oldEnderecosIdOfEnderecoListEndereco = em.merge(oldEnderecosIdOfEnderecoListEndereco);
                }
            }
            for (Campus campusListCampus : enderecos.getCampusList()) {
                Enderecos oldEnderecosIdOfCampusListCampus = campusListCampus.getEnderecosId();
                campusListCampus.setEnderecosId(enderecos);
                campusListCampus = em.merge(campusListCampus);
                if (oldEnderecosIdOfCampusListCampus != null) {
                    oldEnderecosIdOfCampusListCampus.getCampusList().remove(campusListCampus);
                    oldEnderecosIdOfCampusListCampus = em.merge(oldEnderecosIdOfCampusListCampus);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEnderecos(enderecos.getId()) != null) {
                throw new PreexistingEntityException("Enderecos " + enderecos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Enderecos enderecos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Enderecos persistentEnderecos = em.find(Enderecos.class, enderecos.getId());
            List<Endereco> enderecoListOld = persistentEnderecos.getEnderecoList();
            List<Endereco> enderecoListNew = enderecos.getEnderecoList();
            List<Campus> campusListOld = persistentEnderecos.getCampusList();
            List<Campus> campusListNew = enderecos.getCampusList();
            List<String> illegalOrphanMessages = null;
            for (Endereco enderecoListOldEndereco : enderecoListOld) {
                if (!enderecoListNew.contains(enderecoListOldEndereco)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Endereco " + enderecoListOldEndereco + " since its enderecosId field is not nullable.");
                }
            }
            for (Campus campusListOldCampus : campusListOld) {
                if (!campusListNew.contains(campusListOldCampus)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Campus " + campusListOldCampus + " since its enderecosId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Endereco> attachedEnderecoListNew = new ArrayList<Endereco>();
            for (Endereco enderecoListNewEnderecoToAttach : enderecoListNew) {
                enderecoListNewEnderecoToAttach = em.getReference(enderecoListNewEnderecoToAttach.getClass(), enderecoListNewEnderecoToAttach.getId());
                attachedEnderecoListNew.add(enderecoListNewEnderecoToAttach);
            }
            enderecoListNew = attachedEnderecoListNew;
            enderecos.setEnderecoList(enderecoListNew);
            List<Campus> attachedCampusListNew = new ArrayList<Campus>();
            for (Campus campusListNewCampusToAttach : campusListNew) {
                campusListNewCampusToAttach = em.getReference(campusListNewCampusToAttach.getClass(), campusListNewCampusToAttach.getId());
                attachedCampusListNew.add(campusListNewCampusToAttach);
            }
            campusListNew = attachedCampusListNew;
            enderecos.setCampusList(campusListNew);
            enderecos = em.merge(enderecos);
            for (Endereco enderecoListNewEndereco : enderecoListNew) {
                if (!enderecoListOld.contains(enderecoListNewEndereco)) {
                    Enderecos oldEnderecosIdOfEnderecoListNewEndereco = enderecoListNewEndereco.getEnderecosId();
                    enderecoListNewEndereco.setEnderecosId(enderecos);
                    enderecoListNewEndereco = em.merge(enderecoListNewEndereco);
                    if (oldEnderecosIdOfEnderecoListNewEndereco != null && !oldEnderecosIdOfEnderecoListNewEndereco.equals(enderecos)) {
                        oldEnderecosIdOfEnderecoListNewEndereco.getEnderecoList().remove(enderecoListNewEndereco);
                        oldEnderecosIdOfEnderecoListNewEndereco = em.merge(oldEnderecosIdOfEnderecoListNewEndereco);
                    }
                }
            }
            for (Campus campusListNewCampus : campusListNew) {
                if (!campusListOld.contains(campusListNewCampus)) {
                    Enderecos oldEnderecosIdOfCampusListNewCampus = campusListNewCampus.getEnderecosId();
                    campusListNewCampus.setEnderecosId(enderecos);
                    campusListNewCampus = em.merge(campusListNewCampus);
                    if (oldEnderecosIdOfCampusListNewCampus != null && !oldEnderecosIdOfCampusListNewCampus.equals(enderecos)) {
                        oldEnderecosIdOfCampusListNewCampus.getCampusList().remove(campusListNewCampus);
                        oldEnderecosIdOfCampusListNewCampus = em.merge(oldEnderecosIdOfCampusListNewCampus);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = enderecos.getId();
                if (findEnderecos(id) == null) {
                    throw new NonexistentEntityException("The enderecos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Enderecos enderecos;
            try {
                enderecos = em.getReference(Enderecos.class, id);
                enderecos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The enderecos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Endereco> enderecoListOrphanCheck = enderecos.getEnderecoList();
            for (Endereco enderecoListOrphanCheckEndereco : enderecoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Enderecos (" + enderecos + ") cannot be destroyed since the Endereco " + enderecoListOrphanCheckEndereco + " in its enderecoList field has a non-nullable enderecosId field.");
            }
            List<Campus> campusListOrphanCheck = enderecos.getCampusList();
            for (Campus campusListOrphanCheckCampus : campusListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Enderecos (" + enderecos + ") cannot be destroyed since the Campus " + campusListOrphanCheckCampus + " in its campusList field has a non-nullable enderecosId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(enderecos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Enderecos> findEnderecosEntities() {
        return findEnderecosEntities(true, -1, -1);
    }

    public List<Enderecos> findEnderecosEntities(int maxResults, int firstResult) {
        return findEnderecosEntities(false, maxResults, firstResult);
    }

    private List<Enderecos> findEnderecosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Enderecos.class));
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

    public Enderecos findEnderecos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Enderecos.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnderecosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Enderecos> rt = cq.from(Enderecos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
