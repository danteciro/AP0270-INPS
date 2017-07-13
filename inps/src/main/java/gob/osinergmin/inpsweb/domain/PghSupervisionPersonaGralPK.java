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
 * @author rsantosv
 */
@Embeddable
public class PghSupervisionPersonaGralPK implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "ID_SUPERVISION")
    private long idSupervision;
    @Basic(optional = false)
    @Column(name = "ID_PERSONA_GENERAL")
    private long idPersonaGeneral;

    public PghSupervisionPersonaGralPK() {
    }

    public PghSupervisionPersonaGralPK(long idSupervision, long idPersonaGeneral) {
        this.idSupervision = idSupervision;
        this.idPersonaGeneral = idPersonaGeneral;
    }

    public long getIdSupervision() {
        return idSupervision;
    }

    public void setIdSupervision(long idSupervision) {
        this.idSupervision = idSupervision;
    }

    public long getIdPersonaGeneral() {
        return idPersonaGeneral;
    }

    public void setIdPersonaGeneral(long idPersonaGeneral) {
        this.idPersonaGeneral = idPersonaGeneral;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idSupervision;
        hash += (int) idPersonaGeneral;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghSupervisionPersonaGralPK)) {
            return false;
        }
        PghSupervisionPersonaGralPK other = (PghSupervisionPersonaGralPK) object;
        if (this.idSupervision != other.idSupervision) {
            return false;
        }
        if (this.idPersonaGeneral != other.idPersonaGeneral) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghSupervisionPersonaGralPK[ idSupervision=" + idSupervision + ", idPersonaGeneral=" + idPersonaGeneral + " ]";
    }
}
