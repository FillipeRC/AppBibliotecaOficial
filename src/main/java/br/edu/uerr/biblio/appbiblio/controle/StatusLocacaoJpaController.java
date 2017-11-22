/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uerr.biblio.appbiblio.controle;

import br.edu.uerr.biblio.appbiblio.controle.exceptions.NonexistentEntityException;
import br.edu.uerr.biblio.appbiblio.controle.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.edu.uerr.biblio.appbiblio.modelo.Flag;
import br.edu.uerr.biblio.appbiblio.modelo.StatusLocacao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jklac
 */
public class StatusLocacaoJpaController implements Serializable {

    public StatusLocacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(StatusLocacao statusLocacao) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Flag flagId = statusLocacao.getFlagId();
            if (flagId != null) {
                flagId = em.getReference(flagId.getClass(), flagId.getId());
                statusLocacao.setFlagId(flagId);
            }
            em.persist(statusLocacao);
            if (flagId != null) {
                flagId.getStatusLocacaoList().add(statusLocacao);
                flagId = em.merge(flagId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findStatusLocacao(statusLocacao.getId()) != null) {
                throw new PreexistingEntityException("StatusLocacao " + statusLocacao + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(StatusLocacao statusLocacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            StatusLocacao persistentStatusLocacao = em.find(StatusLocacao.class, statusLocacao.getId());
            Flag flagIdOld = persistentStatusLocacao.getFlagId();
            Flag flagIdNew = statusLocacao.getFlagId();
            if (flagIdNew != null) {
                flagIdNew = em.getReference(flagIdNew.getClass(), flagIdNew.getId());
                statusLocacao.setFlagId(flagIdNew);
            }
            statusLocacao = em.merge(statusLocacao);
            if (flagIdOld != null && !flagIdOld.equals(flagIdNew)) {
                flagIdOld.getStatusLocacaoList().remove(statusLocacao);
                flagIdOld = em.merge(flagIdOld);
            }
            if (flagIdNew != null && !flagIdNew.equals(flagIdOld)) {
                flagIdNew.getStatusLocacaoList().add(statusLocacao);
                flagIdNew = em.merge(flagIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = statusLocacao.getId();
                if (findStatusLocacao(id) == null) {
                    throw new NonexistentEntityException("The statusLocacao with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            StatusLocacao statusLocacao;
            try {
                statusLocacao = em.getReference(StatusLocacao.class, id);
                statusLocacao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The statusLocacao with id " + id + " no longer exists.", enfe);
            }
            Flag flagId = statusLocacao.getFlagId();
            if (flagId != null) {
                flagId.getStatusLocacaoList().remove(statusLocacao);
                flagId = em.merge(flagId);
            }
            em.remove(statusLocacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<StatusLocacao> findStatusLocacaoEntities() {
        return findStatusLocacaoEntities(true, -1, -1);
    }

    public List<StatusLocacao> findStatusLocacaoEntities(int maxResults, int firstResult) {
        return findStatusLocacaoEntities(false, maxResults, firstResult);
    }

    private List<StatusLocacao> findStatusLocacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(StatusLocacao.class));
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

    public StatusLocacao findStatusLocacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(StatusLocacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getStatusLocacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<StatusLocacao> rt = cq.from(StatusLocacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
