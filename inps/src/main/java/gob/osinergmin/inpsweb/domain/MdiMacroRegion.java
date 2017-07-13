/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jpiro
 * 
 */
@Entity
@Table(name = "MDI_MACRO_REGION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdiMacroRegion.findAll", query = "SELECT m FROM MdiMacroRegion m")})
public class MdiMacroRegion extends Auditoria {
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MACRO_REGION", nullable = false)
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "MDI_MACRO_REGION_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idMacroRegion;
    @Basic(optional = false)
    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;
    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "ESTADO", nullable = false)
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mdiMacroRegion", fetch = FetchType.LAZY)
    private List<MdiMacroRegionXRegion> mdiMacroRegionXRegionList;

    public MdiMacroRegion() {
    }

    public MdiMacroRegion(Long idMacroRegion) {
        this.idMacroRegion = idMacroRegion;
    }
    
    public MdiMacroRegion(Long idMacroRegion, String nombre) {
        this.idMacroRegion = idMacroRegion;
        this.nombre = nombre;
    }

    public MdiMacroRegion(Long idMacroRegion, String nombre, Date fechaCreacion, String terminalCreacion, String usuarioCreacion, String estado) {
        this.idMacroRegion = idMacroRegion;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.estado = estado;
    }

    public Long getIdMacroRegion() {
        return idMacroRegion;
    }

    public void setIdMacroRegion(Long idMacroRegion) {
        this.idMacroRegion = idMacroRegion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<MdiMacroRegionXRegion> getMdiMacroRegionXRegionList() {
        return mdiMacroRegionXRegionList;
    }

    public void setMdiMacroRegionXRegionList(List<MdiMacroRegionXRegion> mdiMacroRegionXRegionList) {
        this.mdiMacroRegionXRegionList = mdiMacroRegionXRegionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMacroRegion != null ? idMacroRegion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdiMacroRegion)) {
            return false;
        }
        MdiMacroRegion other = (MdiMacroRegion) object;
        if ((this.idMacroRegion == null && other.idMacroRegion != null) || (this.idMacroRegion != null && !this.idMacroRegion.equals(other.idMacroRegion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.MdiMacroRegion[ idMacroRegion=" + idMacroRegion + " ]";
    }
}