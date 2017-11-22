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
import br.edu.uerr.biblio.appbiblio.modelo.TipoUsuarioPerfil;
import br.edu.uerr.biblio.appbiblio.modelo.Usuario;
import br.edu.uerr.biblio.appbiblio.modelo.Locacao;
import br.edu.uerr.biblio.appbiblio.modelo.UsuarioPerfil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jklac
 */
public class UsuarioPerfilJpaController implements Serializable {

    public UsuarioPerfilJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuarioPerfil usuarioPerfil) throws PreexistingEntityException, Exception {
        if (usuarioPerfil.getLocacaoList() == null) {
            usuarioPerfil.setLocacaoList(new ArrayList<Locacao>());
        }
        if (usuarioPerfil.getLocacaoList1() == null) {
            usuarioPerfil.setLocacaoList1(new ArrayList<Locacao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoUsuarioPerfil tipoUsuarioPerfilId = usuarioPerfil.getTipoUsuarioPerfilId();
            if (tipoUsuarioPerfilId != null) {
                tipoUsuarioPerfilId = em.getReference(tipoUsuarioPerfilId.getClass(), tipoUsuarioPerfilId.getId());
                usuarioPerfil.setTipoUsuarioPerfilId(tipoUsuarioPerfilId);
            }
            Usuario usuarioId = usuarioPerfil.getUsuarioId();
            if (usuarioId != null) {
                usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getId());
                usuarioPerfil.setUsuarioId(usuarioId);
            }
            List<Locacao> attachedLocacaoList = new ArrayList<Locacao>();
            for (Locacao locacaoListLocacaoToAttach : usuarioPerfil.getLocacaoList()) {
                locacaoListLocacaoToAttach = em.getReference(locacaoListLocacaoToAttach.getClass(), locacaoListLocacaoToAttach.getId());
                attachedLocacaoList.add(locacaoListLocacaoToAttach);
            }
            usuarioPerfil.setLocacaoList(attachedLocacaoList);
            List<Locacao> attachedLocacaoList1 = new ArrayList<Locacao>();
            for (Locacao locacaoList1LocacaoToAttach : usuarioPerfil.getLocacaoList1()) {
                locacaoList1LocacaoToAttach = em.getReference(locacaoList1LocacaoToAttach.getClass(), locacaoList1LocacaoToAttach.getId());
                attachedLocacaoList1.add(locacaoList1LocacaoToAttach);
            }
            usuarioPerfil.setLocacaoList1(attachedLocacaoList1);
            em.persist(usuarioPerfil);
            if (tipoUsuarioPerfilId != null) {
                tipoUsuarioPerfilId.getUsuarioPerfilList().add(usuarioPerfil);
                tipoUsuarioPerfilId = em.merge(tipoUsuarioPerfilId);
            }
            if (usuarioId != null) {
                usuarioId.getUsuarioPerfilList().add(usuarioPerfil);
                usuarioId = em.merge(usuarioId);
            }
            for (Locacao locacaoListLocacao : usuarioPerfil.getLocacaoList()) {
                UsuarioPerfil oldUsuarioPerfilIdOfLocacaoListLocacao = locacaoListLocacao.getUsuarioPerfilId();
                locacaoListLocacao.setUsuarioPerfilId(usuarioPerfil);
                locacaoListLocacao = em.merge(locacaoListLocacao);
                if (oldUsuarioPerfilIdOfLocacaoListLocacao != null) {
                    oldUsuarioPerfilIdOfLocacaoListLocacao.getLocacaoList().remove(locacaoListLocacao);
                    oldUsuarioPerfilIdOfLocacaoListLocacao = em.merge(oldUsuarioPerfilIdOfLocacaoListLocacao);
                }
            }
            for (Locacao locacaoList1Locacao : usuarioPerfil.getLocacaoList1()) {
                UsuarioPerfil oldFuncionarioPerfilIdOfLocacaoList1Locacao = locacaoList1Locacao.getFuncionarioPerfilId();
                locacaoList1Locacao.setFuncionarioPerfilId(usuarioPerfil);
                locacaoList1Locacao = em.merge(locacaoList1Locacao);
                if (oldFuncionarioPerfilIdOfLocacaoList1Locacao != null) {
                    oldFuncionarioPerfilIdOfLocacaoList1Locacao.getLocacaoList1().remove(locacaoList1Locacao);
                    oldFuncionarioPerfilIdOfLocacaoList1Locacao = em.merge(oldFuncionarioPerfilIdOfLocacaoList1Locacao);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuarioPerfil(usuarioPerfil.getId()) != null) {
                throw new PreexistingEntityException("UsuarioPerfil " + usuarioPerfil + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuarioPerfil usuarioPerfil) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UsuarioPerfil persistentUsuarioPerfil = em.find(UsuarioPerfil.class, usuarioPerfil.getId());
            TipoUsuarioPerfil tipoUsuarioPerfilIdOld = persistentUsuarioPerfil.getTipoUsuarioPerfilId();
            TipoUsuarioPerfil tipoUsuarioPerfilIdNew = usuarioPerfil.getTipoUsuarioPerfilId();
            Usuario usuarioIdOld = persistentUsuarioPerfil.getUsuarioId();
            Usuario usuarioIdNew = usuarioPerfil.getUsuarioId();
            List<Locacao> locacaoListOld = persistentUsuarioPerfil.getLocacaoList();
            List<Locacao> locacaoListNew = usuarioPerfil.getLocacaoList();
            List<Locacao> locacaoList1Old = persistentUsuarioPerfil.getLocacaoList1();
            List<Locacao> locacaoList1New = usuarioPerfil.getLocacaoList1();
            List<String> illegalOrphanMessages = null;
            for (Locacao locacaoListOldLocacao : locacaoListOld) {
                if (!locacaoListNew.contains(locacaoListOldLocacao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Locacao " + locacaoListOldLocacao + " since its usuarioPerfilId field is not nullable.");
                }
            }
            for (Locacao locacaoList1OldLocacao : locacaoList1Old) {
                if (!locacaoList1New.contains(locacaoList1OldLocacao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Locacao " + locacaoList1OldLocacao + " since its funcionarioPerfilId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tipoUsuarioPerfilIdNew != null) {
                tipoUsuarioPerfilIdNew = em.getReference(tipoUsuarioPerfilIdNew.getClass(), tipoUsuarioPerfilIdNew.getId());
                usuarioPerfil.setTipoUsuarioPerfilId(tipoUsuarioPerfilIdNew);
            }
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getId());
                usuarioPerfil.setUsuarioId(usuarioIdNew);
            }
            List<Locacao> attachedLocacaoListNew = new ArrayList<Locacao>();
            for (Locacao locacaoListNewLocacaoToAttach : locacaoListNew) {
                locacaoListNewLocacaoToAttach = em.getReference(locacaoListNewLocacaoToAttach.getClass(), locacaoListNewLocacaoToAttach.getId());
                attachedLocacaoListNew.add(locacaoListNewLocacaoToAttach);
            }
            locacaoListNew = attachedLocacaoListNew;
            usuarioPerfil.setLocacaoList(locacaoListNew);
            List<Locacao> attachedLocacaoList1New = new ArrayList<Locacao>();
            for (Locacao locacaoList1NewLocacaoToAttach : locacaoList1New) {
                locacaoList1NewLocacaoToAttach = em.getReference(locacaoList1NewLocacaoToAttach.getClass(), locacaoList1NewLocacaoToAttach.getId());
                attachedLocacaoList1New.add(locacaoList1NewLocacaoToAttach);
            }
            locacaoList1New = attachedLocacaoList1New;
            usuarioPerfil.setLocacaoList1(locacaoList1New);
            usuarioPerfil = em.merge(usuarioPerfil);
            if (tipoUsuarioPerfilIdOld != null && !tipoUsuarioPerfilIdOld.equals(tipoUsuarioPerfilIdNew)) {
                tipoUsuarioPerfilIdOld.getUsuarioPerfilList().remove(usuarioPerfil);
                tipoUsuarioPerfilIdOld = em.merge(tipoUsuarioPerfilIdOld);
            }
            if (tipoUsuarioPerfilIdNew != null && !tipoUsuarioPerfilIdNew.equals(tipoUsuarioPerfilIdOld)) {
                tipoUsuarioPerfilIdNew.getUsuarioPerfilList().add(usuarioPerfil);
                tipoUsuarioPerfilIdNew = em.merge(tipoUsuarioPerfilIdNew);
            }
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getUsuarioPerfilList().remove(usuarioPerfil);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getUsuarioPerfilList().add(usuarioPerfil);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            for (Locacao locacaoListNewLocacao : locacaoListNew) {
                if (!locacaoListOld.contains(locacaoListNewLocacao)) {
                    UsuarioPerfil oldUsuarioPerfilIdOfLocacaoListNewLocacao = locacaoListNewLocacao.getUsuarioPerfilId();
                    locacaoListNewLocacao.setUsuarioPerfilId(usuarioPerfil);
                    locacaoListNewLocacao = em.merge(locacaoListNewLocacao);
                    if (oldUsuarioPerfilIdOfLocacaoListNewLocacao != null && !oldUsuarioPerfilIdOfLocacaoListNewLocacao.equals(usuarioPerfil)) {
                        oldUsuarioPerfilIdOfLocacaoListNewLocacao.getLocacaoList().remove(locacaoListNewLocacao);
                        oldUsuarioPerfilIdOfLocacaoListNewLocacao = em.merge(oldUsuarioPerfilIdOfLocacaoListNewLocacao);
                    }
                }
            }
            for (Locacao locacaoList1NewLocacao : locacaoList1New) {
                if (!locacaoList1Old.contains(locacaoList1NewLocacao)) {
                    UsuarioPerfil oldFuncionarioPerfilIdOfLocacaoList1NewLocacao = locacaoList1NewLocacao.getFuncionarioPerfilId();
                    locacaoList1NewLocacao.setFuncionarioPerfilId(usuarioPerfil);
                    locacaoList1NewLocacao = em.merge(locacaoList1NewLocacao);
                    if (oldFuncionarioPerfilIdOfLocacaoList1NewLocacao != null && !oldFuncionarioPerfilIdOfLocacaoList1NewLocacao.equals(usuarioPerfil)) {
                        oldFuncionarioPerfilIdOfLocacaoList1NewLocacao.getLocacaoList1().remove(locacaoList1NewLocacao);
                        oldFuncionarioPerfilIdOfLocacaoList1NewLocacao = em.merge(oldFuncionarioPerfilIdOfLocacaoList1NewLocacao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarioPerfil.getId();
                if (findUsuarioPerfil(id) == null) {
                    throw new NonexistentEntityException("The usuarioPerfil with id " + id + " no longer exists.");
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
            UsuarioPerfil usuarioPerfil;
            try {
                usuarioPerfil = em.getReference(UsuarioPerfil.class, id);
                usuarioPerfil.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarioPerfil with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Locacao> locacaoListOrphanCheck = usuarioPerfil.getLocacaoList();
            for (Locacao locacaoListOrphanCheckLocacao : locacaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This UsuarioPerfil (" + usuarioPerfil + ") cannot be destroyed since the Locacao " + locacaoListOrphanCheckLocacao + " in its locacaoList field has a non-nullable usuarioPerfilId field.");
            }
            List<Locacao> locacaoList1OrphanCheck = usuarioPerfil.getLocacaoList1();
            for (Locacao locacaoList1OrphanCheckLocacao : locacaoList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This UsuarioPerfil (" + usuarioPerfil + ") cannot be destroyed since the Locacao " + locacaoList1OrphanCheckLocacao + " in its locacaoList1 field has a non-nullable funcionarioPerfilId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoUsuarioPerfil tipoUsuarioPerfilId = usuarioPerfil.getTipoUsuarioPerfilId();
            if (tipoUsuarioPerfilId != null) {
                tipoUsuarioPerfilId.getUsuarioPerfilList().remove(usuarioPerfil);
                tipoUsuarioPerfilId = em.merge(tipoUsuarioPerfilId);
            }
            Usuario usuarioId = usuarioPerfil.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getUsuarioPerfilList().remove(usuarioPerfil);
                usuarioId = em.merge(usuarioId);
            }
            em.remove(usuarioPerfil);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UsuarioPerfil> findUsuarioPerfilEntities() {
        return findUsuarioPerfilEntities(true, -1, -1);
    }

    public List<UsuarioPerfil> findUsuarioPerfilEntities(int maxResults, int firstResult) {
        return findUsuarioPerfilEntities(false, maxResults, firstResult);
    }

    private List<UsuarioPerfil> findUsuarioPerfilEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuarioPerfil.class));
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

    public UsuarioPerfil findUsuarioPerfil(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuarioPerfil.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioPerfilCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuarioPerfil> rt = cq.from(UsuarioPerfil.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
