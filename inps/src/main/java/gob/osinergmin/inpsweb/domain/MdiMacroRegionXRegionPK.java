/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author jpiro
 * 
 */
@Embeddable
public class MdiMacroRegionXRegionPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_MACRO_REGION", nullable = false)
    private Long idMacroRegion;
    @Basic(optional = false)
    @Column(name = "ID_REGION", nullable = false)
    private Long idRegion;

    public MdiMacroRegionXRegionPK() {
    }

    public MdiMacroRegionXRegionPK(Long idMacroRegion, Long idRegion) {
        this.idMacroRegion = idMacroRegion;
        this.idRegion = idRegion;
    }

    public Long getIdMacroRegion() {
        return idMacroRegion;
    }

    public void setIdMacroRegion(Long idMacroRegion) {
        this.idMacroRegion = idMacroRegion;
    }

    public Long getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Long idRegion) {
        this.idRegion = idRegion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (Long) idMacroRegion;
        hash += (Long) idRegion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdiMacroRegionXRegionPK)) {
            return false;
        }
        MdiMacroRegionXRegionPK other = (MdiMacroRegionXRegionPK) object;
        if (this.idMacroRegion != other.idMacroRegion) {
            return false;
        }
        if (this.idRegion != other.idRegion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.MdiMacroRegionXRegionPK[ idMacroRegion=" + idMacroRegion + ", idRegion=" + idRegion + " ]";
    }
    
}
