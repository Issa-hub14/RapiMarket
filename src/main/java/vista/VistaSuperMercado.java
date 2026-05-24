/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import modelo.Producto;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Vista encargada de guiar al usuario dentro del super mercado
 * Permite buscar productos, escuchar indicaciones de ubicación, recorrer productos
 * de la lista y utilizar comandos por voz
 * 
 * @author isabe
 */
public class VistaSuperMercado extends VistaBase {

    /**
     * Creates new form VistaSuperMercado
     */
    private ActionListener listenerBuscar;
    private ActionListener listenerSiguiente;
    private ActionListener listenerRepetir;
    private ActionListener listenerMicrofono;
    private ActionListener listenerVolver;
    private ActionListener listenerSeleccionLista;

    private Producto ultimoProducto;
    private JButton ultimoBoton = null;
    private boolean listaLista = false;

    public VistaSuperMercado() {
        super("Guía de Supermercado");
        setLocationRelativeTo(null);
        lectorVoz.hablar("Modo supermercado. Busca un producto o sigue tu lista.");
    }

    /**
     * Inicializa los componentes visuales de la ventana
     */
    @Override
    protected void initComponentes() {
        initComponents();
        configurarEstilos();
    }

    /**
     * Configura estilos, eventos y comportamientos de los componentes.
     */
    private void configurarEstilos() {

        lblProductoActual.setHorizontalAlignment(SwingConstants.CENTER);
        lblPasillo.setHorizontalAlignment(SwingConstants.CENTER);
        lblPrecio.setHorizontalAlignment(SwingConstants.CENTER);

        txtBuscar.addActionListener(e -> btnBuscar.doClick());

        aplicarEstiloBoton(btnBuscar, new Color(162, 225, 225), "Buscar producto");

        lstMiLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        lstMiLista.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && listaLista) {
                String item = lstMiLista.getSelectedValue();
                if (item != null && listenerSeleccionLista != null) {
                    listenerSeleccionLista.actionPerformed(new ActionEvent(lstMiLista, ActionEvent.ACTION_PERFORMED, item));
                   
                }
            }
        });

        aplicarEstiloBoton(btnSiguiente, new Color(146, 195, 98), "Siguiente producto");
        aplicarEstiloBoton(btnRepetir, new Color(235, 93, 93), "Repetir indicacion");
        aplicarEstiloBoton(btnVolver, new Color(171, 171, 171), "Volver al menú");
        aplicarEstiloBoton(btnMicrofono, new Color(0, 51, 0), "Microfono");
    }

    /**
     * Aplica estilo y comportamiento personalizado a un botón
     * @param btn Botón a configurar
     * @param color Color principal del botón
     * @param textoVoz Texto reproducido por voz
     */
    private void aplicarEstiloBoton(JButton btn, Color color, String textoVoz) {
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        for (ActionListener al : btn.getActionListeners()) {
            btn.removeActionListener(al);
        }

        btn.addMouseListener(new MouseAdapter() {
            /**
             * Cambia el color del botón cuando el mouse entra
             * @param e evento del mouse
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(color.darker());
            }

            /**
             * Restaura el color original del botón
             * @param e evento del mouse
             */
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(color);
            }

            /**
             * Ejecuta la acción del botón seleccionado
             * @param e evento del mouse
             */
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
                if (btn == btnSiguiente && listenerSiguiente != null) {
                    listenerSiguiente.actionPerformed(new ActionEvent(btn, ActionEvent.ACTION_PERFORMED, ""));
                }
                if (btn == btnRepetir && listenerRepetir != null) {
                    listenerRepetir.actionPerformed(new ActionEvent(btn, ActionEvent.ACTION_PERFORMED, ""));
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

    public String getTextoBusqueda() {
        return txtBuscar.getText().trim();
    }

    public String getItemListaSeleccionado() {
        return lstMiLista.getSelectedValue();
    }

    public void setTextoBusqueda(String texto) {
        txtBuscar.setText(texto);
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

    public void addBtnVolverListener(ActionListener l) {
        this.listenerVolver = l;
    }

    public void addBtnMicrofonoListener(ActionListener l) {
        this.listenerMicrofono = l;
    }
    
    public void addSeleccionListaListener(ActionListener listener){
        this.listenerSeleccionLista = listener;
    }

    /**
     * Muestra la información del producto encontrado y la reproduce por voz
     * @param p Producto encontrado
     */
    public void mostrarInfoProducto(Producto p) {
        ultimoProducto = p;
        if (p == null) {
            lblProductoActual.setText("Producto no encontrado");
            lblPasillo.setText("-");
            lblPrecio.setText("-");
            lectorVoz.hablar("Producto no encontrado");
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

    /**
     * Reproduce nuevamente la indicación del último producto consultado
     */
    public void repetirIndicacion() {
        if (ultimoProducto != null) {
            lectorVoz.hablar(
                    ultimoProducto.getNombre()
                    + ". Está en el  "
                    + ultimoProducto.getPasillo()
            );
        } else {
            lectorVoz.hablar("No hay ningún producto para repetir.");
        }
    }

    /**
     * Actualiza la lista visual de compras 
     * @param lista Lista de productos
     */
    public void actualizarListaMercado(List<String> lista) {
        listaLista = false;
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String item : lista) {
            model.addElement(item);
        }
        lstMiLista.setModel(model);
        listaLista = true;

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
        btnVolver = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstMiLista = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnMicrofono, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(41, 41, 41)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(244, 244, 244)
                                .addComponent(btnRepetir, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 6, Short.MAX_VALUE)))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRepetir, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
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

    private void btnRepetirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRepetirActionPerformed
        // TODO add your handling code here:
        if (listenerRepetir != null) {
            listenerRepetir.actionPerformed(evt);
        }
    }//GEN-LAST:event_btnRepetirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnMicrofono;
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
