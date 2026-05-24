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
 * Vista encargada de gestionar los pedidos en linea
 * Permite buscar productos,agregarlos o eliminarlos del carrito y confirmar la compra
 * 
 * @author isabe
 */
public class VistaPedidoOnline extends VistaBase {

    /**
     * Creates new form VistaPedidoOnline
     */
    private JButton ultimoBoton = null;
    private String ultimoElementoSeleccionado = null;

    private ActionListener accionDobleClick;
    private ActionListener listenerBuscar;
    private ActionListener listenerAgregar;
    private ActionListener listenerEliminar;
    private ActionListener listenerConfirmar;
    private ActionListener listenerVolver;
    private ActionListener listenerMicrofono;
    private ActionListener listenerLeerCarrito;

    public VistaPedidoOnline() {
        super("Comprar en Línea");
        setLocationRelativeTo(null);
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

        lstResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstResultados.setFixedCellHeight(38);

        lstResultados.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String sel = lstResultados.getSelectedValue();
                if (sel != null) {
                    String[] partes = sel.split("—");
                    String nombre = partes[0].trim();
                    String precio = partes[1].replace("$", "").trim();
                    lectorVoz.hablar(
                            nombre
                            + " Cuesta " + precio + " pesos "
                            + "Pulsa nuevamente para agregar al carrito"
                    );

                    actualizarEstado("Seleccionado: " + nombre);
                }
            }
        });

        lstResultados.addMouseListener(new MouseAdapter() {
            /**
             * Detecta dobleClick sobre un producto
             * @param e evento del mouse
             */
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
        txtBuscar.addActionListener(e -> btnBuscar.doClick());

        txtCarrito.setEditable(false);
        txtCarrito.setLineWrap(true);
        txtCarrito.setWrapStyleWord(true);

        lblTotal.setForeground(new Color(34, 136, 74));

        aplicarEstiloBoton(btnBuscar, new Color(162, 225, 225), "Buscar producto");
        aplicarEstiloBoton(btnAgregar, new Color(146, 195, 98), "Agregar al carrito");
        aplicarEstiloBoton(btnEliminar, new Color(235, 93, 93), "Eliminar del carrito");
        aplicarEstiloBoton(btnLeerCarrito, new Color(72, 112, 32), "Leer carrito en voz alta");
        aplicarEstiloBoton(btnConfirmar, new Color(196, 135, 75), "Confirmar pedido");
        aplicarEstiloBoton(btnVolver, new Color(171, 171, 171), "Volver al menu");
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
                if (btn == btnAgregar && listenerAgregar != null) {
                    listenerAgregar.actionPerformed(new ActionEvent(btn, ActionEvent.ACTION_PERFORMED, ""));
                }
                if (btn == btnEliminar && listenerEliminar != null) {
                    listenerEliminar.actionPerformed(new ActionEvent(btn, ActionEvent.ACTION_PERFORMED, ""));
                }
                if (btn == btnBuscar && listenerBuscar != null) {
                    listenerBuscar.actionPerformed(new ActionEvent(btn, ActionEvent.ACTION_PERFORMED, ""));
                }
                if (btn == btnConfirmar && listenerConfirmar != null) {
                    listenerConfirmar.actionPerformed(new ActionEvent(btn, ActionEvent.ACTION_PERFORMED, ""));
                }
                if (btn == btnVolver && listenerVolver != null) {
                    listenerVolver.actionPerformed(new ActionEvent(btn, ActionEvent.ACTION_PERFORMED, ""));
                }
                if (btn == btnMicrofono && listenerMicrofono != null) {
                    listenerMicrofono.actionPerformed(new ActionEvent(btn, ActionEvent.ACTION_PERFORMED, ""));
                }
                if (btn == btnLeerCarrito && listenerLeerCarrito != null) {
                    listenerLeerCarrito.actionPerformed(new ActionEvent(btn, ActionEvent.ACTION_PERFORMED, ""));
                }
            }
        }
        );
    }

    /**
     * Muestra los productos encontrados en la busqueda 
     * @param productos Lista de productos encontrados
     */
    public void mostrarResultados(List<Producto> productos) {
        DefaultListModel<String> model = new DefaultListModel<>();
        if (productos.isEmpty()) {
            model.addElement("Sin resultados. Intenta otra búsqueda.");
            lectorVoz.hablar("No se encuentra producto");
        } else {
            for (Producto p : productos) {
                model.addElement(p.getNombre() + "  —  $" + (int) p.getPrecio());
            }
            
        }
        lstResultados.setModel(model);
    }

    /**
     * Actualiza el resumen y el total del carrito
     * @param resumen texto resumen del carrito
     * @param total Total acumulado de la compra
     */
    public void actualizarCarrito(String resumen, double total) {
        txtCarrito.setText(resumen);
        lblTotal.setText("Total: $" + (int) total); //precio
        
    }

    public String getTextoBusqueda() {
        return txtBuscar.getText().trim();
    }
    
     public void setTextoBusqueda(String texto) {
         txtBuscar.setText(texto);
    }


    public String getProductoSeleccionado() {
        return lstResultados.getSelectedValue();
    }

    public void addBtnBuscarListener(ActionListener l) {
        this.listenerBuscar = l;
    }

    public void addBtnMicrofonoListener(ActionListener l) {
        this.listenerMicrofono = l;
    }

    public void addBtnAgregarListener(ActionListener l) {
        this.listenerAgregar = l;
    }

    public void addBtnEliminarListener(ActionListener l) {
        this.listenerEliminar = l;
    }

    public void addBtnConfirmarListener(ActionListener l) {
        this.listenerConfirmar = l;
    }

    public void addBtnVolverListener(ActionListener l) {
        this.listenerVolver = l;
    }

    public void addBtnLeerCarritoListener(ActionListener l) {
        this.listenerLeerCarrito = l;
    }

    public void addDobleClickProductoListener(ActionListener l) {
        this.accionDobleClick = l;
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
        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstResultados = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtCarrito = new javax.swing.JTextArea();
        btnMicrofono = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLeerCarrito = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        jLabel1.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 0));
        jLabel1.setText("COMPRAR EN LINEA");

        txtBuscar.setText("Buscar producto...");
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        jLabel2.setText("¿Qué producto buscas?");

        btnBuscar.setBackground(new java.awt.Color(162, 225, 225));
        btnBuscar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnBuscar.setText("BUSCAR");
        btnBuscar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lstResultados.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(lstResultados);

        jScrollPane2.setViewportView(jScrollPane3);

        txtCarrito.setColumns(20);
        txtCarrito.setRows(5);
        jScrollPane1.setViewportView(txtCarrito);

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

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 0, 24)); // NOI18N
        jLabel3.setText("🛒 PRODUCTOS DISPONIBLES");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel4.setText("MI CARRITO");

        btnAgregar.setBackground(new java.awt.Color(146, 195, 98));
        btnAgregar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("AGREGAR");
        btnAgregar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAgregar.setPreferredSize(new java.awt.Dimension(95, 47));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(235, 93, 93));
        btnEliminar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("ELIMINAR");
        btnEliminar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEliminar.setPreferredSize(new java.awt.Dimension(93, 47));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnLeerCarrito.setBackground(new java.awt.Color(72, 112, 32));
        btnLeerCarrito.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLeerCarrito.setForeground(new java.awt.Color(255, 255, 255));
        btnLeerCarrito.setText("LEER CARRITO");
        btnLeerCarrito.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLeerCarrito.setPreferredSize(new java.awt.Dimension(93, 47));
        btnLeerCarrito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeerCarritoActionPerformed(evt);
            }
        });

        btnConfirmar.setBackground(new java.awt.Color(196, 135, 75));
        btnConfirmar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnConfirmar.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirmar.setText("CONFIRMAR");
        btnConfirmar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnConfirmar.setPreferredSize(new java.awt.Dimension(103, 47));
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnVolver.setBackground(new java.awt.Color(171, 171, 171));
        btnVolver.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.setText("↩️ REGRESAR");
        btnVolver.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnVolver.setPreferredSize(new java.awt.Dimension(93, 47));
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        lblTotal.setText("TOTAL:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnMicrofono, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(226, 226, 226)
                                .addComponent(btnLeerCarrito, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnVolver, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane2)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel3)
                                .addGap(72, 72, 72)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(141, 141, 141)
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(215, 215, 215)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnMicrofono, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(txtBuscar)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotal)
                        .addGap(31, 31, 31))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                        .addGap(17, 17, 17)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLeerCarrito, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
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

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
        if (listenerBuscar != null) {
            listenerBuscar.actionPerformed(evt);
        }
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void btnMicrofonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMicrofonoActionPerformed
        // TODO add your handling code here:
        if (listenerMicrofono != null) {
            listenerMicrofono.actionPerformed(evt);
        }
    }//GEN-LAST:event_btnMicrofonoActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        // TODO add your handling code here:
        if (listenerConfirmar != null) {
            listenerConfirmar.actionPerformed(evt);
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // TODO add your handling code here:
        if (listenerVolver != null) {
            listenerVolver.actionPerformed(evt);
        }
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        if (listenerEliminar != null) {
            listenerEliminar.actionPerformed(evt);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        if (listenerAgregar != null) {
            listenerAgregar.actionPerformed(evt);
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnLeerCarritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeerCarritoActionPerformed
        // TODO add your handling code here:
        if (listenerLeerCarrito != null) {
            listenerLeerCarrito.actionPerformed(evt);
        }
    }//GEN-LAST:event_btnLeerCarritoActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        if (listenerBuscar != null) {
            listenerBuscar.actionPerformed(evt);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLeerCarrito;
    private javax.swing.JButton btnMicrofono;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JList<String> lstResultados;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextArea txtCarrito;
    // End of variables declaration//GEN-END:variables
}
