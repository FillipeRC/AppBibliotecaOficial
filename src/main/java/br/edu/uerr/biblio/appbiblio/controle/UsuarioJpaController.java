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
import br.edu.uerr.biblio.appbiblio.modelo.Endereco;
import br.edu.uerr.biblio.appbiblio.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import br.edu.uerr.biblio.appbiblio.modelo.UsuarioPerfil;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jklac
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getEnderecoList() == null) {
            usuario.setEnderecoList(new ArrayList<Endereco>());
        }
        if (usuario.getUsuarioPerfilList() == null) {
            usuario.setUsuarioPerfilList(new ArrayList<UsuarioPerfil>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Endereco> attachedEnderecoList = new ArrayList<Endereco>();
            for (Endereco enderecoListEnderecoToAttach : usuario.getEnderecoList()) {
                enderecoListEnderecoToAttach = em.getReference(enderecoListEnderecoToAttach.getClass(), enderecoListEnderecoToAttach.getId());
                attachedEnderecoList.add(enderecoListEnderecoToAttach);
            }
            usuario.setEnderecoList(attachedEnderecoList);
            List<UsuarioPerfil> attachedUsuarioPerfilList = new ArrayList<UsuarioPerfil>();
            for (UsuarioPerfil usuarioPerfilListUsuarioPerfilToAttach : usuario.getUsuarioPerfilList()) {
                usuarioPerfilListUsuarioPerfilToAttach = em.getReference(usuarioPerfilListUsuarioPerfilToAttach.getClass(), usuarioPerfilListUsuarioPerfilToAttach.getId());
                attachedUsuarioPerfilList.add(usuarioPerfilListUsuarioPerfilToAttach);
            }
            usuario.setUsuarioPerfilList(attachedUsuarioPerfilList);
            em.persist(usuario);
            for (Endereco enderecoListEndereco : usuario.getEnderecoList()) {
                Usuario oldUsuarioIdOfEnderecoListEndereco = enderecoListEndereco.getUsuarioId();
                enderecoListEndereco.setUsuarioId(usuario);
                enderecoListEndereco = em.merge(enderecoListEndereco);
                if (oldUsuarioIdOfEnderecoListEndereco != null) {
                    oldUsuarioIdOfEnderecoListEndereco.getEnderecoList().remove(enderecoListEndereco);
                    oldUsuarioIdOfEnderecoListEndereco = em.merge(oldUsuarioIdOfEnderecoListEndereco);
                }
            }
            for (UsuarioPerfil usuarioPerfilListUsuarioPerfil : usuario.getUsuarioPerfilList()) {
                Usuario oldUsuarioIdOfUsuarioPerfilListUsuarioPerfil = usuarioPerfilListUsuarioPerfil.getUsuarioId();
                usuarioPerfilListUsuarioPerfil.setUsuarioId(usuario);
                usuarioPerfilListUsuarioPerfil = em.merge(usuarioPerfilListUsuarioPerfil);
                if (oldUsuarioIdOfUsuarioPerfilListUsuarioPerfil != null) {
                    oldUsuarioIdOfUsuarioPerfilListUsuarioPerfil.getUsuarioPerfilList().remove(usuarioPerfilListUsuarioPerfil);
                    oldUsuarioIdOfUsuarioPerfilListUsuarioPerfil = em.merge(oldUsuarioIdOfUsuarioPerfilListUsuarioPerfil);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getId()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            List<Endereco> enderecoListOld = persistentUsuario.getEnderecoList();
            List<Endereco> enderecoListNew = usuario.getEnderecoList();
            List<UsuarioPerfil> usuarioPerfilListOld = persistentUsuario.getUsuarioPerfilList();
            List<UsuarioPerfil> usuarioPerfilListNew = usuario.getUsuarioPerfilList();
            List<String> illegalOrphanMessages = null;
            for (Endereco enderecoListOldEndereco : enderecoListOld) {
                if (!enderecoListNew.contains(enderecoListOldEndereco)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Endereco " + enderecoListOldEndereco + " since its usuarioId field is not nullable.");
                }
            }
            for (UsuarioPerfil usuarioPerfilListOldUsuarioPerfil : usuarioPerfilListOld) {
                if (!usuarioPerfilListNew.contains(usuarioPerfilListOldUsuarioPerfil)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsuarioPerfil " + usuarioPerfilListOldUsuarioPerfil + " since its usuarioId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Endereco> attachedEnderecoListNew = new ArrayList<Endereco>();
            for (Endereco enderecoListNewEnderecoToAttach : enderecoListNew) {
                enderecoListNewEnderecoToAttach = em.getReference(enderecoListNewEnderecoToAttach.getClass(), enderecoListNewEnderecoToAttach.getId());
                attachedEnderecoListNew.add(enderecoListNewEnderecoToAttach);
            }
            enderecoListNew = attachedEnderecoListNew;
            usuario.setEnderecoList(enderecoListNew);
            List<UsuarioPerfil> attachedUsuarioPerfilListNew = new ArrayList<UsuarioPerfil>();
            for (UsuarioPerfil usuarioPerfilListNewUsuarioPerfilToAttach : usuarioPerfilListNew) {
                usuarioPerfilListNewUsuarioPerfilToAttach = em.getReference(usuarioPerfilListNewUsuarioPerfilToAttach.getClass(), usuarioPerfilListNewUsuarioPerfilToAttach.getId());
                attachedUsuarioPerfilListNew.add(usuarioPerfilListNewUsuarioPerfilToAttach);
            }
            usuarioPerfilListNew = attachedUsuarioPerfilListNew;
            usuario.setUsuarioPerfilList(usuarioPerfilListNew);
            usuario = em.merge(usuario);
            for (Endereco enderecoListNewEndereco : enderecoListNew) {
                if (!enderecoListOld.contains(enderecoListNewEndereco)) {
                    Usuario oldUsuarioIdOfEnderecoListNewEndereco = enderecoListNewEndereco.getUsuarioId();
                    enderecoListNewEndereco.setUsuarioId(usuario);
                    enderecoListNewEndereco = em.merge(enderecoListNewEndereco);
                    if (oldUsuarioIdOfEnderecoListNewEndereco != null && !oldUsuarioIdOfEnderecoListNewEndereco.equals(usuario)) {
                        oldUsuarioIdOfEnderecoListNewEndereco.getEnderecoList().remove(enderecoListNewEndereco);
                        oldUsuarioIdOfEnderecoListNewEndereco = em.merge(oldUsuarioIdOfEnderecoListNewEndereco);
                    }
                }
            }
            for (UsuarioPerfil usuarioPerfilListNewUsuarioPerfil : usuarioPerfilListNew) {
                if (!usuarioPerfilListOld.contains(usuarioPerfilListNewUsuarioPerfil)) {
                    Usuario oldUsuarioIdOfUsuarioPerfilListNewUsuarioPerfil = usuarioPerfilListNewUsuarioPerfil.getUsuarioId();
                    usuarioPerfilListNewUsuarioPerfil.setUsuarioId(usuario);
                    usuarioPerfilListNewUsuarioPerfil = em.merge(usuarioPerfilListNewUsuarioPerfil);
                    if (oldUsuarioIdOfUsuarioPerfilListNewUsuarioPerfil != null && !oldUsuarioIdOfUsuarioPerfilListNewUsuarioPerfil.equals(usuario)) {
                        oldUsuarioIdOfUsuarioPerfilListNewUsuarioPerfil.getUsuarioPerfilList().remove(usuarioPerfilListNewUsuarioPerfil);
                        oldUsuarioIdOfUsuarioPerfilListNewUsuarioPerfil = em.merge(oldUsuarioIdOfUsuarioPerfilListNewUsuarioPerfil);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Endereco> enderecoListOrphanCheck = usuario.getEnderecoList();
            for (Endereco enderecoListOrphanCheckEndereco : enderecoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Endereco " + enderecoListOrphanCheckEndereco + " in its enderecoList field has a non-nullable usuarioId field.");
            }
            List<UsuarioPerfil> usuarioPerfilListOrphanCheck = usuario.getUsuarioPerfilList();
            for (UsuarioPerfil usuarioPerfilListOrphanCheckUsuarioPerfil : usuarioPerfilListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the UsuarioPerfil " + usuarioPerfilListOrphanCheckUsuarioPerfil + " in its usuarioPerfilList field has a non-nullable usuarioId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
