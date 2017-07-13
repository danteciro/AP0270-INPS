/**
* Resumen		
* Objeto		: PghProcesoObligacionTipo.java
* Descripción		: PghProcesoObligacionTipo
* Fecha de Creación	: 
* PR de Creación	: 
* Autor			: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-791     17/08/2016      Yadira Piskulich                Abrir Supervision DSR
* OSINE_SFS-791     06/10/2016      Mario Dioses Fernandez          Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
* 
*/ 

package gob.osinergmin.inpsweb.domain;

import java.math.BigInteger;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rsantosv
 */
@Entity
@Table(name = "PGH_PROCESO_OBLIGACION_TIPO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghProcesoObligacionTipo.findAll", query = "SELECT p FROM PghProcesoObligacionTipo p")
})
public class PghProcesoObligacionTipo extends Auditoria {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PghProcesoObligacionTipoPK pghProcesoObligacionTipoPK;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FLAG_SUPERVISION")
    private String flagSupervision;
    @JoinColumn(name = "ID_ACTIVIDAD", referencedColumnName = "ID_ACTIVIDAD", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiActividad mdiActividad;
    @JoinColumn(name = "ID_OBLIGACION_TIPO", referencedColumnName = "ID_OBLIGACION_TIPO", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private PghObligacionTipo pghObligacionTipo;
    @JoinColumn(name = "ID_PROCESO", referencedColumnName = "ID_PROCESO", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private PghProceso pghProceso;
    @OneToMany(mappedBy = "pghProcesoObligacionTipo",fetch = FetchType.LAZY)
    private List<PghConfObligacion> pghConfObligacionList;
    /* OSINE791 - RSIS 21 - Inicio */
    @JoinColumn(name = "ID_FLUJO_ESTADO", referencedColumnName = "ID_FLUJO_ESTADO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghFlujoEstado idFlujoEstado;
    /* OSINE791 - RSIS 21 - Fin */
    /* OSINE_SFS-791 - RSIS 33 - Inicio */ 
    @Column(name = "FLAG_EVALUA_TIPO_ASIGNACION")
    private String flagEvaluaTipoAsignacion;    
    /* OSINE_SFS-791 - RSIS 33 - Fin */ 
    @OneToMany(mappedBy = "pghProcesoObligacionTipo", fetch = FetchType.LAZY)
    private List<PghCorreo> pghCorreoList;
    @JoinColumn(name = "FLUJO_SUPERV_INPS")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiMaestroColumna flujoSupervInps;

    public PghProcesoObligacionTipo() {
    }

    public PghProcesoObligacionTipo(PghProcesoObligacionTipoPK pghProcesoObligacionTipoPK) {
        this.pghProcesoObligacionTipoPK = pghProcesoObligacionTipoPK;
    }

    public PghProcesoObligacionTipo(long idObligacionTipo, long idProceso, long idActividad, BigInteger idProOblTip) {
        this.pghProcesoObligacionTipoPK = new PghProcesoObligacionTipoPK(idObligacionTipo, idProceso, idActividad, idProOblTip);
    }

    public PghProcesoObligacionTipoPK getPghProcesoObligacionTipoPK() {
        return pghProcesoObligacionTipoPK;
    }

    public void setPghProcesoObligacionTipoPK(PghProcesoObligacionTipoPK pghProcesoObligacionTipoPK) {
        this.pghProcesoObligacionTipoPK = pghProcesoObligacionTipoPK;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFlagSupervision() {
        return flagSupervision;
    }

    public void setFlagSupervision(String flagSupervision) {
        this.flagSupervision = flagSupervision;
    }

    public MdiActividad getMdiActividad() {
        return mdiActividad;
    }

    public void setMdiActividad(MdiActividad mdiActividad) {
        this.mdiActividad = mdiActividad;
    }

    public PghObligacionTipo getPghObligacionTipo() {
        return pghObligacionTipo;
    }

    public void setPghObligacionTipo(PghObligacionTipo pghObligacionTipo) {
        this.pghObligacionTipo = pghObligacionTipo;
    }

    public PghProceso getPghProceso() {
        return pghProceso;
    }

    public void setPghProceso(PghProceso pghProceso) {
        this.pghProceso = pghProceso;
    }

    @XmlTransient
    public List<PghConfObligacion> getPghConfObligacionList() {
        return pghConfObligacionList;
    }

    public void setPghConfObligacionList(List<PghConfObligacion> pghConfObligacionList) {
        this.pghConfObligacionList = pghConfObligacionList;
    }

    /* OSINE791 - RSIS 21 - Inicio */
    public PghFlujoEstado getIdFlujoEstado() {
        return idFlujoEstado;
    }

    public void setIdFlujoEstado(PghFlujoEstado idFlujoEstado) {
        this.idFlujoEstado = idFlujoEstado;
    }
    /* OSINE791 - RSIS 21 - Fin */   

    @XmlTransient
    public List<PghCorreo> getPghCorreoList() {
        return pghCorreoList;
    }

    /* OSINE_SFS-791 - RSIS 33 - Inicio */
    public String getFlagEvaluaTipoAsignacion() {
		return flagEvaluaTipoAsignacion;
	}
     
	public void setFlagEvaluaTipoAsignacion(String flagEvaluaTipoAsignacion) {
		this.flagEvaluaTipoAsignacion = flagEvaluaTipoAsignacion;
	}
	/* OSINE_SFS-791 - RSIS 33 - Fin */ 
	
	public void setPghCorreoList(List<PghCorreo> pghCorreoList) {
        this.pghCorreoList = pghCorreoList;
    }	

    public MdiMaestroColumna getFlujoSupervInps() {
        return flujoSupervInps;
    }

    public void setFlujoSupervInps(MdiMaestroColumna flujoSupervInps) {
        this.flujoSupervInps = flujoSupervInps;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pghProcesoObligacionTipoPK != null ? pghProcesoObligacionTipoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghProcesoObligacionTipo)) {
            return false;
        }
        PghProcesoObligacionTipo other = (PghProcesoObligacionTipo) object;
        if ((this.pghProcesoObligacionTipoPK == null && other.pghProcesoObligacionTipoPK != null) || (this.pghProcesoObligacionTipoPK != null && !this.pghProcesoObligacionTipoPK.equals(other.pghProcesoObligacionTipoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghProcesoObligacionTipo[ pghProcesoObligacionTipoPK=" + pghProcesoObligacionTipoPK + " ]";
    }
    
}
