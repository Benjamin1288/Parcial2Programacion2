package edu.ejercicios.Formularios;

import edu.ejercicios.Base.Pizza;
import edu.ejercicios.Base.Topping;
import edu.ejercicios.Especialidades.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class frmPizza {
    private JComboBox comboBoxPizza;
    private JComboBox comboBoxIngredientes;
    private JButton agregarButton;
    private JLabel lblTotal;
    private JList list1;
    private JButton removerIngredienteButton;
    private JButton prepararButton;
    private JList list2;
    private JPanel panel1;
    private JTextField textPizza;
    private JRadioButton radioPequeña;
    private JRadioButton radioMediana;
    private JRadioButton radioGrande;
    private DefaultListModel modeloLista=new DefaultListModel();

    private List<Topping> ingredientes=new ArrayList<>();

    private List<Pizza> especialidades=new ArrayList<Pizza>();

    public JPanel getJpanelPrincipal() {
        return panel1;
    }
    private double total=0.00;
    private ButtonGroup grupoTamanos = new ButtonGroup();

    public frmPizza() {
        cargarTopppings();
        cargarPizzas();

        comboBoxPizza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pizza pizzaSeleccionada = (Pizza) comboBoxPizza.getSelectedItem();

                // Limpia el modelo de lista para que no se añadan mas ingredientes al momento de seleccionar la pizza
                modeloLista.removeAllElements();
                total=0;

                // Verifica si la pizza seleccionada es pertenece
                if (pizzaSeleccionada instanceof PizzaSushi) {
                    PizzaSushi pizzaSushi = (PizzaSushi) pizzaSeleccionada;
                    List<Topping> ingredientesPizzaSushi = pizzaSushi.getToppings();

                    for (Topping topping : ingredientesPizzaSushi) {
                        modeloLista.addElement(topping.getNombre() + " - Precio: " + topping.getPrecio());
                    }
                } else if (pizzaSeleccionada instanceof PizzaKebab) {
                    PizzaKebab pizzaKebab = (PizzaKebab) pizzaSeleccionada;
                    List<Topping> ingredientesPizzaKebab = pizzaKebab.getToppings();

                    for (Topping topping : ingredientesPizzaKebab) {
                        modeloLista.addElement(topping.getNombre() + " - Precio: " + topping.getPrecio());
                    }
                } else if (pizzaSeleccionada instanceof PizzaHamburguesa) {
                    PizzaHamburguesa pizzaHamburguesa = (PizzaHamburguesa) pizzaSeleccionada;
                    List<Topping> ingredientesPizzaHamburguesa = pizzaHamburguesa.getToppings();

                    for (Topping topping : ingredientesPizzaHamburguesa) {
                        modeloLista.addElement(topping.getNombre() + " - Precio: " + topping.getPrecio());
                    }
                } else if (pizzaSeleccionada instanceof PizzaDePollo) {
                    PizzaDePollo pizzaDePollo = (PizzaDePollo) pizzaSeleccionada;
                    List<Topping> ingredientesPizzaDePollo = pizzaDePollo.getToppings();

                    for (Topping topping : ingredientesPizzaDePollo) {
                        modeloLista.addElement(topping.getNombre() + " - Precio: " + topping.getPrecio());
                    }
                }else if (pizzaSeleccionada instanceof PizzaConPolvosVerdes){
                    PizzaConPolvosVerdes pizzaConPolvosVerdes = (PizzaConPolvosVerdes) pizzaSeleccionada;
                    List<Topping> ingredientesPizzaConPolvosVerdes = pizzaConPolvosVerdes.getToppings();

                    for (Topping topping : ingredientesPizzaConPolvosVerdes) {
                        modeloLista.addElement(topping.getNombre() + " - Precio: " + topping.getPrecio());
                    }
                }

                // Actualiza la lista1
                list1.setModel(modeloLista);

                // Calcula el total de precios
                for (int i = 0; i < modeloLista.getSize(); i++) {
                    String item = (String) modeloLista.getElementAt(i);
                    String[] parts = item.split(" - Precio: ");
                    //si parts.lenght llega a tener 2 elementos
                    if (parts.length == 2) {
                        double precio = Double.parseDouble(parts[1]);
                        total += precio;
                    }
                }
                lblTotal.setText(String.valueOf(total));
            }
        });

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Topping ingredientes=(Topping) comboBoxIngredientes.getSelectedItem();


                modeloLista.addElement(ingredientes.toString());
                list1.setModel(modeloLista);
                //double total=ingredientes.getPrecio();
                //lblTotal.setText(String.valueOf(total));
                total+=ingredientes.getPrecio();
                lblTotal.setText(String.valueOf(total));

            }
        });

        //permite respodener acciones del mouse
        removerIngredienteButton.addMouseListener(new MouseAdapter() {
            int clickCount = 0;
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // Verifica si se han hecho dos clics
                    clickCount=2;

                    if (clickCount == 2) {
                        // Ejecuta la acción de remover ingredientes después del segundo clic
                        Topping ingredientes = (Topping) comboBoxIngredientes.getSelectedItem();
                        modeloLista.removeElement(ingredientes.toString());
                        list1.setModel(modeloLista);
                        if(total>0){
                            total -= ingredientes.getPrecio();
                        } else {
                            total=0;
                        }

                        lblTotal.setText(String.valueOf(total));

                        //clickCount = 0;
                    }
                }
                super.mouseClicked(e);
            }
        });

        prepararButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pizza pizzaSeleccionada = (Pizza) comboBoxPizza.getSelectedItem();

                if(validarPizza()) {
                    prepararButton.setEnabled(false);
                    DefaultListModel<String> modeloLista2 = new DefaultListModel<>(); // Nuevo modelo para list2

                    // Crea un SwingWorker para realizar la preparación de la pizza
                    //se utiliza para realizar taareas en segundo plano
                    //útil cuando se necesita realizar operaciones que pueden bloquear la interfaz de usuario durante un tiempo prolongado
                    SwingWorker<Void, String> preparacionWorker = new SwingWorker<Void, String>() {
                        @Override
                        protected Void doInBackground() throws Exception {

                            String men="Agregando...";
                            publish(men);
                            // Recorre la lista1 y agrega los ingredientes uno por uno
                            for (int i = 0; i < modeloLista.getSize(); i++) {
                                String toppingString = (String) modeloLista.getElementAt(i);
                                    String[] parts = toppingString.split(" - Precio: ");
                                    if (parts.length == 2) {
                                        String nombreIngrediente = parts[0];
                                        publish(nombreIngrediente); // Publica el nombre del ingrediente
                                    }else {
                                        String mensaje = "- " + toppingString;
                                        publish(mensaje);
                                    }
                                    // Pausa durante 1 segundo
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException ex) {
                                        ex.printStackTrace();
                                    }
                                }

                            return null;
                        }

                        @Override
                        protected void process(java.util.List<String> chunks) {
                            // Actualiza la interfaz con el progreso
                            for (String mensaje : chunks) {
                                modeloLista2.addElement(mensaje);
                                list2.setModel(modeloLista2);
                            }
                        }

                        @Override
                        protected void done() {
                            String mensaje2 = "La Pizza Esta Lista";
                            publish(mensaje2);
                            prepararButton.setEnabled(true);
                        }
                    };

                    preparacionWorker.execute(); // Ejecuta el SwingWorker
                }else {
                    JOptionPane.showMessageDialog(null, "La pizza debe tener un nombre, ingredientes y tamaño seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        grupoTamanos.add(radioPequeña);
        grupoTamanos.add(radioMediana);
        grupoTamanos.add(radioGrande);
        // Agrega ActionListeners a los botones de radio
        radioPequeña.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Restablece el total al valor predeterminado
                double nuevoTotal=(total+10);
                lblTotal.setText(String.valueOf(nuevoTotal));

            }
        });

        radioMediana.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Calcula el total con un incremento del 20% y lo muestra
                double nuevoTotal = (total +15)+ 1.20;
                lblTotal.setText(String.valueOf(nuevoTotal));
            }
        });

        radioGrande.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Calcula el total con un incremento del 35% y lo muestra
                double nuevoTotal = (total + 30)+ 1.35;
                lblTotal.setText(String.valueOf(nuevoTotal));
            }
        });
    }

    private void cargarTopppings(){
        ingredientes.add(new Topping("Champiñones",4.55));
        ingredientes.add(new Topping("Tomates",2.55));
        ingredientes.add(new Topping("Cebolla",6.55));
        ingredientes.add(new Topping("Salchicha",10.55));
        ingredientes.add(new Topping("Queso",10.00));
        ingredientes.add(new Topping("Masa",5.00));
        ingredientes.add(new Topping("Kebab",7.56));
        ingredientes.add(new Topping("Polvos Verdes",4.21));
        ingredientes.add(new Topping("Pollo",15.00));
        ingredientes.add(new Topping("Sushi",5.00));
        ingredientes.add(new Topping("Carne",8.55));
        ingredientes.add(new Topping("Pan",11.05));
        ingredientes.add(new Topping("Caramales",11.55));
        ingredientes.add(new Topping("Chucho",14.55));

        DefaultComboBoxModel model=new DefaultComboBoxModel(ingredientes.toArray());
        comboBoxIngredientes.setModel(model);
    }

    private void cargarPizzas(){
        especialidades.add(new Pizza("Yo La Armo"));
        especialidades.add(new PizzaSushi("Sushi"));
        especialidades.add(new PizzaKebab("Kebab"));
        especialidades.add(new PizzaHamburguesa("Hamburguesa"));
        especialidades.add(new PizzaDePollo("Pollo"));
        especialidades.add(new PizzaConPolvosVerdes("Con Polvos Verdes"));
        DefaultComboBoxModel model3=new DefaultComboBoxModel(especialidades.toArray());
        comboBoxPizza.setModel(model3);
    }
    private boolean validarPizza() {
        Pizza pizzaSeleccionada = (Pizza) comboBoxPizza.getSelectedItem();
        String nombrePizza = textPizza.getText();
        boolean tieneIngredientes = modeloLista.getSize() > 0;
        boolean tieneTamañoSeleccionado = radioPequeña.isSelected() || radioMediana.isSelected() || radioGrande.isSelected();

        // Verifica que se haya proporcionado un nombre, ingredientes y se haya seleccionado un tamaño
        return !nombrePizza.isEmpty() && tieneIngredientes && tieneTamañoSeleccionado;
    }

}
