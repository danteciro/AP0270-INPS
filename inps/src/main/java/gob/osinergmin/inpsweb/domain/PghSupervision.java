/**
* Resumen		
* Objeto		: PghSupervision.java
* Descripción		: Clase del modelo de dominio PghSupervision.
* Fecha de Creación	: 22/08/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  22/08/2016   |   Luis García Reyna          |     Crear la funcionalidad para registrar otros incumplimientos
* OSINE_SFS-791     11/10/2016       Mario Dioses Fernandez           Crear la tarea automática que notifique vía correo que se debe elaborar el oficio por obligaciones incumplidas sin subsanar
* OSINE_SFS-791     11/10/2016       Mario Dioses Fernandez           Crear la tarea automática que cancele el registro de hidrocarburos
*    
*/

package gob.osinergmin.inpsweb.domain;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rsantosv
 */
@Entity
@Table(name = "PGH_SUPERVISION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghSupervision.findAll", query = "SELECT p FROM PghSupervision p"),
    @NamedQuery(name = "PghSupervision.findByIdSupervision", query = "SELECT p FROM PghSupervision p WHERE p.idSupervision = :idSupervision"),
    @NamedQuery(name = "PghSupervision.findByTerminalActualizacion", query = "SELECT p FROM PghSupervision p WHERE p.terminalActualizacion = :terminalActualizacion")})
public class PghSupervision extends Auditoria {
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @Column(name = "ID_SUPERVISION")
	@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "PGH_SUPERVISION_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idSupervision;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Column(name = "ACTA_PROBATORIA")
    private String actaProbatoria;
    @Column(name = "CARTA_VISITA")
    private String cartaVisita;
    @Column(name = "FLAG_SUPERVISION")
    private String flagSupervision;
    @Column(name = "OBSERVACION")
    private String observacion;
    @Column(name = "FLAG_IDENTIFICA_PERSONA")
    private String flagIdentificaPersona;
    @Column(name = "OBSERVACION_IDENTIFICA_PERS")
    private String observacionIdentificaPers;
    @Column(name = "ID_SUPERVISION_ANT")
    private Long idSupervisionAnt;
    @JoinColumn(name = "ID_MOTIVO_NO_SUPERVISION", referencedColumnName = "ID_MOTIVO_NO_SUPERVISION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghMotivoNoSupervision idMotivoNoSupervision;
    @Column(name = "DESCRIPCION_MTVO_NO_SUPRVSN")
    private String descripcionMtvoNoSuprvsn;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(mappedBy = "pghSupervision", fetch = FetchType.LAZY)
    private List<PghSupervisionPersonaGral> pghSupervisionPersonaGralList;
    @OneToMany(mappedBy = "idSupervision", fetch = FetchType.LAZY)
    private List<PghDetalleSupervision> pghDetalleSupervisionList;
    @OneToMany(mappedBy = "idSupervision", fetch = FetchType.LAZY)
    private List<PghDocumentoAdjunto> pghDocumentoAdjuntoList;
    @JoinColumn(name="ID_ORDEN_SERVICIO")
    @OneToOne
    private PghOrdenServicio idOrdenServicio;
    /* OSINE_SFS-791 - RSIS 15 - Inicio */
    @Column(name = "OTROS_INCUMPLIMIENTOS")
    private String otrosIncumplimientos;
    /* OSINE_SFS-791 - RSIS 15 - Fin */
    /* OSINE_SFS-791 - RSIS 16 - Inicio */
    @Column(name = "FLAG_EJECUCION_MEDIDA")
    private String flagEjecucionMedida;
    /* OSINE_SFS-791 - RSIS 16 - Fin */
    @JoinColumn(name = "ID_RESULTADO_SUPERVISION", referencedColumnName = "ID_RESULTADO_SUPERVISION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghResultadoSupervision idResultadoSupervision;
    @JoinColumn(name = "ID_RESULT_SUPERV_INICIAL", referencedColumnName = "ID_RESULTADO_SUPERVISION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghResultadoSupervision idResultSupervInicial;
    
    @Column(name = "FECHA_INICIO_PORVERIFICAR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioporVerificar;
    
    @Column(name = "FLAG_CUMPL_PREVIO")
    private String flagCumplimientoPrevio;
    
    public PghSupervision() {
    }

    public PghSupervision(Long idSupervision) {
        this.idSupervision = idSupervision;
    }
    
    /* OSINE_SFS-791 - RSIS 33 - Inicio */
    public PghSupervision(Long idSupervision, PghOrdenServicio ordenServicio) {
        this.idSupervision = idSupervision;
        this.idOrdenServicio = ordenServicio;
    }
    /* OSINE_SFS-791 - RSIS 33 - Fin */

    public PghSupervision(Long idSupervision, String estado, Date fechaCreacion, String usuarioCreacion, String terminalCreacion) {
        this.idSupervision = idSupervision;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.terminalCreacion = terminalCreacion;
    }

    /* OSINE_SFS-791 - RSIS 17 - Inicio */
    public PghSupervision(Long idSupervision,Date fechaInicio, Date fechaFin, Long idOrdenServicio,
            String numeroOrdenServicio, Long idExpediente, String numeroExpediente, Long idUnidadSupervisada, String codigoOsinergmin, Long idLocador, String nombrecompleto, Long idEmpresaSupervisora, String razonSocial,String observacion) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idOrdenServicio = new PghOrdenServicio(idOrdenServicio,numeroOrdenServicio, idExpediente, numeroExpediente, idUnidadSupervisada, codigoOsinergmin,idLocador,nombrecompleto,idEmpresaSupervisora,razonSocial);
        this.observacion = observacion;
        this.idSupervision = idSupervision;
    }
    /* OSINE_SFS-791 - RSIS 17 - Fin */
    
    /* OSINE_SFS-791 - RSIS 19 - Inicio */
    public PghSupervision(Long idSupervision,Long idResultadoSupervision,Long codResultadoSupervision){
        this.idSupervision = idSupervision;
        this.idResultadoSupervision = new PghResultadoSupervision(idResultadoSupervision, codResultadoSupervision);
        
    }
    /* OSINE_SFS-791 - RSIS 19 - Fin */
    
    /* OSINE_SFS-791 - RSIS 46-47 - Inicio */
    public PghSupervision(Long idSupervision, Long idOrdenServicio, String numeroOrdenServicio, Long idExpediente, String numeroExpediente, 
    		Long idUnidadSupervisada, String codigoOsinergmin, Long idActividad, Long idRegistroHidrocarburo, String numeroRegistroHidrocarburo, Long idPersonal, 
    		String nombreUsuarioSiged, Long idPersonalSiged, String flagCorreoOficio, String flagEstadoReghMsfh, String flagEstadoReghInps, String flagEnviarConstanciaSiged, 
    		String flagEstadoSiged, String flagCorreoCancelaRegh, String flagRegistraDocsInps, String flagCorreoScop, Long idMaestroColumna, String descripcion, String codigo) {
    	this.idSupervision = idSupervision;
    	if(flagCorreoOficio!=null || flagEstadoReghMsfh!=null || flagEstadoReghInps!=null || flagEnviarConstanciaSiged!=null || 
        		flagEstadoSiged!=null || flagCorreoCancelaRegh!=null || flagRegistraDocsInps!=null || flagCorreoScop!=null) {
    		this.idOrdenServicio = new PghOrdenServicio(idOrdenServicio, numeroOrdenServicio, 
    			new PghExpediente(idExpediente, numeroExpediente, 
    			new MdiUnidadSupervisada(codigoOsinergmin, idUnidadSupervisada, numeroRegistroHidrocarburo, idActividad), 
    			new PghPersonal(idPersonal, nombreUsuarioSiged, idPersonalSiged),
    			new PghExpedienteTarea(idExpediente, flagCorreoOficio, flagEstadoReghMsfh, flagEstadoReghInps, flagEnviarConstanciaSiged, flagEstadoSiged, flagCorreoCancelaRegh, flagRegistraDocsInps, flagCorreoScop),
    			new MdiMaestroColumna(idMaestroColumna, descripcion, codigo)));
    	} else {
    		this.idOrdenServicio = new PghOrdenServicio(idOrdenServicio, numeroOrdenServicio, 
        			new PghExpediente(idExpediente, numeroExpediente, 
        			new MdiUnidadSupervisada(codigoOsinergmin, idUnidadSupervisada, numeroRegistroHidrocarburo, idActividad), 
        			new PghPersonal(idPersonal, nombreUsuarioSiged, idPersonalSiged), null, new MdiMaestroColumna(idMaestroColumna, descripcion, codigo)));
    	}
    }    
    /* OSINE_SFS-791 - RSIS 46-47 - Fin */

    public Long getIdSupervision() {
        return idSupervision;
    }

    public void setIdSupervision(Long idSupervision) {
        this.idSupervision = idSupervision;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getActaProbatoria() {
        return actaProbatoria;
    }

    public void setActaProbatoria(String actaProbatoria) {
        this.actaProbatoria = actaProbatoria;
    }

    public String getCartaVisita() {
        return cartaVisita;
    }

    public void setCartaVisita(String cartaVisita) {
        this.cartaVisita = cartaVisita;
    }

    public String getFlagSupervision() {
        return flagSupervision;
    }

    public void setFlagSupervision(String flagSupervision) {
        this.flagSupervision = flagSupervision;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFlagIdentificaPersona() {
        return flagIdentificaPersona;
    }

    public void setFlagIdentificaPersona(String flagIdentificaPersona) {
        this.flagIdentificaPersona = flagIdentificaPersona;
    }

    public String getObservacionIdentificaPers() {
		return observacionIdentificaPers;
	}

	public void setObservacionIdentificaPers(String observacionIdentificaPers) {
		this.observacionIdentificaPers = observacionIdentificaPers;
	}

	public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    @XmlTransient
    public List<PghSupervisionPersonaGral> getPghSupervisionPersonaGralList() {
        return pghSupervisionPersonaGralList;
    }

    public void setPghSupervisionPersonaGralList(List<PghSupervisionPersonaGral> pghSupervisionPersonaGralList) {
        this.pghSupervisionPersonaGralList = pghSupervisionPersonaGralList;
    }

    @XmlTransient
    public List<PghDetalleSupervision> getPghDetalleSupervisionList() {
        return pghDetalleSupervisionList;
    }

    public void setPghDetalleSupervisionList(List<PghDetalleSupervision> pghDetalleSupervisionList) {
        this.pghDetalleSupervisionList = pghDetalleSupervisionList;
    }

    @XmlTransient
    public List<PghDocumentoAdjunto> getPghDocumentoAdjuntoList() {
		return pghDocumentoAdjuntoList;
	}

	public void setPghDocumentoAdjuntoList(
			List<PghDocumentoAdjunto> pghDocumentoAdjuntoList) {
		this.pghDocumentoAdjuntoList = pghDocumentoAdjuntoList;
	}

	public PghOrdenServicio getIdOrdenServicio() {
		return idOrdenServicio;
	}

	public void setIdOrdenServicio(PghOrdenServicio idOrdenServicio) {
		this.idOrdenServicio = idOrdenServicio;
	}

	public Long getIdSupervisionAnt() {
		return idSupervisionAnt;
	}

	public void setIdSupervisionAnt(Long idSupervisionAnt) {
		this.idSupervisionAnt = idSupervisionAnt;
	}

	public PghMotivoNoSupervision getIdMotivoNoSupervision() {
		return idMotivoNoSupervision;
	}

	public void setIdMotivoNoSupervision(
			PghMotivoNoSupervision idMotivoNoSupervision) {
		this.idMotivoNoSupervision = idMotivoNoSupervision;
	}

	public String getDescripcionMtvoNoSuprvsn() {
		return descripcionMtvoNoSuprvsn;
	}

	public void setDescripcionMtvoNoSuprvsn(String descripcionMtvoNoSuprvsn) {
		this.descripcionMtvoNoSuprvsn = descripcionMtvoNoSuprvsn;
	}

    public PghResultadoSupervision getIdResultSupervInicial() {
        return idResultSupervInicial;
    }

    public void setIdResultSupervInicial(PghResultadoSupervision idResultSupervInicial) {
        this.idResultSupervInicial = idResultSupervInicial;
    }
    
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idSupervision != null ? idSupervision.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghSupervision)) {
            return false;
        }
        PghSupervision other = (PghSupervision) object;
        if ((this.idSupervision == null && other.idSupervision != null) || (this.idSupervision != null && !this.idSupervision.equals(other.idSupervision))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inps.domain.PghSupervision[ idSupervision=" + idSupervision + " ]";
    }
    
    /* OSINE_SFS-791 - RSIS 15 - Inicio */
    public String getOtrosIncumplimientos() {
        return otrosIncumplimientos;
    }
    public void setOtrosIncumplimientos(String otrosIncumplimientos) {
        this.otrosIncumplimientos = otrosIncumplimientos;
    }
    /* OSINE_SFS-791 - RSIS 15 - Fin */
        /* OSINE_SFS-791 - RSIS 16 - Inicio */
    public String getFlagEjecucionMedida() {
        return flagEjecucionMedida;
    }

    public void setFlagEjecucionMedida(String flagEjecucionMedida) {
        this.flagEjecucionMedida = flagEjecucionMedida;
    }
    /* OSINE_SFS-791 - RSIS 16 - Fin */

    /**
     * @return the idResultadoSupervision
     */
    public PghResultadoSupervision getIdResultadoSupervision() {
        return idResultadoSupervision;
    }

    /**
     * @param idResultadoSupervision the idResultadoSupervision to set
     */
    public void setIdResultadoSupervision(PghResultadoSupervision idResultadoSupervision) {
        this.idResultadoSupervision = idResultadoSupervision;
    }

    /**
     * @return the fechaInicioporVerificar
     */
    public Date getFechaInicioporVerificar() {
        return fechaInicioporVerificar;
    }

    /**
     * @param fechaInicioporVerificar the fechaInicioporVerificar to set
     */
    public void setFechaInicioporVerificar(Date fechaInicioporVerificar) {
        this.fechaInicioporVerificar = fechaInicioporVerificar;
    }

    /**
     * @return the flagCumplimientoPrevio
     */
    public String getFlagCumplimientoPrevio() {
        return flagCumplimientoPrevio;
    }

    /**
     * @param flagCumplimientoPrevio the flagCumplimientoPrevio to set
     */
    public void setFlagCumplimientoPrevio(String flagCumplimientoPrevio) {
        this.flagCumplimientoPrevio = flagCumplimientoPrevio;
    }
    
}
