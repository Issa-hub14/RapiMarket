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

public class VistaListaCompras extends VistaBase {

    /**
     * Creates new form VistaListaCompras
     */
    private ActionListener listenerBuscar;
    private ActionListener listenerRepetir;
    private ActionListener listenerAgregarLista;
    private ActionListener listenerQuitarLista;
    private ActionListener listenerMicrofono;
    private ActionListener listenerVolver;
    private ActionListener accionDobleClick;

    private Producto ultimoProducto;
    private JButton ultimoBoton = null;
    private boolean listaLista = false;

    public VistaListaCompras() {
        super("Lista de Compras");
        setLocationRelativeTo(null);
        lectorVoz.hablar("Modo Lista. Selecciona un producto para agregar a tu lista.");
    }

    @Override
    protected void initComponentes() {
        initComponents();
        configurarEstilos();
    }

    private void configurarEstilos() {
        lstResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstResultados.setFixedCellHeight(38);

        lstResultados.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String sel = lstResultados.getSelectedValue();
                if (sel != null) {
                    ultimoBoton = null;
                    String[] partes = sel.split("—");
                    String nombre = partes[0].trim();
                    String precio = partes[1].replace("$", "").trim();
                    lectorVoz.hablar(
                            nombre
                            + " Cuesta " + precio + " pesos "
                            + "Pulsa boton para agregar a la lista"
                    );

                    actualizarEstado("Seleccionado: " + nombre);
                }
            }
        });

        lstResultados.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && accionDobleClick != null) {
                    String sel = lstResultados.getSelectedValue();
                    if (sel != null) {
                        accionDobleClick.actionPerformed(
                                new ActionEvent(lstResultados, ActionEvent.ACTION_PERFORMED, sel));
                    }
                }
            }
        });
        lstMiLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstMiLista.setFixedCellHeight(38);
        lstMiLista.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && listaLista) {
                String item = lstMiLista.getSelectedValue();
                if (item != null) {
                    ultimoBoton = null;
                    lectorVoz.hablar(item + " en tu lista.");
                }
            }
        });

        txtBuscar.addActionListener(e -> btnBuscar.doClick());

        aplicarEstiloBoton(btnBuscar, new Color(162, 225, 225), "Buscar producto");
        aplicarEstiloBoton(btnRepetir, new Color(235, 93, 93), "Repetir indicacion");
        aplicarEstiloBoton(btnAgregarLista, new Color(72, 112, 32), "Agregar a mi lista");
        aplicarEstiloBoton(btnQuitarLista, new Color(186, 135, 75), "Quitar de mi lista");
        aplicarEstiloBoton(btnVolver, new Color(171, 171, 171), "Volver al menú");
        aplicarEstiloBoton(btnMicrofono, new Color(0, 51, 0), "Microfono");
    }

    private void aplicarEstiloBoton(JButton btn, Color color, String textoVoz) {
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        for (ActionListener al : btn.getActionListeners()) {
            btn.removeActionListener(al);
        }

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(color.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(color);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (ultimoBoton != btn) {
                    ultimoBoton = btn;
                    lectorVoz.hablar(textoVoz);
                    return;
                }

                ultimoBoton = null;

                if (btn == btnBuscar && listenerBuscar != null) {
                    listenerBuscar.actionPerformed(new ActionEvent(btn, ActionEvent.ACTION_PERFORMED, ""));
                }
                if (btn == btnRepetir && listenerRepetir != null) {
                    listenerRepetir.actionPerformed(new ActionEvent(btn, ActionEvent.ACTION_PERFORMED, ""));
                }
                if (btn == btnAgregarLista && listenerAgregarLista != null) {
                    listenerAgregarLista.actionPerformed(new ActionEvent(btn, ActionEvent.ACTION_PERFORMED, ""));
                }
                if (btn == btnQuitarLista && listenerQuitarLista != null) {
                    listenerQuitarLista.actionPerformed(new ActionEvent(btn, ActionEvent.ACTION_PERFORMED, ""));
                }
                if (btn == btnVolver && listenerVolver != null) {
                    listenerVolver.actionPerformed(new ActionEvent(btn, ActionEvent.ACTION_PERFORMED, ""));
                }
                if (btn == btnMicrofono && listenerMicrofono != null) {
                    listenerMicrofono.actionPerformed(new ActionEvent(btn, ActionEvent.ACTION_PERFORMED, ""));
                }
            }
        });
    }

    public void mostrarResultados(List<Producto> productos) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Producto p : productos) {
            model.addElement(p.getNombre() + " — $" + (int) p.getPrecio());
        }
        lstResultados.setModel(model);
    }

    public void actualizarListaMercado(List<String> lista) {
        listaLista = false;
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String item : lista) {
            model.addElement(item);
        }
        lstMiLista.setModel(model);
        listaLista = true;
    }

    public String getTextoBusqueda() {
        return txtBuscar.getText().trim();
    }

    public String getItemListaSeleccionado() {
        return lstMiLista.getSelectedValue();
    }

    public String getProductoSeleccionado() {
        return lstResultados.getSelectedValue();
    }

    public void addBtnBuscarListener(ActionListener l) {
        this.listenerBuscar = l;
    }

    public void addBtnRepetirListener(ActionListener l) {
        this.listenerRepetir = l;
    }

    public void addBtnAgregarListaListener(ActionListener l) {
        this.listenerAgregarLista = l;
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
        lectorVoz.hablar(
                p.getNombre()
                + ". Precio " + (int) p.getPrecio() + " pesos."
        );
    }

    public void repetirIndicacion() {
        DefaultListModel<String> model = (DefaultListModel<String>) lstMiLista.getModel();

        if (model == null || model.isEmpty()) {
            lectorVoz.hablar("Tu lista de compras está vacía.");
            return;
        }
        
        lectorVoz.hablar("Tu lista tiene " + model.size() + " productos.");
        for (int i = 0; i < model.size(); i++) {
            String item = model.get(i);
            lectorVoz.hablar(item);
        }
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        btnRepetir = new javax.swing.JButton();
        btnAgregarLista = new javax.swing.JButton();
        btnQuitarLista = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstMiLista = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstResultados = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(685, 525));

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(685, 525));
        jPanel1.setRequestFocusEnabled(false);

        lblTitulo.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 48)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 51, 0));
        lblTitulo.setText("LISTA DE COMPRAS");

        btnMicrofono.setBackground(new java.awt.Color(0, 51, 0));
        btnMicrofono.setFont(new java.awt.Font("Segoe UI Emoji", 0, 24)); // NOI18N
        btnMicrofono.setForeground(new java.awt.Color(255, 255, 255));
        btnMicrofono.setText("🎙️");
        btnMicrofono.setBorder(null);
        btnMicrofono.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnMicrofono.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
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

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 0, 24)); // NOI18N
        jLabel4.setText("🛒 LISTA DE COMPRAS");

        jLabel5.setFont(new java.awt.Font("Segoe UI Emoji", 0, 24)); // NOI18N
        jLabel5.setText("PRODUCTOS DISPONIBLES");

        btnBuscar.setBackground(new java.awt.Color(162, 225, 225));
        btnBuscar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnBuscar.setText("BUSCAR");
        btnBuscar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
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

        lstResultados.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(lstResultados);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnMicrofono, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnRepetir, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAgregarLista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane2))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnQuitarLista, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnVolver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(29, 29, 29))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(22, Short.MAX_VALUE))))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(lblTitulo)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnMicrofono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnQuitarLista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnRepetir, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAgregarLista, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnVolver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 684, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMicrofonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMicrofonoActionPerformed
        // TODO add your handling code here:
        if (listenerMicrofono != null) {
            listenerMicrofono.actionPerformed(evt);
        }
    }//GEN-LAST:event_btnMicrofonoActionPerformed

    private void btnQuitarListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarListaActionPerformed
        // TODO add your handling code here:
        if (listenerQuitarLista != null) {
            listenerQuitarLista.actionPerformed(evt);
        }
    }//GEN-LAST:event_btnQuitarListaActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // TODO add your handling code here:
        if (listenerVolver != null) {
            listenerVolver.actionPerformed(evt);
        }
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnAgregarListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarListaActionPerformed
        // TODO add your handling code here:
        if (listenerAgregarLista != null) {
            listenerAgregarLista.actionPerformed(evt);
        }
    }//GEN-LAST:event_btnAgregarListaActionPerformed

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

    private void btnRepetirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRepetirActionPerformed
        // TODO add your handling code here:
        if (listenerRepetir != null) {
            listenerRepetir.actionPerformed(evt);
        }
    }//GEN-LAST:event_btnRepetirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarLista;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnMicrofono;
    private javax.swing.JButton btnQuitarLista;
    private javax.swing.JButton btnRepetir;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JList<String> lstMiLista;
    private javax.swing.JList<String> lstResultados;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
