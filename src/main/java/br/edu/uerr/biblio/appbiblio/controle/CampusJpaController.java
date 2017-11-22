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
import br.edu.uerr.biblio.appbiblio.modelo.Enderecos;
import br.edu.uerr.biblio.appbiblio.modelo.AcervoPerfil;
import br.edu.uerr.biblio.appbiblio.modelo.Campus;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jklac
 */
public class CampusJpaController implements Serializable {

    public CampusJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Campus campus) throws PreexistingEntityException, Exception {
        if (campus.getAcervoPerfilList() == null) {
            campus.setAcervoPerfilList(new ArrayList<AcervoPerfil>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Enderecos enderecosId = campus.getEnderecosId();
            if (enderecosId != null) {
                enderecosId = em.getReference(enderecosId.getClass(), enderecosId.getId());
                campus.setEnderecosId(enderecosId);
            }
            List<AcervoPerfil> attachedAcervoPerfilList = new ArrayList<AcervoPerfil>();
            for (AcervoPerfil acervoPerfilListAcervoPerfilToAttach : campus.getAcervoPerfilList()) {
                acervoPerfilListAcervoPerfilToAttach = em.getReference(acervoPerfilListAcervoPerfilToAttach.getClass(), acervoPerfilListAcervoPerfilToAttach.getId());
                attachedAcervoPerfilList.add(acervoPerfilListAcervoPerfilToAttach);
            }
            campus.setAcervoPerfilList(attachedAcervoPerfilList);
            em.persist(campus);
            if (enderecosId != null) {
                enderecosId.getCampusList().add(campus);
                enderecosId = em.merge(enderecosId);
            }
            for (AcervoPerfil acervoPerfilListAcervoPerfil : campus.getAcervoPerfilList()) {
                Campus oldCampusIdOfAcervoPerfilListAcervoPerfil = acervoPerfilListAcervoPerfil.getCampusId();
                acervoPerfilListAcervoPerfil.setCampusId(campus);
                acervoPerfilListAcervoPerfil = em.merge(acervoPerfilListAcervoPerfil);
                if (oldCampusIdOfAcervoPerfilListAcervoPerfil != null) {
                    oldCampusIdOfAcervoPerfilListAcervoPerfil.getAcervoPerfilList().remove(acervoPerfilListAcervoPerfil);
                    oldCampusIdOfAcervoPerfilListAcervoPerfil = em.merge(oldCampusIdOfAcervoPerfilListAcervoPerfil);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCampus(campus.getId()) != null) {
                throw new PreexistingEntityException("Campus " + campus + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Campus campus) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Campus persistentCampus = em.find(Campus.class, campus.getId());
            Enderecos enderecosIdOld = persistentCampus.getEnderecosId();
            Enderecos enderecosIdNew = campus.getEnderecosId();
            List<AcervoPerfil> acervoPerfilListOld = persistentCampus.getAcervoPerfilList();
            List<AcervoPerfil> acervoPerfilListNew = campus.getAcervoPerfilList();
            List<String> illegalOrphanMessages = null;
            for (AcervoPerfil acervoPerfilListOldAcervoPerfil : acervoPerfilListOld) {
                if (!acervoPerfilListNew.contains(acervoPerfilListOldAcervoPerfil)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AcervoPerfil " + acervoPerfilListOldAcervoPerfil + " since its campusId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (enderecosIdNew != null) {
                enderecosIdNew = em.getReference(enderecosIdNew.getClass(), enderecosIdNew.getId());
                campus.setEnderecosId(enderecosIdNew);
            }
            List<AcervoPerfil> attachedAcervoPerfilListNew = new ArrayList<AcervoPerfil>();
            for (AcervoPerfil acervoPerfilListNewAcervoPerfilToAttach : acervoPerfilListNew) {
                acervoPerfilListNewAcervoPerfilToAttach = em.getReference(acervoPerfilListNewAcervoPerfilToAttach.getClass(), acervoPerfilListNewAcervoPerfilToAttach.getId());
                attachedAcervoPerfilListNew.add(acervoPerfilListNewAcervoPerfilToAttach);
            }
            acervoPerfilListNew = attachedAcervoPerfilListNew;
            campus.setAcervoPerfilList(acervoPerfilListNew);
            campus = em.merge(campus);
            if (enderecosIdOld != null && !enderecosIdOld.equals(enderecosIdNew)) {
                enderecosIdOld.getCampusList().remove(campus);
                enderecosIdOld = em.merge(enderecosIdOld);
            }
            if (enderecosIdNew != null && !enderecosIdNew.equals(enderecosIdOld)) {
                enderecosIdNew.getCampusList().add(campus);
                enderecosIdNew = em.merge(enderecosIdNew);
            }
            for (AcervoPerfil acervoPerfilListNewAcervoPerfil : acervoPerfilListNew) {
                if (!acervoPerfilListOld.contains(acervoPerfilListNewAcervoPerfil)) {
                    Campus oldCampusIdOfAcervoPerfilListNewAcervoPerfil = acervoPerfilListNewAcervoPerfil.getCampusId();
                    acervoPerfilListNewAcervoPerfil.setCampusId(campus);
                    acervoPerfilListNewAcervoPerfil = em.merge(acervoPerfilListNewAcervoPerfil);
                    if (oldCampusIdOfAcervoPerfilListNewAcervoPerfil != null && !oldCampusIdOfAcervoPerfilListNewAcervoPerfil.equals(campus)) {
                        oldCampusIdOfAcervoPerfilListNewAcervoPerfil.getAcervoPerfilList().remove(acervoPerfilListNewAcervoPerfil);
                        oldCampusIdOfAcervoPerfilListNewAcervoPerfil = em.merge(oldCampusIdOfAcervoPerfilListNewAcervoPerfil);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = campus.getId();
                if (findCampus(id) == null) {
                    throw new NonexistentEntityException("The campus with id " + id + " no longer exists.");
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
            Campus campus;
            try {
                campus = em.getReference(Campus.class, id);
                campus.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The campus with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<AcervoPerfil> acervoPerfilListOrphanCheck = campus.getAcervoPerfilList();
            for (AcervoPerfil acervoPerfilListOrphanCheckAcervoPerfil : acervoPerfilListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Campus (" + campus + ") cannot be destroyed since the AcervoPerfil " + acervoPerfilListOrphanCheckAcervoPerfil + " in its acervoPerfilList field has a non-nullable campusId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Enderecos enderecosId = campus.getEnderecosId();
            if (enderecosId != null) {
                enderecosId.getCampusList().remove(campus);
                enderecosId = em.merge(enderecosId);
            }
            em.remove(campus);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Campus> findCampusEntities() {
        return findCampusEntities(true, -1, -1);
    }

    public List<Campus> findCampusEntities(int maxResults, int firstResult) {
        return findCampusEntities(false, maxResults, firstResult);
    }

    private List<Campus> findCampusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Campus.class));
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

    public Campus findCampus(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Campus.class, id);
        } finally {
            em.close();
        }
    }

    public int getCampusCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Campus> rt = cq.from(Campus.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
