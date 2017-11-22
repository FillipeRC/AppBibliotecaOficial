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
import br.edu.uerr.biblio.appbiblio.modelo.Campus;
import br.edu.uerr.biblio.appbiblio.modelo.TipoAcervoPerfil;
import br.edu.uerr.biblio.appbiblio.modelo.Acervo;
import br.edu.uerr.biblio.appbiblio.modelo.AcervoPerfil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jklac
 */
public class AcervoPerfilJpaController implements Serializable {

    public AcervoPerfilJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AcervoPerfil acervoPerfil) throws PreexistingEntityException, Exception {
        if (acervoPerfil.getAcervoList() == null) {
            acervoPerfil.setAcervoList(new ArrayList<Acervo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Campus campusId = acervoPerfil.getCampusId();
            if (campusId != null) {
                campusId = em.getReference(campusId.getClass(), campusId.getId());
                acervoPerfil.setCampusId(campusId);
            }
            TipoAcervoPerfil tipoAcervoPerfilId = acervoPerfil.getTipoAcervoPerfilId();
            if (tipoAcervoPerfilId != null) {
                tipoAcervoPerfilId = em.getReference(tipoAcervoPerfilId.getClass(), tipoAcervoPerfilId.getId());
                acervoPerfil.setTipoAcervoPerfilId(tipoAcervoPerfilId);
            }
            List<Acervo> attachedAcervoList = new ArrayList<Acervo>();
            for (Acervo acervoListAcervoToAttach : acervoPerfil.getAcervoList()) {
                acervoListAcervoToAttach = em.getReference(acervoListAcervoToAttach.getClass(), acervoListAcervoToAttach.getId());
                attachedAcervoList.add(acervoListAcervoToAttach);
            }
            acervoPerfil.setAcervoList(attachedAcervoList);
            em.persist(acervoPerfil);
            if (campusId != null) {
                campusId.getAcervoPerfilList().add(acervoPerfil);
                campusId = em.merge(campusId);
            }
            if (tipoAcervoPerfilId != null) {
                tipoAcervoPerfilId.getAcervoPerfilList().add(acervoPerfil);
                tipoAcervoPerfilId = em.merge(tipoAcervoPerfilId);
            }
            for (Acervo acervoListAcervo : acervoPerfil.getAcervoList()) {
                AcervoPerfil oldAcervoPerfilIdOfAcervoListAcervo = acervoListAcervo.getAcervoPerfilId();
                acervoListAcervo.setAcervoPerfilId(acervoPerfil);
                acervoListAcervo = em.merge(acervoListAcervo);
                if (oldAcervoPerfilIdOfAcervoListAcervo != null) {
                    oldAcervoPerfilIdOfAcervoListAcervo.getAcervoList().remove(acervoListAcervo);
                    oldAcervoPerfilIdOfAcervoListAcervo = em.merge(oldAcervoPerfilIdOfAcervoListAcervo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAcervoPerfil(acervoPerfil.getId()) != null) {
                throw new PreexistingEntityException("AcervoPerfil " + acervoPerfil + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AcervoPerfil acervoPerfil) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AcervoPerfil persistentAcervoPerfil = em.find(AcervoPerfil.class, acervoPerfil.getId());
            Campus campusIdOld = persistentAcervoPerfil.getCampusId();
            Campus campusIdNew = acervoPerfil.getCampusId();
            TipoAcervoPerfil tipoAcervoPerfilIdOld = persistentAcervoPerfil.getTipoAcervoPerfilId();
            TipoAcervoPerfil tipoAcervoPerfilIdNew = acervoPerfil.getTipoAcervoPerfilId();
            List<Acervo> acervoListOld = persistentAcervoPerfil.getAcervoList();
            List<Acervo> acervoListNew = acervoPerfil.getAcervoList();
            List<String> illegalOrphanMessages = null;
            for (Acervo acervoListOldAcervo : acervoListOld) {
                if (!acervoListNew.contains(acervoListOldAcervo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Acervo " + acervoListOldAcervo + " since its acervoPerfilId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (campusIdNew != null) {
                campusIdNew = em.getReference(campusIdNew.getClass(), campusIdNew.getId());
                acervoPerfil.setCampusId(campusIdNew);
            }
            if (tipoAcervoPerfilIdNew != null) {
                tipoAcervoPerfilIdNew = em.getReference(tipoAcervoPerfilIdNew.getClass(), tipoAcervoPerfilIdNew.getId());
                acervoPerfil.setTipoAcervoPerfilId(tipoAcervoPerfilIdNew);
            }
            List<Acervo> attachedAcervoListNew = new ArrayList<Acervo>();
            for (Acervo acervoListNewAcervoToAttach : acervoListNew) {
                acervoListNewAcervoToAttach = em.getReference(acervoListNewAcervoToAttach.getClass(), acervoListNewAcervoToAttach.getId());
                attachedAcervoListNew.add(acervoListNewAcervoToAttach);
            }
            acervoListNew = attachedAcervoListNew;
            acervoPerfil.setAcervoList(acervoListNew);
            acervoPerfil = em.merge(acervoPerfil);
            if (campusIdOld != null && !campusIdOld.equals(campusIdNew)) {
                campusIdOld.getAcervoPerfilList().remove(acervoPerfil);
                campusIdOld = em.merge(campusIdOld);
            }
            if (campusIdNew != null && !campusIdNew.equals(campusIdOld)) {
                campusIdNew.getAcervoPerfilList().add(acervoPerfil);
                campusIdNew = em.merge(campusIdNew);
            }
            if (tipoAcervoPerfilIdOld != null && !tipoAcervoPerfilIdOld.equals(tipoAcervoPerfilIdNew)) {
                tipoAcervoPerfilIdOld.getAcervoPerfilList().remove(acervoPerfil);
                tipoAcervoPerfilIdOld = em.merge(tipoAcervoPerfilIdOld);
            }
            if (tipoAcervoPerfilIdNew != null && !tipoAcervoPerfilIdNew.equals(tipoAcervoPerfilIdOld)) {
                tipoAcervoPerfilIdNew.getAcervoPerfilList().add(acervoPerfil);
                tipoAcervoPerfilIdNew = em.merge(tipoAcervoPerfilIdNew);
            }
            for (Acervo acervoListNewAcervo : acervoListNew) {
                if (!acervoListOld.contains(acervoListNewAcervo)) {
                    AcervoPerfil oldAcervoPerfilIdOfAcervoListNewAcervo = acervoListNewAcervo.getAcervoPerfilId();
                    acervoListNewAcervo.setAcervoPerfilId(acervoPerfil);
                    acervoListNewAcervo = em.merge(acervoListNewAcervo);
                    if (oldAcervoPerfilIdOfAcervoListNewAcervo != null && !oldAcervoPerfilIdOfAcervoListNewAcervo.equals(acervoPerfil)) {
                        oldAcervoPerfilIdOfAcervoListNewAcervo.getAcervoList().remove(acervoListNewAcervo);
                        oldAcervoPerfilIdOfAcervoListNewAcervo = em.merge(oldAcervoPerfilIdOfAcervoListNewAcervo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = acervoPerfil.getId();
                if (findAcervoPerfil(id) == null) {
                    throw new NonexistentEntityException("The acervoPerfil with id " + id + " no longer exists.");
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
            AcervoPerfil acervoPerfil;
            try {
                acervoPerfil = em.getReference(AcervoPerfil.class, id);
                acervoPerfil.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The acervoPerfil with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Acervo> acervoListOrphanCheck = acervoPerfil.getAcervoList();
            for (Acervo acervoListOrphanCheckAcervo : acervoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AcervoPerfil (" + acervoPerfil + ") cannot be destroyed since the Acervo " + acervoListOrphanCheckAcervo + " in its acervoList field has a non-nullable acervoPerfilId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Campus campusId = acervoPerfil.getCampusId();
            if (campusId != null) {
                campusId.getAcervoPerfilList().remove(acervoPerfil);
                campusId = em.merge(campusId);
            }
            TipoAcervoPerfil tipoAcervoPerfilId = acervoPerfil.getTipoAcervoPerfilId();
            if (tipoAcervoPerfilId != null) {
                tipoAcervoPerfilId.getAcervoPerfilList().remove(acervoPerfil);
                tipoAcervoPerfilId = em.merge(tipoAcervoPerfilId);
            }
            em.remove(acervoPerfil);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AcervoPerfil> findAcervoPerfilEntities() {
        return findAcervoPerfilEntities(true, -1, -1);
    }

    public List<AcervoPerfil> findAcervoPerfilEntities(int maxResults, int firstResult) {
        return findAcervoPerfilEntities(false, maxResults, firstResult);
    }

    private List<AcervoPerfil> findAcervoPerfilEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AcervoPerfil.class));
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

    public AcervoPerfil findAcervoPerfil(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AcervoPerfil.class, id);
        } finally {
            em.close();
        }
    }

    public int getAcervoPerfilCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AcervoPerfil> rt = cq.from(AcervoPerfil.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
