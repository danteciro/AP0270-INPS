/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;


import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vubaldo
 */
@Entity
@Table(name = "NPS_INFORME_SUPE_RECH_CARGA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NpsInformeSupeRechCarga.findAll", query = "SELECT n FROM NpsInformeSupeRechCarga n"),
    @NamedQuery(name = "NpsInformeSupeRechCarga.findByIdInformeSupeRechCarga", query = "SELECT n FROM NpsInformeSupeRechCarga n WHERE n.idInformeSupeRechCarga = :idInformeSupeRechCarga"),
    @NamedQuery(name = "NpsInformeSupeRechCarga.findByNombreInformeDoc", query = "SELECT n FROM NpsInformeSupeRechCarga n WHERE n.nombreInformeDoc = :nombreInformeDoc"),
    @NamedQuery(name = "NpsInformeSupeRechCarga.findByFlgObservado", query = "SELECT n FROM NpsInformeSupeRechCarga n WHERE n.flgObservado = :flgObservado"),
    @NamedQuery(name = "NpsInformeSupeRechCarga.findByObservacion", query = "SELECT n FROM NpsInformeSupeRechCarga n WHERE n.observacion = :observacion"),
    @NamedQuery(name = "NpsInformeSupeRechCarga.findByUsuarioCreacion", query = "SELECT n FROM NpsInformeSupeRechCarga n WHERE n.usuarioCreacion = :usuarioCreacion"),
    @NamedQuery(name = "NpsInformeSupeRechCarga.findByFechaCreacion", query = "SELECT n FROM NpsInformeSupeRechCarga n WHERE n.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "NpsInformeSupeRechCarga.findByTerminalCreacion", query = "SELECT n FROM NpsInformeSupeRechCarga n WHERE n.terminalCreacion = :terminalCreacion"),
    @NamedQuery(name = "NpsInformeSupeRechCarga.findByUsuarioActualizacion", query = "SELECT n FROM NpsInformeSupeRechCarga n WHERE n.usuarioActualizacion = :usuarioActualizacion"),
    @NamedQuery(name = "NpsInformeSupeRechCarga.findByFechaActualizacion", query = "SELECT n FROM NpsInformeSupeRechCarga n WHERE n.fechaActualizacion = :fechaActualizacion"),
    @NamedQuery(name = "NpsInformeSupeRechCarga.findByTerminalActualizacion", query = "SELECT n FROM NpsInformeSupeRechCarga n WHERE n.terminalActualizacion = :terminalActualizacion"),
    @NamedQuery(name = "NpsInformeSupeRechCarga.findByEstado", query = "SELECT n FROM NpsInformeSupeRechCarga n WHERE n.estado = :estado")})
public class NpsInformeSupeRechCarga extends Auditoria {
	private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_INFORME_SUPE_RECH_CARGA")
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "NPS_INFORME_SUP_RECH_CARGA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idInformeSupeRechCarga;
    @Column(name = "NOMBRE_INFORME_DOC")
    private String nombreInformeDoc;
    @Column(name = "FLG_OBSERVADO")
    private String flgObservado;
    @Column(name = "OBSERVACION")
    private String observacion;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "ID_INFORME_DOC")
    private Long idInformeDoc;
    @JoinColumn(name = "ID_SUPERVISION_RECH_CARGA", referencedColumnName = "ID_SUPERVISION_RECH_CARGA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private NpsSupervisionRechCarga idSupervisionRechCarga;

    public NpsInformeSupeRechCarga() {
    }

    public NpsInformeSupeRechCarga(Long idInformeSupeRechCarga) {
        this.idInformeSupeRechCarga = idInformeSupeRechCarga;
    }

    public NpsInformeSupeRechCarga(Long idInformeSupeRechCarga, String usuarioCreacion, Date fechaCreacion, String terminalCreacion, String estado) {
        this.idInformeSupeRechCarga = idInformeSupeRechCarga;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
        this.estado = estado;
    }

    public Long getIdInformeSupeRechCarga() {
        return idInformeSupeRechCarga;
    }

    public void setIdInformeSupeRechCarga(Long idInformeSupeRechCarga) {
        this.idInformeSupeRechCarga = idInformeSupeRechCarga;
    }

    public String getNombreInformeDoc() {
        return nombreInformeDoc;
    }

    public void setNombreInformeDoc(String nombreInformeDoc) {
        this.nombreInformeDoc = nombreInformeDoc;
    }

  public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTerminalCreacion() {
        return terminalCreacion;
    }

    public void setTerminalCreacion(String terminalCreacion) {
        this.terminalCreacion = terminalCreacion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getTerminalActualizacion() {
        return terminalActualizacion;
    }

    public void setTerminalActualizacion(String terminalActualizacion) {
        this.terminalActualizacion = terminalActualizacion;
    }

    public NpsSupervisionRechCarga getIdSupervisionRechCarga() {
        return idSupervisionRechCarga;
    }

    public void setIdSupervisionRechCarga(NpsSupervisionRechCarga idSupervisionRechCarga) {
        this.idSupervisionRechCarga = idSupervisionRechCarga;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInformeSupeRechCarga != null ? idInformeSupeRechCarga.hashCode() : 0);
        return hash;
    }

    
    
    public String getFlgObservado() {
		return flgObservado;
	}

	public void setFlgObservado(String flgObservado) {
		this.flgObservado = flgObservado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

    public Long getIdInformeDoc() {
		return idInformeDoc;
	}

	public void setIdInformeDoc(Long idInformeDoc) {
		this.idInformeDoc = idInformeDoc;
	}

	@Override
    public boolean equals(Object object) {
        if (!(object instanceof NpsInformeSupeRechCarga)) {
            return false;
        }
        NpsInformeSupeRechCarga other = (NpsInformeSupeRechCarga) object;
        if ((this.idInformeSupeRechCarga == null && other.idInformeSupeRechCarga != null) || (this.idInformeSupeRechCarga != null && !this.idInformeSupeRechCarga.equals(other.idInformeSupeRechCarga))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.siged.com.NpsInformeSupeRechCarga[ idInformeSupeRechCarga=" + idInformeSupeRechCarga + " ]";
    }
    
}
