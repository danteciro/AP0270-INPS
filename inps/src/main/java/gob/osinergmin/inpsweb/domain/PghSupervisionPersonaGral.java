/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rsantosv
 */
@Entity
@Table(name = "PGH_SUPERVISION_PERSONA_GRAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghSupervisionPersonaGral.findAll", query = "SELECT p FROM PghSupervisionPersonaGral p"),
    @NamedQuery(name = "PghSupervisionPersonaGral.findByIdSupervision", query = "SELECT p FROM PghSupervisionPersonaGral p WHERE p.pghSupervisionPersonaGralPK.idSupervision = :idSupervision"),
    @NamedQuery(name = "PghSupervisionPersonaGral.findByIdPersonaGeneral", query = "SELECT p FROM PghSupervisionPersonaGral p WHERE p.pghSupervisionPersonaGralPK.idPersonaGeneral = :idPersonaGeneral")})
public class PghSupervisionPersonaGral extends Auditoria {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PghSupervisionPersonaGralPK pghSupervisionPersonaGralPK;
    @JoinColumn(name = "CARGO")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiMaestroColumna cargo;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_PERSONA_GENERAL", referencedColumnName = "ID_PERSONA_GENERAL", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private PghPersonaGeneral pghPersonaGeneral;
    @JoinColumn(name = "ID_SUPERVISION", referencedColumnName = "ID_SUPERVISION", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private PghSupervision pghSupervision;
    @Column(name = "CORREO")
    private String correo;

    public PghSupervisionPersonaGral() {
    }

    public PghSupervisionPersonaGral(PghSupervisionPersonaGralPK pghSupervisionPersonaGralPK) {
        this.pghSupervisionPersonaGralPK = pghSupervisionPersonaGralPK;
    }

    public PghSupervisionPersonaGral(PghSupervisionPersonaGralPK pghSupervisionPersonaGralPK, String estado, String usuarioCreacion, Date fechaCreacion, String terminalCreacion) {
        this.pghSupervisionPersonaGralPK = pghSupervisionPersonaGralPK;
        this.estado = estado;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
    }

    public PghSupervisionPersonaGral(PghSupervisionPersonaGralPK pghSupervisionPersonaGralPK, String estado, String usuarioCreacion, Date fechaCreacion, String terminalCreacion, String correo) {
        this.pghSupervisionPersonaGralPK = pghSupervisionPersonaGralPK;
        this.estado = estado;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
        this.correo = correo;
    }

    public PghSupervisionPersonaGral(long idSupervision, long idPersonaGeneral) {
        this.pghSupervisionPersonaGralPK = new PghSupervisionPersonaGralPK(idSupervision, idPersonaGeneral);
    }

    public PghSupervisionPersonaGralPK getPghSupervisionPersonaGralPK() {
        return pghSupervisionPersonaGralPK;
    }

    public void setPghSupervisionPersonaGralPK(PghSupervisionPersonaGralPK pghSupervisionPersonaGralPK) {
        this.pghSupervisionPersonaGralPK = pghSupervisionPersonaGralPK;
    }

    public MdiMaestroColumna getCargo() {
        return cargo;
    }

    public void setCargo(MdiMaestroColumna cargo) {
        this.cargo = cargo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PghPersonaGeneral getPghPersonaGeneral() {
        return pghPersonaGeneral;
    }

    public void setPghPersonaGeneral(PghPersonaGeneral pghPersonaGeneral) {
        this.pghPersonaGeneral = pghPersonaGeneral;
    }

    public PghSupervision getPghSupervision() {
        return pghSupervision;
    }

    public void setPghSupervision(PghSupervision pghSupervision) {
        this.pghSupervision = pghSupervision;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pghSupervisionPersonaGralPK != null ? pghSupervisionPersonaGralPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghSupervisionPersonaGral)) {
            return false;
        }
        PghSupervisionPersonaGral other = (PghSupervisionPersonaGral) object;
        if ((this.pghSupervisionPersonaGralPK == null && other.pghSupervisionPersonaGralPK != null) || (this.pghSupervisionPersonaGralPK != null && !this.pghSupervisionPersonaGralPK.equals(other.pghSupervisionPersonaGralPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghSupervisionPersonaGral[ pghSupervisionPersonaGralPK=" + pghSupervisionPersonaGralPK + " ]";
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
