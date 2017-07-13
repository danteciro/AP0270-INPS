/**
*
* Resumen		
* Objeto		: PghInfraccion.java
* Descripcion		: 
* Fecha de Creacion	: 
* PR de Creacion	: 
* Autor			: 
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripcion
* =====================================================================================================================================================================
* OSINE_MAN_DSR_0025       05/07/2017     Carlos Quijano Chavez::ADAPTER          Agregar la Columna Codigo y eliminar prioridad
* 
* 
*/
package gob.osinergmin.inpsweb.domain;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "PGH_INFRACCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghInfraccion.findAll", query = "SELECT p FROM PghInfraccion p"),
    /*OSINE_SFS-791 - RSIS 16 - Inicio*/
    @NamedQuery(name = "PghInfraccion.findByIdInfraccion", query = "SELECT p FROM PghInfraccion p where estado='1' and p.idInfraccion=:idInfraccion ")
    /*OSINE_SFS-791 - RSIS 16 - Fin*/    
})
public class PghInfraccion extends Auditoria {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_INFRACCION")
    private Long idInfraccion;
    @Size(max = 500)
    @Column(name = "DESCRIPCION_INFRACCION")
    private String descripcionInfraccion;
    @Column(name = "ID_MEDIDA_SEGURIDAD_MAESTRO")
    private Long idMedidaSeguridadMaestro;
    @Column(name = "ID_ACCION_MAESTRO")
    private Long idAccionMaestro;
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
    @JoinColumn(name = "ID_OBLIGACION", referencedColumnName = "ID_OBLIGACION")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PghObligacion idObligacion;
    @JoinColumn(name = "ID_DOCUMENTO_ADJUNTO", referencedColumnName = "ID_DOCUMENTO_ADJUNTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiDocumentoAdjunto idDocumentoAdjunto;
    @Size(max = 20)
    @Column(name = "CODIGO")
    private String codigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idInfraccion", fetch = FetchType.LAZY)
    private List<PghEscenarioIncumplimiento> pghEscenarioIncumplimientoList;
    @OneToMany(mappedBy = "idInfraccion", fetch = FetchType.LAZY)
    private List<PghComentarioIncumplimiento> pghComentarioIncumplimientoList;
    @OneToMany(mappedBy = "idInfraccion", fetch = FetchType.LAZY)
    private List<PghDetalleSupervision> pghDetalleSupervisionList;

    public PghInfraccion() {
    }

    public PghInfraccion(Long idInfraccion) {
        this.idInfraccion = idInfraccion;
    }
    /* OSINE_MAN_DSR_0025 - Inicio */
    public PghInfraccion(String codigo) {
        this.codigo = codigo;
    }
    /* OSINE_MAN_DSR_0025 - Fin */
    public PghInfraccion(Long idInfraccion,String descripcionInfraccion,String nombreArchivoInfraccion,String rutaAlfrescoInfraccion) {
        this.idInfraccion = idInfraccion;
        this.descripcionInfraccion=descripcionInfraccion;
        this.idDocumentoAdjunto=new MdiDocumentoAdjunto(null,nombreArchivoInfraccion,rutaAlfrescoInfraccion);
    }

    public PghInfraccion(Long idInfraccion, String estado, String usuarioCreacion, Date fechaCreacion, String terminalCreacion) {
        this.idInfraccion = idInfraccion;
        this.estado = estado;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
    }

    public Long getIdInfraccion() {
        return idInfraccion;
    }

    public void setIdInfraccion(Long idInfraccion) {
        this.idInfraccion = idInfraccion;
    }

    public String getDescripcionInfraccion() {
        return descripcionInfraccion;
    }

    public void setDescripcionInfraccion(String descripcionInfraccion) {
        this.descripcionInfraccion = descripcionInfraccion;
    }

    public Long getIdMedidaSeguridadMaestro() {
        return idMedidaSeguridadMaestro;
    }

    public void setIdMedidaSeguridadMaestro(Long idMedidaSeguridadMaestro) {
        this.idMedidaSeguridadMaestro = idMedidaSeguridadMaestro;
    }

    public Long getIdAccionMaestro() {
        return idAccionMaestro;
    }

    public void setIdAccionMaestro(Long idAccionMaestro) {
        this.idAccionMaestro = idAccionMaestro;
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

    public PghObligacion getIdObligacion() {
        return idObligacion;
    }

    public void setIdObligacion(PghObligacion idObligacion) {
        this.idObligacion = idObligacion;
    }

    public MdiDocumentoAdjunto getIdDocumentoAdjunto() {
        return idDocumentoAdjunto;
    }

    public void setIdDocumentoAdjunto(MdiDocumentoAdjunto idDocumentoAdjunto) {
        this.idDocumentoAdjunto = idDocumentoAdjunto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    @XmlTransient
    public List<PghEscenarioIncumplimiento> getPghEscenarioIncumplimientoList() {
        return pghEscenarioIncumplimientoList;
    }

    public void setPghEscenarioIncumplimientoList(List<PghEscenarioIncumplimiento> pghEscenarioIncumplimientoList) {
        this.pghEscenarioIncumplimientoList = pghEscenarioIncumplimientoList;
    }
    
    @XmlTransient
    public List<PghComentarioIncumplimiento> getPghComentarioIncumplimientoList() {
        return pghComentarioIncumplimientoList;
    }

    public void setPghComentarioIncumplimientoList(List<PghComentarioIncumplimiento> pghComentarioIncumplimientoList) {
        this.pghComentarioIncumplimientoList = pghComentarioIncumplimientoList;
    }
    
    @XmlTransient
    public List<PghDetalleSupervision> getPghDetalleSupervisionList() {
        return pghDetalleSupervisionList;
    }

    public void setPghDetalleSupervisionList(List<PghDetalleSupervision> pghDetalleSupervisionList) {
        this.pghDetalleSupervisionList = pghDetalleSupervisionList;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInfraccion != null ? idInfraccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghInfraccion)) {
            return false;
        }
        PghInfraccion other = (PghInfraccion) object;
        if ((this.idInfraccion == null && other.idInfraccion != null) || (this.idInfraccion != null && !this.idInfraccion.equals(other.idInfraccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghInfraccion[ idInfraccion=" + idInfraccion + " ]";
    }
    
}
