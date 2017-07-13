/**
*
* Resumen		
* Objeto		: PghDetalleSupervision.java
* Descripción		: Clase del modelo de dominio PghDetalleSupervision
* Fecha de Creación	: 02/09/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  02/09/2016   |   Luis García Reyna          |     Ejecucion Medida - Listar Obligaciones
* OSINE_SFS-791  |  06/10/2016   |   Mario Dioses Fernandez     |     Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD. 
* OSINE_SFS-791  |  12/10/2016   |   Luis García Reyna          |     Terminar Supervision - Listar Infracciones No Subsanadas                |               |                              |
* OSINE_MAN_DSR_0025      05/07/2017     Carlos Quijano Chavez::ADAPTER          Agregar la Columna Codigo y eliminar prioridad
* 
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rsantosv
 */
@Entity
@Table(name = "PGH_DETALLE_SUPERVISION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghDetalleSupervision.findAll", query = "SELECT p FROM PghDetalleSupervision p"),
    @NamedQuery(name = "PghDetalleSupervision.findById", query = "SELECT p FROM PghDetalleSupervision p where p.idDetalleSupervision=:idDetalleSupervision and p.estado=:estado"),
    @NamedQuery(name = "PghDetalleSupervision.findByPrioridad", query = "SELECT p FROM PghDetalleSupervision p where p.idSupervision.idSupervision=:idSupervision and p.prioridad=:prioridad and p.estado=:estado")})
public class PghDetalleSupervision extends Auditoria {
	
	public static final String BUSCAR_POR_ID = "PghDetalleSupervision.findById";
	public static final String BUSCAR_POR_PRIORIDAD = "PghDetalleSupervision.findByPrioridad";

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DETALLE_SUPERVISION")
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "PGH_DETALLE_SUPERVISION_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idDetalleSupervision;
    @Column(name = "FLAG_RESULTADO")
    private String flagResultado;
    @Column(name = "DESCRIPCION_RESULTADO")
    private String descripcionResultado;
    @Column(name = "FLAG_REGISTRADO")
    private String flagRegistrado;
    @Column(name = "ID_DETALLE_SUPERVISION_ANT")
    private Long idDetalleSupervisionAnt;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_SUPERVISION", referencedColumnName = "ID_SUPERVISION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghSupervision idSupervision;
    @JoinColumn(name = "ID_OBLIGACION", referencedColumnName = "ID_OBLIGACION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghObligacion idObligacion;
    @JoinColumn(name = "ID_TIPIFICACION", referencedColumnName = "ID_TIPIFICACION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghTipificacion idTipificacion;
    @JoinColumn(name = "ID_CRITERIO", referencedColumnName = "ID_CRITERIO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghCriterio idCriterio;
    @OneToMany(mappedBy = "idDetalleSupervision", fetch = FetchType.LAZY)
    private List<PghDocumentoAdjunto> pghDocumentoAdjuntoList;
    @Column(name = "PRIORIDAD")
    private Long prioridad;
    @Column(name = "FECHA_INICIO_PORVERIFICAR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioPorVerificar;
    @OneToMany(mappedBy = "idDetalleSupervision", fetch = FetchType.LAZY)
    private List<PghProductoSuspender> pghProductoSuspenderList;
    @Column(name = "COMENTARIO")
    private String comentario;
    @OneToMany(mappedBy = "idDetalleSupervision", fetch = FetchType.LAZY)
    private List<PghEscenarioIncumplido> pghEscenarioIncumplidoList;
    @JoinColumn(name = "ID_RESULTADO_SUPERVISION_ANT", referencedColumnName = "ID_RESULTADO_SUPERVISION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghResultadoSupervision idResultadoSupervisionAnt;
    @JoinColumn(name = "ID_RESULTADO_SUPERVISION", referencedColumnName = "ID_RESULTADO_SUPERVISION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghResultadoSupervision idResultadoSupervision;
    @OneToMany(mappedBy = "idDetalleSupervision", fetch = FetchType.LAZY)
    private List<PghComentarioDetsupervision> pghComentarioDetsupervisionList;
    @Column(name = "ID_MEDIDA_SEGURIDAD")
    private Long idMedidaSeguridad;
    @Transient
    private Long countEscIncumplido;
    @Transient
    private Long countComentarioDetSupervision;
    /* OSINE_SFS-791 - RSIS 33 - Inicio */
    @Column(name = "ID_DETALLE_SUPERVISION_SUBSANA")
    private Long idDetalleSupervisionSubsana;
    @OneToMany(mappedBy = "idDetalleSupervision", fetch = FetchType.LAZY)
    private List<PghDetalleLevantamiento> pghDetalleLevantamientoList;
    /* OSINE_SFS-791 - RSIS 33 - Fin */
    @OneToMany(mappedBy = "idDetaLevaAtiende", fetch = FetchType.LAZY)
    private List<PghDetalleLevantamiento> pghDetalleLevantamientoList1;
    @JoinColumn(name = "ID_INFRACCION", referencedColumnName = "ID_INFRACCION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghInfraccion idInfraccion;
    
    /* OSINE_SFS-791 - RSIS 39 - Inicio */
    @Transient
    private Long countEscIncumplidoAnt;
    @Transient
    private String flagMostrarProducto;
    /* OSINE_SFS-791 - RSIS 39 - Fin */
    public PghDetalleSupervision() {
    }

    public PghDetalleSupervision(Long idDetalleSupervision, Long idDetalleSupervisionAnt, String flagResultado, String descripcionResultado, String flagRegistrado, 
            Long idObligacion, Long idSupervision, Long idTipificacion,Long idCriterio) {
        this.idDetalleSupervision = idDetalleSupervision;
        this.idDetalleSupervisionAnt = idDetalleSupervisionAnt;
        this.flagResultado = flagResultado;
        this.descripcionResultado = descripcionResultado;
        this.flagRegistrado = flagRegistrado;
        this.idObligacion = new PghObligacion(idObligacion);
        this.idSupervision = new PghSupervision(idSupervision);
        this.idTipificacion = new PghTipificacion(idTipificacion);
        this.idCriterio = new PghCriterio(idCriterio);        
    }

   
    public PghDetalleSupervision(Long idDetalleSupervision, Long idDetalleSupervisionAnt, String flagResultado, String descripcionResultado, 
            String flagRegistrado, Long prioridad, String comentario, Long idObligacion, Long idSupervision, Long idTipificacion,
            Long idCriterio, Long idInfraccion, String descripcionInfraccion, String nombreArchivoInfraccion,String rutaAlfrescoInfraccion,            
            Long idResultadosupervision, Long codigoResultsuper,Long countEscIncumplido,Long countComentarioDetSupervision,Long countEscIncumplidoAnt,
            Long idResultSuperAnt,Long codigoResultSuperAnt,Long idMedidaSeguridad,  String flagMostrarProducto) {
        this.idDetalleSupervision = idDetalleSupervision;
        this.idDetalleSupervisionAnt = idDetalleSupervisionAnt;
        this.flagResultado = flagResultado;
        this.descripcionResultado = descripcionResultado;
        this.flagRegistrado = flagRegistrado;
        /* OSINE_SFS-791 - RSIS 16 - Inicio */
        this.prioridad = prioridad;
        this.comentario=comentario;
        /* OSINE_SFS-791 - RSIS 16 - Fin */
        //this.idObligacion = new PghObligacion(idObligacion, new PghInfraccion(idInfraccion, descripcionInfraccion,nombreArchivoInfraccion,rutaAlfrescoInfraccion));
        this.idObligacion = new PghObligacion(idObligacion);
        this.idInfraccion=new PghInfraccion(idInfraccion, descripcionInfraccion,nombreArchivoInfraccion,rutaAlfrescoInfraccion);
        this.idSupervision = new PghSupervision(idSupervision);
        this.idTipificacion = new PghTipificacion(idTipificacion);
        this.idCriterio = new PghCriterio(idCriterio);
        this.idResultadoSupervision = new PghResultadoSupervision(idResultadosupervision,codigoResultsuper); 
        this.countEscIncumplido = countEscIncumplido;
        this.countComentarioDetSupervision = countComentarioDetSupervision;
        /* OSINE_SFS-791 - RSIS 39 - Inicio */
        this.countEscIncumplidoAnt = countEscIncumplidoAnt;
        /* OSINE_SFS-791 - RSIS 39 - Fin */
        this.idResultadoSupervisionAnt = new PghResultadoSupervision(idResultSuperAnt,codigoResultSuperAnt);
        this.idMedidaSeguridad = idMedidaSeguridad;
        this.flagMostrarProducto = flagMostrarProducto;
    }
    
    public PghDetalleSupervision(Long idDetalleSupervision,String Descripcion,String ClaseIcono,String descripcionResSup,Long priorida,Long codigo, 
            Long idDetalleSupervisionAnt, Long iteracion) {
        this.idDetalleSupervision = idDetalleSupervision;
        this.descripcionResultado = Descripcion;
        this.idResultadoSupervision = new PghResultadoSupervision(ClaseIcono,descripcionResSup,codigo);
        this.prioridad = priorida;  
        this.idDetalleSupervisionAnt=idDetalleSupervisionAnt;
        this.idSupervision = new PghSupervision(null,new PghOrdenServicio(null,iteracion));
    }
    public PghDetalleSupervision(Long idDetalleSupervision,Long idResultadoEstado,Date fechaPorVerificar) {
        this.idDetalleSupervision = idDetalleSupervision;
        this.idResultadoSupervision = new PghResultadoSupervision(idResultadoEstado);
        this.fechaInicioPorVerificar = fechaPorVerificar;
    }
    /* OSINE_MAN_DSR_0025 - Inicio */
    public PghDetalleSupervision(Long idDetalleSupervision,String Descripcion,String ClaseIcono,String descripcionResSup,Long priorida,Long codigo, 
            Long idDetalleSupervisionAnt, Long iteracion,String codigoInfraccion) {
        this.idDetalleSupervision = idDetalleSupervision;
        this.descripcionResultado = Descripcion;
        this.idResultadoSupervision = new PghResultadoSupervision(ClaseIcono,descripcionResSup,codigo);
        this.prioridad = priorida;  
        this.idDetalleSupervisionAnt=idDetalleSupervisionAnt;
        this.idSupervision = new PghSupervision(null,new PghOrdenServicio(null,iteracion));
        this.idInfraccion =new PghInfraccion(codigoInfraccion);   
    }
    /* OSINE_MAN_DSR_0025 - Fin */

    public PghDetalleSupervision(Long idDetalleSupervision) {
        this.idDetalleSupervision = idDetalleSupervision;
    }

    public PghDetalleSupervision(Long idDetalleSupervision, String estado, Date fechaCreacion, String usuarioCreacion, String terminalCreacion) {
        this.idDetalleSupervision = idDetalleSupervision;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.terminalCreacion = terminalCreacion;
    }
    
    /* OSINE_SFS-791 - RSIS 33 - Inicio */
    public PghDetalleSupervision(Long idSupervision, Long idDetalleSupervision, Long idObligacion, Long idInfraccion, String descripcionInfraccion,
    		Long idOrdenServicio, String numeroOrdenServicio, Long idExpediente, String numeroExpediente, Long idUnidadSupervisada, String codigoOsinergmin,
   		Long idRegistroHidrocarburo, String numeroRegistroHidrocarburo, Long idPersonal, String nombreUsuarioSiged, Long idPersonalSiged) {
   	this.idSupervision = new PghSupervision(idSupervision, 
   			new PghOrdenServicio(idOrdenServicio, numeroOrdenServicio, 
    			new PghExpediente(idExpediente, numeroExpediente, 
   			new MdiUnidadSupervisada(codigoOsinergmin, idUnidadSupervisada, numeroRegistroHidrocarburo), 
    			new PghPersonal(idPersonal, nombreUsuarioSiged, idPersonalSiged))));
        this.idDetalleSupervision = idDetalleSupervision;        
       this.idObligacion = new PghObligacion(idObligacion);
       this.idInfraccion=new PghInfraccion(idInfraccion, descripcionInfraccion,"","");        
    }    
    /* OSINE_SFS-791 - RSIS 33 - Fin */

    public Long getIdDetalleSupervision() {
        return idDetalleSupervision;
    }

    public void setIdDetalleSupervision(Long idDetalleSupervision) {
        this.idDetalleSupervision = idDetalleSupervision;
    }

    public String getFlagResultado() {
        return flagResultado;
    }

    public void setFlagResultado(String flagResultado) {
        this.flagResultado = flagResultado;
    }

    public String getDescripcionResultado() {
        return descripcionResultado;
    }

    public void setDescripcionResultado(String descripcionResultado) {
        this.descripcionResultado = descripcionResultado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PghSupervision getIdSupervision() {
        return idSupervision;
    }

    public void setIdSupervision(PghSupervision idSupervision) {
        this.idSupervision = idSupervision;
    }

    public PghObligacion getIdObligacion() {
        return idObligacion;
    }

    public void setIdObligacion(PghObligacion idObligacion) {
        this.idObligacion = idObligacion;
    }

    public PghTipificacion getIdTipificacion() {
        return idTipificacion;
    }

    public void setIdTipificacion(PghTipificacion idTipificacion) {
        this.idTipificacion = idTipificacion;
    }

    public PghCriterio getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(PghCriterio idCriterio) {
        this.idCriterio = idCriterio;
    }

    public String getFlagRegistrado() {
        return flagRegistrado;
    }

    public void setFlagRegistrado(String flagRegistrado) {
        this.flagRegistrado = flagRegistrado;
    }

    public Long getIdDetalleSupervisionAnt() {
        return idDetalleSupervisionAnt;
    }

    public void setIdDetalleSupervisionAnt(Long idDetalleSupervisionAnt) {
        this.idDetalleSupervisionAnt = idDetalleSupervisionAnt;
    }

    @XmlTransient
    public List<PghDocumentoAdjunto> getPghDocumentoAdjuntoList() {
        return pghDocumentoAdjuntoList;
    }

    public void setPghDocumentoAdjuntoList(List<PghDocumentoAdjunto> pghDocumentoAdjuntoList) {
        this.pghDocumentoAdjuntoList = pghDocumentoAdjuntoList;
    }

    @XmlTransient
    public List<PghProductoSuspender> getPghProductoSuspenderList() {
        return pghProductoSuspenderList;
    }

    public void setPghProductoSuspenderList(List<PghProductoSuspender> pghProductoSuspenderList) {
        this.pghProductoSuspenderList = pghProductoSuspenderList;
    }

    @XmlTransient
    public List<PghEscenarioIncumplido> getPghEscenarioIncumplidoList() {
        return pghEscenarioIncumplidoList;
    }

    public void setPghEscenarioIncumplidoList(List<PghEscenarioIncumplido> pghEscenarioIncumplidoList) {
        this.pghEscenarioIncumplidoList = pghEscenarioIncumplidoList;
    }
    
    public PghResultadoSupervision getIdResultadoSupervisionAnt() {
        return idResultadoSupervisionAnt;
    }

    public void setIdResultadoSupervisionAnt(PghResultadoSupervision idResultadoSupervisionAnt) {
        this.idResultadoSupervisionAnt = idResultadoSupervisionAnt;
    }

    public PghResultadoSupervision getIdResultadoSupervision() {
        return idResultadoSupervision;
    }

    public void setIdResultadoSupervision(PghResultadoSupervision idResultadoSupervision) {
        this.idResultadoSupervision = idResultadoSupervision;
    }

    public Long getCountEscIncumplido() {
        return countEscIncumplido;
    }

    public void setCountEscIncumplido(Long countEscIncumplido) {
        this.countEscIncumplido = countEscIncumplido;
    }

    public Long getCountComentarioDetSupervision() {
        return countComentarioDetSupervision;
    }

    public void setCountComentarioDetSupervision(Long countComentarioDetSupervision) {
        this.countComentarioDetSupervision = countComentarioDetSupervision;
    }
    
    @XmlTransient
    public List<PghComentarioDetsupervision> getPghComentarioDetsupervisionList() {
        return pghComentarioDetsupervisionList;
    }

    public void setPghComentarioDetsupervisionList(List<PghComentarioDetsupervision> pghComentarioDetsupervisionList) {
        this.pghComentarioDetsupervisionList = pghComentarioDetsupervisionList;
    }
    
    @XmlTransient
    public List<PghDetalleLevantamiento> getPghDetalleLevantamientoList1() {
        return pghDetalleLevantamientoList1;
    }

    public void setPghDetalleLevantamientoList1(List<PghDetalleLevantamiento> pghDetalleLevantamientoList1) {
        this.pghDetalleLevantamientoList1 = pghDetalleLevantamientoList1;
    }
    
    public PghInfraccion getIdInfraccion() {
        return idInfraccion;
    }

    public void setIdInfraccion(PghInfraccion idInfraccion) {
        this.idInfraccion = idInfraccion;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleSupervision != null ? idDetalleSupervision.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghDetalleSupervision)) {
            return false;
        }
        PghDetalleSupervision other = (PghDetalleSupervision) object;
        if ((this.idDetalleSupervision == null && other.idDetalleSupervision != null) || (this.idDetalleSupervision != null && !this.idDetalleSupervision.equals(other.idDetalleSupervision))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghDetalleSupervision[ idDetalleSupervision=" + idDetalleSupervision + " ]";
    }

    public Long getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Long prioridad) {
        this.prioridad = prioridad;
    }

    public Date getFechaInicioPorVerificar() {
        return fechaInicioPorVerificar;
    }

    public void setFechaInicioPorVerificar(Date fechaInicioPorVerificar) {
        this.fechaInicioPorVerificar = fechaInicioPorVerificar;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }    

    /**
     * @return the idMedidaSeguridad
     */
    public Long getIdMedidaSeguridad() {
        return idMedidaSeguridad;
    }

    /**
     * @param idMedidaSeguridad the idMedidaSeguridad to set
     */
    public void setIdMedidaSeguridad(Long idMedidaSeguridad) {
        this.idMedidaSeguridad = idMedidaSeguridad;
    }

    /* OSINE_SFS-791 - RSIS 33 - Inicio */
	public Long getIdDetalleSupervisionSubsana() {
		return idDetalleSupervisionSubsana;
	}

	public void setIdDetalleSupervisionSubsana(Long idDetalleSupervisionSubsana) {
		this.idDetalleSupervisionSubsana = idDetalleSupervisionSubsana;
	}

	public List<PghDetalleLevantamiento> getPghDetalleLevantamientoList() {
		return pghDetalleLevantamientoList;
	}

	public void setPghDetalleLevantamientoList(
			List<PghDetalleLevantamiento> pghDetalleLevantamientoList) {
		this.pghDetalleLevantamientoList = pghDetalleLevantamientoList;
	}
	/* OSINE_SFS-791 - RSIS 33 - Fin */

    public Long getCountEscIncumplidoAnt() {
        return countEscIncumplidoAnt;
    }

    public void setCountEscIncumplidoAnt(Long countEscIncumplidoAnt) {
        this.countEscIncumplidoAnt = countEscIncumplidoAnt;
    }

	public String getFlagMostrarProducto() {
		return flagMostrarProducto;
	}

	public void setFlagMostrarProducto(String flagMostrarProducto) {
		this.flagMostrarProducto = flagMostrarProducto;
	}
}
