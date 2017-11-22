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
import br.edu.uerr.biblio.appbiblio.modelo.AcervoPerfil;
import br.edu.uerr.biblio.appbiblio.modelo.TipoAcervoPerfil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jklac
 */
public class TipoAcervoPerfilJpaController implements Serializable {

    public TipoAcervoPerfilJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoAcervoPerfil tipoAcervoPerfil) throws PreexistingEntityException, Exception {
        if (tipoAcervoPerfil.getAcervoPerfilList() == null) {
            tipoAcervoPerfil.setAcervoPerfilList(new ArrayList<AcervoPerfil>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<AcervoPerfil> attachedAcervoPerfilList = new ArrayList<AcervoPerfil>();
            for (AcervoPerfil acervoPerfilListAcervoPerfilToAttach : tipoAcervoPerfil.getAcervoPerfilList()) {
                acervoPerfilListAcervoPerfilToAttach = em.getReference(acervoPerfilListAcervoPerfilToAttach.getClass(), acervoPerfilListAcervoPerfilToAttach.getId());
                attachedAcervoPerfilList.add(acervoPerfilListAcervoPerfilToAttach);
            }
            tipoAcervoPerfil.setAcervoPerfilList(attachedAcervoPerfilList);
            em.persist(tipoAcervoPerfil);
            for (AcervoPerfil acervoPerfilListAcervoPerfil : tipoAcervoPerfil.getAcervoPerfilList()) {
                TipoAcervoPerfil oldTipoAcervoPerfilIdOfAcervoPerfilListAcervoPerfil = acervoPerfilListAcervoPerfil.getTipoAcervoPerfilId();
                acervoPerfilListAcervoPerfil.setTipoAcervoPerfilId(tipoAcervoPerfil);
                acervoPerfilListAcervoPerfil = em.merge(acervoPerfilListAcervoPerfil);
                if (oldTipoAcervoPerfilIdOfAcervoPerfilListAcervoPerfil != null) {
                    oldTipoAcervoPerfilIdOfAcervoPerfilListAcervoPerfil.getAcervoPerfilList().remove(acervoPerfilListAcervoPerfil);
                    oldTipoAcervoPerfilIdOfAcervoPerfilListAcervoPerfil = em.merge(oldTipoAcervoPerfilIdOfAcervoPerfilListAcervoPerfil);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoAcervoPerfil(tipoAcervoPerfil.getId()) != null) {
                throw new PreexistingEntityException("TipoAcervoPerfil " + tipoAcervoPerfil + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoAcervoPerfil tipoAcervoPerfil) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoAcervoPerfil persistentTipoAcervoPerfil = em.find(TipoAcervoPerfil.class, tipoAcervoPerfil.getId());
            List<AcervoPerfil> acervoPerfilListOld = persistentTipoAcervoPerfil.getAcervoPerfilList();
            List<AcervoPerfil> acervoPerfilListNew = tipoAcervoPerfil.getAcervoPerfilList();
            List<String> illegalOrphanMessages = null;
            for (AcervoPerfil acervoPerfilListOldAcervoPerfil : acervoPerfilListOld) {
                if (!acervoPerfilListNew.contains(acervoPerfilListOldAcervoPerfil)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AcervoPerfil " + acervoPerfilListOldAcervoPerfil + " since its tipoAcervoPerfilId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<AcervoPerfil> attachedAcervoPerfilListNew = new ArrayList<AcervoPerfil>();
            for (AcervoPerfil acervoPerfilListNewAcervoPerfilToAttach : acervoPerfilListNew) {
                acervoPerfilListNewAcervoPerfilToAttach = em.getReference(acervoPerfilListNewAcervoPerfilToAttach.getClass(), acervoPerfilListNewAcervoPerfilToAttach.getId());
                attachedAcervoPerfilListNew.add(acervoPerfilListNewAcervoPerfilToAttach);
            }
            acervoPerfilListNew = attachedAcervoPerfilListNew;
            tipoAcervoPerfil.setAcervoPerfilList(acervoPerfilListNew);
            tipoAcervoPerfil = em.merge(tipoAcervoPerfil);
            for (AcervoPerfil acervoPerfilListNewAcervoPerfil : acervoPerfilListNew) {
                if (!acervoPerfilListOld.contains(acervoPerfilListNewAcervoPerfil)) {
                    TipoAcervoPerfil oldTipoAcervoPerfilIdOfAcervoPerfilListNewAcervoPerfil = acervoPerfilListNewAcervoPerfil.getTipoAcervoPerfilId();
                    acervoPerfilListNewAcervoPerfil.setTipoAcervoPerfilId(tipoAcervoPerfil);
                    acervoPerfilListNewAcervoPerfil = em.merge(acervoPerfilListNewAcervoPerfil);
                    if (oldTipoAcervoPerfilIdOfAcervoPerfilListNewAcervoPerfil != null && !oldTipoAcervoPerfilIdOfAcervoPerfilListNewAcervoPerfil.equals(tipoAcervoPerfil)) {
                        oldTipoAcervoPerfilIdOfAcervoPerfilListNewAcervoPerfil.getAcervoPerfilList().remove(acervoPerfilListNewAcervoPerfil);
                        oldTipoAcervoPerfilIdOfAcervoPerfilListNewAcervoPerfil = em.merge(oldTipoAcervoPerfilIdOfAcervoPerfilListNewAcervoPerfil);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoAcervoPerfil.getId();
                if (findTipoAcervoPerfil(id) == null) {
                    throw new NonexistentEntityException("The tipoAcervoPerfil with id " + id + " no longer exists.");
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
            TipoAcervoPerfil tipoAcervoPerfil;
            try {
                tipoAcervoPerfil = em.getReference(TipoAcervoPerfil.class, id);
                tipoAcervoPerfil.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoAcervoPerfil with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<AcervoPerfil> acervoPerfilListOrphanCheck = tipoAcervoPerfil.getAcervoPerfilList();
            for (AcervoPerfil acervoPerfilListOrphanCheckAcervoPerfil : acervoPerfilListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoAcervoPerfil (" + tipoAcervoPerfil + ") cannot be destroyed since the AcervoPerfil " + acervoPerfilListOrphanCheckAcervoPerfil + " in its acervoPerfilList field has a non-nullable tipoAcervoPerfilId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoAcervoPerfil);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoAcervoPerfil> findTipoAcervoPerfilEntities() {
        return findTipoAcervoPerfilEntities(true, -1, -1);
    }

    public List<TipoAcervoPerfil> findTipoAcervoPerfilEntities(int maxResults, int firstResult) {
        return findTipoAcervoPerfilEntities(false, maxResults, firstResult);
    }

    private List<TipoAcervoPerfil> findTipoAcervoPerfilEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoAcervoPerfil.class));
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

    public TipoAcervoPerfil findTipoAcervoPerfil(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoAcervoPerfil.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoAcervoPerfilCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoAcervoPerfil> rt = cq.from(TipoAcervoPerfil.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
