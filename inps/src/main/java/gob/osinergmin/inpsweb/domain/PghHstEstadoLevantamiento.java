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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "PGH_HST_ESTADO_LEVANTAMIENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghHstEstadoLevantamiento.findAll", query = "SELECT p FROM PghHstEstadoLevantamiento p")
})
public class PghHstEstadoLevantamiento extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ESTADO_LEVANTAMIENTO")
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "pgh_hst_estado_levant_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idEstadoLevantamiento;
    @Column(name = "ID_ESTADO")
    private Long idEstado;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.DATE)
    private Date fechaEstado;
    @Column(name = "FLAG_ULTIMO")
    private Character flagUltimo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_EXPEDIENTE", referencedColumnName = "ID_EXPEDIENTE")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghExpediente idExpediente;

    public PghHstEstadoLevantamiento() {
    }

    public PghHstEstadoLevantamiento(Long idEstadoLevantamiento) {
        this.idEstadoLevantamiento = idEstadoLevantamiento;
    }

    public Long getIdEstadoLevantamiento() {
        return idEstadoLevantamiento;
    }

    public void setIdEstadoLevantamiento(Long idEstadoLevantamiento) {
        this.idEstadoLevantamiento = idEstadoLevantamiento;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public Date getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(Date fechaEstado) {
        this.fechaEstado = fechaEstado;
    }

    public Character getFlagUltimo() {
        return flagUltimo;
    }

    public void setFlagUltimo(Character flagUltimo) {
        this.flagUltimo = flagUltimo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PghExpediente getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(PghExpediente idExpediente) {
        this.idExpediente = idExpediente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadoLevantamiento != null ? idEstadoLevantamiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghHstEstadoLevantamiento)) {
            return false;
        }
        PghHstEstadoLevantamiento other = (PghHstEstadoLevantamiento) object;
        if ((this.idEstadoLevantamiento == null && other.idEstadoLevantamiento != null) || (this.idEstadoLevantamiento != null && !this.idEstadoLevantamiento.equals(other.idEstadoLevantamiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghHstEstadoLevantamiento[ idEstadoLevantamiento=" + idEstadoLevantamiento + " ]";
    }
    
}
