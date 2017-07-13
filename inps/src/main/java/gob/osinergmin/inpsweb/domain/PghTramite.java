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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "PGH_TRAMITE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghTramite.findAll", query = "SELECT p FROM PghTramite p"),
    @NamedQuery(name = "PghTramite.findByIdEtapa", query = "SELECT p FROM PghTramite p where p.estado='1' and p.idEtapa.idEtapa=:idEtapa ")
})
public class PghTramite extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TRAMITE")
    private Long idTramite;
    @Size(max = 500)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ESTADO")
    private Character estado;
    @OneToMany(mappedBy = "idTramite", fetch = FetchType.LAZY)
    private List<PghProcedimientoTramite> pghProcedimientoTramiteList;
    @OneToMany(mappedBy = "idTramite", fetch = FetchType.LAZY)
    private List<PghExpediente> pghExpedienteList;
    @JoinColumn(name = "ID_ETAPA", referencedColumnName = "ID_ETAPA")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghEtapa idEtapa;

    public PghTramite() {
    }

    public PghTramite(Long idTramite) {
        this.idTramite = idTramite;
    }
    
    public PghTramite(Long idTramite,Long idEtapa,Long idProceso) {
        this.idTramite = idTramite;
        this.idEtapa=new PghEtapa(idEtapa,idProceso);
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    @XmlTransient
    @JsonIgnore
    public List<PghProcedimientoTramite> getPghProcedimientoTramiteList() {
        return pghProcedimientoTramiteList;
    }

    public void setPghProcedimientoTramiteList(List<PghProcedimientoTramite> pghProcedimientoTramiteList) {
        this.pghProcedimientoTramiteList = pghProcedimientoTramiteList;
    }

    @XmlTransient
    @JsonIgnore
    public List<PghExpediente> getPghExpedienteList() {
        return pghExpedienteList;
    }

    public void setPghExpedienteList(List<PghExpediente> pghExpedienteList) {
        this.pghExpedienteList = pghExpedienteList;
    }

    public PghEtapa getIdEtapa() {
        return idEtapa;
    }

    public void setIdEtapa(PghEtapa idEtapa) {
        this.idEtapa = idEtapa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTramite != null ? idTramite.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghTramite)) {
            return false;
        }
        PghTramite other = (PghTramite) object;
        if ((this.idTramite == null && other.idTramite != null) || (this.idTramite != null && !this.idTramite.equals(other.idTramite))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inps.domain.PghTramite[ idTramite=" + idTramite + " ]";
    }
    
}
