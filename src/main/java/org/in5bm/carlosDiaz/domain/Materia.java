package org.in5bm.carlosDiaz.domain;

import java.time.LocalTime;

public class Materia {
    
    private int id;
    private String nombreMateria;
    private int cicloEscolar;
    private LocalTime horarioInicio;
    private LocalTime horarioFinal;
    private String catedratico;
    private String salon;
    private int cupoMaximo;
    private int cupoMinimo;
    private int nota;
    
    public Materia (){
        
    }
    
    public Materia (int id){
        this.id = id;
    }

    public Materia(String nombreMateria, int cicloEscolar, LocalTime horarioInicio, LocalTime horarioFinal, String catedratico, String salon, int cupoMaximo, int cupoMinimo, int nota) {
        this.nombreMateria = nombreMateria;
        this.cicloEscolar = cicloEscolar;
        this.horarioInicio = horarioInicio;
        this.horarioFinal = horarioFinal;
        this.catedratico = catedratico;
        this.salon = salon;
        this.cupoMaximo = cupoMaximo;
        this.cupoMinimo = cupoMinimo;
        this.nota = nota;
    }

    public Materia(int id, String nombreMateria, int cicloEscolar, LocalTime horarioInicio, LocalTime horarioFinal, String catedratico, String salon, int cupoMaximo, int cupoMinimo, int nota) {
        this.id = id;
        this.nombreMateria = nombreMateria;
        this.cicloEscolar = cicloEscolar;
        this.horarioInicio = horarioInicio;
        this.horarioFinal = horarioFinal;
        this.catedratico = catedratico;
        this.salon = salon;
        this.cupoMaximo = cupoMaximo;
        this.cupoMinimo = cupoMinimo;
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public int getCicloEscolar() {
        return cicloEscolar;
    }

    public void setCicloEscolar(int cicloEscolar) {
        this.cicloEscolar = cicloEscolar;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalTime getHorarioFinal() {
        return horarioFinal;
    }

    public void setHorarioFinal(LocalTime horarioFinal) {
        this.horarioFinal = horarioFinal;
    }

    public String getCatedratico() {
        return catedratico;
    }

    public void setCatedratico(String catedratico) {
        this.catedratico = catedratico;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public int getCupoMinimo() {
        return cupoMinimo;
    }

    public void setCupoMinimo(int cupoMinimo) {
        this.cupoMinimo = cupoMinimo;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Materia{" + "id=" + id + ", nombreMateria=" + nombreMateria + ", cicloEscolar=" + cicloEscolar + ", horarioInicio=" + horarioInicio 
                + ", horarioFinal=" + horarioFinal + ", catedratico=" + catedratico + ", salon=" + salon + ", cupoMaximo=" + cupoMaximo 
                + ", cupoMinimo=" + cupoMinimo + ", nota=" + nota + '}';
    }   
    
}
