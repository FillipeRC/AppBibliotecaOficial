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
import br.edu.uerr.biblio.appbiblio.modelo.Acervo;
import br.edu.uerr.biblio.appbiblio.modelo.Genero;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jklac
 */
public class GeneroJpaController implements Serializable {

    public GeneroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Genero genero) throws PreexistingEntityException, Exception {
        if (genero.getAcervoList() == null) {
            genero.setAcervoList(new ArrayList<Acervo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Acervo> attachedAcervoList = new ArrayList<Acervo>();
            for (Acervo acervoListAcervoToAttach : genero.getAcervoList()) {
                acervoListAcervoToAttach = em.getReference(acervoListAcervoToAttach.getClass(), acervoListAcervoToAttach.getId());
                attachedAcervoList.add(acervoListAcervoToAttach);
            }
            genero.setAcervoList(attachedAcervoList);
            em.persist(genero);
            for (Acervo acervoListAcervo : genero.getAcervoList()) {
                Genero oldGeneroIdOfAcervoListAcervo = acervoListAcervo.getGeneroId();
                acervoListAcervo.setGeneroId(genero);
                acervoListAcervo = em.merge(acervoListAcervo);
                if (oldGeneroIdOfAcervoListAcervo != null) {
                    oldGeneroIdOfAcervoListAcervo.getAcervoList().remove(acervoListAcervo);
                    oldGeneroIdOfAcervoListAcervo = em.merge(oldGeneroIdOfAcervoListAcervo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGenero(genero.getId()) != null) {
                throw new PreexistingEntityException("Genero " + genero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Genero genero) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Genero persistentGenero = em.find(Genero.class, genero.getId());
            List<Acervo> acervoListOld = persistentGenero.getAcervoList();
            List<Acervo> acervoListNew = genero.getAcervoList();
            List<String> illegalOrphanMessages = null;
            for (Acervo acervoListOldAcervo : acervoListOld) {
                if (!acervoListNew.contains(acervoListOldAcervo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Acervo " + acervoListOldAcervo + " since its generoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Acervo> attachedAcervoListNew = new ArrayList<Acervo>();
            for (Acervo acervoListNewAcervoToAttach : acervoListNew) {
                acervoListNewAcervoToAttach = em.getReference(acervoListNewAcervoToAttach.getClass(), acervoListNewAcervoToAttach.getId());
                attachedAcervoListNew.add(acervoListNewAcervoToAttach);
            }
            acervoListNew = attachedAcervoListNew;
            genero.setAcervoList(acervoListNew);
            genero = em.merge(genero);
            for (Acervo acervoListNewAcervo : acervoListNew) {
                if (!acervoListOld.contains(acervoListNewAcervo)) {
                    Genero oldGeneroIdOfAcervoListNewAcervo = acervoListNewAcervo.getGeneroId();
                    acervoListNewAcervo.setGeneroId(genero);
                    acervoListNewAcervo = em.merge(acervoListNewAcervo);
                    if (oldGeneroIdOfAcervoListNewAcervo != null && !oldGeneroIdOfAcervoListNewAcervo.equals(genero)) {
                        oldGeneroIdOfAcervoListNewAcervo.getAcervoList().remove(acervoListNewAcervo);
                        oldGeneroIdOfAcervoListNewAcervo = em.merge(oldGeneroIdOfAcervoListNewAcervo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = genero.getId();
                if (findGenero(id) == null) {
                    throw new NonexistentEntityException("The genero with id " + id + " no longer exists.");
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
            Genero genero;
            try {
                genero = em.getReference(Genero.class, id);
                genero.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The genero with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Acervo> acervoListOrphanCheck = genero.getAcervoList();
            for (Acervo acervoListOrphanCheckAcervo : acervoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Genero (" + genero + ") cannot be destroyed since the Acervo " + acervoListOrphanCheckAcervo + " in its acervoList field has a non-nullable generoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(genero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Genero> findGeneroEntities() {
        return findGeneroEntities(true, -1, -1);
    }

    public List<Genero> findGeneroEntities(int maxResults, int firstResult) {
        return findGeneroEntities(false, maxResults, firstResult);
    }

    private List<Genero> findGeneroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Genero.class));
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

    public Genero findGenero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Genero.class, id);
        } finally {
            em.close();
        }
    }

    public int getGeneroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Genero> rt = cq.from(Genero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
