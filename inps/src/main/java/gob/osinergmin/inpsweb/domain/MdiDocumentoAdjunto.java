/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jpaucar
 */
@Entity
@Table(name = "MDI_DOCUMENTO_ADJUNTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdiDocumentoAdjunto.findAll", query = "SELECT m FROM MdiDocumentoAdjunto m"),
    @NamedQuery(name = "MdiDocumentoAdjunto.findByIdDocumentoAdjunto", query = "SELECT m FROM MdiDocumentoAdjunto m WHERE m.idDocumentoAdjunto = :idDocumentoAdjunto"),
    @NamedQuery(name = "MdiDocumentoAdjunto.findByNombreArchivo", query = "SELECT m FROM MdiDocumentoAdjunto m WHERE m.nombreArchivo = :nombreArchivo"),
    @NamedQuery(name = "MdiDocumentoAdjunto.findByTitulo", query = "SELECT m FROM MdiDocumentoAdjunto m WHERE m.titulo = :titulo"),
    @NamedQuery(name = "MdiDocumentoAdjunto.findByComentario", query = "SELECT m FROM MdiDocumentoAdjunto m WHERE m.comentario = :comentario"),
    @NamedQuery(name = "MdiDocumentoAdjunto.findByFechaCarga", query = "SELECT m FROM MdiDocumentoAdjunto m WHERE m.fechaCarga = :fechaCarga"),
    @NamedQuery(name = "MdiDocumentoAdjunto.findByFechaDocumento", query = "SELECT m FROM MdiDocumentoAdjunto m WHERE m.fechaDocumento = :fechaDocumento"),
    @NamedQuery(name = "MdiDocumentoAdjunto.findByUsuarioCreacion", query = "SELECT m FROM MdiDocumentoAdjunto m WHERE m.usuarioCreacion = :usuarioCreacion"),
    @NamedQuery(name = "MdiDocumentoAdjunto.findByFechaCreacion", query = "SELECT m FROM MdiDocumentoAdjunto m WHERE m.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "MdiDocumentoAdjunto.findByTerminalCreacion", query = "SELECT m FROM MdiDocumentoAdjunto m WHERE m.terminalCreacion = :terminalCreacion"),
    @NamedQuery(name = "MdiDocumentoAdjunto.findByUsuarioActualizacion", query = "SELECT m FROM MdiDocumentoAdjunto m WHERE m.usuarioActualizacion = :usuarioActualizacion"),
    @NamedQuery(name = "MdiDocumentoAdjunto.findByFechaActualizacion", query = "SELECT m FROM MdiDocumentoAdjunto m WHERE m.fechaActualizacion = :fechaActualizacion"),
    @NamedQuery(name = "MdiDocumentoAdjunto.findByTipoDocumentoCarga", query = "SELECT m FROM MdiDocumentoAdjunto m WHERE m.tipoDocumentoCarga = :tipoDocumentoCarga"),
    @NamedQuery(name = "MdiDocumentoAdjunto.findByTerminalActualizacion", query = "SELECT m FROM MdiDocumentoAdjunto m WHERE m.terminalActualizacion = :terminalActualizacion"),
    @NamedQuery(name = "MdiDocumentoAdjunto.findByFechaCaptura", query = "SELECT m FROM MdiDocumentoAdjunto m WHERE m.fechaCaptura = :fechaCaptura"),
    @NamedQuery(name = "MdiDocumentoAdjunto.findByIdUnidadSupervisada", query = "SELECT m FROM MdiDocumentoAdjunto m WHERE m.idUnidadSupervisada = :idUnidadSupervisada"),
    @NamedQuery(name = "MdiDocumentoAdjunto.findByRutaAlfresco", query = "SELECT m FROM MdiDocumentoAdjunto m WHERE m.rutaAlfresco = :rutaAlfresco"),
    @NamedQuery(name = "MdiDocumentoAdjunto.findByEstado", query = "SELECT m FROM MdiDocumentoAdjunto m WHERE m.estado = :estado"),
    @NamedQuery(name = "MdiDocumentoAdjunto.findByTipoProceso", query = "SELECT m FROM MdiDocumentoAdjunto m WHERE m.tipoProceso = :tipoProceso"),
    @NamedQuery(name = "MdiDocumentoAdjunto.findByNroDocumento", query = "SELECT m FROM MdiDocumentoAdjunto m WHERE m.nroDocumento = :nroDocumento"),
    @NamedQuery(name = "MdiDocumentoAdjunto.findByFotoPrincipal", query = "SELECT m FROM MdiDocumentoAdjunto m WHERE m.fotoPrincipal = :fotoPrincipal")})
public class MdiDocumentoAdjunto extends Auditoria {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DOCUMENTO_ADJUNTO")
    private Long idDocumentoAdjunto;
    @Column(name = "NOMBRE_ARCHIVO")
    private String nombreArchivo;
    @Column(name = "TITULO")
    private String titulo;
    @Column(name = "COMENTARIO")
    private String comentario;
    @Column(name = "FECHA_CARGA")
    @Temporal(TemporalType.DATE)
    private Date fechaCarga;
    @Column(name = "FECHA_DOCUMENTO")
    @Temporal(TemporalType.DATE)
    private Date fechaDocumento;
    @Column(name = "TIPO_DOCUMENTO_CARGA")
    private Long tipoDocumentoCarga;
    @Column(name = "FECHA_CAPTURA")
    @Temporal(TemporalType.DATE)
    private Date fechaCaptura;
    @Column(name = "ID_UNIDAD_SUPERVISADA")
    private Long idUnidadSupervisada;
    @Column(name = "RUTA_ALFRESCO")
    private String rutaAlfresco;
    @Column(name = "ESTADO")
    private Character estado;
    @Column(name = "TIPO_PROCESO")
    private Long tipoProceso;
    @Column(name = "NRO_DOCUMENTO")
    private String nroDocumento;
    @Column(name = "FOTO_PRINCIPAL")
    private Character fotoPrincipal;
    @OneToMany(mappedBy = "idDocumentoAdjunto")
    private Collection<PghObligacion> pghObligacionCollection;
    @OneToMany(mappedBy = "idDocumentoAdjunto", fetch = FetchType.LAZY)
    private List<PghInfraccion> pghInfraccionList;

    public MdiDocumentoAdjunto() {
    }

    public MdiDocumentoAdjunto(Long idDocumentoAdjunto) {
        this.idDocumentoAdjunto = idDocumentoAdjunto;
    }
    
    public MdiDocumentoAdjunto(Long idDocumentoAdjunto,String nombreArchivoInfraccion,String rutaAlfrescoInfraccion) {
        this.idDocumentoAdjunto = idDocumentoAdjunto;
        this.nombreArchivo=nombreArchivoInfraccion;
        this.rutaAlfresco=rutaAlfrescoInfraccion;
    }

    public Long getIdDocumentoAdjunto() {
        return idDocumentoAdjunto;
    }

    public void setIdDocumentoAdjunto(Long idDocumentoAdjunto) {
        this.idDocumentoAdjunto = idDocumentoAdjunto;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public Long getTipoDocumentoCarga() {
        return tipoDocumentoCarga;
    }

    public void setTipoDocumentoCarga(Long tipoDocumentoCarga) {
        this.tipoDocumentoCarga = tipoDocumentoCarga;
    }

    public Date getFechaCaptura() {
        return fechaCaptura;
    }

    public void setFechaCaptura(Date fechaCaptura) {
        this.fechaCaptura = fechaCaptura;
    }

    public Long getIdUnidadSupervisada() {
        return idUnidadSupervisada;
    }

    public void setIdUnidadSupervisada(Long idUnidadSupervisada) {
        this.idUnidadSupervisada = idUnidadSupervisada;
    }

    public String getRutaAlfresco() {
        return rutaAlfresco;
    }

    public void setRutaAlfresco(String rutaAlfresco) {
        this.rutaAlfresco = rutaAlfresco;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public Long getTipoProceso() {
        return tipoProceso;
    }

    public void setTipoProceso(Long tipoProceso) {
        this.tipoProceso = tipoProceso;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public Character getFotoPrincipal() {
        return fotoPrincipal;
    }

    public void setFotoPrincipal(Character fotoPrincipal) {
        this.fotoPrincipal = fotoPrincipal;
    }

    @XmlTransient
    public Collection<PghObligacion> getPghObligacionCollection() {
        return pghObligacionCollection;
    }

    public void setPghObligacionCollection(Collection<PghObligacion> pghObligacionCollection) {
        this.pghObligacionCollection = pghObligacionCollection;
    }

    @XmlTransient
    public List<PghInfraccion> getPghInfraccionList() {
        return pghInfraccionList;
    }

    public void setPghInfraccionList(List<PghInfraccion> pghInfraccionList) {
        this.pghInfraccionList = pghInfraccionList;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDocumentoAdjunto != null ? idDocumentoAdjunto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdiDocumentoAdjunto)) {
            return false;
        }
        MdiDocumentoAdjunto other = (MdiDocumentoAdjunto) object;
        if ((this.idDocumentoAdjunto == null && other.idDocumentoAdjunto != null) || (this.idDocumentoAdjunto != null && !this.idDocumentoAdjunto.equals(other.idDocumentoAdjunto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nuevo.domain.MdiDocumentoAdjunto[ idDocumentoAdjunto=" + idDocumentoAdjunto + " ]";
    }
    
}
