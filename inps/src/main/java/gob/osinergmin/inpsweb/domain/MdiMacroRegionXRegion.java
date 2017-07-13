/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;

import java.util.Date;
import java.util.List;

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
 * @author jpiro
 * 
 */
@Entity
@Table(name = "MDI_MACRO_REGION_X_REGION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdiMacroRegionXRegion.findAll", query = "SELECT m FROM MdiMacroRegionXRegion m")
})
public class MdiMacroRegionXRegion extends Auditoria {
    @EmbeddedId
    protected MdiMacroRegionXRegionPK mdiMacroRegionXRegionPK;
    @OneToMany(mappedBy = "mdiMacroRegionXRegion", fetch = FetchType.LAZY)
    private List<MdiLocadorDestaque> mdiLocadorDestaqueList;
    @JoinColumn(name = "ID_REGION", referencedColumnName = "ID_REGION", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MdiRegion mdiRegion;
    @JoinColumn(name = "ID_MACRO_REGION", referencedColumnName = "ID_MACRO_REGION", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MdiMacroRegion mdiMacroRegion;

    public MdiMacroRegionXRegion() {
    }

    public MdiMacroRegionXRegion(MdiMacroRegionXRegionPK mdiMacroRegionXRegionPK) {
        this.mdiMacroRegionXRegionPK = mdiMacroRegionXRegionPK;
    }
    
    public MdiMacroRegionXRegion(MdiMacroRegionXRegionPK mdiMacroRegionXRegionPK, Date fechaCreacion, String terminalCreacion, String usuarioCreacion) {
        this.mdiMacroRegionXRegionPK = mdiMacroRegionXRegionPK;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
        this.usuarioCreacion = usuarioCreacion;
    }

    public MdiMacroRegionXRegion(long idMacroRegion, long idRegion) {
        this.mdiMacroRegionXRegionPK = new MdiMacroRegionXRegionPK(idMacroRegion, idRegion);
    }

    public MdiMacroRegionXRegionPK getMdiMacroRegionXRegionPK() {
        return mdiMacroRegionXRegionPK;
    }

    public void setMdiMacroRegionXRegionPK(MdiMacroRegionXRegionPK mdiMacroRegionXRegionPK) {
        this.mdiMacroRegionXRegionPK = mdiMacroRegionXRegionPK;
    }

    @XmlTransient
    public List<MdiLocadorDestaque> getMdiLocadorDestaqueList() {
        return mdiLocadorDestaqueList;
    }

    public void setMdiLocadorDestaqueList(List<MdiLocadorDestaque> mdiLocadorDestaqueList) {
        this.mdiLocadorDestaqueList = mdiLocadorDestaqueList;
    }

    public MdiRegion getMdiRegion() {
        return mdiRegion;
    }

    public void setMdiRegion(MdiRegion mdiRegion) {
        this.mdiRegion = mdiRegion;
    }

    public MdiMacroRegion getMdiMacroRegion() {
        return mdiMacroRegion;
    }

    public void setMdiMacroRegion(MdiMacroRegion mdiMacroRegion) {
        this.mdiMacroRegion = mdiMacroRegion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mdiMacroRegionXRegionPK != null ? mdiMacroRegionXRegionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdiMacroRegionXRegion)) {
            return false;
        }
        MdiMacroRegionXRegion other = (MdiMacroRegionXRegion) object;
        if ((this.mdiMacroRegionXRegionPK == null && other.mdiMacroRegionXRegionPK != null) || (this.mdiMacroRegionXRegionPK != null && !this.mdiMacroRegionXRegionPK.equals(other.mdiMacroRegionXRegionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.MdiMacroRegionXRegion[ mdiMacroRegionXRegionPK=" + mdiMacroRegionXRegionPK + " ]";
    }
    
}
