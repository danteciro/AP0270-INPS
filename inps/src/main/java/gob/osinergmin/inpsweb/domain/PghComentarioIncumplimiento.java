/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "PGH_COMENTARIO_INCUMPLIMIENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghComentarioIncumplimiento.findByIdEsceIncumplimientoIdInfraccion", 
            query = "SELECT new PghComentarioIncumplimiento(ci.idComentarioIncumplimiento,ci.descripcion,"
                    + "(SELECT count(cc.idComentarioComplemento) from PghComentarioComplemento cc where cc.estado=1 and cc.idComentarioIncumplimiento.idComentarioIncumplimiento=ci.idComentarioIncumplimiento) as countComentarioComplemento ) "
                    + "FROM PghComentarioIncumplimiento ci where ci.estado=1 and ci.idEsceIncumplimiento.idEsceIncumplimiento=:idEsceIncumplimiento and ci.idInfraccion.idInfraccion=:idInfraccion "),
    @NamedQuery(name = "PghComentarioIncumplimiento.findByIdEsceIncumplimiento", 
            query = "SELECT new PghComentarioIncumplimiento(ci.idComentarioIncumplimiento,ci.descripcion,"
                    + "(SELECT count(cc.idComentarioComplemento) from PghComentarioComplemento cc where cc.estado=1 and cc.idComentarioIncumplimiento.idComentarioIncumplimiento=ci.idComentarioIncumplimiento) as countComentarioComplemento ) "
                    + "FROM PghComentarioIncumplimiento ci where ci.estado=1 and ci.idEsceIncumplimiento.idEsceIncumplimiento=:idEsceIncumplimiento "),
    @NamedQuery(name = "PghComentarioIncumplimiento.findByIdInfraccion", 
            query = "SELECT new PghComentarioIncumplimiento(ci.idComentarioIncumplimiento,ci.descripcion,"
                    + "(SELECT count(cc.idComentarioComplemento) from PghComentarioComplemento cc where cc.estado=1 and cc.idComentarioIncumplimiento.idComentarioIncumplimiento=ci.idComentarioIncumplimiento) as countComentarioComplemento ) "
                    + "FROM PghComentarioIncumplimiento ci where ci.estado=1 and ci.idInfraccion.idInfraccion=:idInfraccion and ci.idEsceIncumplimiento.idEsceIncumplimiento is null "),
    @NamedQuery(name = "PghComentarioIncumplimiento.findAll", query = "SELECT p FROM PghComentarioIncumplimiento p")
})
public class PghComentarioIncumplimiento extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_COMENTARIO_INCUMPLIMIENTO")
    private Long idComentarioIncumplimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4000)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_INFRACCION", referencedColumnName = "ID_INFRACCION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghInfraccion idInfraccion;
    @JoinColumn(name = "ID_ESCE_INCUMPLIMIENTO", referencedColumnName = "ID_ESCE_INCUMPLIMIENTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghEscenarioIncumplimiento idEsceIncumplimiento;
    @OneToMany(mappedBy = "idComentarioIncumplimiento", fetch = FetchType.LAZY)
    private List<PghComentarioComplemento> pghComentarioComplementoList;
    @OneToMany(mappedBy = "idComentarioIncumplimiento", fetch = FetchType.LAZY)
    private List<PghComentarioDetsupervision> pghComentarioDetsupervisionList;
    
    @Transient
    private Long countComentarioComplemento;

    public PghComentarioIncumplimiento() {
    }

    public PghComentarioIncumplimiento(Long idComentarioIncumplimiento) {
        this.idComentarioIncumplimiento = idComentarioIncumplimiento;
    }
    public PghComentarioIncumplimiento(Long idComentarioIncumplimiento,String dscripcion) {
        this.idComentarioIncumplimiento = idComentarioIncumplimiento;
        this.descripcion = dscripcion;
    }
    
    public PghComentarioIncumplimiento(Long idComentarioIncumplimiento,String descripcion, Long countComentarioComplemento) {
        this.idComentarioIncumplimiento = idComentarioIncumplimiento;
        this.descripcion=descripcion;
        this.countComentarioComplemento=countComentarioComplemento;
    }

    public Long getIdComentarioIncumplimiento() {
        return idComentarioIncumplimiento;
    }

    public void setIdComentarioIncumplimiento(Long idComentarioIncumplimiento) {
        this.idComentarioIncumplimiento = idComentarioIncumplimiento;
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

    public PghInfraccion getIdInfraccion() {
        return idInfraccion;
    }

    public void setIdInfraccion(PghInfraccion idInfraccion) {
        this.idInfraccion = idInfraccion;
    }

    public PghEscenarioIncumplimiento getIdEsceIncumplimiento() {
        return idEsceIncumplimiento;
    }

    public void setIdEsceIncumplimiento(PghEscenarioIncumplimiento idEsceIncumplimiento) {
        this.idEsceIncumplimiento = idEsceIncumplimiento;
    }

    public Long getCountComentarioComplemento() {
        return countComentarioComplemento;
    }

    public void setCountComentarioComplemento(Long countComentarioComplemento) {
        this.countComentarioComplemento = countComentarioComplemento;
    }

    @XmlTransient
    public List<PghComentarioComplemento> getPghComentarioComplementoList() {
        return pghComentarioComplementoList;
    }

    public void setPghComentarioComplementoList(List<PghComentarioComplemento> pghComentarioComplementoList) {
        this.pghComentarioComplementoList = pghComentarioComplementoList;
    }
    
    @XmlTransient
    public List<PghComentarioDetsupervision> getPghComentarioDetsupervisionList() {
        return pghComentarioDetsupervisionList;
    }

    public void setPghComentarioDetsupervisionList(List<PghComentarioDetsupervision> pghComentarioDetsupervisionList) {
        this.pghComentarioDetsupervisionList = pghComentarioDetsupervisionList;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComentarioIncumplimiento != null ? idComentarioIncumplimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghComentarioIncumplimiento)) {
            return false;
        }
        PghComentarioIncumplimiento other = (PghComentarioIncumplimiento) object;
        if ((this.idComentarioIncumplimiento == null && other.idComentarioIncumplimiento != null) || (this.idComentarioIncumplimiento != null && !this.idComentarioIncumplimiento.equals(other.idComentarioIncumplimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mavenproject2.PghComentarioIncumplimiento[ idComentarioIncumplimiento=" + idComentarioIncumplimiento + " ]";
    }
    
}
