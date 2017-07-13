/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;

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
@Table(name = "PGH_ESTADO_PROCESO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghEstadoProceso.findAll", query = "SELECT p FROM PghEstadoProceso p"),
    @NamedQuery(name = "PghEstadoProceso.findByIdentificadorEstado", query = "SELECT p FROM PghEstadoProceso p where p.estado='1' and p.identificadorEstado=:identificadorEstado"),
    @NamedQuery(name = "PghEstadoProceso.findByIdTipoEstadoProceso", query = "SELECT p FROM PghEstadoProceso p where p.estado='1' and p.idTipoEstadoProceso=:idTipoEstadoProceso")
})
public class PghEstadoProceso extends Auditoria {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(mappedBy = "idEstadoProceso", fetch = FetchType.LAZY)
    private List<PghHistoricoEstado> pghHistoricoEstadoList;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ESTADO_PROCESO")
    private Long idEstadoProceso;
    @Size(max = 20)
    @Column(name = "NOMBRE_ESTADO")
    private String nombreEstado;
    @Column(name = "ID_TIPO_ESTADO_PROCESO")
    private Long idTipoEstadoProceso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "IDENTIFICADOR_ESTADO")
    private String identificadorEstado;
    @OneToMany(mappedBy = "idEstadoProceso", fetch = FetchType.LAZY)
    private List<PghExpediente> pghExpedienteList;
    @OneToMany(mappedBy = "idEstadoProceso", fetch = FetchType.LAZY)
    private List<PghOrdenServicio> pghOrdenServicioList;
    /* OSINE791 - RSIS 21 - Inicio */
    @OneToMany(mappedBy = "idEstadoProcesoSgt", fetch = FetchType.LAZY)
    private List<PghFlujoEstEstadoProc> pghFlujoEstEstadoProcList;
    @OneToMany(mappedBy = "idEstadoProceso", fetch = FetchType.LAZY)
    private List<PghFlujoEstEstadoProc> pghFlujoEstEstadoProcList1;
    /* OSINE791 - RSIS 21 - Fin */
    
    public PghEstadoProceso() {
    }

    public PghEstadoProceso(Long idEstadoProceso) {
        this.idEstadoProceso = idEstadoProceso;
    }

    public PghEstadoProceso(Long idEstadoProceso, String estado, String usuarioCreacion, Date fechaCreacion, String terminalCreacion, String identificadorEstado) {
        this.idEstadoProceso = idEstadoProceso;
        this.estado = estado;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
        this.identificadorEstado = identificadorEstado;
    }

    public PghEstadoProceso(Long idEstadoProceso, String identificadorEstado,String nombreEstado) {
        this.idEstadoProceso = idEstadoProceso;
        this.identificadorEstado = identificadorEstado;
        this.nombreEstado = nombreEstado;
    }
    
    public Long getIdEstadoProceso() {
        return idEstadoProceso;
    }

    public void setIdEstadoProceso(Long idEstadoProceso) {
        this.idEstadoProceso = idEstadoProceso;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }


    public Long getIdTipoEstadoProceso() {
        return idTipoEstadoProceso;
    }

    public void setIdTipoEstadoProceso(Long idTipoEstadoProceso) {
        this.idTipoEstadoProceso = idTipoEstadoProceso;
    }

    public String getIdentificadorEstado() {
        return identificadorEstado;
    }

    public void setIdentificadorEstado(String identificadorEstado) {
        this.identificadorEstado = identificadorEstado;
    }

    @XmlTransient
    @JsonIgnore
    public List<PghExpediente> getPghExpedienteList() {
        return pghExpedienteList;
    }

    public void setPghExpedienteList(List<PghExpediente> pghExpedienteList) {
        this.pghExpedienteList = pghExpedienteList;
    }

    @XmlTransient
    @JsonIgnore
    public List<PghOrdenServicio> getPghOrdenServicioList() {
        return pghOrdenServicioList;
    }

    public void setPghOrdenServicioList(List<PghOrdenServicio> pghOrdenServicioList) {
        this.pghOrdenServicioList = pghOrdenServicioList;
    }
    
    @XmlTransient
    public List<PghFlujoEstEstadoProc> getPghFlujoEstEstadoProcList() {
        return pghFlujoEstEstadoProcList;
    }

    public void setPghFlujoEstEstadoProcList(List<PghFlujoEstEstadoProc> pghFlujoEstEstadoProcList) {
        this.pghFlujoEstEstadoProcList = pghFlujoEstEstadoProcList;
    }

    @XmlTransient
    public List<PghFlujoEstEstadoProc> getPghFlujoEstEstadoProcList1() {
        return pghFlujoEstEstadoProcList1;
    }

    public void setPghFlujoEstEstadoProcList1(List<PghFlujoEstEstadoProc> pghFlujoEstEstadoProcList1) {
        this.pghFlujoEstEstadoProcList1 = pghFlujoEstEstadoProcList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadoProceso != null ? idEstadoProceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghEstadoProceso)) {
            return false;
        }
        PghEstadoProceso other = (PghEstadoProceso) object;
        if ((this.idEstadoProceso == null && other.idEstadoProceso != null) || (this.idEstadoProceso != null && !this.idEstadoProceso.equals(other.idEstadoProceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inps.domain.PghEstadoProceso[ idEstadoProceso=" + idEstadoProceso + " ]";
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    @JsonIgnore
    public List<PghHistoricoEstado> getPghHistoricoEstadoList() {
        return pghHistoricoEstadoList;
    }

    public void setPghHistoricoEstadoList(List<PghHistoricoEstado> pghHistoricoEstadoList) {
        this.pghHistoricoEstadoList = pghHistoricoEstadoList;
    }
    
}
