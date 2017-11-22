
package br.edu.uerr.biblio.appbiblio.visao;

import br.edu.uerr.biblio.appbiblio.controle.UsuarioJpaController;
import br.edu.uerr.biblio.appbiblio.modelo.Usuario;
import br.edu.uerr.biblio.appbiblio.modelo.UsuarioPerfil;
import br.edu.uerr.biblio.appbiblio.util.AppInfoUtils;
import br.edu.uerr.biblio.appbiblio.visao.adm.LoginAdm;
import br.edu.uerr.biblio.appbiblio.visao.adm.cadastro.UICadastroUsuario;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 *
 * @author jklac
 */
public class UIInterfaceFuncionario extends javax.swing.JFrame {
    
    private static UIInterfaceFuncionario uiif;
    private UsuarioPerfil perfil;
    private static Usuario user;
    private UsuarioJpaController ujc = null;
    private EntityManagerFactory emf = null;
    
    public UIInterfaceFuncionario(UsuarioPerfil perfil) {
        this.perfil = perfil;
        emf = Persistence.createEntityManagerFactory(AppInfoUtils.PU);
        ujc = new UsuarioJpaController(emf);
        user = new Usuario();
        user = ujc.findUsuario(this.perfil.getUsuarioId().getId());
        
        initComponents();
        
        if ( AppInfoUtils.getHoraDoDia() >= 6 
                && AppInfoUtils.getHoraDoDia() <= 12){
            tv_saudacao_online.setText("Bom dia, ");
        }else if(AppInfoUtils.getHoraDoDia() >= 13
                && AppInfoUtils.getHoraDoDia() <= 18){
            tv_saudacao_online.setText("Boa tarde, ");
        }else{
            tv_saudacao_online.setText("Boa noite, ");
        }
        tv_date_inteface.setText("Hoje é dia: "+AppInfoUtils.getDateFormat(AppInfoUtils.getDataAtual()));
        tv_func_online.setText(user.getNome());
    }

    private UIInterfaceFuncionario() {
        
    }

    public static void setInstance( UIInterfaceFuncionario aux){
        uiif = aux;
    }
    
    public static UIInterfaceFuncionario getInstance(){
        return uiif;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        tv_saudacao_online = new javax.swing.JLabel();
        tv_func_online = new javax.swing.JLabel();
        bt_search = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        tv_area_cad = new javax.swing.JLabel();
        bt_new_user = new javax.swing.JButton();
        bt_new_acerv = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_pendent_locations = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        ta_notify_news = new javax.swing.JTextArea();
        bt_new_reserv = new javax.swing.JButton();
        bt_logoff = new javax.swing.JButton();
        tv_pendent_locations = new javax.swing.JLabel();
        tv_nofifys = new javax.swing.JLabel();
        tv_date_inteface = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tv_saudacao_online.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tv_saudacao_online.setText("bd bt bn");

        tv_func_online.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        tv_func_online.setText("fulano");

        bt_search.setText("PESQUISAR");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 204), null), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N

        tv_area_cad.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tv_area_cad.setText("Preferencias");

        bt_new_user.setText("Editar Usuario");
        bt_new_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_new_userActionPerformed(evt);
            }
        });

        bt_new_acerv.setText("Editar Acervo");

        jButton2.setText("Editar Propriedades");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tv_area_cad)
                    .addComponent(bt_new_acerv, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_new_user, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tv_area_cad)
                .addGap(18, 18, 18)
                .addComponent(bt_new_user)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_new_acerv)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tb_pendent_locations.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tb_pendent_locations);

        ta_notify_news.setColumns(20);
        ta_notify_news.setRows(5);
        jScrollPane2.setViewportView(ta_notify_news);

        bt_new_reserv.setText("NOVA RESERVA");

        bt_logoff.setText("SAIR");
        bt_logoff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_logoffActionPerformed(evt);
            }
        });

        tv_pendent_locations.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tv_pendent_locations.setText("Locações Pendentes");

        tv_nofifys.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tv_nofifys.setText("Notificações");

        tv_date_inteface.setText("data");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tv_date_inteface)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bt_logoff))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bt_new_reserv, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 502, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tv_saudacao_online)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tv_func_online))
                            .addComponent(bt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(tv_pendent_locations)
                            .addComponent(tv_nofifys)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tv_saudacao_online)
                            .addComponent(tv_func_online))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tv_date_inteface))
                    .addComponent(bt_logoff))
                .addGap(18, 18, 18)
                .addComponent(bt_search)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_new_reserv)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(tv_pendent_locations)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tv_nofifys)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_logoffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_logoffActionPerformed
        int confirm = JOptionPane.showConfirmDialog(null, "Deseja sair do aplicativo?", "Aviso", JOptionPane.YES_OPTION);
        if (confirm == JOptionPane.YES_OPTION){
            uiif.setVisible(false);
            LoginAdm.loginAdm = new LoginAdm();
            LoginAdm.loginAdm.setLocationRelativeTo(null);
            LoginAdm.loginAdm.setResizable(false);
            LoginAdm.loginAdm.setVisible(true);
        }
    }//GEN-LAST:event_bt_logoffActionPerformed

    private void bt_new_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_new_userActionPerformed
        UICadastroUsuario aux = new UICadastroUsuario();
        aux.setLocationRelativeTo(null);
        aux.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        aux.setResizable(false);
    }//GEN-LAST:event_bt_new_userActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_logoff;
    private javax.swing.JButton bt_new_acerv;
    private javax.swing.JButton bt_new_reserv;
    private javax.swing.JButton bt_new_user;
    private javax.swing.JButton bt_search;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea ta_notify_news;
    private javax.swing.JTable tb_pendent_locations;
    private javax.swing.JLabel tv_area_cad;
    private javax.swing.JLabel tv_date_inteface;
    private javax.swing.JLabel tv_func_online;
    private javax.swing.JLabel tv_nofifys;
    private javax.swing.JLabel tv_pendent_locations;
    private javax.swing.JLabel tv_saudacao_online;
    // End of variables declaration//GEN-END:variables
}
