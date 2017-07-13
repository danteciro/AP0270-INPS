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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author jcsar
 */
@Entity
@Table(name = "PGH_RESULTADO_SUPERVISION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghResultadoSupervision.findAll", query = "SELECT p FROM PghResultadoSupervision p")
})
public class PghResultadoSupervision extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_RESULTADO_SUPERVISION")
    private Long idResultadoSupervision;
    @Size(max = 20)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 20)
    @Column(name = "CSS_ICONO")
    private String cssIcono;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "CODIGO")
    private Long codigo;
    @Column(name = "ID_NIVEL_RESULTADO_MAESTRO")
    private Long idNivelResultadoMaestro;
    @OneToMany(mappedBy = "idResultadoSupervisionAnt", fetch = FetchType.LAZY)
    private List<PghDetalleSupervision> pghDetalleSupervisionList;
    @OneToMany(mappedBy = "idResultadoSupervision", fetch = FetchType.LAZY)
    private List<PghDetalleSupervision> pghDetalleSupervisionList1;
    @OneToMany(mappedBy = "idResultadoSupervision", fetch = FetchType.LAZY)
    private List<PghSupervision> pghSupervisionList;
    @OneToMany(mappedBy = "idResultSupervInicial", fetch = FetchType.LAZY)
    private List<PghSupervision> pghSupervisionList1;
    
    public PghResultadoSupervision() {
    }

    public PghResultadoSupervision(Long idResultadoSupervision) {
        this.idResultadoSupervision = idResultadoSupervision;
    }

    public PghResultadoSupervision(Long idResultadoSupervision, String estado, Date fechaCreacion, String usuarioCreacion, String terminalCreacion) {
        this.idResultadoSupervision = idResultadoSupervision;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.terminalCreacion = terminalCreacion;
    }
    
    public PghResultadoSupervision(Long idResultadoSupervision, Long codigo) {
        this.idResultadoSupervision = idResultadoSupervision;
        this.codigo=codigo;
    }
    
    public PghResultadoSupervision(Long idResultadoSupervision, String descripcion,String cssicono,String estado,Long codigo) {
        this.idResultadoSupervision = idResultadoSupervision;
        this.descripcion = descripcion;
        this.cssIcono = cssicono;
        this.estado = estado;
        this.codigo = codigo;
    }
    
    public PghResultadoSupervision(String cssIcono,String descripcion,Long codigo) {
        this.cssIcono = cssIcono;
        this.descripcion=descripcion;
        this.codigo=codigo;
    }

    public Long getIdResultadoSupervision() {
        return idResultadoSupervision;
    }

    public void setIdResultadoSupervision(Long idResultadoSupervision) {
        this.idResultadoSupervision = idResultadoSupervision;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCssIcono() {
        return cssIcono;
    }

    public void setCssIcono(String cssIcono) {
        this.cssIcono = cssIcono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Long getIdNivelResultadoMaestro() {
        return idNivelResultadoMaestro;
    }

    public void setIdNivelResultadoMaestro(Long idNivelResultadoMaestro) {
        this.idNivelResultadoMaestro = idNivelResultadoMaestro;
    }

    @XmlTransient
    public List<PghDetalleSupervision> getPghDetalleSupervisionList() {
        return pghDetalleSupervisionList;
    }

    public void setPghDetalleSupervisionList(List<PghDetalleSupervision> pghDetalleSupervisionList) {
        this.pghDetalleSupervisionList = pghDetalleSupervisionList;
    }

    @XmlTransient
    public List<PghDetalleSupervision> getPghDetalleSupervisionList1() {
        return pghDetalleSupervisionList1;
    }

    public void setPghDetalleSupervisionList1(List<PghDetalleSupervision> pghDetalleSupervisionList1) {
        this.pghDetalleSupervisionList1 = pghDetalleSupervisionList1;
    }

    @XmlTransient
    @JsonIgnore
    public List<PghSupervision> getPghSupervisionList() {
        return pghSupervisionList;
    }

    public void setPghSupervisionList(List<PghSupervision> pghSupervisionList) {
        this.pghSupervisionList = pghSupervisionList;
    }
    
    @XmlTransient
    @JsonIgnore
    public List<PghSupervision> getPghSupervisionList1() {
        return pghSupervisionList1;
    }

    public void setPghSupervisionList1(List<PghSupervision> pghSupervisionList1) {
        this.pghSupervisionList1 = pghSupervisionList1;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idResultadoSupervision != null ? idResultadoSupervision.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghResultadoSupervision)) {
            return false;
        }
        PghResultadoSupervision other = (PghResultadoSupervision) object;
        if ((this.idResultadoSupervision == null && other.idResultadoSupervision != null) || (this.idResultadoSupervision != null && !this.idResultadoSupervision.equals(other.idResultadoSupervision))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghResultadoSupervision[ idResultadoSupervision=" + idResultadoSupervision + " ]";
    }
    
}
