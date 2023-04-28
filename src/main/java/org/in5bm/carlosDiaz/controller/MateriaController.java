package org.in5bm.carlosDiaz.controller;

import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.in5bm.carlosDiaz.db.Conexion;
import org.in5bm.carlosDiaz.domain.Materia;
import org.in5bm.carlosDiaz.system.Principal;


public class MateriaController implements Initializable {

    @FXML
    private ImageView imgNuevo;
    @FXML
    private Button btnNuevo;
    @FXML
    private ImageView imgModificar;
    @FXML
    private Button btnModificar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private Button btnEliminar;
    @FXML
    private ImageView imgReporte;
    @FXML
    private Button btnReporte;
    @FXML
    private TextField txtMateria;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtSalon;
    @FXML
    private TextField txtCatedratico;
    @FXML
    private Spinner<Integer> spnCiclo;
    private SpinnerValueFactory<Integer> valueFactoryCiclo;

    @FXML
    private Spinner<Integer> spnNota;
    private SpinnerValueFactory<Integer> valueFactoryNota;

    @FXML
    private Spinner<Integer> spnCupoMinimo;
    private SpinnerValueFactory<Integer> valueFactoryCupoMinimo;

    @FXML
    private Spinner<Integer> spnCupoMaximo;
    private SpinnerValueFactory<Integer> valueFactoryCupoMaximo;

    @FXML
    private JFXTimePicker tpkHorarioInicio;
    @FXML
    private JFXTimePicker tpkHorarioFinal;
    @FXML
    private TableView<Materia> tblMateria;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colMateria;
    @FXML
    private TableColumn colCiclo;
    @FXML
    private TableColumn colHorarioInicio;
    @FXML
    private TableColumn colHorarioFinal;
    @FXML
    private TableColumn colCatedratico;
    @FXML
    private TableColumn colSalon;
    @FXML
    private TableColumn colCupoMaximo;
    @FXML
    private TableColumn colCupoMinimo;
    @FXML
    private TableColumn colNota;
    @FXML
    private ObservableList<Materia> listaMateria;

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        valueFactoryCiclo = new SpinnerValueFactory.IntegerSpinnerValueFactory(2022, 2025, 2022);
        spnCiclo.setValueFactory(valueFactoryCiclo);

        valueFactoryCupoMaximo = new SpinnerValueFactory.IntegerSpinnerValueFactory(75, 250, 50);
        spnCupoMaximo.setValueFactory(valueFactoryCupoMaximo);

        valueFactoryCupoMinimo = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 75, 1);
        spnCupoMinimo.setValueFactory(valueFactoryCupoMinimo);

        valueFactoryNota = new SpinnerValueFactory.IntegerSpinnerValueFactory(70, 100, 70);
        spnNota.setValueFactory(valueFactoryNota);

        cargarDatos();
    }
    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    public void cargarDatos() {
        tblMateria.setItems(getMateria());
        colId.setCellValueFactory(new PropertyValueFactory<Materia, Integer>("id"));
        colMateria.setCellValueFactory(new PropertyValueFactory<Materia, String>("nombreMateria"));
        colCiclo.setCellValueFactory(new PropertyValueFactory<Materia, Integer>("cicloEscolar"));
        colHorarioInicio.setCellValueFactory(new PropertyValueFactory<Materia, String>("horarioInicio"));
        colHorarioFinal.setCellValueFactory(new PropertyValueFactory<Materia, String>("horarioFinal"));
        colCatedratico.setCellValueFactory(new PropertyValueFactory<Materia, String>("catedratico"));
        colSalon.setCellValueFactory(new PropertyValueFactory<Materia, Integer>("salon"));
        colCupoMaximo.setCellValueFactory(new PropertyValueFactory<Materia, Integer>("cupoMaximo"));
        colCupoMinimo.setCellValueFactory(new PropertyValueFactory<Materia, Integer>("cupoMinimo"));
        colNota.setCellValueFactory(new PropertyValueFactory<Materia, String>("nota"));
    }   

    @FXML
    public void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            txtId.setText(String.valueOf(((Materia) tblMateria.getSelectionModel().getSelectedItem()).getId()));

            txtMateria.setText(((Materia) tblMateria.getSelectionModel().getSelectedItem()).getNombreMateria());

            spnCiclo.getValueFactory().setValue(((Materia) tblMateria.getSelectionModel().getSelectedItem()).getCicloEscolar());

            tpkHorarioInicio.setValue(((Materia) tblMateria.getSelectionModel().getSelectedItem()).getHorarioInicio());

            tpkHorarioFinal.setValue(((Materia) tblMateria.getSelectionModel().getSelectedItem()).getHorarioFinal());

            txtCatedratico.setText(((Materia) tblMateria.getSelectionModel().getSelectedItem()).getCatedratico());

            txtSalon.setText(((Materia) tblMateria.getSelectionModel().getSelectedItem()).getSalon());

            spnCupoMaximo.getValueFactory().setValue(((Materia) tblMateria.getSelectionModel().getSelectedItem()).getCupoMaximo());

            spnCupoMinimo.getValueFactory().setValue(((Materia) tblMateria.getSelectionModel().getSelectedItem()).getCupoMinimo());

            spnNota.getValueFactory().setValue(((Materia) tblMateria.getSelectionModel().getSelectedItem()).getNota());
        }
    }
    
    public boolean existeElementoSeleccionado() {
        return (tblMateria.getSelectionModel().getSelectedItem() != null);
    }

    public boolean agregarMateria() {
        Materia materia = new Materia();

        materia.setNombreMateria(txtMateria.getText());
        materia.setCicloEscolar(spnCiclo.getValue());
        materia.setHorarioInicio(tpkHorarioInicio.getValue().now());
        materia.setHorarioFinal(tpkHorarioFinal.getValue().now());
        materia.setCatedratico(txtCatedratico.getText());
        materia.setSalon(txtSalon.getText());
        materia.setCupoMaximo(spnCupoMaximo.getValue());
        materia.setCupoMinimo(spnCupoMinimo.getValue());
        materia.setNota(spnNota.getValue());

        PreparedStatement pstmt = null;

        try {

            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_materia_create(?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            pstmt.setString(1, materia.getNombreMateria());
            pstmt.setInt(2, materia.getCicloEscolar());
            pstmt.setObject(3, materia.getHorarioInicio());
            pstmt.setObject(4, materia.getHorarioFinal());
            pstmt.setString(5, materia.getCatedratico());
            pstmt.setString(6, materia.getSalon());
            pstmt.setInt(7, materia.getCupoMaximo());
            pstmt.setInt(8, materia.getCupoMinimo());
            pstmt.setInt(9, materia.getNota());
            
            System.out.println(pstmt.toString());
            pstmt.execute();
            listaMateria.add(materia);

            return true;

        } catch (SQLException e) {
            System.err.println("\n Se produjo un error al intentar insertar la siguiente materia: " + materia.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    private boolean actualizarMateria() {
        Materia materia = new Materia(
                Integer.parseInt(txtId.getText()),
                txtMateria.getText(),
                spnCiclo.getValue(),
                tpkHorarioInicio.getValue(),
                tpkHorarioFinal.getValue(),
                txtCatedratico.getText(),
                txtSalon.getText(),
                spnCupoMaximo.getValue(),
                spnCupoMinimo.getValue(),
                spnNota.getValue()
        );

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_materia_update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            pstmt.setInt(1, materia.getId());
            pstmt.setString(2, materia.getNombreMateria());
            pstmt.setInt(3, materia.getCicloEscolar());
            pstmt.setObject(4, materia.getHorarioInicio());
            pstmt.setObject(5, materia.getHorarioFinal());
            pstmt.setString(6, materia.getCatedratico());
            pstmt.setString(7, materia.getSalon());
            pstmt.setInt(8, materia.getCupoMaximo());
            pstmt.setInt(9, materia.getCupoMinimo());
            pstmt.setInt(10, materia.getNota());

            System.out.println(pstmt.toString());
            pstmt.execute();

            return true;

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar actualizar el siguiente registro" + materia.toString());
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean eliminarMateria() {
        if (existeElementoSeleccionado()) {
            Materia materia = (Materia) tblMateria.getSelectionModel().getSelectedItem();
            System.out.println(materia.toString());

            PreparedStatement pstmt = null;
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_materia_delete(?)}");
                pstmt.setInt(1, materia.getId());
                System.out.println(pstmt.toString());

                pstmt.execute();
                return true;

            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar la siguiente Materia: " + materia.toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return false;
    }
    
    public ObservableList getMateria() {

        ArrayList<Materia> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("call sp_materia_read()");
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Materia materia = new Materia();

                materia.setId(rs.getInt(1));
                materia.setNombreMateria(rs.getString(2));
                materia.setCicloEscolar(rs.getInt(3));
                materia.setHorarioInicio(rs.getTime(4).toLocalTime());
                materia.setHorarioFinal(rs.getTime(5).toLocalTime());
                materia.setCatedratico(rs.getString(6));
                materia.setSalon(rs.getString(7));
                materia.setCupoMaximo(rs.getInt(8));
                materia.setCupoMinimo(rs.getInt(9));
                materia.setNota(rs.getInt(10));

                lista.add(materia);

                System.out.println(materia.toString());
            }

            listaMateria = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("\n Se produjo un error al intentar consultar la lista de Alumnos");
            System.out.println("Message: " + e.getMessage());
            System.out.println("Error code: " + e.getErrorCode());
            System.out.println("SQLState: " + e.getSQLState());
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listaMateria;
    }

    private void habilitarCampos() {
        txtId.setEditable(false);
        txtMateria.setEditable(true);
        spnCiclo.setEditable(true);
        tpkHorarioInicio.setEditable(true);
        tpkHorarioFinal.setEditable(true);
        txtCatedratico.setEditable(true);
        txtSalon.setEditable(true);
        spnCupoMaximo.setEditable(true);
        spnCupoMinimo.setEditable(true);
        spnNota.setEditable(true);

        txtId.setDisable(true);
        txtMateria.setDisable(false);
        spnCiclo.setDisable(false);
        tpkHorarioInicio.setDisable(false);
        tpkHorarioFinal.setDisable(false);
        txtCatedratico.setDisable(false);
        txtSalon.setDisable(false);
        spnCupoMaximo.setDisable(false);
        spnCupoMinimo.setDisable(false);
        spnNota.setDisable(false);
    }

    private void deshabilitarCampos() {
        txtId.setEditable(false);
        txtMateria.setEditable(false);
        spnCiclo.setEditable(false);
        txtCatedratico.setEditable(false);
        txtSalon.setEditable(false);
        spnCupoMaximo.setEditable(false);
        spnCupoMinimo.setEditable(false);
        spnNota.setEditable(false);

        txtId.setDisable(true);
        txtMateria.setDisable(true);
        spnCiclo.setDisable(true);
        tpkHorarioInicio.setDisable(true);
        tpkHorarioFinal.setDisable(true);
        txtCatedratico.setDisable(true);
        txtSalon.setDisable(true);
        spnCupoMaximo.setDisable(true);
        spnCupoMinimo.setDisable(true);
        spnNota.setDisable(true);
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtMateria.setText("");
        spnCiclo.getValueFactory().setValue(2020);
        tpkHorarioInicio.getEditor().clear();
        tpkHorarioFinal.getEditor().clear();
        txtCatedratico.clear();
        txtSalon.clear();
        spnCupoMaximo.getValueFactory().setValue(0);
        spnCupoMinimo.getValueFactory().setValue(0);
        spnNota.getValueFactory().setValue(0);

    }

    

    @FXML
    private void clicNuevo() {

        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                tblMateria.setDisable(true);

                txtId.setEditable(false);
                txtId.setDisable(true);

                limpiarCampos();
                btnNuevo.setText("Guardar");
               

                btnModificar.setText("Cancelar");
                

                btnEliminar.setDisable(true);
                imgEliminar.setDisable(true);

                btnEliminar.setVisible(false);
                imgEliminar.setVisible(false);

                operacion = Operacion.GUARDAR;
                break;

            case GUARDAR:

                if (txtMateria.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Materia es obligatorio");
                    Stage dialog = new Stage();
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alert.show();

                } else if (txtCatedratico.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("El Catedrático es obligatorio");
                    Stage dialog = new Stage();
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alert.show();

                } else if (txtSalon.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Campo Salón es obligatorio");
                    Stage dialog = new Stage();
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alert.show();
                } else {
                    if (agregarMateria()) {
                        cargarDatos();
                        limpiarCampos();
                        deshabilitarCampos();
                        tblMateria.setDisable(false);

                        btnNuevo.setText("Nuevo");
                       
                        btnModificar.setText("Modificar");
                        

                        btnEliminar.setDisable(false);
                        imgEliminar.setDisable(false);

                        btnEliminar.setVisible(true);
                        imgEliminar.setVisible(true);

                        btnReporte.setDisable(false);
                        btnReporte.setDisable(false);

                        btnReporte.setVisible(true);
                        imgReporte.setVisible(true);

                        operacion = Operacion.NINGUNO;
                    }
                }

                break;
        }
    }
    
    @FXML
    private void clicModificar() {

        switch (operacion) {
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    habilitarCampos();

                    btnNuevo.setDisable(true);
                    imgNuevo.setDisable(true);

                    btnNuevo.setVisible(false);
                    imgNuevo.setVisible(false);

                    btnModificar.setText("Guardar");
                    

                    btnEliminar.setText("Cancelar");
                   
                    operacion = Operacion.ACTUALIZAR;

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Materia");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar seleccione un registro");
                    Stage dialog = new Stage();
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alert.show();
                }

                break;

            case GUARDAR:

                btnNuevo.setText("Nuevo");
               

                btnModificar.setText("Modificar");
                

                btnEliminar.setDisable(false);
                imgEliminar.setDisable(false);

                btnEliminar.setVisible(true);
                imgEliminar.setVisible(true);

                limpiarCampos();
                deshabilitarCampos();

                tblMateria.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;

            case ACTUALIZAR:
                if (existeElementoSeleccionado()) {
                    if (actualizarMateria()) {
                        cargarDatos();
                        limpiarCampos();
                        deshabilitarCampos();
                        tblMateria.setDisable(false);
                        tblMateria.getSelectionModel().clearSelection();

                        btnNuevo.setVisible(true);
                        imgNuevo.setVisible(true);

                        btnNuevo.setDisable(false);
                        imgNuevo.setDisable(false);

                        btnNuevo.setText("Nuevo");
                        

                        btnModificar.setText("Modificar");
                       

                        btnEliminar.setText("Eliminar");
                        

                        btnEliminar.setDisable(false);
                        imgEliminar.setDisable(false);

                        btnEliminar.setVisible(true);
                        imgEliminar.setVisible(true);

                        btnReporte.setDisable(false);
                        btnReporte.setDisable(false);

                        btnReporte.setVisible(true);
                        imgReporte.setVisible(true);

                        operacion = Operacion.NINGUNO;
                    }
                }
                break;

        }
    }

    @FXML
    private void clicEliminar() {
        switch (operacion) {
            case ACTUALIZAR: //CANCELAR LA ACTUALIZACIÓN
                btnNuevo.setDisable(false);
                imgNuevo.setDisable(false);

                btnNuevo.setVisible(true);
                imgNuevo.setVisible(true);

                btnModificar.setText("Modificar");
                

                btnEliminar.setText("Eliminar");
               

                limpiarCampos();
                deshabilitarCampos();

                tblMateria.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;

                break;

            case NINGUNO:

                if (existeElementoSeleccionado()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Materia");
                    alert.setHeaderText(null);
                    Stage dialog = new Stage();
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alert.setContentText("¿Desea eliminar el registro seleccionado?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get().equals(ButtonType.OK)) {
                        if (eliminarMateria()) {
                            listaMateria.remove(tblMateria.getSelectionModel().getFocusedIndex());
                            limpiarCampos();
                            cargarDatos();

                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Materia");
                            alert.setHeaderText(null);
                            alert.setContentText("Registro eliminado exitosamente");
                            dialog = new Stage();
                            stage = (Stage) alert.getDialogPane().getScene().getWindow();
                            alert.show();
                        }

                    } else if (result.get().equals(ButtonType.CANCEL)) {
                        alert.close();
                        tblMateria.getSelectionModel().clearSelection();
                        limpiarCampos();
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Materia");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar seleccione un registro");
                    Stage dialog = new Stage();
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alert.show();
                }
                break;
        }
    }
    
    

}
