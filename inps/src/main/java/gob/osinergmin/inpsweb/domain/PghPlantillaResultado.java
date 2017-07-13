/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.osinergmin.inpsweb.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rsantosv
 */
@Entity
@Table(name = "PGH_PLANTILLA_RESULTADO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghPlantillaResultado.findAll", query = "SELECT p FROM PghPlantillaResultado p")})
public class PghPlantillaResultado extends Auditoria {
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PLANTILLA_RESULTADO")
    private Long idPlantillaResultado;
    @Basic(optional = false)
    @Column(name = "ITERACION")
    private short iteracion;
    @Basic(optional = false)
    @Column(name = "FLAG_RESULTADO")
    private String flagResultado;
    @Column(name = "FLAG_SUPERVISION")
    private String flagSupervision;
    @Column(name = "NOMBRE_DOCUMENTO")
    private String nombreDocumento;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "DESCRIPCION_DOCUMENTO")
    private String descripcionDocumento;
    @JoinColumn(name = "ID_PROCESO", referencedColumnName = "ID_PROCESO")
    @ManyToOne(optional = false)
    private PghProceso idProceso;
    @JoinColumn(name = "ID_OBLIGACION_TIPO", referencedColumnName = "ID_OBLIGACION_TIPO")
    @ManyToOne(optional = false)
    private PghObligacionTipo idObligacionTipo;
    @JoinColumn(name = "ID_ACTIVIDAD", referencedColumnName = "ID_ACTIVIDAD")
    @ManyToOne(optional = false)
    private MdiActividad idActividad;
    @Column(name = "CARPETA")
    private String carpeta;
    @Column(name = "IDENTIFICADOR_PLANTILLA")
    private String identificadorPlantilla;
    @Column(name = "ID_MOTIVO_NO_SUPERVISION") //mdiosesf
    private Long idMotivoNoSupervision;  
    
    public PghPlantillaResultado() {
    }    

	public PghPlantillaResultado(Long idPlantillaResultado,String nombreDocumento, 
    							String descripcionDocumento,short iteracion, 
    							String flagResultado,String estado,
    							Long idProceso,Long idObligacionTipo,
    							Long idActividad) {
        this.idPlantillaResultado = idPlantillaResultado;
        this.nombreDocumento=nombreDocumento;
        this.descripcionDocumento=descripcionDocumento;
        this.iteracion = iteracion;
        this.flagResultado = flagResultado;
        this.estado = estado;
        this.idProceso=new PghProceso(idProceso);
        this.idObligacionTipo= new PghObligacionTipo(idObligacionTipo);
        this.idActividad=new MdiActividad(idActividad);
    }
	
	public Long getIdMotivoNoSupervision() {
		return idMotivoNoSupervision;
	}

	public void setIdMotivoNoSupervision(Long idMotivoNoSupervision) {
		this.idMotivoNoSupervision = idMotivoNoSupervision;
	}

    public PghPlantillaResultado(Long idPlantillaResultado) {
        this.idPlantillaResultado = idPlantillaResultado;
    }

    public String getIdentificadorPlantilla() {
        return identificadorPlantilla;
    }

    public void setIdentificadorPlantilla(String identificadorPlantilla) {
        this.identificadorPlantilla = identificadorPlantilla;
    }

    public String getCarpeta() {
        return carpeta;
    }

    public void setCarpeta(String carpeta) {
        this.carpeta = carpeta;
    }
    
    public Long getIdPlantillaResultado() {
        return idPlantillaResultado;
    }

    public void setIdPlantillaResultado(Long idPlantillaResultado) {
        this.idPlantillaResultado = idPlantillaResultado;
    }

    public short getIteracion() {
        return iteracion;
    }

    public void setIteracion(short iteracion) {
        this.iteracion = iteracion;
    }

    public String getFlagResultado() {
        return flagResultado;
    }
    public void setFlagResultado(String flagResultado) {
        this.flagResultado = flagResultado;
    }
    public String getFlagSupervision() {
        return flagSupervision;
    }
    public void setFlagSupervision(String flagSupervision) {
        this.flagSupervision = flagSupervision;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcionDocumento() {
        return descripcionDocumento;
    }

    public void setDescripcionDocumento(String descripcionDocumento) {
        this.descripcionDocumento = descripcionDocumento;
    }

    public PghProceso getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(PghProceso idProceso) {
        this.idProceso = idProceso;
    }

    public PghObligacionTipo getIdObligacionTipo() {
        return idObligacionTipo;
    }

    public void setIdObligacionTipo(PghObligacionTipo idObligacionTipo) {
        this.idObligacionTipo = idObligacionTipo;
    }

    public MdiActividad getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(MdiActividad idActividad) {
        this.idActividad = idActividad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlantillaResultado != null ? idPlantillaResultado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghPlantillaResultado)) {
            return false;
        }
        PghPlantillaResultado other = (PghPlantillaResultado) object;
        if ((this.idPlantillaResultado == null && other.idPlantillaResultado != null) || (this.idPlantillaResultado != null && !this.idPlantillaResultado.equals(other.idPlantillaResultado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
    	return "gob.osinergmin.inpsweb.domain.PghPlantillaResultado[ idPlantillaResultado=" + idPlantillaResultado + " ]";
    }
    
}
