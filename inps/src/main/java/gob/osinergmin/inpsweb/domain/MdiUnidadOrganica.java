/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "MDI_UNIDAD_ORGANICA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdiUnidadOrganica.findAll", query = "SELECT m FROM MdiUnidadOrganica m where estado=1"),
    @NamedQuery(name = "MdiUnidadOrganica.findByIdUnidadOrganica", query = "SELECT m FROM MdiUnidadOrganica m where estado=1 and m.idUnidadOrganica=:idUnidadOrganica "),
    @NamedQuery(name = "MdiUnidadOrganica.findByIdUnidadOrganicaSuperior", query = "SELECT m FROM MdiUnidadOrganica m where estado=1 and m.idUnidadOrganicaSuperior=:idUnidadOrganicaSuperior ")
})
public class MdiUnidadOrganica extends Auditoria{
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_UNIDAD_ORGANICA")
    private Long idUnidadOrganica;
    @Column(name = "ID_UNIDAD_ORGANICA_SUPERIOR")
    private Long idUnidadOrganicaSuperior;
    @Column(name = "SEDE")
    private BigInteger sede;
    @Size(max = 100)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 200)
    @Column(name = "DETALLE")
    private String detalle;
    @Size(max = 4)
    @Column(name = "COD_DEP_SIGA")
    private String codDepSiga;
    @Column(name = "ID_TIPO_UNIDAD_ORGANICA")
    private BigInteger idTipoUnidadOrganica;
    @Size(max = 15)
    @Column(name = "SIGLA")
    private String sigla;
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(mappedBy = "idUnidadOrganica", fetch = FetchType.LAZY)
    private List<PghPersonalUnidadOrganica> pghPersonalUnidadOrganicaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUnidadOrganica", fetch = FetchType.LAZY)
    private List<NpsCnfUnidOrga> npsCnfUnidOrgaList;
    /*OSINE_SFS-1344 - Inicio */
    @OneToMany(mappedBy = "idUnidadOrganica", fetch = FetchType.LAZY)
    private List<PghObliTipoUnidOrga> pghObliTipoUnidOrgaList;
    /*OSINE_SFS-1344 - Fin */
    
    public MdiUnidadOrganica() {
    }

    public MdiUnidadOrganica(Long idUnidadOrganica) {
        this.idUnidadOrganica = idUnidadOrganica;
    }

    public Long getIdUnidadOrganica() {
        return idUnidadOrganica;
    }

    public void setIdUnidadOrganica(Long idUnidadOrganica) {
        this.idUnidadOrganica = idUnidadOrganica;
    }

    public Long getIdUnidadOrganicaSuperior() {
        return idUnidadOrganicaSuperior;
    }

    public void setIdUnidadOrganicaSuperior(Long idUnidadOrganicaSuperior) {
        this.idUnidadOrganicaSuperior = idUnidadOrganicaSuperior;
    }

    public BigInteger getSede() {
        return sede;
    }

    public void setSede(BigInteger sede) {
        this.sede = sede;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getCodDepSiga() {
        return codDepSiga;
    }

    public void setCodDepSiga(String codDepSiga) {
        this.codDepSiga = codDepSiga;
    }

    public BigInteger getIdTipoUnidadOrganica() {
        return idTipoUnidadOrganica;
    }

    public void setIdTipoUnidadOrganica(BigInteger idTipoUnidadOrganica) {
        this.idTipoUnidadOrganica = idTipoUnidadOrganica;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @XmlTransient
    public List<PghObliTipoUnidOrga> getPghObliTipoUnidOrgaList() {
		return pghObliTipoUnidOrgaList;
	}

	public void setPghObliTipoUnidOrgaList(List<PghObliTipoUnidOrga> pghObliTipoUnidOrgaList) {
		this.pghObliTipoUnidOrgaList = pghObliTipoUnidOrgaList;
	}

	@XmlTransient
    public List<PghPersonalUnidadOrganica> getPghPersonalUnidadOrganicaList() {
        return pghPersonalUnidadOrganicaList;
    }

    public void setPghPersonalUnidadOrganicaList(List<PghPersonalUnidadOrganica> pghPersonalUnidadOrganicaList) {
        this.pghPersonalUnidadOrganicaList = pghPersonalUnidadOrganicaList;
    }
    
    @XmlTransient
    public List<NpsCnfUnidOrga> getNpsCnfUnidOrgaList() {
        return npsCnfUnidOrgaList;
    }

    public void setNpsCnfUnidOrgaList(List<NpsCnfUnidOrga> npsCnfUnidOrgaList) {
        this.npsCnfUnidOrgaList = npsCnfUnidOrgaList;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUnidadOrganica != null ? idUnidadOrganica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdiUnidadOrganica)) {
            return false;
        }
        MdiUnidadOrganica other = (MdiUnidadOrganica) object;
        if ((this.idUnidadOrganica == null && other.idUnidadOrganica != null) || (this.idUnidadOrganica != null && !this.idUnidadOrganica.equals(other.idUnidadOrganica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inps.domain.MdiUnidadOrganica[ idUnidadOrganica=" + idUnidadOrganica + " ]";
    }
    
}
