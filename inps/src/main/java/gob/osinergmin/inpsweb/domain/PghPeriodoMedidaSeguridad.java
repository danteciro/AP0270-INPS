/**
* Resumen		
* Objeto			: PghPeriodoMedidaSeguridad.java
* Descripción		: Clase Domain PghPeriodoMedidaSeguridad
* Fecha de Creación	: 24/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
*
*/ 
package gob.osinergmin.inpsweb.domain;

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
 * @author jpiro
 */
@Entity
@Table(name = "PGH_PERIODO_MEDIDA_SEGURIDAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghPeriodoMedidaSeguridad.findAll", query = "SELECT p FROM PghPeriodoMedidaSeguridad p")
})
public class PghPeriodoMedidaSeguridad extends Auditoria {
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PERIODO_MEDIDA_SEGURIDAD")
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "PGH_PERIODO_MEDIDA_SEG_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idPeriodoMedidaSeguridad;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "FECHA_FIN_PLANEADO")
    @Temporal(TemporalType.DATE)
    private Date fechaFinPlaneado;
    @Column(name = "FECHA_SUBSANADO")
    @Temporal(TemporalType.DATE)
    private Date fechaSubsanado;
    @Column(name = "FECHA_CANCELACION")
    @Temporal(TemporalType.DATE)
    private Date fechaCancelacion;
    @Column(name = "FLAG_ACTUALIZAR_AUTO")
    private String flagActualizarAuto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_EXPEDIENTE", referencedColumnName = "ID_EXPEDIENTE")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghExpediente idExpediente;
    
    public PghPeriodoMedidaSeguridad() {
    }

    public PghPeriodoMedidaSeguridad(Long idPeriodoMedidaSeguridad) {
        this.idPeriodoMedidaSeguridad = idPeriodoMedidaSeguridad;
    }

    public Long getIdPeriodoMedidaSeguridad() {
        return idPeriodoMedidaSeguridad;
    }

    public void setIdPeriodoMedidaSeguridad(Long idPeriodoMedidaSeguridad) {
        this.idPeriodoMedidaSeguridad = idPeriodoMedidaSeguridad;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinPlaneado() {
        return fechaFinPlaneado;
    }

    public void setFechaFinPlaneado(Date fechaFinPlaneado) {
        this.fechaFinPlaneado = fechaFinPlaneado;
    }

    public Date getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(Date fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public String getFlagActualizarAuto() {
        return flagActualizarAuto;
    }

    public void setFlagActualizarAuto(String flagActualizarAuto) {
        this.flagActualizarAuto = flagActualizarAuto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }

    public String getTerminalCreacion() {
        return terminalCreacion;
    }

    public void setTerminalCreacion(String terminalCreacion) {
        this.terminalCreacion = terminalCreacion;
    }

    public String getTerminalActualizacion() {
        return terminalActualizacion;
    }

    public void setTerminalActualizacion(String terminalActualizacion) {
        this.terminalActualizacion = terminalActualizacion;
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
        hash += (idPeriodoMedidaSeguridad != null ? idPeriodoMedidaSeguridad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghPeriodoMedidaSeguridad)) {
            return false;
        }
        PghPeriodoMedidaSeguridad other = (PghPeriodoMedidaSeguridad) object;
        if ((this.idPeriodoMedidaSeguridad == null && other.idPeriodoMedidaSeguridad != null) || (this.idPeriodoMedidaSeguridad != null && !this.idPeriodoMedidaSeguridad.equals(other.idPeriodoMedidaSeguridad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mavenproject2.PghPeriodoMedidaSeguridad[ idPeriodoMedidaSeguridad=" + idPeriodoMedidaSeguridad + " ]";
    }

    public Date getFechaSubsanado() {
        return fechaSubsanado;
    }

    public void setFechaSubsanado(Date fechaSubsanado) {
        this.fechaSubsanado = fechaSubsanado;
    }    
}
