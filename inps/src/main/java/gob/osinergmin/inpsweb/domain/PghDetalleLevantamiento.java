/**
* Resumen		
* Objeto			: PghDetalleLevantamiento.java
* Descripción		: Clase del modelo de dominio PghDetalleLevantamiento
* Fecha de Creación	: 07/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     08/10/2016      Mario Dioses Fernandez      Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
* OSINE_SFS-791     24/10/2016      Paul Moscoso                Crear Nuevo Campo Detalle Levantamiento Atiende
*/ 
package gob.osinergmin.inpsweb.domain;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "PGH_DETALLE_LEVANTAMIENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghDetalleLevantamiento.findAll", query = "SELECT p FROM PghDetalleLevantamiento p"),
    @NamedQuery(name = "PghDetalleLevantamiento.findByIdDetalleLevantamiento", query = "SELECT p FROM PghDetalleLevantamiento p WHERE p.idDetalleLevantamiento = :idDetalleLevantamiento and p.estado = '1'"),
    @NamedQuery(name = "PghDetalleLevantamiento.findByIdDetalleSupervision", query = "SELECT p FROM PghDetalleLevantamiento p WHERE p.idDetalleSupervision.idDetalleSupervision = :idDetalleSupervision and p.estado = '1'"),
    @NamedQuery(name = "PghDetalleLevantamiento.findByFlagUltimoRegistro", query = "SELECT p FROM PghDetalleLevantamiento p WHERE p.idDetalleSupervision.idDetalleSupervision = :idDetalleSupervision and p.flagUltimoRegistro = :flagUltimoRegistro and p.estado = '1'"),
    @NamedQuery(name = "PghDetalleLevantamiento.findByIdDetaLevaAtiende", query = "SELECT p FROM PghDetalleLevantamiento p WHERE p.idDetaLevaAtiende.idDetalleSupervision = :idDetaLevaAtiende and p.estado = '1'")})
public class PghDetalleLevantamiento extends Auditoria {
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "PGH_DETALLE_LEVANTAMIENTO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    @Basic(optional = false)
    @Column(name = "ID_DETALLE_LEVANTAMIENTO")
    private Long idDetalleLevantamiento;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "FLAG_ULTIMO_REGISTRO")
    private String flagUltimoRegistro;
    @Column(name = "FECHA_INGRESO_LEVANTAMIENTO")
    @Temporal(TemporalType.DATE)
    private Date fechaIngresoLevantamiento;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_DETALLE_SUPERVISION", referencedColumnName = "ID_DETALLE_SUPERVISION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghDetalleSupervision idDetalleSupervision;
    @OneToMany(mappedBy = "idDetalleLevantamiento", fetch = FetchType.LAZY)
    private List<PghDocumentoAdjunto> pghDocumentoAdjuntoList;
    @JoinColumn(name = "ID_DETA_LEVA_ATIENDE", referencedColumnName = "ID_DETALLE_SUPERVISION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghDetalleSupervision idDetaLevaAtiende;

    public PghDetalleLevantamiento() {
    }

    public PghDetalleLevantamiento(Long idDetalleLevantamiento) {
        this.idDetalleLevantamiento = idDetalleLevantamiento;
    }

    public Long getIdDetalleLevantamiento() {
        return idDetalleLevantamiento;
    }

    public void setIdDetalleLevantamiento(Long idDetalleLevantamiento) {
        this.idDetalleLevantamiento = idDetalleLevantamiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFlagUltimoRegistro() {
        return flagUltimoRegistro;
    }

    public void setFlagUltimoRegistro(String flagUltimoRegistro) {
        this.flagUltimoRegistro = flagUltimoRegistro;
    }

    public Date getFechaIngresoLevantamiento() {
        return fechaIngresoLevantamiento;
    }

    public void setFechaIngresoLevantamiento(Date fechaIngresoLevantamiento) {
        this.fechaIngresoLevantamiento = fechaIngresoLevantamiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PghDetalleSupervision getIdDetalleSupervision() {
        return idDetalleSupervision;
    }

    public void setIdDetalleSupervision(PghDetalleSupervision idDetalleSupervision) {
        this.idDetalleSupervision = idDetalleSupervision;
    }   
    
    public List<PghDocumentoAdjunto> getPghDocumentoAdjuntoList() {
		return pghDocumentoAdjuntoList;
	}

	public void setPghDocumentoAdjuntoList(
			List<PghDocumentoAdjunto> pghDocumentoAdjuntoList) {
		this.pghDocumentoAdjuntoList = pghDocumentoAdjuntoList;
	}
        
    public PghDetalleSupervision getIdDetaLevaAtiende() {
        return idDetaLevaAtiende;
    }

    public void setIdDetaLevaAtiende(PghDetalleSupervision idDetaLevaAtiende) {
        this.idDetaLevaAtiende = idDetaLevaAtiende;
    }

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleLevantamiento != null ? idDetalleLevantamiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghDetalleLevantamiento)) {
            return false;
        }
        PghDetalleLevantamiento other = (PghDetalleLevantamiento) object;
        if ((this.idDetalleLevantamiento == null && other.idDetalleLevantamiento != null) || (this.idDetalleLevantamiento != null && !this.idDetalleLevantamiento.equals(other.idDetalleLevantamiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghDetalleLevantamiento [ idDetalleLevantamiento=" + idDetalleLevantamiento + " ]";
    }    
}
