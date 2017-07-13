/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mdiosesf
 */

@Entity
@Table(name = "PGH_DETALLE_EVALUACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghDetalleEvaluacion.findAll", query = "SELECT p FROM PghDetalleEvaluacion p"),
    @NamedQuery(name = "PghDetalleEvaluacion.findDetalleEvaluacion", query = "SELECT p FROM PghDetalleEvaluacion p WHERE p.idDetalleSupervision.idDetalleSupervision = :idDetalleSupervision AND p.idTipificacion.idTipificacion = :idTipificacion AND p.idCriterio.idCriterio = :idCriterio "),
    @NamedQuery(name = "PghDetalleEvaluacion.findByEstado", query = "SELECT p FROM PghDetalleEvaluacion p WHERE p.estado = :estado"),
    @NamedQuery(name = "PghDetalleEvaluacion.findByUsuarioCreacion", query = "SELECT p FROM PghDetalleEvaluacion p WHERE p.usuarioCreacion = :usuarioCreacion"),
    @NamedQuery(name = "PghDetalleEvaluacion.findByFechaCreacion", query = "SELECT p FROM PghDetalleEvaluacion p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "PghDetalleEvaluacion.findByUsuarioActualizacion", query = "SELECT p FROM PghDetalleEvaluacion p WHERE p.usuarioActualizacion = :usuarioActualizacion"),
    @NamedQuery(name = "PghDetalleEvaluacion.findByFechaActualizacion", query = "SELECT p FROM PghDetalleEvaluacion p WHERE p.fechaActualizacion = :fechaActualizacion"),
    @NamedQuery(name = "PghDetalleEvaluacion.findByTerminalActualizacion", query = "SELECT p FROM PghDetalleEvaluacion p WHERE p.terminalActualizacion = :terminalActualizacion"),
    @NamedQuery(name = "PghDetalleEvaluacion.findByIdDetalleEvaluacion", query = "SELECT p FROM PghDetalleEvaluacion p WHERE p.idDetalleEvaluacion = :idDetalleEvaluacion"),    
    @NamedQuery(name = "PghDetalleEvaluacion.findByIdTipificacion", query = "SELECT p FROM PghDetalleEvaluacion p WHERE p.idTipificacion = :idTipificacion"),
    @NamedQuery(name = "PghDetalleEvaluacion.findByIdCriterio", query = "SELECT p FROM PghDetalleEvaluacion p WHERE p.idCriterio = :idCriterio"),   
    @NamedQuery(name = "PghDetalleEvaluacion.findByFlagRegistrado", query = "SELECT p FROM PghDetalleEvaluacion p WHERE p.flagRegistrado = :flagRegistrado")})
	
public class PghDetalleEvaluacion extends Auditoria {
    private static final long serialVersionUID = 1L;
    @Column(name = "DESCRIPCION_RESULTADO")
    private String descripcionResultado;
    @Column(name = "ESTADO")
    private String estado;
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "PGH_DETALLE_EVALUACION_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    @Basic(optional = false)
    @Column(name = "ID_DETALLE_EVALUACION")
    private Long idDetalleEvaluacion;
    @Column(name = "FLAG_REGISTRADO")
    private String flagRegistrado;
    @Column(name = "FLAG_RESULTADO")
    private String flagResultado;   
	@JoinColumn(name = "ID_TIPIFICACION", referencedColumnName = "ID_TIPIFICACION")
    @ManyToOne(optional = false)
    private PghTipificacion idTipificacion;
    @JoinColumn(name = "ID_DETALLE_SUPERVISION", referencedColumnName = "ID_DETALLE_SUPERVISION")
    @ManyToOne(optional = false)
    private PghDetalleSupervision idDetalleSupervision;
    @JoinColumn(name = "ID_DETALLE_CRITERIO", referencedColumnName = "ID_DETALLE_CRITERIO")
    @ManyToOne(optional = false)
    private PghDetalleCriterio idDetalleCriterio;
    @JoinColumn(name = "ID_CRITERIO", referencedColumnName = "ID_CRITERIO")
    @ManyToOne(optional = false)
    private PghCriterio idCriterio;

    public PghDetalleEvaluacion() {
    }
    
    public PghDetalleEvaluacion(PghCriterio idCriterio, PghTipificacion idTipificacion, PghDetalleCriterio idDetalleCriterio,
    		PghDetalleSupervision idDetalleSupervision, String descripcionResultado, String estado,
			Long idDetalleEvaluacion, String flagRegistrado, String flagResultado) {
		this.descripcionResultado = descripcionResultado;
		this.estado = estado;
		this.idDetalleEvaluacion = idDetalleEvaluacion;
		this.flagRegistrado = flagRegistrado;
		this.idTipificacion = idTipificacion;
		this.idDetalleSupervision = idDetalleSupervision;
		this.idDetalleCriterio = idDetalleCriterio;
		this.idCriterio = idCriterio;
		this.flagResultado = flagResultado;
	}

    public String getFlagResultado() {
		return flagResultado;
	}

	public void setFlagResultado(String flagResultado) {
		this.flagResultado = flagResultado;
	}
    
	public PghDetalleEvaluacion(Long idDetalleEvaluacion) {
        this.idDetalleEvaluacion = idDetalleEvaluacion;
    }

    public String getDescripcionResultado() {
        return descripcionResultado;
    }

    public void setDescripcionResultado(String descripcionResultado) {
        this.descripcionResultado = descripcionResultado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdDetalleEvaluacion() {
        return idDetalleEvaluacion;
    }

    public void setIdDetalleEvaluacion(Long idDetalleEvaluacion) {
        this.idDetalleEvaluacion = idDetalleEvaluacion;
    }

    public String getFlagRegistrado() {
        return flagRegistrado;
    }

    public void setFlagRegistrado(String flagRegistrado) {
        this.flagRegistrado = flagRegistrado;
    }

    public PghTipificacion getIdTipificacion() {
        return idTipificacion;
    }

    public void setIdTipificacion(PghTipificacion idTipificacion) {
        this.idTipificacion = idTipificacion;
    }

    public PghDetalleSupervision getIdDetalleSupervision() {
        return idDetalleSupervision;
    }

    public void setIdDetalleSupervision(PghDetalleSupervision idDetalleSupervision) {
        this.idDetalleSupervision = idDetalleSupervision;
    }

    public PghDetalleCriterio getIdDetalleCriterio() {
        return idDetalleCriterio;
    }

    public void setIdDetalleCriterio(PghDetalleCriterio idDetalleCriterio) {
        this.idDetalleCriterio = idDetalleCriterio;
    }

    public PghCriterio getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(PghCriterio idCriterio) {
        this.idCriterio = idCriterio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleEvaluacion != null ? idDetalleEvaluacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghDetalleEvaluacion)) {
            return false;
        }
        PghDetalleEvaluacion other = (PghDetalleEvaluacion) object;
        if ((this.idDetalleEvaluacion == null && other.idDetalleEvaluacion != null) || (this.idDetalleEvaluacion != null && !this.idDetalleEvaluacion.equals(other.idDetalleEvaluacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
    	return "gob.osinergmin.inps.domain.PghDetalleEvaluacion[ idDetalleEvaluacion=" + idDetalleEvaluacion + " ]";
    }
}
