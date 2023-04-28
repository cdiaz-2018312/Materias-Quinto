package org.in5bm.CarlosDiaz.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import org.in5bm.carlosDiaz.db.Conexion;
import org.in5bm.carlosDiaz.domain.Materia;
import org.in5bm.carlosDiaz.models.idao.IMateriaDAO;

public class MateriaDaoImpl implements IMateriaDAO {

    private final String SQL_CREATE = "{CALL sp_materia_create(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private final String SQL_READ = "{CALL sp_materia_read()}";
    private final String SQL_READ_BY_ID = "{CALL sp_materia_read_by_id(?)}";
    private final String SQL_UPDATE = "{CALL sp_materia_update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private final String SQL_DELETE = "{CALL sp_materia_delete(?)}";

    private Connection con = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private Materia materia = null;
    private List<Materia> listaMateria = new ArrayList<>();

    @Override
    public List<Materia> getAll() {
        try {
            con = Conexion.getInstance().getConexion();
            pstmt = con.prepareStatement(SQL_READ);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                materia = new Materia(rs.getInt("id_materia"), rs.getString("nombre_materia"), rs.getInt("ciclo_escolar"),
                        rs.getTime("horario_inicio").toLocalTime(), rs.getTime("horario_final").toLocalTime(), rs.getString("catedratico"),
                        rs.getString("salon"), rs.getInt("cupo_maximo"), rs.getInt("cupo_minimo"), rs.getInt("nota"));
                listaMateria.add(materia);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(pstmt);
            Conexion.close(con);
        }
        return listaMateria;
    }

    @Override
    public int add(Materia materia) {
        int rows = 0;
        try {
            con = Conexion.getInstance().getConexion();
            pstmt = con.prepareStatement(SQL_CREATE);
            pstmt.setString(1, materia.getNombreMateria());
            pstmt.setInt(2, materia.getCicloEscolar());
            pstmt.setTime(3, Time.valueOf(materia.getHorarioInicio()));
            pstmt.setTime(4, Time.valueOf(materia.getHorarioFinal()));
            pstmt.setString(5, materia.getCatedratico());
            pstmt.setString(6, materia.getSalon());
            pstmt.setInt(7, materia.getCupoMaximo());
            pstmt.setInt(8, materia.getCupoMinimo());
            pstmt.setInt(9, materia.getNota());
            System.out.println(pstmt.toString());
            rows = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar insertar el siguiente registro: " + materia.toString());
            e.printStackTrace(System.out);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(pstmt);
            Conexion.close(con);
        }
        return rows;
    }

    @Override
    public int update(Materia materia) {
        int rows = 0;       
        try {
            con = Conexion.getInstance().getConexion();            
            pstmt = con.prepareStatement(SQL_UPDATE);
            pstmt.setString(1, materia.getNombreMateria());
            pstmt.setInt(2, materia.getCicloEscolar());
            pstmt.setTime(3, Time.valueOf(materia.getHorarioInicio()));
            pstmt.setTime(4, Time.valueOf(materia.getHorarioFinal()));
            pstmt.setString(5, materia.getCatedratico());
            pstmt.setString(6, materia.getSalon());
            pstmt.setInt(7, materia.getCupoMaximo());
            pstmt.setInt(8, materia.getCupoMinimo());
            pstmt.setInt(9, materia.getNota());
            pstmt.setInt(10, materia.getId());
            
            System.out.println(pstmt.toString());            
            rows = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar actualizar el siguiente registro: " + materia.toString());
            e.printStackTrace(System.out);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(pstmt);
            Conexion.close(con);
        }
        return rows;
    }

    @Override
    public int delete(Materia materia) {
        int rows = 0;
        try {
            con = Conexion.getInstance().getConexion();
            pstmt = con.prepareStatement(SQL_DELETE);
            pstmt.setInt(1, materia.getId());
            System.out.println(pstmt.toString());
            rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar eliminar el registro con el id:" + materia.getId());
            e.printStackTrace(System.out);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return rows;
    }
    
    public Materia get(Materia materia) {
        try {
            con = Conexion.getInstance().getConexion();
            pstmt = con.prepareStatement(SQL_READ_BY_ID);
            pstmt.setInt(1, materia.getId());
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                materia = new Materia(rs.getInt("id_materia"), rs.getString("nombre_materia"), rs.getInt("ciclo_escolar"),rs.getTime("horario_inicio").toLocalTime(), rs.getTime("horario_final").toLocalTime(), rs.getString("catedratico"),
                        rs.getString("salon"), rs.getInt("cupo_maximo"), rs.getInt("cupo_minimo"), rs.getInt("nota"));
            }
            System.out.println("materia: " + materia);
        } catch (SQLException e) {
            System.out.println("\nSQLException\n");
            e.printStackTrace(System.out);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(pstmt);
            Conexion.close(con);
        }
        return materia;
    }    
}
