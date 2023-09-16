package edu.ejercicios.Especialidades;

import edu.ejercicios.Base.Pizza;
import edu.ejercicios.Base.Topping;

import java.util.ArrayList;
import java.util.List;

public class PizzaHamburguesa extends Pizza {
    private List<String> ingredientes = new ArrayList<>();

    public PizzaHamburguesa(String name) {
        super(name);
        // Agregar los ingredientes de la pizza Sushi
        agregarIngrediente("Queso",10.00);
        agregarIngrediente("Tomate",2.55);
        agregarIngrediente("Carne",8.55);
        agregarIngrediente("Pan",11.05);
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

    private void agregarIngrediente(String ingrediente,double precio) {
        Topping topping = new Topping(ingrediente, precio);
        addTopping(topping);
    }
}
