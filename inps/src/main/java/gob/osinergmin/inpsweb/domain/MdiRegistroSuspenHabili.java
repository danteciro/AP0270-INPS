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
@Table(name = "MDI_REGISTRO_SUSPEN_HABILI")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdiRegistroSuspenHabili.findAll", query = "SELECT m FROM MdiRegistroSuspenHabili m")
})
public class MdiRegistroSuspenHabili extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_REGISTRO_SUSPEN_HABIL")    
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "mdi_registro_susp_hab_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idRegistroSuspenHabil;
    @Column(name = "FECHA_INICIO_SUSPENSION")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioSuspension;
    @Column(name = "FECHA_FIN_SUSPENSION")
    @Temporal(TemporalType.DATE)
    private Date fechaFinSuspension;
    @Size(max = 400)
    @Column(name = "OBSERVACION")
    private String observacion;
    @Size(max = 50)
    @Column(name = "PROCESO")
    private String proceso;
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_REGISTRO_HIDROCARBURO", referencedColumnName = "ID_REGISTRO_HIDROCARBURO")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiRegistroHidrocarburo idRegistroHidrocarburo;

    public MdiRegistroSuspenHabili() {
    }

    public MdiRegistroSuspenHabili(Long idRegistroSuspenHabil) {
        this.idRegistroSuspenHabil = idRegistroSuspenHabil;
    }

    public Long getIdRegistroSuspenHabil() {
        return idRegistroSuspenHabil;
    }

    public void setIdRegistroSuspenHabil(Long idRegistroSuspenHabil) {
        this.idRegistroSuspenHabil = idRegistroSuspenHabil;
    }

    public Date getFechaInicioSuspension() {
        return fechaInicioSuspension;
    }

    public void setFechaInicioSuspension(Date fechaInicioSuspension) {
        this.fechaInicioSuspension = fechaInicioSuspension;
    }

    public Date getFechaFinSuspension() {
        return fechaFinSuspension;
    }

    public void setFechaFinSuspension(Date fechaFinSuspension) {
        this.fechaFinSuspension = fechaFinSuspension;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public MdiRegistroHidrocarburo getIdRegistroHidrocarburo() {
        return idRegistroHidrocarburo;
    }

    public void setIdRegistroHidrocarburo(MdiRegistroHidrocarburo idRegistroHidrocarburo) {
        this.idRegistroHidrocarburo = idRegistroHidrocarburo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistroSuspenHabil != null ? idRegistroSuspenHabil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdiRegistroSuspenHabili)) {
            return false;
        }
        MdiRegistroSuspenHabili other = (MdiRegistroSuspenHabili) object;
        if ((this.idRegistroSuspenHabil == null && other.idRegistroSuspenHabil != null) || (this.idRegistroSuspenHabil != null && !this.idRegistroSuspenHabil.equals(other.idRegistroSuspenHabil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.MdiRegistroSuspenHabili[ idRegistroSuspenHabil=" + idRegistroSuspenHabil + " ]";
    }
    
}
