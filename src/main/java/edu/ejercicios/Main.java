package edu.ejercicios;

import edu.ejercicios.Formularios.frmPizza;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame=new JFrame("frmPizza");
        frame.setContentPane(new frmPizza().getJpanelPrincipal());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,1000);
        frame.pack();
        frame.setVisible(true);
    }
}