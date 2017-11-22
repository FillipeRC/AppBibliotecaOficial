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
import br.edu.uerr.biblio.appbiblio.modelo.Acervo;
import br.edu.uerr.biblio.appbiblio.modelo.Locacao;
import br.edu.uerr.biblio.appbiblio.modelo.Locacoes;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jklac
 */
public class LocacoesJpaController implements Serializable {

    public LocacoesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Locacoes locacoes) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Acervo acervoId = locacoes.getAcervoId();
            if (acervoId != null) {
                acervoId = em.getReference(acervoId.getClass(), acervoId.getId());
                locacoes.setAcervoId(acervoId);
            }
            Locacao locacaoId = locacoes.getLocacaoId();
            if (locacaoId != null) {
                locacaoId = em.getReference(locacaoId.getClass(), locacaoId.getId());
                locacoes.setLocacaoId(locacaoId);
            }
            em.persist(locacoes);
            if (acervoId != null) {
                acervoId.getLocacoesList().add(locacoes);
                acervoId = em.merge(acervoId);
            }
            if (locacaoId != null) {
                locacaoId.getLocacoesList().add(locacoes);
                locacaoId = em.merge(locacaoId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLocacoes(locacoes.getId()) != null) {
                throw new PreexistingEntityException("Locacoes " + locacoes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Locacoes locacoes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Locacoes persistentLocacoes = em.find(Locacoes.class, locacoes.getId());
            Acervo acervoIdOld = persistentLocacoes.getAcervoId();
            Acervo acervoIdNew = locacoes.getAcervoId();
            Locacao locacaoIdOld = persistentLocacoes.getLocacaoId();
            Locacao locacaoIdNew = locacoes.getLocacaoId();
            if (acervoIdNew != null) {
                acervoIdNew = em.getReference(acervoIdNew.getClass(), acervoIdNew.getId());
                locacoes.setAcervoId(acervoIdNew);
            }
            if (locacaoIdNew != null) {
                locacaoIdNew = em.getReference(locacaoIdNew.getClass(), locacaoIdNew.getId());
                locacoes.setLocacaoId(locacaoIdNew);
            }
            locacoes = em.merge(locacoes);
            if (acervoIdOld != null && !acervoIdOld.equals(acervoIdNew)) {
                acervoIdOld.getLocacoesList().remove(locacoes);
                acervoIdOld = em.merge(acervoIdOld);
            }
            if (acervoIdNew != null && !acervoIdNew.equals(acervoIdOld)) {
                acervoIdNew.getLocacoesList().add(locacoes);
                acervoIdNew = em.merge(acervoIdNew);
            }
            if (locacaoIdOld != null && !locacaoIdOld.equals(locacaoIdNew)) {
                locacaoIdOld.getLocacoesList().remove(locacoes);
                locacaoIdOld = em.merge(locacaoIdOld);
            }
            if (locacaoIdNew != null && !locacaoIdNew.equals(locacaoIdOld)) {
                locacaoIdNew.getLocacoesList().add(locacoes);
                locacaoIdNew = em.merge(locacaoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = locacoes.getId();
                if (findLocacoes(id) == null) {
                    throw new NonexistentEntityException("The locacoes with id " + id + " no longer exists.");
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
            Locacoes locacoes;
            try {
                locacoes = em.getReference(Locacoes.class, id);
                locacoes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The locacoes with id " + id + " no longer exists.", enfe);
            }
            Acervo acervoId = locacoes.getAcervoId();
            if (acervoId != null) {
                acervoId.getLocacoesList().remove(locacoes);
                acervoId = em.merge(acervoId);
            }
            Locacao locacaoId = locacoes.getLocacaoId();
            if (locacaoId != null) {
                locacaoId.getLocacoesList().remove(locacoes);
                locacaoId = em.merge(locacaoId);
            }
            em.remove(locacoes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Locacoes> findLocacoesEntities() {
        return findLocacoesEntities(true, -1, -1);
    }

    public List<Locacoes> findLocacoesEntities(int maxResults, int firstResult) {
        return findLocacoesEntities(false, maxResults, firstResult);
    }

    private List<Locacoes> findLocacoesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Locacoes.class));
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

    public Locacoes findLocacoes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Locacoes.class, id);
        } finally {
            em.close();
        }
    }

    public int getLocacoesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Locacoes> rt = cq.from(Locacoes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
