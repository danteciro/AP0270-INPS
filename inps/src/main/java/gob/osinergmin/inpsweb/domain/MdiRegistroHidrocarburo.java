/**
* Resumen.
* Objeto            : MdiRegistroHidrocarburo.java.
* Descripción       : Domain con los atributos del Registro de Hidrocarburos.
* Fecha de Creación : 12/05/2016             
* Autor             : Luis García.
* --------------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha         Nombre          Descripción
* --------------------------------------------------------------------------------------------------------------
*                                                           
*/

package gob.osinergmin.inpsweb.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lgarciar
 */
@Entity
@Table(name = "MDI_REGISTRO_HIDROCARBURO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdiRegistroHidrocarburo.findAll", query = "SELECT m FROM MdiRegistroHidrocarburo m")
})
public class MdiRegistroHidrocarburo extends Auditoria {
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @Column(name = "ID_REGISTRO_HIDROCARBURO")
    private Long idRegistroHidrocarburo;
    @Basic(optional = false)
    @Column(name = "NUMERO_REGISTRO_HIDROCARBURO")
    private String numeroRegistroHidrocarburo;
    @Column(name = "FECHA_EMISION")
    @Temporal(TemporalType.DATE)
    private Date fechaEmision;
    @Column(name = "FECHA_APROBACION")
    @Temporal(TemporalType.DATE)
    private Date fechaAprobacion;
    @Column(name = "FECHA_INICIO_SUSPENCION")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioSuspencion;
    @Column(name = "FECHA_FIN_SUSPENCION")
    @Temporal(TemporalType.DATE)
    private Date fechaFinSuspencion;
    @Column(name = "ESTADO_PROCESO")
    private Long estadoProceso;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "NRO_EXPEDIENTE_SIGED")
    private String nroExpedienteSiged;
    @Column(name = "ID_ESTADO_PROCESO")
    private Long idEstadoProceso;
    @JoinColumn(name = "ID_UNIDAD_SUPERVISADA", referencedColumnName = "ID_UNIDAD_SUPERVISADA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MdiUnidadSupervisada idUnidadSupervisada;
    @OneToMany(mappedBy = "idRegistroHidrocarburo", fetch = FetchType.LAZY)
    private List<MdiRegistroSuspenHabili> mdiRegistroSuspenHabiliList;

    public MdiRegistroHidrocarburo() {
    }

    public MdiRegistroHidrocarburo(Long idRegistroHidrocarburo) {
        this.idRegistroHidrocarburo = idRegistroHidrocarburo;
    }

    public MdiRegistroHidrocarburo(Long idRegistroHidrocarburo, String numeroRegistroHidrocarburo, String usuarioCreacion, Date fechaCreacion, String terminalCreacion, String estado) {
        this.idRegistroHidrocarburo = idRegistroHidrocarburo;
        this.numeroRegistroHidrocarburo = numeroRegistroHidrocarburo;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
        this.estado = estado;
    }

    public Long getIdRegistroHidrocarburo() {
        return idRegistroHidrocarburo;
    }

    public void setIdRegistroHidrocarburo(Long idRegistroHidrocarburo) {
        this.idRegistroHidrocarburo = idRegistroHidrocarburo;
    }

    public String getNumeroRegistroHidrocarburo() {
        return numeroRegistroHidrocarburo;
    }

    public void setNumeroRegistroHidrocarburo(String numeroRegistroHidrocarburo) {
        this.numeroRegistroHidrocarburo = numeroRegistroHidrocarburo;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public Date getFechaInicioSuspencion() {
        return fechaInicioSuspencion;
    }

    public void setFechaInicioSuspencion(Date fechaInicioSuspencion) {
        this.fechaInicioSuspencion = fechaInicioSuspencion;
    }

    public Date getFechaFinSuspencion() {
        return fechaFinSuspencion;
    }

    public void setFechaFinSuspencion(Date fechaFinSuspencion) {
        this.fechaFinSuspencion = fechaFinSuspencion;
    }

    public Long getEstadoProceso() {
        return estadoProceso;
    }

    public void setEstadoProceso(Long estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNroExpedienteSiged() {
        return nroExpedienteSiged;
    }

    public void setNroExpedienteSiged(String nroExpedienteSiged) {
        this.nroExpedienteSiged = nroExpedienteSiged;
    }

    public Long getIdEstadoProceso() {
        return idEstadoProceso;
    }

    public void setIdEstadoProceso(Long idEstadoProceso) {
        this.idEstadoProceso = idEstadoProceso;
    }

    public MdiUnidadSupervisada getIdUnidadSupervisada() {
        return idUnidadSupervisada;
    }

    public void setIdUnidadSupervisada(MdiUnidadSupervisada idUnidadSupervisada) {
        this.idUnidadSupervisada = idUnidadSupervisada;
    }

    @XmlTransient
    public List<MdiRegistroSuspenHabili> getMdiRegistroSuspenHabiliList() {
        return mdiRegistroSuspenHabiliList;
    }

    public void setMdiRegistroSuspenHabiliList(List<MdiRegistroSuspenHabili> mdiRegistroSuspenHabiliList) {
        this.mdiRegistroSuspenHabiliList = mdiRegistroSuspenHabiliList;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistroHidrocarburo != null ? idRegistroHidrocarburo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdiRegistroHidrocarburo)) {
            return false;
        }
        MdiRegistroHidrocarburo other = (MdiRegistroHidrocarburo) object;
        if ((this.idRegistroHidrocarburo == null && other.idRegistroHidrocarburo != null) || (this.idRegistroHidrocarburo != null && !this.idRegistroHidrocarburo.equals(other.idRegistroHidrocarburo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.MdiRegistroHidrocarburo[ idRegistroHidrocarburo=" + idRegistroHidrocarburo + " ]";
    }
    
}
