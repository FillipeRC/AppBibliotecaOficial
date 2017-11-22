/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uerr.biblio.appbiblio.visao.adm.cadastro;

import br.edu.uerr.biblio.appbiblio.controle.EnderecoJpaController;
import br.edu.uerr.biblio.appbiblio.controle.EnderecosJpaController;
import br.edu.uerr.biblio.appbiblio.controle.TipoUsuarioPerfilJpaController;
import br.edu.uerr.biblio.appbiblio.controle.UsuarioJpaController;
import br.edu.uerr.biblio.appbiblio.controle.UsuarioPerfilJpaController;
import br.edu.uerr.biblio.appbiblio.modelo.Endereco;
import br.edu.uerr.biblio.appbiblio.modelo.Enderecos;
import br.edu.uerr.biblio.appbiblio.modelo.TipoUsuarioPerfil;
import br.edu.uerr.biblio.appbiblio.modelo.Usuario;
import br.edu.uerr.biblio.appbiblio.modelo.UsuarioPerfil;
import br.edu.uerr.biblio.appbiblio.util.AppInfoUtils;
import br.edu.uerr.biblio.appbiblio.util.WsViaCep;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;
import org.json.JSONObject;

/**
 *
 * @author jklac
 */
public class UICadastroUsuario extends javax.swing.JFrame {

    private static UICadastroUsuario uicu;
    
    private List<Usuario> users;
    private List<UsuarioPerfil> perfis;
    private List<TipoUsuarioPerfil> tipoPerfis;
    private List<Enderecos> enderecosList;
    private List<Endereco> enderecoList;
    
    
    private UsuarioJpaController ujc = null;
    private UsuarioPerfilJpaController upjc = null;
    private TipoUsuarioPerfilJpaController tupjc = null; 
    private EnderecosJpaController ejc = null;
    private EnderecoJpaController ejc1 = null;
    
    
    public UICadastroUsuario() {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(AppInfoUtils.PU);
        ujc = new UsuarioJpaController(emf);
        upjc = new UsuarioPerfilJpaController(emf);
        tupjc = new TipoUsuarioPerfilJpaController(emf);
        ejc = new EnderecosJpaController(emf);
        ejc1 = new EnderecoJpaController(emf);
        
        users = new ArrayList<>();
        users = ujc.findUsuarioEntities();
        
        perfis = new ArrayList<>();
        perfis = upjc.findUsuarioPerfilEntities();
        
        tipoPerfis = new ArrayList<>();
        tipoPerfis = tupjc.findTipoUsuarioPerfilEntities();
        
        enderecosList = new ArrayList<>();
        enderecosList = ejc.findEnderecosEntities();
        
        enderecoList = new ArrayList<>();
        enderecoList = ejc1.findEnderecoEntities();
        
        initComponents();
        pgb_cad_user.setVisible(false);
        try {
            MaskFormatter format = new MaskFormatter(AppInfoUtils.INPUT_CPF_FORMAT);
            format.install(et_cpf_cad_user);
            format = new MaskFormatter(AppInfoUtils.INPUT_DATE_FORMAT);
            format.install(et_born_date_cad_user);
            format = new MaskFormatter(AppInfoUtils.INPUT_CELULAR_FORMAT);
            format.install(et_celular_cad_user);
            format = new MaskFormatter(AppInfoUtils.INPUT_TELEPHONE_FORMAT);
            format.install(et_residencial_cad_user);
            format = new MaskFormatter(AppInfoUtils.INPUT_CEP_FORMAT);
            format.install(et_cep_cad_user);
        } catch (ParseException ex) {
            Logger.getLogger(UICadastroUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        carregaTabela();
        carregaTipos();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rg_sex_cad_user = new javax.swing.ButtonGroup();
        painel_cad_user = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tv_cep_cad_user = new javax.swing.JLabel();
        et_cep_cad_user = new javax.swing.JFormattedTextField();
        tv_logradouro_cad_user = new javax.swing.JLabel();
        et_logradouro_cad_user = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        et_bairro_cad_user = new javax.swing.JTextField();
        tv_cidade_cad_user = new javax.swing.JLabel();
        et_cidade_cad_user = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        et_estado_cad_user = new javax.swing.JTextField();
        tv_numero_cad_user = new javax.swing.JLabel();
        et_numero_cad_user = new javax.swing.JTextField();
        pgb_cad_user = new javax.swing.JProgressBar();
        jPanel3 = new javax.swing.JPanel();
        tv_cpf_cad_user = new javax.swing.JLabel();
        et_cpf_cad_user = new javax.swing.JFormattedTextField();
        tv_rg_cad_user = new javax.swing.JLabel();
        tv_name_cad_user = new javax.swing.JLabel();
        et_name_cad_user = new javax.swing.JTextField();
        tv_born_date_cad_user = new javax.swing.JLabel();
        et_born_date_cad_user = new javax.swing.JFormattedTextField();
        tv_email_cad_user = new javax.swing.JLabel();
        et_email_cad_user = new javax.swing.JTextField();
        tv_data_cad_user = new javax.swing.JLabel();
        et_rg_cad_user = new javax.swing.JTextField();
        tv_sex_cad_user = new javax.swing.JLabel();
        rb_masculino_cad_user = new javax.swing.JRadioButton();
        rb_feminino_cad_user = new javax.swing.JRadioButton();
        tv_celular_cad_user = new javax.swing.JLabel();
        et_celular_cad_user = new javax.swing.JFormattedTextField();
        tv_residencial_cad_user = new javax.swing.JLabel();
        et_residencial_cad_user = new javax.swing.JFormattedTextField();
        tv_matricula_cad_user = new javax.swing.JLabel();
        et_matricula_cad_user = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        tv_token_cad_user = new javax.swing.JLabel();
        et_token_cad_user = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        tv_senha_cad_user = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        et_senha2_cad_user = new javax.swing.JPasswordField();
        et_senha_cad_user = new javax.swing.JPasswordField();
        et_login_cad_user = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        sp_tipos_cad_user = new javax.swing.JComboBox<>();
        tv_cad_new_user = new javax.swing.JLabel();
        bt_back_cad_user = new javax.swing.JButton();
        bt_save_cad_user = new javax.swing.JButton();
        painel_lista_cad_user = new javax.swing.JPanel();
        tv_relation_cad_user = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_cad_user = new javax.swing.JTable();
        bt_listar_cad_user = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        painel_cad_user.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 204), null), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, null));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Residencia");

        tv_cep_cad_user.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tv_cep_cad_user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tv_cep_cad_user.setText("CEP:");

        et_cep_cad_user.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        et_cep_cad_user.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                et_cep_cad_userFocusLost(evt);
            }
        });

        tv_logradouro_cad_user.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tv_logradouro_cad_user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tv_logradouro_cad_user.setText("Rua / Av:");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Bairro:");

        tv_cidade_cad_user.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tv_cidade_cad_user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tv_cidade_cad_user.setText("Cidade");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Estado");

        tv_numero_cad_user.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tv_numero_cad_user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tv_numero_cad_user.setText("Numero");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(et_bairro_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(et_estado_cad_user))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(tv_logradouro_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(et_logradouro_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pgb_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(tv_cep_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(et_cep_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(tv_numero_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(et_numero_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(tv_cidade_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(et_cidade_cad_user)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(pgb_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tv_cep_cad_user)
                    .addComponent(et_cep_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tv_numero_cad_user)
                    .addComponent(et_numero_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tv_logradouro_cad_user)
                    .addComponent(et_logradouro_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tv_cidade_cad_user)
                    .addComponent(et_cidade_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(et_bairro_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(et_estado_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        tv_numero_cad_user.getAccessibleContext().setAccessibleName("");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, null));

        tv_cpf_cad_user.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tv_cpf_cad_user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tv_cpf_cad_user.setText("CPF:");

        et_cpf_cad_user.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        et_cpf_cad_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                et_cpf_cad_userActionPerformed(evt);
            }
        });

        tv_rg_cad_user.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tv_rg_cad_user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tv_rg_cad_user.setText("RG:");

        tv_name_cad_user.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tv_name_cad_user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tv_name_cad_user.setText("Nome:");
        tv_name_cad_user.setToolTipText("");
        tv_name_cad_user.setDebugGraphicsOptions(javax.swing.DebugGraphics.FLASH_OPTION);

        et_name_cad_user.setToolTipText("Nome");

        tv_born_date_cad_user.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tv_born_date_cad_user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tv_born_date_cad_user.setText("Data nasc:");

        et_born_date_cad_user.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        tv_email_cad_user.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tv_email_cad_user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tv_email_cad_user.setText("E-mail:");

        et_email_cad_user.setToolTipText("user@uerr.com ...");

        tv_data_cad_user.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        tv_data_cad_user.setText("Dados pessoais");

        tv_sex_cad_user.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tv_sex_cad_user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tv_sex_cad_user.setText("Sexo:");

        rg_sex_cad_user.add(rb_masculino_cad_user);
        rb_masculino_cad_user.setText("Masculino");
        rb_masculino_cad_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_masculino_cad_userActionPerformed(evt);
            }
        });

        rg_sex_cad_user.add(rb_feminino_cad_user);
        rb_feminino_cad_user.setText("Feminino");

        tv_celular_cad_user.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tv_celular_cad_user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tv_celular_cad_user.setText("Celular:");

        et_celular_cad_user.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        tv_residencial_cad_user.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tv_residencial_cad_user.setText("Tel Residencial:");

        et_residencial_cad_user.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        et_residencial_cad_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                et_residencial_cad_userActionPerformed(evt);
            }
        });

        tv_matricula_cad_user.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tv_matricula_cad_user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tv_matricula_cad_user.setText("Matricula:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(tv_email_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(et_email_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(tv_born_date_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(et_born_date_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(tv_name_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(et_name_cad_user))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(tv_cpf_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(et_cpf_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tv_rg_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(et_rg_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(tv_data_cad_user))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tv_sex_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tv_celular_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(et_celular_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(rb_masculino_cad_user)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_feminino_cad_user))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(tv_residencial_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(et_residencial_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(tv_matricula_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(et_matricula_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tv_data_cad_user)
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tv_cpf_cad_user)
                    .addComponent(et_cpf_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tv_rg_cad_user)
                    .addComponent(et_rg_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tv_name_cad_user)
                    .addComponent(et_name_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tv_born_date_cad_user)
                    .addComponent(et_born_date_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tv_email_cad_user)
                    .addComponent(et_email_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tv_sex_cad_user)
                    .addComponent(rb_masculino_cad_user)
                    .addComponent(rb_feminino_cad_user))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tv_celular_cad_user)
                    .addComponent(et_celular_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tv_residencial_cad_user)
                    .addComponent(et_residencial_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tv_matricula_cad_user)
                    .addComponent(et_matricula_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, null));

        tv_token_cad_user.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tv_token_cad_user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tv_token_cad_user.setText("Cadastro");

        et_token_cad_user.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        et_token_cad_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                et_token_cad_userActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Dados do sistema");

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, null));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Login:");

        tv_senha_cad_user.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tv_senha_cad_user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tv_senha_cad_user.setText("Senha:");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Conf Senha:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(et_login_cad_user))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(tv_senha_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(et_senha_cad_user))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(et_senha2_cad_user)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(et_login_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tv_senha_cad_user)
                    .addComponent(et_senha_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(et_senha2_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Tipo:");

        sp_tipos_cad_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sp_tipos_cad_userActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(tv_token_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(et_token_cad_user))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sp_tipos_cad_user, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tv_token_cad_user)
                    .addComponent(et_token_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(sp_tipos_cad_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout painel_cad_userLayout = new javax.swing.GroupLayout(painel_cad_user);
        painel_cad_user.setLayout(painel_cad_userLayout);
        painel_cad_userLayout.setHorizontalGroup(
            painel_cad_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_cad_userLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painel_cad_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(painel_cad_userLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        painel_cad_userLayout.setVerticalGroup(
            painel_cad_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_cad_userLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painel_cad_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tv_cad_new_user.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        tv_cad_new_user.setText("Cadastro de usuários");

        bt_back_cad_user.setText("VOLTAR");
        bt_back_cad_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_back_cad_userActionPerformed(evt);
            }
        });

        bt_save_cad_user.setText("SALVAR");
        bt_save_cad_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_save_cad_userActionPerformed(evt);
            }
        });

        painel_lista_cad_user.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, null));

        tv_relation_cad_user.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        tv_relation_cad_user.setText("Relação de usuários");

        jScrollPane1.setViewportView(tb_cad_user);

        javax.swing.GroupLayout painel_lista_cad_userLayout = new javax.swing.GroupLayout(painel_lista_cad_user);
        painel_lista_cad_user.setLayout(painel_lista_cad_userLayout);
        painel_lista_cad_userLayout.setHorizontalGroup(
            painel_lista_cad_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_lista_cad_userLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tv_relation_cad_user)
                .addContainerGap(391, Short.MAX_VALUE))
            .addGroup(painel_lista_cad_userLayout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                .addContainerGap())
        );
        painel_lista_cad_userLayout.setVerticalGroup(
            painel_lista_cad_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_lista_cad_userLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tv_relation_cad_user)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                .addContainerGap())
        );

        bt_listar_cad_user.setText("LISTAR");
        bt_listar_cad_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_listar_cad_userActionPerformed(evt);
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
                        .addComponent(tv_cad_new_user)
                        .addGap(250, 250, 250)
                        .addComponent(bt_listar_cad_user)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bt_save_cad_user))
                    .addComponent(painel_cad_user, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painel_lista_cad_user, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 477, Short.MAX_VALUE)
                        .addComponent(bt_back_cad_user)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tv_cad_new_user)
                    .addComponent(bt_back_cad_user)
                    .addComponent(bt_save_cad_user)
                    .addComponent(bt_listar_cad_user))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painel_cad_user, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painel_lista_cad_user, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void et_token_cad_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_et_token_cad_userActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_et_token_cad_userActionPerformed

    private void et_residencial_cad_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_et_residencial_cad_userActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_et_residencial_cad_userActionPerformed

    private void bt_back_cad_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_back_cad_userActionPerformed
        this.uicu.setVisible(false);
    }//GEN-LAST:event_bt_back_cad_userActionPerformed

    private void et_cep_cad_userFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_et_cep_cad_userFocusLost
        JSONObject cepResposta = null;
        try {
            pgb_cad_user.setVisible(true);
            cepResposta = WsViaCep.getCepResponse(et_cep_cad_user.getText());
            System.out.println(cepResposta.toString(1));
            et_logradouro_cad_user.setText(cepResposta.getString("logradouro"));
            et_bairro_cad_user.setText(cepResposta.getString("bairro"));
            et_cidade_cad_user.setText(cepResposta.getString("localidade"));
            et_estado_cad_user.setText(cepResposta.getString("uf"));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally{
            pgb_cad_user.setVisible(false);
        }
    }//GEN-LAST:event_et_cep_cad_userFocusLost

    private void bt_save_cad_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_save_cad_userActionPerformed
        Usuario usuario = new Usuario(ujc.getUsuarioCount()+1);/**************************************8*/
        usuario.setCelular(et_celular_cad_user.getText());
        usuario.setCpf(et_cpf_cad_user.getText());
        usuario.setDataCad(new Date());
        usuario.setMatricula(et_matricula_cad_user.getText());
        usuario.setRg(et_rg_cad_user.getText());
        usuario.setNome(et_name_cad_user.getText());
        usuario.setTelefone(et_residencial_cad_user.getText());
        usuario.setCadastro(et_token_cad_user.getText());
        
        Enderecos endereco = new Enderecos(ejc.getEnderecosCount()+1);/********************************8*/
        endereco.setBairro(et_bairro_cad_user.getText());
        endereco.setCep(et_cep_cad_user.getText());
        endereco.setCidade(et_cidade_cad_user.getText());
        endereco.setLogradouro(et_logradouro_cad_user.getText());
        endereco.setEstado(et_estado_cad_user.getText());
        
        Endereco endereco1 = new Endereco(ejc1.getEnderecoCount()+1);/*********************************8*/
        endereco1.setUsuarioId(usuario);
        endereco1.setEnderecosId(endereco);
        endereco1.setNumero(et_numero_cad_user.getText());
        
        UsuarioPerfil perfil = new UsuarioPerfil(upjc.getUsuarioPerfilCount()+1);/*********************8*/
        perfil.setLogin(et_login_cad_user.getText());
        perfil.setSenha(et_senha_cad_user.getText());
        perfil.setTipoUsuarioPerfilId( (TipoUsuarioPerfil) sp_tipos_cad_user.getModel().getSelectedItem());
        perfil.setUsuarioId(usuario);
        
        try {
            ejc.create(endereco);
            ujc.create(usuario);
            ejc1.create(endereco1);
            upjc.create(perfil);
            carregaTabela();
            JOptionPane.showMessageDialog(null, "Novo usuário cadastrado !!");
            limparForm();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao inserir um usuário !!"+ex.getLocalizedMessage());
        } finally{
            
        }
        
    }//GEN-LAST:event_bt_save_cad_userActionPerformed

    private void sp_tipos_cad_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sp_tipos_cad_userActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sp_tipos_cad_userActionPerformed

    private void bt_listar_cad_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_listar_cad_userActionPerformed
        verificaPressionado();
    }//GEN-LAST:event_bt_listar_cad_userActionPerformed

    private void et_cpf_cad_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_et_cpf_cad_userActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_et_cpf_cad_userActionPerformed

    private void rb_masculino_cad_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_masculino_cad_userActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_masculino_cad_userActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_back_cad_user;
    private javax.swing.JToggleButton bt_listar_cad_user;
    private javax.swing.JButton bt_save_cad_user;
    private javax.swing.JTextField et_bairro_cad_user;
    private javax.swing.JFormattedTextField et_born_date_cad_user;
    private javax.swing.JFormattedTextField et_celular_cad_user;
    private javax.swing.JFormattedTextField et_cep_cad_user;
    private javax.swing.JTextField et_cidade_cad_user;
    private javax.swing.JFormattedTextField et_cpf_cad_user;
    private javax.swing.JTextField et_email_cad_user;
    private javax.swing.JTextField et_estado_cad_user;
    private javax.swing.JTextField et_login_cad_user;
    private javax.swing.JTextField et_logradouro_cad_user;
    private javax.swing.JTextField et_matricula_cad_user;
    private javax.swing.JTextField et_name_cad_user;
    private javax.swing.JTextField et_numero_cad_user;
    private javax.swing.JFormattedTextField et_residencial_cad_user;
    private javax.swing.JTextField et_rg_cad_user;
    private javax.swing.JPasswordField et_senha2_cad_user;
    private javax.swing.JPasswordField et_senha_cad_user;
    private javax.swing.JTextField et_token_cad_user;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel painel_cad_user;
    private javax.swing.JPanel painel_lista_cad_user;
    private javax.swing.JProgressBar pgb_cad_user;
    private javax.swing.JRadioButton rb_feminino_cad_user;
    private javax.swing.JRadioButton rb_masculino_cad_user;
    private javax.swing.ButtonGroup rg_sex_cad_user;
    private javax.swing.JComboBox<String> sp_tipos_cad_user;
    private javax.swing.JTable tb_cad_user;
    private javax.swing.JLabel tv_born_date_cad_user;
    private javax.swing.JLabel tv_cad_new_user;
    private javax.swing.JLabel tv_celular_cad_user;
    private javax.swing.JLabel tv_cep_cad_user;
    private javax.swing.JLabel tv_cidade_cad_user;
    private javax.swing.JLabel tv_cpf_cad_user;
    private javax.swing.JLabel tv_data_cad_user;
    private javax.swing.JLabel tv_email_cad_user;
    private javax.swing.JLabel tv_logradouro_cad_user;
    private javax.swing.JLabel tv_matricula_cad_user;
    private javax.swing.JLabel tv_name_cad_user;
    private javax.swing.JLabel tv_numero_cad_user;
    private javax.swing.JLabel tv_relation_cad_user;
    private javax.swing.JLabel tv_residencial_cad_user;
    private javax.swing.JLabel tv_rg_cad_user;
    private javax.swing.JLabel tv_senha_cad_user;
    private javax.swing.JLabel tv_sex_cad_user;
    private javax.swing.JLabel tv_token_cad_user;
    // End of variables declaration//GEN-END:variables

    private void carregaTabela() {
        tb_cad_user.setModel(new UsersTableModel());
    }

    private void carregaTipos() {
        ComboBoxModel modelo = new TiposComboModel();
        sp_tipos_cad_user.setModel(modelo);
    }

    private void limparForm() {
        
    }

    private void verificaPressionado() {
        if(bt_listar_cad_user.isSelected()){
            painel_lista_cad_user.setVisible(true);
            painel_cad_user.setVisible(false);
        }else{
            painel_lista_cad_user.setVisible(false);
            painel_cad_user.setVisible(true);
        }
    }
        
    private class UsersTableModel implements TableModel{

        String[] colunas = new String[]{
            "Nome",
            "Cadastro",
            "Data nasc",
            "Data cadastro",
            "CPF",
            "RG",
            "Celular",
            "Residencial",
            "matricula"};
        
        @Override
        public int getRowCount() {
            return users.size();
        }

        @Override
        public int getColumnCount() {
            return colunas.length;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return colunas[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch(columnIndex){
                case 0:
                    return String.class;
                case 1:
                    return String.class;
                case 2:
                    return String.class;
                case 3:
                    return String.class;
                case 4:
                    return String.class;
                case 5:
                    return String.class;
                case 6:
                    return String.class;
                case 7:
                    return String.class;
                case 8:
                    return String.class;
                default:
                    return null;
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Usuario u = users.get(rowIndex);
            switch(columnIndex){
                case 0:
                    return u.getNome();
                case 1:
                    return u.getCadastro();
                case 2:
                    return u.getDataNasc();
                case 3:    
                    return u.getDataCad();
                case 4:    
                    return u.getCpf();
                case 5:    
                    return u.getRg();
                case 6:    
                    return u.getCelular();    
                case 7:    
                    return u.getTelefone();
                case 8:    
                    return u.getMatricula();
                default:
                    return null;
            }
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            
        }

        @Override
        public void addTableModelListener(TableModelListener l) {
            
        }

        @Override
        public void removeTableModelListener(TableModelListener l) {
            
        }
        
    }
    
    private class TiposComboModel extends AbstractListModel<TipoUsuarioPerfil> implements ComboBoxModel<TipoUsuarioPerfil>{

        TipoUsuarioPerfil selected;

        private TiposComboModel() {
            setSelectedItem(tipoPerfis.get(0));
        }
        
        @Override
        public void setSelectedItem(Object anItem) {
            selected = (TipoUsuarioPerfil) anItem;
        }

        @Override
        public Object getSelectedItem() {
            return selected;
        }

        @Override
        public int getSize() {
            return tipoPerfis.size();
        }

        @Override
        public TipoUsuarioPerfil getElementAt(int index) {
            return tipoPerfis.get(index);
        }

        @Override
        public void addListDataListener(ListDataListener l) {
        }

        @Override
        public void removeListDataListener(ListDataListener l) {
        }
        
    }
}
