/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uerr.biblio.appbiblio.controle;

import br.edu.uerr.biblio.appbiblio.controle.exceptions.IllegalOrphanException;
import br.edu.uerr.biblio.appbiblio.controle.exceptions.NonexistentEntityException;
import br.edu.uerr.biblio.appbiblio.controle.exceptions.PreexistingEntityException;
import br.edu.uerr.biblio.appbiblio.modelo.Flag;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.edu.uerr.biblio.appbiblio.modelo.Locacao;
import br.edu.uerr.biblio.appbiblio.modelo.StatusLocacao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jklac
 */
public class FlagJpaController implements Serializable {

    public FlagJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Flag flag) throws PreexistingEntityException, Exception {
        if (flag.getStatusLocacaoList() == null) {
            flag.setStatusLocacaoList(new ArrayList<StatusLocacao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Locacao locacaoId = flag.getLocacaoId();
            if (locacaoId != null) {
                locacaoId = em.getReference(locacaoId.getClass(), locacaoId.getId());
                flag.setLocacaoId(locacaoId);
            }
            List<StatusLocacao> attachedStatusLocacaoList = new ArrayList<StatusLocacao>();
            for (StatusLocacao statusLocacaoListStatusLocacaoToAttach : flag.getStatusLocacaoList()) {
                statusLocacaoListStatusLocacaoToAttach = em.getReference(statusLocacaoListStatusLocacaoToAttach.getClass(), statusLocacaoListStatusLocacaoToAttach.getId());
                attachedStatusLocacaoList.add(statusLocacaoListStatusLocacaoToAttach);
            }
            flag.setStatusLocacaoList(attachedStatusLocacaoList);
            em.persist(flag);
            if (locacaoId != null) {
                locacaoId.getFlagList().add(flag);
                locacaoId = em.merge(locacaoId);
            }
            for (StatusLocacao statusLocacaoListStatusLocacao : flag.getStatusLocacaoList()) {
                Flag oldFlagIdOfStatusLocacaoListStatusLocacao = statusLocacaoListStatusLocacao.getFlagId();
                statusLocacaoListStatusLocacao.setFlagId(flag);
                statusLocacaoListStatusLocacao = em.merge(statusLocacaoListStatusLocacao);
                if (oldFlagIdOfStatusLocacaoListStatusLocacao != null) {
                    oldFlagIdOfStatusLocacaoListStatusLocacao.getStatusLocacaoList().remove(statusLocacaoListStatusLocacao);
                    oldFlagIdOfStatusLocacaoListStatusLocacao = em.merge(oldFlagIdOfStatusLocacaoListStatusLocacao);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFlag(flag.getId()) != null) {
                throw new PreexistingEntityException("Flag " + flag + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Flag flag) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Flag persistentFlag = em.find(Flag.class, flag.getId());
            Locacao locacaoIdOld = persistentFlag.getLocacaoId();
            Locacao locacaoIdNew = flag.getLocacaoId();
            List<StatusLocacao> statusLocacaoListOld = persistentFlag.getStatusLocacaoList();
            List<StatusLocacao> statusLocacaoListNew = flag.getStatusLocacaoList();
            List<String> illegalOrphanMessages = null;
            for (StatusLocacao statusLocacaoListOldStatusLocacao : statusLocacaoListOld) {
                if (!statusLocacaoListNew.contains(statusLocacaoListOldStatusLocacao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain StatusLocacao " + statusLocacaoListOldStatusLocacao + " since its flagId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (locacaoIdNew != null) {
                locacaoIdNew = em.getReference(locacaoIdNew.getClass(), locacaoIdNew.getId());
                flag.setLocacaoId(locacaoIdNew);
            }
            List<StatusLocacao> attachedStatusLocacaoListNew = new ArrayList<StatusLocacao>();
            for (StatusLocacao statusLocacaoListNewStatusLocacaoToAttach : statusLocacaoListNew) {
                statusLocacaoListNewStatusLocacaoToAttach = em.getReference(statusLocacaoListNewStatusLocacaoToAttach.getClass(), statusLocacaoListNewStatusLocacaoToAttach.getId());
                attachedStatusLocacaoListNew.add(statusLocacaoListNewStatusLocacaoToAttach);
            }
            statusLocacaoListNew = attachedStatusLocacaoListNew;
            flag.setStatusLocacaoList(statusLocacaoListNew);
            flag = em.merge(flag);
            if (locacaoIdOld != null && !locacaoIdOld.equals(locacaoIdNew)) {
                locacaoIdOld.getFlagList().remove(flag);
                locacaoIdOld = em.merge(locacaoIdOld);
            }
            if (locacaoIdNew != null && !locacaoIdNew.equals(locacaoIdOld)) {
                locacaoIdNew.getFlagList().add(flag);
                locacaoIdNew = em.merge(locacaoIdNew);
            }
            for (StatusLocacao statusLocacaoListNewStatusLocacao : statusLocacaoListNew) {
                if (!statusLocacaoListOld.contains(statusLocacaoListNewStatusLocacao)) {
                    Flag oldFlagIdOfStatusLocacaoListNewStatusLocacao = statusLocacaoListNewStatusLocacao.getFlagId();
                    statusLocacaoListNewStatusLocacao.setFlagId(flag);
                    statusLocacaoListNewStatusLocacao = em.merge(statusLocacaoListNewStatusLocacao);
                    if (oldFlagIdOfStatusLocacaoListNewStatusLocacao != null && !oldFlagIdOfStatusLocacaoListNewStatusLocacao.equals(flag)) {
                        oldFlagIdOfStatusLocacaoListNewStatusLocacao.getStatusLocacaoList().remove(statusLocacaoListNewStatusLocacao);
                        oldFlagIdOfStatusLocacaoListNewStatusLocacao = em.merge(oldFlagIdOfStatusLocacaoListNewStatusLocacao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = flag.getId();
                if (findFlag(id) == null) {
                    throw new NonexistentEntityException("The flag with id " + id + " no longer exists.");
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
            Flag flag;
            try {
                flag = em.getReference(Flag.class, id);
                flag.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The flag with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<StatusLocacao> statusLocacaoListOrphanCheck = flag.getStatusLocacaoList();
            for (StatusLocacao statusLocacaoListOrphanCheckStatusLocacao : statusLocacaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Flag (" + flag + ") cannot be destroyed since the StatusLocacao " + statusLocacaoListOrphanCheckStatusLocacao + " in its statusLocacaoList field has a non-nullable flagId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Locacao locacaoId = flag.getLocacaoId();
            if (locacaoId != null) {
                locacaoId.getFlagList().remove(flag);
                locacaoId = em.merge(locacaoId);
            }
            em.remove(flag);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Flag> findFlagEntities() {
        return findFlagEntities(true, -1, -1);
    }

    public List<Flag> findFlagEntities(int maxResults, int firstResult) {
        return findFlagEntities(false, maxResults, firstResult);
    }

    private List<Flag> findFlagEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Flag.class));
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

    public Flag findFlag(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Flag.class, id);
        } finally {
            em.close();
        }
    }

    public int getFlagCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Flag> rt = cq.from(Flag.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
