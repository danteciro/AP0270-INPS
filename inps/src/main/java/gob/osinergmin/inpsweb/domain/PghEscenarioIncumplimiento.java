/**
*
* Resumen		
* Objeto		: PghEscenarioIncumplimiento.java
* Descripción		: Clase del modelo de dominio PghEscenarioIncumplimiento
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  05/09/2016   |   Luis García Reyna          |     Buscar Escenarios Incumplidos de la Supervisión
*                |               |                              |
*                |               |                              |
* 
*/


package gob.osinergmin.inpsweb.domain;

import java.math.BigInteger;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "PGH_ESCENARIO_INCUMPLIMIENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghEscenarioIncumplimiento.findAll", query = "SELECT p FROM PghEscenarioIncumplimiento p where p.estado=1"),
    @NamedQuery(name = "PghEscenarioIncumplimiento.findByIdInfraccion", query = "SELECT p FROM PghEscenarioIncumplimiento p where p.idInfraccion.idInfraccion=:idInfraccion and p.estado=1"),
    @NamedQuery(name = "PghEscenarioIncumplimiento.findByIdEsceIncumplimiento", query = "SELECT p FROM PghEscenarioIncumplimiento p where p.idEsceIncumplimiento=:idEsceIncumplimiento and p.estado=1")
})
public class PghEscenarioIncumplimiento extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ESCE_INCUMPLIMIENTO")
    private Long idEsceIncumplimiento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESCE_INCU_MAESTRO")
    private MdiMaestroColumna idEsceIncuMaestro;
    @Column(name = "ID_PADRE")
    private BigInteger idPadre;
    @Size(max = 20)
    @Column(name = "COD_TRAZABILIDAD")
    private String codTrazabilidad;
    @Size(max = 1)
    @Column(name = "COD_ACCION")
    private String codAccion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_INFRACCION", referencedColumnName = "ID_INFRACCION")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PghInfraccion idInfraccion;
    @OneToMany(mappedBy = "idEsceIncumplimiento", fetch = FetchType.LAZY)
    private List<PghEscenarioIncumplido> pghEscenarioIncumplidoList;
    @OneToMany(mappedBy = "idEsceIncumplimiento", fetch = FetchType.LAZY)
    private List<PghComentarioIncumplimiento> pghComentarioIncumplimientoList;
    
    public PghEscenarioIncumplimiento() {
    }
    
    public PghEscenarioIncumplimiento(Long idEsceIncumplimiento) {
        this.idEsceIncumplimiento = idEsceIncumplimiento;
    }
    
    /* OSINE_SFS-791 - RSIS 16 - Inicio */
    public PghEscenarioIncumplimiento(Long idEsceIncumplimiento , Long idEsceIncuMaestro, String descripcion) {
        this.idEsceIncumplimiento = idEsceIncumplimiento;
        this.idEsceIncuMaestro = new MdiMaestroColumna(idEsceIncuMaestro, descripcion);
    }
    /* OSINE_SFS-791 - RSIS 16 - Fin */
    public Long getIdEsceIncumplimiento() {
        return idEsceIncumplimiento;
    }

    public void setIdEsceIncumplimiento(Long idEsceIncumplimiento) {
        this.idEsceIncumplimiento = idEsceIncumplimiento;
    }
    
    public MdiMaestroColumna getIdEsceIncuMaestro() {
        return idEsceIncuMaestro;
    }

    public void setIdEsceIncuMaestro(MdiMaestroColumna idEsceIncuMaestro) {
        this.idEsceIncuMaestro = idEsceIncuMaestro;
    }

    public BigInteger getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(BigInteger idPadre) {
        this.idPadre = idPadre;
    }

    public String getCodTrazabilidad() {
        return codTrazabilidad;
    }

    public void setCodTrazabilidad(String codTrazabilidad) {
        this.codTrazabilidad = codTrazabilidad;
    }

    public String getCodAccion() {
        return codAccion;
    }

    public void setCodAccion(String codAccion) {
        this.codAccion = codAccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PghInfraccion getIdInfraccion() {
        return idInfraccion;
    }

    public void setIdInfraccion(PghInfraccion idInfraccion) {
        this.idInfraccion = idInfraccion;
    }

    @XmlTransient
    public List<PghEscenarioIncumplido> getPghEscenarioIncumplidoList() {
        return pghEscenarioIncumplidoList;
    }

    public void setPghEscenarioIncumplidoList(List<PghEscenarioIncumplido> pghEscenarioIncumplidoList) {
        this.pghEscenarioIncumplidoList = pghEscenarioIncumplidoList;
    }
    
    @XmlTransient
    public List<PghComentarioIncumplimiento> getPghComentarioIncumplimientoList() {
        return pghComentarioIncumplimientoList;
    }

    public void setPghComentarioIncumplimientoList(List<PghComentarioIncumplimiento> pghComentarioIncumplimientoList) {
        this.pghComentarioIncumplimientoList = pghComentarioIncumplimientoList;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEsceIncumplimiento != null ? idEsceIncumplimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghEscenarioIncumplimiento)) {
            return false;
        }
        PghEscenarioIncumplimiento other = (PghEscenarioIncumplimiento) object;
        if ((this.idEsceIncumplimiento == null && other.idEsceIncumplimiento != null) || (this.idEsceIncumplimiento != null && !this.idEsceIncumplimiento.equals(other.idEsceIncumplimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghEscenarioIncumplimiento[ idEsceIncumplimiento=" + idEsceIncumplimiento + " ]";
    }
    
}
