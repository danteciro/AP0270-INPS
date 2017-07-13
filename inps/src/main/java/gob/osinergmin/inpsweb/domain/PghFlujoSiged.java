/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;

import java.io.Serializable;
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
@Table(name = "PGH_FLUJO_SIGED")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghFlujoSiged.findAll", query = "SELECT p FROM PghFlujoSiged p"),
    })
public class PghFlujoSiged extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_FLUJO_SIGED")
    private Long idFlujoSiged;
    @Size(max = 500)
    @Column(name = "NOMBRE_FLUJO_SIGED")
    private String nombreFlujoSiged;
    @Column(name = "CODIGO_SIGED")
    private Long codigoSiged;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private Character estado;
    @OneToMany(mappedBy = "idFlujoSiged", fetch = FetchType.LAZY)
    private List<PghExpediente> pghExpedienteList;

    public PghFlujoSiged() {
    }

    public PghFlujoSiged(Long idFlujoSiged) {
        this.idFlujoSiged = idFlujoSiged;
    }

    public PghFlujoSiged(Long idFlujoSiged, String usuarioCreacion, Date fechaCreacion, String terminalCreacion, Character estado) {
        this.idFlujoSiged = idFlujoSiged;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
        this.estado = estado;
    }
    
    public PghFlujoSiged(Long idFlujoSiged,String nombreFlujoSiged) {
        this.idFlujoSiged = idFlujoSiged;
        this.nombreFlujoSiged=nombreFlujoSiged;
    }

    public Long getIdFlujoSiged() {
        return idFlujoSiged;
    }

    public void setIdFlujoSiged(Long idFlujoSiged) {
        this.idFlujoSiged = idFlujoSiged;
    }

    public String getNombreFlujoSiged() {
        return nombreFlujoSiged;
    }

    public void setNombreFlujoSiged(String nombreFlujoSiged) {
        this.nombreFlujoSiged = nombreFlujoSiged;
    }

    public Long getCodigoSiged() {
        return codigoSiged;
    }

    public void setCodigoSiged(Long codigoSiged) {
        this.codigoSiged = codigoSiged;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    @XmlTransient
    @JsonIgnore
    public List<PghExpediente> getPghExpedienteList() {
        return pghExpedienteList;
    }

    public void setPghExpedienteList(List<PghExpediente> pghExpedienteList) {
        this.pghExpedienteList = pghExpedienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFlujoSiged != null ? idFlujoSiged.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghFlujoSiged)) {
            return false;
        }
        PghFlujoSiged other = (PghFlujoSiged) object;
        if ((this.idFlujoSiged == null && other.idFlujoSiged != null) || (this.idFlujoSiged != null && !this.idFlujoSiged.equals(other.idFlujoSiged))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inps.domain.PghFlujoSiged[ idFlujoSiged=" + idFlujoSiged + " ]";
    }
    
}
