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
import br.edu.uerr.biblio.appbiblio.modelo.UsuarioPerfil;
import br.edu.uerr.biblio.appbiblio.modelo.Flag;
import br.edu.uerr.biblio.appbiblio.modelo.Locacao;
import java.util.ArrayList;
import java.util.List;
import br.edu.uerr.biblio.appbiblio.modelo.Locacoes;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jklac
 */
public class LocacaoJpaController implements Serializable {

    public LocacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Locacao locacao) throws PreexistingEntityException, Exception {
        if (locacao.getFlagList() == null) {
            locacao.setFlagList(new ArrayList<Flag>());
        }
        if (locacao.getLocacoesList() == null) {
            locacao.setLocacoesList(new ArrayList<Locacoes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UsuarioPerfil usuarioPerfilId = locacao.getUsuarioPerfilId();
            if (usuarioPerfilId != null) {
                usuarioPerfilId = em.getReference(usuarioPerfilId.getClass(), usuarioPerfilId.getId());
                locacao.setUsuarioPerfilId(usuarioPerfilId);
            }
            UsuarioPerfil funcionarioPerfilId = locacao.getFuncionarioPerfilId();
            if (funcionarioPerfilId != null) {
                funcionarioPerfilId = em.getReference(funcionarioPerfilId.getClass(), funcionarioPerfilId.getId());
                locacao.setFuncionarioPerfilId(funcionarioPerfilId);
            }
            List<Flag> attachedFlagList = new ArrayList<Flag>();
            for (Flag flagListFlagToAttach : locacao.getFlagList()) {
                flagListFlagToAttach = em.getReference(flagListFlagToAttach.getClass(), flagListFlagToAttach.getId());
                attachedFlagList.add(flagListFlagToAttach);
            }
            locacao.setFlagList(attachedFlagList);
            List<Locacoes> attachedLocacoesList = new ArrayList<Locacoes>();
            for (Locacoes locacoesListLocacoesToAttach : locacao.getLocacoesList()) {
                locacoesListLocacoesToAttach = em.getReference(locacoesListLocacoesToAttach.getClass(), locacoesListLocacoesToAttach.getId());
                attachedLocacoesList.add(locacoesListLocacoesToAttach);
            }
            locacao.setLocacoesList(attachedLocacoesList);
            em.persist(locacao);
            if (usuarioPerfilId != null) {
                usuarioPerfilId.getLocacaoList().add(locacao);
                usuarioPerfilId = em.merge(usuarioPerfilId);
            }
            if (funcionarioPerfilId != null) {
                funcionarioPerfilId.getLocacaoList().add(locacao);
                funcionarioPerfilId = em.merge(funcionarioPerfilId);
            }
            for (Flag flagListFlag : locacao.getFlagList()) {
                Locacao oldLocacaoIdOfFlagListFlag = flagListFlag.getLocacaoId();
                flagListFlag.setLocacaoId(locacao);
                flagListFlag = em.merge(flagListFlag);
                if (oldLocacaoIdOfFlagListFlag != null) {
                    oldLocacaoIdOfFlagListFlag.getFlagList().remove(flagListFlag);
                    oldLocacaoIdOfFlagListFlag = em.merge(oldLocacaoIdOfFlagListFlag);
                }
            }
            for (Locacoes locacoesListLocacoes : locacao.getLocacoesList()) {
                Locacao oldLocacaoIdOfLocacoesListLocacoes = locacoesListLocacoes.getLocacaoId();
                locacoesListLocacoes.setLocacaoId(locacao);
                locacoesListLocacoes = em.merge(locacoesListLocacoes);
                if (oldLocacaoIdOfLocacoesListLocacoes != null) {
                    oldLocacaoIdOfLocacoesListLocacoes.getLocacoesList().remove(locacoesListLocacoes);
                    oldLocacaoIdOfLocacoesListLocacoes = em.merge(oldLocacaoIdOfLocacoesListLocacoes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLocacao(locacao.getId()) != null) {
                throw new PreexistingEntityException("Locacao " + locacao + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Locacao locacao) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Locacao persistentLocacao = em.find(Locacao.class, locacao.getId());
            UsuarioPerfil usuarioPerfilIdOld = persistentLocacao.getUsuarioPerfilId();
            UsuarioPerfil usuarioPerfilIdNew = locacao.getUsuarioPerfilId();
            UsuarioPerfil funcionarioPerfilIdOld = persistentLocacao.getFuncionarioPerfilId();
            UsuarioPerfil funcionarioPerfilIdNew = locacao.getFuncionarioPerfilId();
            List<Flag> flagListOld = persistentLocacao.getFlagList();
            List<Flag> flagListNew = locacao.getFlagList();
            List<Locacoes> locacoesListOld = persistentLocacao.getLocacoesList();
            List<Locacoes> locacoesListNew = locacao.getLocacoesList();
            List<String> illegalOrphanMessages = null;
            for (Flag flagListOldFlag : flagListOld) {
                if (!flagListNew.contains(flagListOldFlag)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Flag " + flagListOldFlag + " since its locacaoId field is not nullable.");
                }
            }
            for (Locacoes locacoesListOldLocacoes : locacoesListOld) {
                if (!locacoesListNew.contains(locacoesListOldLocacoes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Locacoes " + locacoesListOldLocacoes + " since its locacaoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioPerfilIdNew != null) {
                usuarioPerfilIdNew = em.getReference(usuarioPerfilIdNew.getClass(), usuarioPerfilIdNew.getId());
                locacao.setUsuarioPerfilId(usuarioPerfilIdNew);
            }
            if (funcionarioPerfilIdNew != null) {
                funcionarioPerfilIdNew = em.getReference(funcionarioPerfilIdNew.getClass(), funcionarioPerfilIdNew.getId());
                locacao.setFuncionarioPerfilId(funcionarioPerfilIdNew);
            }
            List<Flag> attachedFlagListNew = new ArrayList<Flag>();
            for (Flag flagListNewFlagToAttach : flagListNew) {
                flagListNewFlagToAttach = em.getReference(flagListNewFlagToAttach.getClass(), flagListNewFlagToAttach.getId());
                attachedFlagListNew.add(flagListNewFlagToAttach);
            }
            flagListNew = attachedFlagListNew;
            locacao.setFlagList(flagListNew);
            List<Locacoes> attachedLocacoesListNew = new ArrayList<Locacoes>();
            for (Locacoes locacoesListNewLocacoesToAttach : locacoesListNew) {
                locacoesListNewLocacoesToAttach = em.getReference(locacoesListNewLocacoesToAttach.getClass(), locacoesListNewLocacoesToAttach.getId());
                attachedLocacoesListNew.add(locacoesListNewLocacoesToAttach);
            }
            locacoesListNew = attachedLocacoesListNew;
            locacao.setLocacoesList(locacoesListNew);
            locacao = em.merge(locacao);
            if (usuarioPerfilIdOld != null && !usuarioPerfilIdOld.equals(usuarioPerfilIdNew)) {
                usuarioPerfilIdOld.getLocacaoList().remove(locacao);
                usuarioPerfilIdOld = em.merge(usuarioPerfilIdOld);
            }
            if (usuarioPerfilIdNew != null && !usuarioPerfilIdNew.equals(usuarioPerfilIdOld)) {
                usuarioPerfilIdNew.getLocacaoList().add(locacao);
                usuarioPerfilIdNew = em.merge(usuarioPerfilIdNew);
            }
            if (funcionarioPerfilIdOld != null && !funcionarioPerfilIdOld.equals(funcionarioPerfilIdNew)) {
                funcionarioPerfilIdOld.getLocacaoList().remove(locacao);
                funcionarioPerfilIdOld = em.merge(funcionarioPerfilIdOld);
            }
            if (funcionarioPerfilIdNew != null && !funcionarioPerfilIdNew.equals(funcionarioPerfilIdOld)) {
                funcionarioPerfilIdNew.getLocacaoList().add(locacao);
                funcionarioPerfilIdNew = em.merge(funcionarioPerfilIdNew);
            }
            for (Flag flagListNewFlag : flagListNew) {
                if (!flagListOld.contains(flagListNewFlag)) {
                    Locacao oldLocacaoIdOfFlagListNewFlag = flagListNewFlag.getLocacaoId();
                    flagListNewFlag.setLocacaoId(locacao);
                    flagListNewFlag = em.merge(flagListNewFlag);
                    if (oldLocacaoIdOfFlagListNewFlag != null && !oldLocacaoIdOfFlagListNewFlag.equals(locacao)) {
                        oldLocacaoIdOfFlagListNewFlag.getFlagList().remove(flagListNewFlag);
                        oldLocacaoIdOfFlagListNewFlag = em.merge(oldLocacaoIdOfFlagListNewFlag);
                    }
                }
            }
            for (Locacoes locacoesListNewLocacoes : locacoesListNew) {
                if (!locacoesListOld.contains(locacoesListNewLocacoes)) {
                    Locacao oldLocacaoIdOfLocacoesListNewLocacoes = locacoesListNewLocacoes.getLocacaoId();
                    locacoesListNewLocacoes.setLocacaoId(locacao);
                    locacoesListNewLocacoes = em.merge(locacoesListNewLocacoes);
                    if (oldLocacaoIdOfLocacoesListNewLocacoes != null && !oldLocacaoIdOfLocacoesListNewLocacoes.equals(locacao)) {
                        oldLocacaoIdOfLocacoesListNewLocacoes.getLocacoesList().remove(locacoesListNewLocacoes);
                        oldLocacaoIdOfLocacoesListNewLocacoes = em.merge(oldLocacaoIdOfLocacoesListNewLocacoes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = locacao.getId();
                if (findLocacao(id) == null) {
                    throw new NonexistentEntityException("The locacao with id " + id + " no longer exists.");
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
            Locacao locacao;
            try {
                locacao = em.getReference(Locacao.class, id);
                locacao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The locacao with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Flag> flagListOrphanCheck = locacao.getFlagList();
            for (Flag flagListOrphanCheckFlag : flagListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Locacao (" + locacao + ") cannot be destroyed since the Flag " + flagListOrphanCheckFlag + " in its flagList field has a non-nullable locacaoId field.");
            }
            List<Locacoes> locacoesListOrphanCheck = locacao.getLocacoesList();
            for (Locacoes locacoesListOrphanCheckLocacoes : locacoesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Locacao (" + locacao + ") cannot be destroyed since the Locacoes " + locacoesListOrphanCheckLocacoes + " in its locacoesList field has a non-nullable locacaoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            UsuarioPerfil usuarioPerfilId = locacao.getUsuarioPerfilId();
            if (usuarioPerfilId != null) {
                usuarioPerfilId.getLocacaoList().remove(locacao);
                usuarioPerfilId = em.merge(usuarioPerfilId);
            }
            UsuarioPerfil funcionarioPerfilId = locacao.getFuncionarioPerfilId();
            if (funcionarioPerfilId != null) {
                funcionarioPerfilId.getLocacaoList().remove(locacao);
                funcionarioPerfilId = em.merge(funcionarioPerfilId);
            }
            em.remove(locacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Locacao> findLocacaoEntities() {
        return findLocacaoEntities(true, -1, -1);
    }

    public List<Locacao> findLocacaoEntities(int maxResults, int firstResult) {
        return findLocacaoEntities(false, maxResults, firstResult);
    }

    private List<Locacao> findLocacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Locacao.class));
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

    public Locacao findLocacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Locacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getLocacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Locacao> rt = cq.from(Locacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
