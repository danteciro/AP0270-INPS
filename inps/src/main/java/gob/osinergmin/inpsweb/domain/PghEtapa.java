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
@Table(name = "PGH_ETAPA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghEtapa.findAll", query = "SELECT p FROM PghEtapa p"),
    @NamedQuery(name = "PghEtapa.findByIdProceso", query = "SELECT p FROM PghEtapa p WHERE p.idProceso.idProceso = :idProceso and p.estado='1'")
})
public class PghEtapa extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ETAPA")
    private Long idEtapa;
    @Size(max = 500)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_PROCESO", referencedColumnName = "ID_PROCESO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghProceso idProceso;
    @OneToMany(mappedBy = "idEtapa", fetch = FetchType.LAZY)
    private List<PghTramite> pghTramiteList;

    public PghEtapa() {
    }

    public PghEtapa(Long idEtapa) {
        this.idEtapa = idEtapa;
    }
    
    public PghEtapa(Long idEtapa,Long idProceso) {
        this.idEtapa = idEtapa;
        this.idProceso=new PghProceso(idProceso);
    }

    public Long getIdEtapa() {
        return idEtapa;
    }

    public void setIdEtapa(Long idEtapa) {
        this.idEtapa = idEtapa;
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

    public PghProceso getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(PghProceso idProceso) {
        this.idProceso = idProceso;
    }

    @XmlTransient
    @JsonIgnore
    public List<PghTramite> getPghTramiteList() {
        return pghTramiteList;
    }

    public void setPghTramiteList(List<PghTramite> pghTramiteList) {
        this.pghTramiteList = pghTramiteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEtapa != null ? idEtapa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghEtapa)) {
            return false;
        }
        PghEtapa other = (PghEtapa) object;
        if ((this.idEtapa == null && other.idEtapa != null) || (this.idEtapa != null && !this.idEtapa.equals(other.idEtapa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inps.domain.PghEtapa[ idEtapa=" + idEtapa + " ]";
    }
    
}
