/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uerr.biblio.appbiblio.controle;

import br.edu.uerr.biblio.appbiblio.controle.exceptions.NonexistentEntityException;
import br.edu.uerr.biblio.appbiblio.controle.exceptions.PreexistingEntityException;
import br.edu.uerr.biblio.appbiblio.modelo.Endereco;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.edu.uerr.biblio.appbiblio.modelo.Enderecos;
import br.edu.uerr.biblio.appbiblio.modelo.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jklac
 */
public class EnderecoJpaController implements Serializable {

    public EnderecoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Endereco endereco) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Enderecos enderecosId = endereco.getEnderecosId();
            if (enderecosId != null) {
                enderecosId = em.getReference(enderecosId.getClass(), enderecosId.getId());
                endereco.setEnderecosId(enderecosId);
            }
            Usuario usuarioId = endereco.getUsuarioId();
            if (usuarioId != null) {
                usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getId());
                endereco.setUsuarioId(usuarioId);
            }
            em.persist(endereco);
            if (enderecosId != null) {
                enderecosId.getEnderecoList().add(endereco);
                enderecosId = em.merge(enderecosId);
            }
            if (usuarioId != null) {
                usuarioId.getEnderecoList().add(endereco);
                usuarioId = em.merge(usuarioId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEndereco(endereco.getId()) != null) {
                throw new PreexistingEntityException("Endereco " + endereco + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Endereco endereco) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Endereco persistentEndereco = em.find(Endereco.class, endereco.getId());
            Enderecos enderecosIdOld = persistentEndereco.getEnderecosId();
            Enderecos enderecosIdNew = endereco.getEnderecosId();
            Usuario usuarioIdOld = persistentEndereco.getUsuarioId();
            Usuario usuarioIdNew = endereco.getUsuarioId();
            if (enderecosIdNew != null) {
                enderecosIdNew = em.getReference(enderecosIdNew.getClass(), enderecosIdNew.getId());
                endereco.setEnderecosId(enderecosIdNew);
            }
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getId());
                endereco.setUsuarioId(usuarioIdNew);
            }
            endereco = em.merge(endereco);
            if (enderecosIdOld != null && !enderecosIdOld.equals(enderecosIdNew)) {
                enderecosIdOld.getEnderecoList().remove(endereco);
                enderecosIdOld = em.merge(enderecosIdOld);
            }
            if (enderecosIdNew != null && !enderecosIdNew.equals(enderecosIdOld)) {
                enderecosIdNew.getEnderecoList().add(endereco);
                enderecosIdNew = em.merge(enderecosIdNew);
            }
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getEnderecoList().remove(endereco);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getEnderecoList().add(endereco);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = endereco.getId();
                if (findEndereco(id) == null) {
                    throw new NonexistentEntityException("The endereco with id " + id + " no longer exists.");
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
            Endereco endereco;
            try {
                endereco = em.getReference(Endereco.class, id);
                endereco.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The endereco with id " + id + " no longer exists.", enfe);
            }
            Enderecos enderecosId = endereco.getEnderecosId();
            if (enderecosId != null) {
                enderecosId.getEnderecoList().remove(endereco);
                enderecosId = em.merge(enderecosId);
            }
            Usuario usuarioId = endereco.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getEnderecoList().remove(endereco);
                usuarioId = em.merge(usuarioId);
            }
            em.remove(endereco);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Endereco> findEnderecoEntities() {
        return findEnderecoEntities(true, -1, -1);
    }

    public List<Endereco> findEnderecoEntities(int maxResults, int firstResult) {
        return findEnderecoEntities(false, maxResults, firstResult);
    }

    private List<Endereco> findEnderecoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Endereco.class));
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

    public Endereco findEndereco(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Endereco.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnderecoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Endereco> rt = cq.from(Endereco.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
