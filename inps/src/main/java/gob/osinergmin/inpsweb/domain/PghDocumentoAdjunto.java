/**
* Resumen		
* Objeto		: PghDocumentoAdjunto.java
* Descripción		: Clase del modelo de dominio PghDocumentoAdjunto
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Roy Robert Santos Villanueva  
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                        Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     21/05/2016      Julio Piro Gonzales           Insertar imágenes de medios probatorios en plantillas de Informe de Supervisión
* OSINE791-RSIS42   18/10/2016      Alexander Vilca Narvaez       Adecuar la funcionalidad "CERRAR ORDEN" cuando se atiende una orden de levantamiento DSR-CRITICIDAD
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rsantosv
 */
@Entity
@Table(name = "PGH_DOCUMENTO_ADJUNTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghDocumentoAdjunto.findAll", query = "SELECT p FROM PghDocumentoAdjunto p"),
    @NamedQuery(name = "PghDocumentoAdjunto.findByIdDocumentoAdjunto", query = "SELECT p FROM PghDocumentoAdjunto p WHERE p.idDocumentoAdjunto = :idDocumentoAdjunto"),
    /* OSINE_SFS-480 - RSIS 01 - Inicio */
    @NamedQuery(name = "PghDocumentoAdjunto.findFromDetalleSupervAndSuperv", query = "SELECT da FROM PghDocumentoAdjunto da LEFT JOIN da.idDetalleSupervision ds LEFT JOIN ds.idSupervision su WHERE su.idSupervision = :idSupervision and da.estado=1 and ds.estado=1 and su.estado=1 order by da.idDocumentoAdjunto ") 
    /* OSINE_SFS-480 - RSIS 01 - Fin */    
})
public class PghDocumentoAdjunto extends Auditoria {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DOCUMENTO_ADJUNTO")
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "PGH_DOCUMENTO_ADJUNTO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idDocumentoAdjunto;
//    @Column(name = "ID_TIPO_DOCUMENTO")
//    private Long idTipoDocumento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_DOCUMENTO")
    private MdiMaestroColumna idTipoDocumento;
    @Column(name = "DESCRIPCION_DOCUMENTO")
    private String descripcionDocumento;
    @Column(name = "NOMBRE_DOCUMENTO")
    private String nombreDocumento;
    @Lob
    @Column(name = "ARCHIVO_ADJUNTO")
    private byte[] archivoAdjunto;
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_DETALLE_SUPERVISION", referencedColumnName = "ID_DETALLE_SUPERVISION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghDetalleSupervision idDetalleSupervision;
    @JoinColumn(name = "ID_SUPERVISION", referencedColumnName = "ID_SUPERVISION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghSupervision idSupervision;
    @JoinColumn(name = "ID_ORDEN_SERVICIO", referencedColumnName = "ID_ORDEN_SERVICIO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghOrdenServicio idOrdenServicio;
    @Column(name = "FLAG_ENVIADO_SIGED")
    private String flagEnviadoSiged;
    /* OSINE_SFS-791 - RSIS 33 - Inicio */     
    @JoinColumn(name = "ID_DETALLE_LEVANTAMIENTO", referencedColumnName = "ID_DETALLE_LEVANTAMIENTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghDetalleLevantamiento idDetalleLevantamiento;
    /* OSINE_SFS-791 - RSIS 33 - Fin */ 
    
    public PghDocumentoAdjunto() {
    }

    public PghDocumentoAdjunto(Long idDocumentoAdjunto) {
        this.idDocumentoAdjunto = idDocumentoAdjunto;
    }

    public PghDocumentoAdjunto(Long idDocumentoAdjunto,String descripcionDocumento,String nombreDocumento,Long idMaestroColumna, String descripcion) {
        this.idDocumentoAdjunto = idDocumentoAdjunto;
        this.descripcionDocumento = descripcionDocumento;
        this.nombreDocumento = nombreDocumento;
        this.idTipoDocumento = new MdiMaestroColumna(idMaestroColumna,descripcion);
    }   
    
    /* OSINE791 RSIS42 - Inicio */
    public PghDocumentoAdjunto(Long idDocumentoAdjunto,String descripcionDocumento,String nombreDocumento,Long idMaestroColumna, String descripcion ,Date fechaCreacion, String codigoMaestro) {
        this.idDocumentoAdjunto = idDocumentoAdjunto;
        this.descripcionDocumento = descripcionDocumento;
        this.nombreDocumento = nombreDocumento;
        this.idTipoDocumento = new MdiMaestroColumna(idMaestroColumna,descripcion,codigoMaestro);
        this.fechaCreacion = fechaCreacion;
    }
    /* OSINE791 RSIS42 - Fin */
   
    public PghDocumentoAdjunto(Long idDocumentoAdjunto,String descripcionDocumento,String nombreDocumento,Long idMaestroColumna, String descripcion ,Date fechaCreacion) {
        this.idDocumentoAdjunto = idDocumentoAdjunto;
        this.descripcionDocumento = descripcionDocumento;
        this.nombreDocumento = nombreDocumento;
        this.idTipoDocumento = new MdiMaestroColumna(idMaestroColumna,descripcion);
        this.fechaCreacion = fechaCreacion;
    }
    
    public PghDocumentoAdjunto(Long idDocumentoAdjunto, String usuarioCreacion, Date fechaCreacion, String terminalCreacion) {
        this.idDocumentoAdjunto = idDocumentoAdjunto;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
    }
    
    public PghOrdenServicio getIdOrdenServicio() {
        return idOrdenServicio;
    }

    public void setIdOrdenServicio(PghOrdenServicio idOrdenServicio) {
        this.idOrdenServicio = idOrdenServicio;
    }
    
    public Long getIdDocumentoAdjunto() {
        return idDocumentoAdjunto;
    }

    public void setIdDocumentoAdjunto(Long idDocumentoAdjunto) {
        this.idDocumentoAdjunto = idDocumentoAdjunto;
    }

//    public Long getIdTipoDocumento() {
//        return idTipoDocumento;
//    }
//
//    public void setIdTipoDocumento(Long idTipoDocumento) {
//        this.idTipoDocumento = idTipoDocumento;
//    }

    public MdiMaestroColumna getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(MdiMaestroColumna idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getDescripcionDocumento() {
        return descripcionDocumento;
    }

    public void setDescripcionDocumento(String descripcionDocumento) {
        this.descripcionDocumento = descripcionDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public byte[] getArchivoAdjunto() {
        return archivoAdjunto;
    }

    public void setArchivoAdjunto(byte[] archivoAdjunto) {
        this.archivoAdjunto = archivoAdjunto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PghDetalleSupervision getIdDetalleSupervision() {
        return idDetalleSupervision;
    }

    public void setIdDetalleSupervision(PghDetalleSupervision idDetalleSupervision) {
        this.idDetalleSupervision = idDetalleSupervision;
    }

    public PghSupervision getIdSupervision() {
        return idSupervision;
    }

    public void setIdSupervision(PghSupervision idSupervision) {
        this.idSupervision = idSupervision;
    }

    public String getFlagEnviadoSiged() {
        return flagEnviadoSiged;
    }

    public void setFlagEnviadoSiged(String flagEnviadoSiged) {
        this.flagEnviadoSiged = flagEnviadoSiged;
    }   
    /* OSINE_SFS-791 - RSIS 33 - Inicio */ 
    public PghDetalleLevantamiento getIdDetalleLevantamiento() {
		return idDetalleLevantamiento;
	}

	public void setIdDetalleLevantamiento(PghDetalleLevantamiento idDetalleLevantamiento) {
		this.idDetalleLevantamiento = idDetalleLevantamiento;
	}
	/* OSINE_SFS-791 - RSIS 33 - Fin */ 
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idDocumentoAdjunto != null ? idDocumentoAdjunto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghDocumentoAdjunto)) {
            return false;
        }
        PghDocumentoAdjunto other = (PghDocumentoAdjunto) object;
        if ((this.idDocumentoAdjunto == null && other.idDocumentoAdjunto != null) || (this.idDocumentoAdjunto != null && !this.idDocumentoAdjunto.equals(other.idDocumentoAdjunto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghDocumentoAdjunto[ idDocumentoAdjunto=" + idDocumentoAdjunto + " ]";
    }
}
