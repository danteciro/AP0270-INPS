/**
* Resumen		
* Objeto		: PghHistoricoEstado.java
* Descripción		: Clase del modelo de dominio PghHistoricoEstado
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     23/05/2016      Luis García Reyna           Implementar la funcionalidad de devolución de asignaciones de acuerdo a especificaciones.
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "PGH_HISTORICO_ESTADO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghHistoricoEstado.findAll", query = "SELECT p FROM PghHistoricoEstado p where p.estado='1'"),
    @NamedQuery(name = "PghHistoricoEstado.findByIdOrdenServicio", query = "SELECT p FROM PghHistoricoEstado p where p.estado='1' and p.idOrdenServicio.idOrdenServicio=:idOrdenServicio order by p.idHistoricoEstado ")
})
public class PghHistoricoEstado extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_HISTORICO_ESTADO")
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "PGH_HISTORICO_ESTADO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idHistoricoEstado;
//    @Column(name = "ID_TIPO_HISTORICO")
//    private Long idTipoHistorico;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_HISTORICO")
    private MdiMaestroColumna idTipoHistorico;
    @Size(max = 400)
    @Column(name = "MOTIVO_REASIGNACION")
    private String motivoReasignacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_PERSONAL_ORI", referencedColumnName = "ID_PERSONAL")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghPersonal idPersonalOri;
    @JoinColumn(name = "ID_PERSONAL_DEST", referencedColumnName = "ID_PERSONAL")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghPersonal idPersonalDest;
    @JoinColumn(name = "ID_ORDEN_SERVICIO", referencedColumnName = "ID_ORDEN_SERVICIO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghOrdenServicio idOrdenServicio;
    @JoinColumn(name = "ID_EXPEDIENTE", referencedColumnName = "ID_EXPEDIENTE")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghExpediente idExpediente;
    @JoinColumn(name = "ID_ESTADO_PROCESO", referencedColumnName = "ID_ESTADO_PROCESO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghEstadoProceso idEstadoProceso;    
    /* OSINE_SFS-480 - RSIS 40 - Inicio */
    @JoinColumn(name = "ID_PETICION")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiMaestroColumna idPeticion;
    @JoinColumn(name = "ID_MOTIVO")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiMaestroColumna idMotivo;
    /* OSINE_SFS-480 - RSIS 40 - Fin */
        
    public PghHistoricoEstado() {
    }

    public PghHistoricoEstado(Long idHistoricoEstado) {
        this.idHistoricoEstado = idHistoricoEstado;
    }

    public PghHistoricoEstado(Long idHistoricoEstado, String estado, String usuarioCreacion, Date fechaCreacion, String terminalCreacion) {
        this.idHistoricoEstado = idHistoricoEstado;
        this.estado = estado;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
    }

    public Long getIdHistoricoEstado() {
        return idHistoricoEstado;
    }

    public void setIdHistoricoEstado(Long idHistoricoEstado) {
        this.idHistoricoEstado = idHistoricoEstado;
    }

    public MdiMaestroColumna getIdTipoHistorico() {
        return idTipoHistorico;
    }

    public void setIdTipoHistorico(MdiMaestroColumna idTipoHistorico) {
        this.idTipoHistorico = idTipoHistorico;
    }

    public String getMotivoReasignacion() {
        return motivoReasignacion;
    }

    public void setMotivoReasignacion(String motivoReasignacion) {
        this.motivoReasignacion = motivoReasignacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PghPersonal getIdPersonalOri() {
        return idPersonalOri;
    }

    public void setIdPersonalOri(PghPersonal idPersonalOri) {
        this.idPersonalOri = idPersonalOri;
    }

    public PghPersonal getIdPersonalDest() {
        return idPersonalDest;
    }

    public void setIdPersonalDest(PghPersonal idPersonalDest) {
        this.idPersonalDest = idPersonalDest;
    }

    public PghOrdenServicio getIdOrdenServicio() {
        return idOrdenServicio;
    }

    public void setIdOrdenServicio(PghOrdenServicio idOrdenServicio) {
        this.idOrdenServicio = idOrdenServicio;
    }

    public PghExpediente getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(PghExpediente idExpediente) {
        this.idExpediente = idExpediente;
    }

    public PghEstadoProceso getIdEstadoProceso() {
        return idEstadoProceso;
    }

    public void setIdEstadoProceso(PghEstadoProceso idEstadoProceso) {
        this.idEstadoProceso = idEstadoProceso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHistoricoEstado != null ? idHistoricoEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghHistoricoEstado)) {
            return false;
        }
        PghHistoricoEstado other = (PghHistoricoEstado) object;
        if ((this.idHistoricoEstado == null && other.idHistoricoEstado != null) || (this.idHistoricoEstado != null && !this.idHistoricoEstado.equals(other.idHistoricoEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inps.domain.PghHistoricoEstado[ idHistoricoEstado=" + idHistoricoEstado + " ]";
    }
    
    /* OSINE_SFS-480 - RSIS 40 - Inicio */
    public MdiMaestroColumna getIdPeticion() {
        return idPeticion;
    }
    
    public void setIdPeticion(MdiMaestroColumna idPeticion) {
        this.idPeticion = idPeticion;
    }

    public MdiMaestroColumna getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(MdiMaestroColumna idMotivo) {
        this.idMotivo = idMotivo;
    }
    /* OSINE_SFS-480 - RSIS 40 - Fin */
}
