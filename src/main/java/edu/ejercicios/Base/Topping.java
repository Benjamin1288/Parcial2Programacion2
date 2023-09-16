package edu.ejercicios.Base;

import java.util.ArrayList;

public class Topping {


    private String nombre; // Nombre del topping


    public double getPrecio() {
        return precio;
    }

    private double precio;
    private ArrayList<String> ingredientes = new ArrayList<>(); // Ingredientes del topping

    public void agregarIngrediente(String ingrediente) {
        this.ingredientes.add(ingrediente);
    }

    public Topping(String nombre,double precio) {
        this.precio=precio;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Nombre: "+nombre+", precio: "+precio;
    }


    //getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<String> ingredientes) {
        this.ingredientes = ingredientes;
    }


}