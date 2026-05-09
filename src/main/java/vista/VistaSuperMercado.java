/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 *
 * @author isabe
 */
import modelo.Producto;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class VistaSuperMercado extends VistaBase {

    /**
     * Creates new form VistaSuperMercado
     */
    private ActionListener listenerBuscar;
    private ActionListener listenerSiguiente;
    private ActionListener listenerRepetir;
    private ActionListener listenerAgrgarLista;
    private ActionListener listenerQuitarLista;
    private ActionListener listenerMicrofono;
    private ActionListener listenerVolver;

    private Producto ultimoProducto;

    public VistaSuperMercado() {
        super("Guía de Supermercado");
        setLocationRelativeTo(null);
        lectorVoz.hablar("Modo supermercado. Busca un producto o sigue tu lista.");
    }

    @Override
    protected void initComponentes() {
        initComponents();
        configurarEstilos();
    }

    private void configurarEstilos() {

        lblProductoActual.setHorizontalAlignment(SwingConstants.CENTER);
        lblPasillo.setHorizontalAlignment(SwingConstants.CENTER);
        lblPrecio.setHorizontalAlignment(SwingConstants.CENTER);

        txtBuscar.addActionListener(e -> btnBuscar.doClick());

        aplicarEstiloBoton(btnBuscar, new Color(162, 225, 225), "Buscar producto");

        
        lstMiLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        

        lstMiLista.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String item = lstMiLista.getSelectedValue();
                if (item != null) {
                    lectorVoz.hablar(item + " en tu lista.");
                }
            }
        });

        aplicarEstiloBoton(btnSiguiente, new Color(146, 195, 98), "Siguiente producto");
        aplicarEstiloBoton(btnRepetir, new Color(235, 93, 93), "Repetir indicación");
        aplicarEstiloBoton(btnAgregarLista, new Color(72, 112, 32), "Agregar a mi lista");
        aplicarEstiloBoton(btnQuitarLista, new Color(186, 135, 75), "Quitar de mi lista");
        aplicarEstiloBoton(btnVolver, new Color(171, 171, 171), "Volver al menú");
    }

    private void aplicarEstiloBoton(JButton btn, Color color, String textoVoz) {
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(color.darker());
                lectorVoz.hablar(textoVoz);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(color);
            }
        });
    }

    public String getTextoBusqueda() {
        return txtBuscar.getText().trim();
    }

    public String getItemListaSeleccionado() {
        return lstMiLista.getSelectedValue();
    }

    public void addBtnBuscarListener(ActionListener l) {
        this.listenerBuscar = l;
    }

    public void addBtnSiguienteListener(ActionListener l) {
        this.listenerSiguiente = l;
    }

    public void addBtnRepetirListener(ActionListener l) {
        this.listenerRepetir = l;
    }

    public void addBtnAgregarListaListener(ActionListener l) {
        this.listenerAgrgarLista = l;
    }

    public void addBtnQuitarListaListener(ActionListener l) {
        this.listenerQuitarLista = l;
    }

    public void addBtnVolverListener(ActionListener l) {
        this.listenerVolver = l;
    }

    public void addBtnMicrofonoListener(ActionListener l) {
        this.listenerMicrofono = l;
    }

    public void mostrarInfoProducto(Producto p) {
        ultimoProducto = p;
        if (p == null) {
            lblProductoActual.setText("Producto no encontrado");
            lblPasillo.setText("-");
            lblPrecio.setText("-");
            return;
        }

        lblProductoActual.setText(p.getNombre());
        lblPasillo.setText("Pasillo: " + p.getPasillo());
        lblPrecio.setText("Precio: $" + (int) p.getPrecio());

        lectorVoz.hablar(
                p.getNombre()
                + ". Pasillo " + p.getPasillo()
                + ". Precio " + (int) p.getPrecio() + " pesos."
        );
    }

    public void repetirIndicacion() {
        if (ultimoProducto != null) {
            lectorVoz.hablar(
                    ultimoProducto.getNombre()
                    + ". Está en el pasillo "
                    + ultimoProducto.getPasillo()
            );
        }
    }

    public void actualizarListaMercado(List<String> lista) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String item : lista) {
            model.addElement(item);
        }
        lstMiLista.setModel(model);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        btnMicrofono = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblProductoActual = new javax.swing.JLabel();
        lblPasillo = new javax.swing.JLabel();
        lblPrecio = new javax.swing.JLabel();
        btnSiguiente = new javax.swing.JButton();
        btnRepetir = new javax.swing.JButton();
        btnAgregarLista = new javax.swing.JButton();
        btnQuitarLista = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstMiLista = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(685, 525));

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        lblTitulo.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 48)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 51, 0));
        lblTitulo.setText("GUÍA DE SUPERMERCADO");

        btnMicrofono.setBackground(new java.awt.Color(0, 51, 0));
        btnMicrofono.setFont(new java.awt.Font("Segoe UI Emoji", 0, 24)); // NOI18N
        btnMicrofono.setForeground(new java.awt.Color(255, 255, 255));
        btnMicrofono.setText("🎙️");
        btnMicrofono.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnMicrofono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMicrofonoActionPerformed(evt);
            }
        });

        txtBuscar.setText("Buscar producto...");
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(162, 225, 225));
        btnBuscar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnBuscar.setText("BUSCAR");
        btnBuscar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 0, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("📢 INFO. DEL PRODUCTO");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 0, 24)); // NOI18N
        jLabel4.setText("🛒 LISTA DE COMPRAS");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setMaximumSize(new java.awt.Dimension(293, 293));
        jPanel3.setName(""); // NOI18N

        lblProductoActual.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblProductoActual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProductoActual.setText("Selecciona un producto");

        lblPasillo.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 24)); // NOI18N
        lblPasillo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPasillo.setText("-");

        lblPrecio.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPrecio.setForeground(new java.awt.Color(102, 102, 102));
        lblPrecio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPrecio.setText("Precio");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPasillo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblProductoActual, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 28, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lblPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblProductoActual)
                .addGap(26, 26, 26)
                .addComponent(lblPasillo)
                .addGap(32, 32, 32)
                .addComponent(lblPrecio)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        btnSiguiente.setBackground(new java.awt.Color(146, 195, 98));
        btnSiguiente.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSiguiente.setForeground(new java.awt.Color(255, 255, 255));
        btnSiguiente.setText("SIGUIENTE");
        btnSiguiente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });

        btnRepetir.setBackground(new java.awt.Color(235, 93, 93));
        btnRepetir.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRepetir.setForeground(new java.awt.Color(255, 255, 255));
        btnRepetir.setText("REPETIR");
        btnRepetir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRepetir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRepetirActionPerformed(evt);
            }
        });

        btnAgregarLista.setBackground(new java.awt.Color(72, 112, 32));
        btnAgregarLista.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAgregarLista.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarLista.setText("AGREGAR A LISTA");
        btnAgregarLista.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAgregarLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarListaActionPerformed(evt);
            }
        });

        btnQuitarLista.setBackground(new java.awt.Color(196, 135, 75));
        btnQuitarLista.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnQuitarLista.setForeground(new java.awt.Color(255, 255, 255));
        btnQuitarLista.setText("QUITAR DE LISTA");
        btnQuitarLista.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnQuitarLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarListaActionPerformed(evt);
            }
        });

        btnVolver.setBackground(new java.awt.Color(171, 171, 171));
        btnVolver.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.setText("↩️ REGRESAR");
        btnVolver.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        lstMiLista.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(lstMiLista);

        jScrollPane1.setViewportView(jScrollPane2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTitulo)
                .addGap(96, 96, 96))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnMicrofono, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(btnSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRepetir, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnAgregarLista, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnQuitarLista, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnVolver, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMicrofono, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVolver, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnRepetir, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAgregarLista, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnQuitarLista, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMicrofonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMicrofonoActionPerformed
        // TODO add your handling code here:
        if (listenerMicrofono != null) {
            listenerMicrofono.actionPerformed(evt);
        }
    }//GEN-LAST:event_btnMicrofonoActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
        if (listenerBuscar != null) {
            listenerBuscar.actionPerformed(evt);
        }
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        if (listenerBuscar != null) {
            listenerBuscar.actionPerformed(evt);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        // TODO add your handling code here:
        if (listenerSiguiente != null) {
            listenerSiguiente.actionPerformed(evt);
        }
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // TODO add your handling code here:
        if (listenerVolver != null) {
            listenerVolver.actionPerformed(evt);
        }
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnAgregarListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarListaActionPerformed
        // TODO add your handling code here:
        if (listenerAgrgarLista != null) {
            listenerAgrgarLista.actionPerformed(evt);
        }
    }//GEN-LAST:event_btnAgregarListaActionPerformed

    private void btnRepetirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRepetirActionPerformed
        // TODO add your handling code here:
        if (listenerRepetir != null) {
            listenerRepetir.actionPerformed(evt);
        }
    }//GEN-LAST:event_btnRepetirActionPerformed

    private void btnQuitarListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarListaActionPerformed
        // TODO add your handling code here:
        if (listenerQuitarLista != null) {
            listenerQuitarLista.actionPerformed(evt);
        }
    }//GEN-LAST:event_btnQuitarListaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaSuperMercado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaSuperMercado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaSuperMercado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaSuperMercado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaSuperMercado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarLista;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnMicrofono;
    private javax.swing.JButton btnQuitarLista;
    private javax.swing.JButton btnRepetir;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblPasillo;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblProductoActual;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JList<String> lstMiLista;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
