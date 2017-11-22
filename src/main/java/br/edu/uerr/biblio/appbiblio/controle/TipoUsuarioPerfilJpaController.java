/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uerr.biblio.appbiblio.controle;

import br.edu.uerr.biblio.appbiblio.controle.exceptions.IllegalOrphanException;
import br.edu.uerr.biblio.appbiblio.controle.exceptions.NonexistentEntityException;
import br.edu.uerr.biblio.appbiblio.controle.exceptions.PreexistingEntityException;
import br.edu.uerr.biblio.appbiblio.modelo.TipoUsuarioPerfil;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.edu.uerr.biblio.appbiblio.modelo.UsuarioPerfil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jklac
 */
public class TipoUsuarioPerfilJpaController implements Serializable {

    public TipoUsuarioPerfilJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoUsuarioPerfil tipoUsuarioPerfil) throws PreexistingEntityException, Exception {
        if (tipoUsuarioPerfil.getUsuarioPerfilList() == null) {
            tipoUsuarioPerfil.setUsuarioPerfilList(new ArrayList<UsuarioPerfil>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<UsuarioPerfil> attachedUsuarioPerfilList = new ArrayList<UsuarioPerfil>();
            for (UsuarioPerfil usuarioPerfilListUsuarioPerfilToAttach : tipoUsuarioPerfil.getUsuarioPerfilList()) {
                usuarioPerfilListUsuarioPerfilToAttach = em.getReference(usuarioPerfilListUsuarioPerfilToAttach.getClass(), usuarioPerfilListUsuarioPerfilToAttach.getId());
                attachedUsuarioPerfilList.add(usuarioPerfilListUsuarioPerfilToAttach);
            }
            tipoUsuarioPerfil.setUsuarioPerfilList(attachedUsuarioPerfilList);
            em.persist(tipoUsuarioPerfil);
            for (UsuarioPerfil usuarioPerfilListUsuarioPerfil : tipoUsuarioPerfil.getUsuarioPerfilList()) {
                TipoUsuarioPerfil oldTipoUsuarioPerfilIdOfUsuarioPerfilListUsuarioPerfil = usuarioPerfilListUsuarioPerfil.getTipoUsuarioPerfilId();
                usuarioPerfilListUsuarioPerfil.setTipoUsuarioPerfilId(tipoUsuarioPerfil);
                usuarioPerfilListUsuarioPerfil = em.merge(usuarioPerfilListUsuarioPerfil);
                if (oldTipoUsuarioPerfilIdOfUsuarioPerfilListUsuarioPerfil != null) {
                    oldTipoUsuarioPerfilIdOfUsuarioPerfilListUsuarioPerfil.getUsuarioPerfilList().remove(usuarioPerfilListUsuarioPerfil);
                    oldTipoUsuarioPerfilIdOfUsuarioPerfilListUsuarioPerfil = em.merge(oldTipoUsuarioPerfilIdOfUsuarioPerfilListUsuarioPerfil);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoUsuarioPerfil(tipoUsuarioPerfil.getId()) != null) {
                throw new PreexistingEntityException("TipoUsuarioPerfil " + tipoUsuarioPerfil + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoUsuarioPerfil tipoUsuarioPerfil) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoUsuarioPerfil persistentTipoUsuarioPerfil = em.find(TipoUsuarioPerfil.class, tipoUsuarioPerfil.getId());
            List<UsuarioPerfil> usuarioPerfilListOld = persistentTipoUsuarioPerfil.getUsuarioPerfilList();
            List<UsuarioPerfil> usuarioPerfilListNew = tipoUsuarioPerfil.getUsuarioPerfilList();
            List<String> illegalOrphanMessages = null;
            for (UsuarioPerfil usuarioPerfilListOldUsuarioPerfil : usuarioPerfilListOld) {
                if (!usuarioPerfilListNew.contains(usuarioPerfilListOldUsuarioPerfil)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsuarioPerfil " + usuarioPerfilListOldUsuarioPerfil + " since its tipoUsuarioPerfilId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<UsuarioPerfil> attachedUsuarioPerfilListNew = new ArrayList<UsuarioPerfil>();
            for (UsuarioPerfil usuarioPerfilListNewUsuarioPerfilToAttach : usuarioPerfilListNew) {
                usuarioPerfilListNewUsuarioPerfilToAttach = em.getReference(usuarioPerfilListNewUsuarioPerfilToAttach.getClass(), usuarioPerfilListNewUsuarioPerfilToAttach.getId());
                attachedUsuarioPerfilListNew.add(usuarioPerfilListNewUsuarioPerfilToAttach);
            }
            usuarioPerfilListNew = attachedUsuarioPerfilListNew;
            tipoUsuarioPerfil.setUsuarioPerfilList(usuarioPerfilListNew);
            tipoUsuarioPerfil = em.merge(tipoUsuarioPerfil);
            for (UsuarioPerfil usuarioPerfilListNewUsuarioPerfil : usuarioPerfilListNew) {
                if (!usuarioPerfilListOld.contains(usuarioPerfilListNewUsuarioPerfil)) {
                    TipoUsuarioPerfil oldTipoUsuarioPerfilIdOfUsuarioPerfilListNewUsuarioPerfil = usuarioPerfilListNewUsuarioPerfil.getTipoUsuarioPerfilId();
                    usuarioPerfilListNewUsuarioPerfil.setTipoUsuarioPerfilId(tipoUsuarioPerfil);
                    usuarioPerfilListNewUsuarioPerfil = em.merge(usuarioPerfilListNewUsuarioPerfil);
                    if (oldTipoUsuarioPerfilIdOfUsuarioPerfilListNewUsuarioPerfil != null && !oldTipoUsuarioPerfilIdOfUsuarioPerfilListNewUsuarioPerfil.equals(tipoUsuarioPerfil)) {
                        oldTipoUsuarioPerfilIdOfUsuarioPerfilListNewUsuarioPerfil.getUsuarioPerfilList().remove(usuarioPerfilListNewUsuarioPerfil);
                        oldTipoUsuarioPerfilIdOfUsuarioPerfilListNewUsuarioPerfil = em.merge(oldTipoUsuarioPerfilIdOfUsuarioPerfilListNewUsuarioPerfil);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoUsuarioPerfil.getId();
                if (findTipoUsuarioPerfil(id) == null) {
                    throw new NonexistentEntityException("The tipoUsuarioPerfil with id " + id + " no longer exists.");
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
            TipoUsuarioPerfil tipoUsuarioPerfil;
            try {
                tipoUsuarioPerfil = em.getReference(TipoUsuarioPerfil.class, id);
                tipoUsuarioPerfil.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoUsuarioPerfil with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<UsuarioPerfil> usuarioPerfilListOrphanCheck = tipoUsuarioPerfil.getUsuarioPerfilList();
            for (UsuarioPerfil usuarioPerfilListOrphanCheckUsuarioPerfil : usuarioPerfilListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoUsuarioPerfil (" + tipoUsuarioPerfil + ") cannot be destroyed since the UsuarioPerfil " + usuarioPerfilListOrphanCheckUsuarioPerfil + " in its usuarioPerfilList field has a non-nullable tipoUsuarioPerfilId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoUsuarioPerfil);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoUsuarioPerfil> findTipoUsuarioPerfilEntities() {
        return findTipoUsuarioPerfilEntities(true, -1, -1);
    }

    public List<TipoUsuarioPerfil> findTipoUsuarioPerfilEntities(int maxResults, int firstResult) {
        return findTipoUsuarioPerfilEntities(false, maxResults, firstResult);
    }

    private List<TipoUsuarioPerfil> findTipoUsuarioPerfilEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoUsuarioPerfil.class));
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

    public TipoUsuarioPerfil findTipoUsuarioPerfil(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoUsuarioPerfil.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoUsuarioPerfilCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoUsuarioPerfil> rt = cq.from(TipoUsuarioPerfil.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
