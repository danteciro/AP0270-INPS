package gob.osinergmin.inpsweb.domain;
import java.util.Date;
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
 * @author mdiosesf
 */
@Entity
@Table(name = "PGH_EMPR_SUPE_ACTI_OBLI")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghEmprSupeActiObli.findAll", query = "SELECT p FROM PghEmprSupeActiObli p"),
    @NamedQuery(name = "PghEmprSupeActiObli.findByIdEmprSupeActiObli", query = "SELECT p FROM PghEmprSupeActiObli p WHERE p.idEmprSupeActiObli = :idEmprSupeActiObli"),
    @NamedQuery(name = "PghEmprSupeActiObli.findByEstado", query = "SELECT p FROM PghEmprSupeActiObli p WHERE p.estado = :estado"),
    @NamedQuery(name = "PghEmprSupeActiObli.findByUsuarioCreacion", query = "SELECT p FROM PghEmprSupeActiObli p WHERE p.usuarioCreacion = :usuarioCreacion"),
    @NamedQuery(name = "PghEmprSupeActiObli.findByFechaCreacion", query = "SELECT p FROM PghEmprSupeActiObli p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "PghEmprSupeActiObli.findByTerminalCreacion", query = "SELECT p FROM PghEmprSupeActiObli p WHERE p.terminalCreacion = :terminalCreacion"),
    @NamedQuery(name = "PghEmprSupeActiObli.findByUsuarioActualizacion", query = "SELECT p FROM PghEmprSupeActiObli p WHERE p.usuarioActualizacion = :usuarioActualizacion"),
    @NamedQuery(name = "PghEmprSupeActiObli.findByFechaActualizacion", query = "SELECT p FROM PghEmprSupeActiObli p WHERE p.fechaActualizacion = :fechaActualizacion"),
    @NamedQuery(name = "PghEmprSupeActiObli.findByTerminalActualizacion", query = "SELECT p FROM PghEmprSupeActiObli p WHERE p.terminalActualizacion = :terminalActualizacion")})
public class PghEmprSupeActiObli extends Auditoria {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_EMPR_SUPE_ACTI_OBLI")
    private Long idEmprSupeActiObli;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_PROCESO", referencedColumnName = "ID_PROCESO")
    @ManyToOne
    private PghProceso idProceso;
    @JoinColumn(name = "ID_OBLIGACION_TIPO", referencedColumnName = "ID_OBLIGACION_TIPO")
    @ManyToOne
    private PghObligacionTipo idObligacionTipo;
    @JoinColumn(name = "ID_SUPERVISORA_EMPRESA", referencedColumnName = "ID_SUPERVISORA_EMPRESA")
    @ManyToOne
    private MdiSupervisoraEmpresa idSupervisoraEmpresa;
    @JoinColumn(name = "ID_LOCADOR", referencedColumnName = "ID_LOCADOR")
    @ManyToOne
    private MdiLocador idLocador;
    @JoinColumn(name = "ID_ACTIVIDAD", referencedColumnName = "ID_ACTIVIDAD")
    @ManyToOne
    private MdiActividad idActividad;

    public PghEmprSupeActiObli() {
    }

    public PghEmprSupeActiObli(Long idEmprSupeActiObli) {
        this.idEmprSupeActiObli = idEmprSupeActiObli;
    }

    public PghEmprSupeActiObli(Long idEmprSupeActiObli, String estado, String usuarioCreacion, Date fechaCreacion, String terminalCreacion) {
        this.idEmprSupeActiObli = idEmprSupeActiObli;
        this.estado = estado;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
    }

    public Long getIdEmprSupeActiObli() {
        return idEmprSupeActiObli;
    }

    public void setIdEmprSupeActiObli(Long idEmprSupeActiObli) {
        this.idEmprSupeActiObli = idEmprSupeActiObli;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public MdiSupervisoraEmpresa getIdSupervisoraEmpresa() {
        return idSupervisoraEmpresa;
    }

    public void setIdSupervisoraEmpresa(MdiSupervisoraEmpresa idSupervisoraEmpresa) {
        this.idSupervisoraEmpresa = idSupervisoraEmpresa;
    }

    public MdiLocador getIdLocador() {
        return idLocador;
    }

    public void setIdLocador(MdiLocador idLocador) {
        this.idLocador = idLocador;
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
        hash += (idEmprSupeActiObli != null ? idEmprSupeActiObli.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghEmprSupeActiObli)) {
            return false;
        }
        PghEmprSupeActiObli other = (PghEmprSupeActiObli) object;
        if ((this.idEmprSupeActiObli == null && other.idEmprSupeActiObli != null) || (this.idEmprSupeActiObli != null && !this.idEmprSupeActiObli.equals(other.idEmprSupeActiObli))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HIBERNATE.PghEmprSupeActiObli[ idEmprSupeActiObli=" + idEmprSupeActiObli + " ]";
    } 
}
