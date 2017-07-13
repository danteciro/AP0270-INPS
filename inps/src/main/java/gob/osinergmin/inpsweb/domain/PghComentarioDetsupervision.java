package gob.osinergmin.inpsweb.domain;

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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "PGH_COMENTARIO_DETSUPERVISION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghComentarioDetsupervision.findAll", query = "SELECT p FROM PghComentarioDetsupervision p")
})
public class PghComentarioDetsupervision extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_COMENTARIO_DETSUPERVISION")
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "PGH_COMENTARIO_DETSUP_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idComentarioDetsupervision;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_ESCENARIO_INCUMPLIDO", referencedColumnName = "ID_ESCENARIO_INCUMPLIDO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghEscenarioIncumplido idEscenarioIncumplido;
    @JoinColumn(name = "ID_DETALLE_SUPERVISION", referencedColumnName = "ID_DETALLE_SUPERVISION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghDetalleSupervision idDetalleSupervision;
    @JoinColumn(name = "ID_COMENTARIO_INCUMPLIMIENTO", referencedColumnName = "ID_COMENTARIO_INCUMPLIMIENTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghComentarioIncumplimiento idComentarioIncumplimiento;
    @OneToMany(mappedBy = "idComentarioDetsupervision", fetch = FetchType.LAZY)
    private List<PghComplementoDetsupervision> pghComplementoDetsupervisionList;

    public PghComentarioDetsupervision() {
    }

    public PghComentarioDetsupervision(Long idComentarioDetsupervision) {
        this.idComentarioDetsupervision = idComentarioDetsupervision;
    }

    public PghComentarioDetsupervision(Long idComentarioDetsupervision, Long idDetalleSupervision, Long idEscenarioIncumplido, Long idEsceIncumplimiento, Long idComentarioIncumplimiento,String descripcionComntIncumpl){
        this.idComentarioDetsupervision = idComentarioDetsupervision;
        this.idDetalleSupervision = new PghDetalleSupervision(idDetalleSupervision);
        this.idEscenarioIncumplido = new PghEscenarioIncumplido(idEscenarioIncumplido,idEsceIncumplimiento);
        this.idComentarioIncumplimiento = new PghComentarioIncumplimiento(idComentarioIncumplimiento,descripcionComntIncumpl);
    }
    
    public Long getIdComentarioDetsupervision() {
        return idComentarioDetsupervision;
    }

    public void setIdComentarioDetsupervision(Long idComentarioDetsupervision) {
        this.idComentarioDetsupervision = idComentarioDetsupervision;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PghEscenarioIncumplido getIdEscenarioIncumplido() {
        return idEscenarioIncumplido;
    }

    public void setIdEscenarioIncumplido(PghEscenarioIncumplido idEscenarioIncumplido) {
        this.idEscenarioIncumplido = idEscenarioIncumplido;
    }

    public PghDetalleSupervision getIdDetalleSupervision() {
        return idDetalleSupervision;
    }

    public void setIdDetalleSupervision(PghDetalleSupervision idDetalleSupervision) {
        this.idDetalleSupervision = idDetalleSupervision;
    }

    public PghComentarioIncumplimiento getIdComentarioIncumplimiento() {
        return idComentarioIncumplimiento;
    }

    public void setIdComentarioIncumplimiento(PghComentarioIncumplimiento idComentarioIncumplimiento) {
        this.idComentarioIncumplimiento = idComentarioIncumplimiento;
    }

    @XmlTransient
    public List<PghComplementoDetsupervision> getPghComplementoDetsupervisionList() {
        return pghComplementoDetsupervisionList;
    }

    public void setPghComplementoDetsupervisionList(List<PghComplementoDetsupervision> pghComplementoDetsupervisionList) {
        this.pghComplementoDetsupervisionList = pghComplementoDetsupervisionList;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComentarioDetsupervision != null ? idComentarioDetsupervision.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghComentarioDetsupervision)) {
            return false;
        }
        PghComentarioDetsupervision other = (PghComentarioDetsupervision) object;
        if ((this.idComentarioDetsupervision == null && other.idComentarioDetsupervision != null) || (this.idComentarioDetsupervision != null && !this.idComentarioDetsupervision.equals(other.idComentarioDetsupervision))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mavenproject2.PghComentarioDetsupervision[ idComentarioDetsupervision=" + idComentarioDetsupervision + " ]";
    }
    
}
