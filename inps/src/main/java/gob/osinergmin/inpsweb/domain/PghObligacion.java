/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
@Table(name = "PGH_OBLIGACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghObligacion.findAll", query = "SELECT p FROM PghObligacion p"),
    @NamedQuery(name = "PghObligacion.findByIdObligacion", query = "SELECT p FROM PghObligacion p WHERE p.idObligacion = :idObligacion")})
public class PghObligacion extends Auditoria {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_OBLIGACION")
    private Long idObligacion;
    @Column(name = "CODIGO_OBLIGACION")
    private String codigoObligacion;
    @Column(name = "FECHA_ENTRADA_VIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntradaVigencia;
    @Column(name = "FECHA_TERMINO_VIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaTerminoVigencia;
    @Column(name = "ID_TIPO_CREACION")
    private Long idTipoCreacion;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ID_CRITICIDAD")
    private Long idCriticidad;
    @Column(name = "RUTA")
    private String ruta;
    @Column(name = "ID_OBLIGACION_REF")
    private Long idObligacionRef;
    @Column(name = "ESTADO")
    private Character estado;
    @Column(name = "ID_PADRE")
    private BigInteger idPadre;
    @Column(name = "COD_TRAZABILIDAD")
    private String codTrazabilidad;
    @Column(name = "COD_ACCION")
    private String codAccion;
    @JoinColumn(name = "ID_DOCUMENTO_ADJUNTO", referencedColumnName = "ID_DOCUMENTO_ADJUNTO")
    @ManyToOne
    private MdiDocumentoAdjunto idDocumentoAdjunto;
    @OneToMany(mappedBy = "idObligacion", fetch = FetchType.LAZY)
    private List<PghConfObligacion> pghConfObligacionList;
    @OneToMany(mappedBy = "idObligacion", fetch = FetchType.LAZY)
    private List<PghObligacionBaseLegal> pghObligacionBaseLegalList;
    @OneToMany(mappedBy = "idObligacion", fetch = FetchType.LAZY)
    private List<PghObligacionTipificacion> pghObligacionTipificacionList;
//    @OneToMany(mappedBy = "idObligacion",fetch = FetchType.LAZY)
//    private List<PghCriterio> pghCriterioList;
    @OneToMany(mappedBy = "idObligacion", fetch = FetchType.LAZY)
    private List<PghObliTipiCriterio> pghObliTipiCriterioList;
    @OneToMany(mappedBy = "idObligacion")
    private List<PghTemaObligacionMaestro> pghTemaObligacionMaestroList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idObligacion", fetch = FetchType.LAZY)
    private List<PghInfraccion> pghInfraccionList;
    /* OSINE_SFS-791 - RSIS 16 - Inicio */
//    @Transient
//    private PghInfraccion infraccion;
    /* OSINE_SFS-791 - RSIS 16 - Fin */

    public PghObligacion() {
    }

    public PghObligacion(Long idObligacion) {
        this.idObligacion = idObligacion;
    }
    
//    public PghObligacion(Long idObligacion, PghInfraccion infraccion) {
//        this.idObligacion = idObligacion;
//        this.infraccion=infraccion;
//    }

    public PghObligacion(Long idObligacion, String codigoObligacion, String descripcion) {
        this.idObligacion = idObligacion;
        this.codigoObligacion = codigoObligacion;
        this.descripcion = descripcion;
    }
    /* OSINE_SFS-791 - RSIS 16 - Inicio */
//    public PghInfraccion getInfraccion() {
//        return infraccion;
//    }
//
//    public void setInfraccion(PghInfraccion infraccion) {
//        this.infraccion = infraccion;
//    }
    /* OSINE_SFS-791 - RSIS 16 - Fin */
    public Long getIdObligacion() {
        return idObligacion;
    }

    public void setIdObligacion(Long idObligacion) {
        this.idObligacion = idObligacion;
    }

    public String getCodigoObligacion() {
        return codigoObligacion;
    }

    public void setCodigoObligacion(String codigoObligacion) {
        this.codigoObligacion = codigoObligacion;
    }

    public Date getFechaEntradaVigencia() {
        return fechaEntradaVigencia;
    }

    public void setFechaEntradaVigencia(Date fechaEntradaVigencia) {
        this.fechaEntradaVigencia = fechaEntradaVigencia;
    }

    public Date getFechaTerminoVigencia() {
        return fechaTerminoVigencia;
    }

    public void setFechaTerminoVigencia(Date fechaTerminoVigencia) {
        this.fechaTerminoVigencia = fechaTerminoVigencia;
    }

    public Long getIdTipoCreacion() {
        return idTipoCreacion;
    }

    public void setIdTipoCreacion(Long idTipoCreacion) {
        this.idTipoCreacion = idTipoCreacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdCriticidad() {
        return idCriticidad;
    }

    public void setIdCriticidad(Long idCriticidad) {
        this.idCriticidad = idCriticidad;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Long getIdObligacionRef() {
        return idObligacionRef;
    }

    public void setIdObligacionRef(Long idObligacionRef) {
        this.idObligacionRef = idObligacionRef;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
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

    public MdiDocumentoAdjunto getIdDocumentoAdjunto() {
        return idDocumentoAdjunto;
    }

    public void setIdDocumentoAdjunto(MdiDocumentoAdjunto idDocumentoAdjunto) {
        this.idDocumentoAdjunto = idDocumentoAdjunto;
    }

    @XmlTransient
    public List<PghConfObligacion> getPghConfObligacionList() {
        return pghConfObligacionList;
    }

    public void setPghConfObligacionList(List<PghConfObligacion> pghConfObligacionList) {
        this.pghConfObligacionList = pghConfObligacionList;
    }

    @XmlTransient
    public List<PghObligacionBaseLegal> getPghObligacionBaseLegalList() {
        return pghObligacionBaseLegalList;
    }

    public void setPghObligacionBaseLegalList(List<PghObligacionBaseLegal> pghObligacionBaseLegalList) {
        this.pghObligacionBaseLegalList = pghObligacionBaseLegalList;
    }

    @XmlTransient
    public List<PghObligacionTipificacion> getPghObligacionTipificacionList() {
        return pghObligacionTipificacionList;
    }

    public void setPghObligacionTipificacionList(
            List<PghObligacionTipificacion> pghObligacionTipificacionList) {
        this.pghObligacionTipificacionList = pghObligacionTipificacionList;
    }

//	@XmlTransient
//	public List<PghCriterio> getPghCriterioList() {
//		return pghCriterioList;
//	}
//
//	public void setPghCriterioList(List<PghCriterio> pghCriterioList) {
//		this.pghCriterioList = pghCriterioList;
//	}
    @XmlTransient
    public List<PghObliTipiCriterio> getPghObliTipiCriterioList() {
        return pghObliTipiCriterioList;
    }

    public void setPghObliTipiCriterioList(List<PghObliTipiCriterio> pghObliTipiCriterioList) {
        this.pghObliTipiCriterioList = pghObliTipiCriterioList;
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
        hash += (idObligacion != null ? idObligacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PghObligacion)) {
            return false;
        }
        PghObligacion other = (PghObligacion) object;
        if ((this.idObligacion == null && other.idObligacion != null) || (this.idObligacion != null && !this.idObligacion.equals(other.idObligacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.domain.PghObligacion[ idObligacion=" + idObligacion + " ]";
    }

    @XmlTransient
    public List<PghTemaObligacionMaestro> getPghTemaObligacionMaestroList() {
        return pghTemaObligacionMaestroList;
    }

    public void setPghTemaObligacionMaestroList(List<PghTemaObligacionMaestro> pghTemaObligacionMaestroCollection) {
        this.pghTemaObligacionMaestroList = pghTemaObligacionMaestroCollection;
    }
}
