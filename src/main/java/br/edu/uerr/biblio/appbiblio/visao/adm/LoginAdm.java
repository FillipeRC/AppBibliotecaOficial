package br.edu.uerr.biblio.appbiblio.visao.adm;

import br.edu.uerr.biblio.appbiblio.controle.UsuarioPerfilJpaController;
import br.edu.uerr.biblio.appbiblio.modelo.UsuarioPerfil;
import br.edu.uerr.biblio.appbiblio.util.AppInfoUtils;
import br.edu.uerr.biblio.appbiblio.visao.UIInterfaceFuncionario;
import br.edu.uerr.biblio.appbiblio.visao.adm.cadastro.UICadastroUsuario;
import br.edu.uerr.biblio.appbiblio.visao.locador.UIInterfaceUsuario;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

/**
 *
 * @author jklac
 */
public class LoginAdm extends javax.swing.JFrame {

    public static LoginAdm loginAdm;
    UsuarioPerfil perfil;
    EntityManagerFactory emf = null;
    UsuarioPerfilJpaController upjc = null;
    private String login;
    private String senha;

    public LoginAdm() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tv_titulo_adm = new javax.swing.JLabel();
        tv_login_adm = new javax.swing.JLabel();
        tv_senha_adm = new javax.swing.JLabel();
        et_login_adm = new javax.swing.JTextField();
        bt_entrar_adm = new javax.swing.JButton();
        et_senha_adm = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tv_titulo_adm.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        tv_titulo_adm.setText("Registro Bibliotecário UERR");

        tv_login_adm.setText("LOGIN:");

        tv_senha_adm.setText("SENHA:");

        et_login_adm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                et_login_admActionPerformed(evt);
            }
        });

        bt_entrar_adm.setText("ENTRAR");
        bt_entrar_adm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_entrar_admActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tv_senha_adm, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(et_senha_adm))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tv_login_adm, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(et_login_adm, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(43, 43, 43)
                .addComponent(bt_entrar_adm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(tv_titulo_adm, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tv_titulo_adm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tv_login_adm)
                            .addComponent(et_login_adm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tv_senha_adm)
                            .addComponent(et_senha_adm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(bt_entrar_adm, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void et_login_admActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_et_login_admActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_et_login_admActionPerformed

    private void bt_entrar_admActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_entrar_admActionPerformed
        login = et_login_adm.getText();
        senha = et_senha_adm.getText();
        emf = Persistence.createEntityManagerFactory(AppInfoUtils.PU);
        upjc = new UsuarioPerfilJpaController(emf);
        List<UsuarioPerfil> perfis = new ArrayList<>();
        if (upjc.getUsuarioPerfilCount() != 0) {
            perfis = upjc.findUsuarioPerfilEntities();
            boolean aux = false;

            for (UsuarioPerfil u : perfis) {
                if (u.getLogin().equals(login) && u.getSenha().equals(senha)) {
                    aux = true;
                    perfil = u;
                }
            }
            System.out.print(perfil.getTipoUsuarioPerfilId().getDescricao());
            if (aux && perfil.getTipoUsuarioPerfilId().getId() == 1 ){
                JOptionPane.showMessageDialog(null, "Login Realizado com sucesso!! ");
                try {
                    loginAdm.setVisible(false);
                    UIInterfaceFuncionario funcionario = new UIInterfaceFuncionario(perfil);
                    funcionario.setLocationRelativeTo(null);
                    funcionario.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
                    funcionario.setResizable(false);
                    UIInterfaceFuncionario.setInstance(funcionario);
                    UIInterfaceFuncionario.getInstance().setVisible(aux);
                } catch (Throwable ex) {
                    Logger.getLogger(LoginAdm.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if(perfil.getTipoUsuarioPerfilId().getId() == 3 ) {
                loginAdm.setVisible(false);
                UIInterfaceUsuario usuario = new UIInterfaceUsuario();
                usuario.setLocationRelativeTo(null);
                usuario.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
                usuario.setResizable(false);
                UIInterfaceUsuario.setInstance(usuario);
                usuario.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null, "Login e Senha não conferem...");
            }
        } else {
            new UICadastroUsuario().setVisible(true);
        }
    }//GEN-LAST:event_bt_entrar_admActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginAdm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginAdm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginAdm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginAdm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                loginAdm = new LoginAdm();
                loginAdm.setLocationRelativeTo(null);
                loginAdm.setResizable(false);
                loginAdm.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_entrar_adm;
    private javax.swing.JTextField et_login_adm;
    private javax.swing.JPasswordField et_senha_adm;
    private javax.swing.JLabel tv_login_adm;
    private javax.swing.JLabel tv_senha_adm;
    private javax.swing.JLabel tv_titulo_adm;
    // End of variables declaration//GEN-END:variables
}
