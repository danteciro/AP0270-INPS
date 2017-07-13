/**
*
* Resumen		
* Objeto		: PghEscenarioIncumplido.java
* Descripción		: Clase del modelo de dominio PghEscenarioIncumplido
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  05/09/2016   |   Luis García Reyna          |     Buscar Escenarios Incumplidos de la Supervisión
* OSINE791-RSIS25|  08/09/2016   |	Alexander Vilca Narvaez     | Crear la funcionalidad para consultar el comentario de ejecución de medida ingresado para las infracciones identificadas DSR-CRITICIDAD
*                |               |                              |
* 
*/


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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "PGH_ESCENARIO_INCUMPLIDO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghEscenarioIncumplido.findAll", query = "SELECT p FROM PghEscenarioIncumplido p")
})
public class PghEscenarioIncumplido extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ESCENARIO_INCUMPLIDO")
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "PGH_ESCENARIO_INCUMPLIDO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idEscenarioIncumplido;
    @Size(max = 4000)
    @Column(name = "COMENTARIO_EJECUCION")
    private String comentarioEjecucion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_ESCE_INCUMPLIMIENTO", referencedColumnName = "ID_ESCE_INCUMPLIMIENTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghEscenarioIncumplimiento idEsceIncumplimiento;
    @JoinColumn(name = "ID_DETALLE_SUPERVISION", referencedColumnName = "ID_DETALLE_SUPERVISION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghDetalleSupervision idDetalleSupervision;
    @OneToMany(mappedBy = "idEscenarioIncumplido", fetch = FetchType.LAZY)
    private List<PghComentarioDetsupervision> pghComentarioDetsupervisionList;

    public PghEscenarioIncumplido() {
    }
    
    /* OSINE_SFS-791 - RSIS 16 - Inicio */
    public PghEscenarioIncumplido(Long idEscenarioIncumplido,Long idDetalleSupervision, Long idEsceIncumplimiento, Long idEsceIncuMaestro, String descripcion,String comentarioEjecucion) {
        this.idEscenarioIncumplido = idEscenarioIncumplido;
        this.idDetalleSupervision = new PghDetalleSupervision(idDetalleSupervision);
        this.idEsceIncumplimiento = new PghEscenarioIncumplimiento(idEsceIncumplimiento, idEsceIncuMaestro, descripcion);
        /* OSINE791 - RSIS25 - Inicio */
        this.comentarioEjecucion = comentarioEjecucion;
        /* OSINE791 - RSIS25 - Fin */
    }
    /* OSINE_SFS-791 - RSIS 16 - Fin */
    
    public PghEscenarioIncumplido(Long idEscenarioIncumplido) {
        this.idEscenarioIncumplido = idEscenarioIncumplido;
    }
    
    public PghEscenarioIncumplido(Long idEscenarioIncumplido,Long idEsceIncumplimiento) {
        this.idEscenarioIncumplido = idEscenarioIncumplido;
        this.idEsceIncumplimiento = new PghEscenarioIncumplimiento(idEsceIncumplimiento);
    }

    public Long getIdEscenarioIncumplido() {
        return idEscenarioIncumplido;
    }

    public void setIdEscenarioIncumplido(Long idEscenarioIncumplido) {
        this.idEscenarioIncumplido = idEscenarioIncumplido;
    }

    public String getComentarioEjecucion() {
        return comentarioEjecucion;
    }

    public void setComentarioEjecucion(String comentarioEjecucion) {
        this.comentarioEjecucion = comentarioEjecucion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PghEscenarioIncumplimiento getIdEsceIncumplimiento() {
        return idEsceIncumplimiento;
    }

    public void setIdEsceIncumplimiento(PghEscenarioIncumplimiento idEsceIncumplimiento) {
        this.idEsceIncumplimiento = idEsceIncumplimiento;
    }

    public PghDetalleSupervision getIdDetalleSupervision() {
        return idDetalleSupervision;
    }

    public void setIdDetalleSupervision(PghDetalleSupervision idDetalleSupervision) {
        this.idDetalleSupervision = idDetalleSupervision;
    }
    
    @XmlTransient
    public List<PghComentarioDetsupervision> getPghComentarioDetsupervisionList() {
        return pghComentarioDetsupervisionList;
    }

    public void setPghComentarioDetsupervisionList(List<PghComentarioDetsupervision> pghComentarioDetsupervisionList) {
        this.pghComentarioDetsupervisionList = pghComentarioDetsupervisionList;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEscenarioIncumplido != null ? idEscenarioIncumplido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghEscenarioIncumplido)) {
            return false;
        }
        PghEscenarioIncumplido other = (PghEscenarioIncumplido) object;
        if ((this.idEscenarioIncumplido == null && other.idEscenarioIncumplido != null) || (this.idEscenarioIncumplido != null && !this.idEscenarioIncumplido.equals(other.idEscenarioIncumplido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domain.PghEscenarioIncumplido[ idEscenarioIncumplido=" + idEscenarioIncumplido + " ]";
    }
    
}
