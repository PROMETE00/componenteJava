package com.mycompany.componentebarra;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ComponenteBarra extends JPanel {
    public JProgressBar jProgressBar1;
    private Consumer<String> onCompletionListener; 

    public ComponenteBarra() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        jProgressBar1 = new JProgressBar();
        jProgressBar1.setStringPainted(true);
        jProgressBar1.setValue(0);
        add(jProgressBar1, BorderLayout.CENTER);
    }
    
    public int estado() {
        return jProgressBar1.getValue();
    }
    
    public void reiniciar(int valor) {
        jProgressBar1.setValue(valor);
    }
    
    public void Visible() {
        jProgressBar1.setVisible(true);
    }
    
    public void Invisible() {
        jProgressBar1.setVisible(false);
    }

    // Método para establecer el listener de finalización
    public void setOnCompletionListener(Consumer<String> listener) {
        this.onCompletionListener = listener;
    }

    public void iniciarSubida() {
        new Thread(() -> {
            long tiempoInicio = System.currentTimeMillis();
            for (int progreso = 0; progreso <= 100; progreso++) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                long tiempoTranscurrido = System.currentTimeMillis() - tiempoInicio;
                long tiempoTotalEstimado = (tiempoTranscurrido * 100) / (progreso + 1);
                long tiempoRestante = tiempoTotalEstimado - tiempoTranscurrido;

                final int progresoFinal = progreso;
                final long segundosRestantes = tiempoRestante / 1000;

                SwingUtilities.invokeLater(() -> {
                    jProgressBar1.setValue(progresoFinal);
                    jProgressBar1.setString(progresoFinal + "% - Tiempo restante: " + segundosRestantes + "s");

                    // Notificar al completar el progreso
                    if (progresoFinal == 100 && onCompletionListener != null) {
                        onCompletionListener.accept("Archivo cargado correctamente.");
                    }
                });
            }
        }).start();
    }
}
