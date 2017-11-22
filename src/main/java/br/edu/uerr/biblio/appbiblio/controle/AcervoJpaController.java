/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uerr.biblio.appbiblio.controle;

import br.edu.uerr.biblio.appbiblio.controle.exceptions.IllegalOrphanException;
import br.edu.uerr.biblio.appbiblio.controle.exceptions.NonexistentEntityException;
import br.edu.uerr.biblio.appbiblio.controle.exceptions.PreexistingEntityException;
import br.edu.uerr.biblio.appbiblio.modelo.Acervo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.edu.uerr.biblio.appbiblio.modelo.AcervoPerfil;
import br.edu.uerr.biblio.appbiblio.modelo.Genero;
import br.edu.uerr.biblio.appbiblio.modelo.Locacoes;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jklac
 */
public class AcervoJpaController implements Serializable {

    public AcervoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Acervo acervo) throws PreexistingEntityException, Exception {
        if (acervo.getLocacoesList() == null) {
            acervo.setLocacoesList(new ArrayList<Locacoes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AcervoPerfil acervoPerfilId = acervo.getAcervoPerfilId();
            if (acervoPerfilId != null) {
                acervoPerfilId = em.getReference(acervoPerfilId.getClass(), acervoPerfilId.getId());
                acervo.setAcervoPerfilId(acervoPerfilId);
            }
            Genero generoId = acervo.getGeneroId();
            if (generoId != null) {
                generoId = em.getReference(generoId.getClass(), generoId.getId());
                acervo.setGeneroId(generoId);
            }
            List<Locacoes> attachedLocacoesList = new ArrayList<Locacoes>();
            for (Locacoes locacoesListLocacoesToAttach : acervo.getLocacoesList()) {
                locacoesListLocacoesToAttach = em.getReference(locacoesListLocacoesToAttach.getClass(), locacoesListLocacoesToAttach.getId());
                attachedLocacoesList.add(locacoesListLocacoesToAttach);
            }
            acervo.setLocacoesList(attachedLocacoesList);
            em.persist(acervo);
            if (acervoPerfilId != null) {
                acervoPerfilId.getAcervoList().add(acervo);
                acervoPerfilId = em.merge(acervoPerfilId);
            }
            if (generoId != null) {
                generoId.getAcervoList().add(acervo);
                generoId = em.merge(generoId);
            }
            for (Locacoes locacoesListLocacoes : acervo.getLocacoesList()) {
                Acervo oldAcervoIdOfLocacoesListLocacoes = locacoesListLocacoes.getAcervoId();
                locacoesListLocacoes.setAcervoId(acervo);
                locacoesListLocacoes = em.merge(locacoesListLocacoes);
                if (oldAcervoIdOfLocacoesListLocacoes != null) {
                    oldAcervoIdOfLocacoesListLocacoes.getLocacoesList().remove(locacoesListLocacoes);
                    oldAcervoIdOfLocacoesListLocacoes = em.merge(oldAcervoIdOfLocacoesListLocacoes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAcervo(acervo.getId()) != null) {
                throw new PreexistingEntityException("Acervo " + acervo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Acervo acervo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Acervo persistentAcervo = em.find(Acervo.class, acervo.getId());
            AcervoPerfil acervoPerfilIdOld = persistentAcervo.getAcervoPerfilId();
            AcervoPerfil acervoPerfilIdNew = acervo.getAcervoPerfilId();
            Genero generoIdOld = persistentAcervo.getGeneroId();
            Genero generoIdNew = acervo.getGeneroId();
            List<Locacoes> locacoesListOld = persistentAcervo.getLocacoesList();
            List<Locacoes> locacoesListNew = acervo.getLocacoesList();
            List<String> illegalOrphanMessages = null;
            for (Locacoes locacoesListOldLocacoes : locacoesListOld) {
                if (!locacoesListNew.contains(locacoesListOldLocacoes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Locacoes " + locacoesListOldLocacoes + " since its acervoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (acervoPerfilIdNew != null) {
                acervoPerfilIdNew = em.getReference(acervoPerfilIdNew.getClass(), acervoPerfilIdNew.getId());
                acervo.setAcervoPerfilId(acervoPerfilIdNew);
            }
            if (generoIdNew != null) {
                generoIdNew = em.getReference(generoIdNew.getClass(), generoIdNew.getId());
                acervo.setGeneroId(generoIdNew);
            }
            List<Locacoes> attachedLocacoesListNew = new ArrayList<Locacoes>();
            for (Locacoes locacoesListNewLocacoesToAttach : locacoesListNew) {
                locacoesListNewLocacoesToAttach = em.getReference(locacoesListNewLocacoesToAttach.getClass(), locacoesListNewLocacoesToAttach.getId());
                attachedLocacoesListNew.add(locacoesListNewLocacoesToAttach);
            }
            locacoesListNew = attachedLocacoesListNew;
            acervo.setLocacoesList(locacoesListNew);
            acervo = em.merge(acervo);
            if (acervoPerfilIdOld != null && !acervoPerfilIdOld.equals(acervoPerfilIdNew)) {
                acervoPerfilIdOld.getAcervoList().remove(acervo);
                acervoPerfilIdOld = em.merge(acervoPerfilIdOld);
            }
            if (acervoPerfilIdNew != null && !acervoPerfilIdNew.equals(acervoPerfilIdOld)) {
                acervoPerfilIdNew.getAcervoList().add(acervo);
                acervoPerfilIdNew = em.merge(acervoPerfilIdNew);
            }
            if (generoIdOld != null && !generoIdOld.equals(generoIdNew)) {
                generoIdOld.getAcervoList().remove(acervo);
                generoIdOld = em.merge(generoIdOld);
            }
            if (generoIdNew != null && !generoIdNew.equals(generoIdOld)) {
                generoIdNew.getAcervoList().add(acervo);
                generoIdNew = em.merge(generoIdNew);
            }
            for (Locacoes locacoesListNewLocacoes : locacoesListNew) {
                if (!locacoesListOld.contains(locacoesListNewLocacoes)) {
                    Acervo oldAcervoIdOfLocacoesListNewLocacoes = locacoesListNewLocacoes.getAcervoId();
                    locacoesListNewLocacoes.setAcervoId(acervo);
                    locacoesListNewLocacoes = em.merge(locacoesListNewLocacoes);
                    if (oldAcervoIdOfLocacoesListNewLocacoes != null && !oldAcervoIdOfLocacoesListNewLocacoes.equals(acervo)) {
                        oldAcervoIdOfLocacoesListNewLocacoes.getLocacoesList().remove(locacoesListNewLocacoes);
                        oldAcervoIdOfLocacoesListNewLocacoes = em.merge(oldAcervoIdOfLocacoesListNewLocacoes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = acervo.getId();
                if (findAcervo(id) == null) {
                    throw new NonexistentEntityException("The acervo with id " + id + " no longer exists.");
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
            Acervo acervo;
            try {
                acervo = em.getReference(Acervo.class, id);
                acervo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The acervo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Locacoes> locacoesListOrphanCheck = acervo.getLocacoesList();
            for (Locacoes locacoesListOrphanCheckLocacoes : locacoesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Acervo (" + acervo + ") cannot be destroyed since the Locacoes " + locacoesListOrphanCheckLocacoes + " in its locacoesList field has a non-nullable acervoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            AcervoPerfil acervoPerfilId = acervo.getAcervoPerfilId();
            if (acervoPerfilId != null) {
                acervoPerfilId.getAcervoList().remove(acervo);
                acervoPerfilId = em.merge(acervoPerfilId);
            }
            Genero generoId = acervo.getGeneroId();
            if (generoId != null) {
                generoId.getAcervoList().remove(acervo);
                generoId = em.merge(generoId);
            }
            em.remove(acervo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Acervo> findAcervoEntities() {
        return findAcervoEntities(true, -1, -1);
    }

    public List<Acervo> findAcervoEntities(int maxResults, int firstResult) {
        return findAcervoEntities(false, maxResults, firstResult);
    }

    private List<Acervo> findAcervoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Acervo.class));
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

    public Acervo findAcervo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Acervo.class, id);
        } finally {
            em.close();
        }
    }

    public int getAcervoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Acervo> rt = cq.from(Acervo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
