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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "PGH_COMENTARIO_COMPLEMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghComentarioComplemento.findAll", query = "SELECT p FROM PghComentarioComplemento p")
})
public class PghComentarioComplemento extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_COMENTARIO_COMPLEMENTO")
    private Long idComentarioComplemento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_COMPLEMENTO", referencedColumnName = "ID_COMPLEMENTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghComplemento idComplemento;
    @JoinColumn(name = "ID_COMENTARIO_INCUMPLIMIENTO", referencedColumnName = "ID_COMENTARIO_INCUMPLIMIENTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghComentarioIncumplimiento idComentarioIncumplimiento;
    @OneToMany(mappedBy = "idComentarioComplemento", fetch = FetchType.LAZY)
    private List<PghComplementoDetsupervision> pghComplementoDetsupervisionList;

    public PghComentarioComplemento() {
    }
    
    public PghComentarioComplemento(Long idComentarioComplemento) {
        this.idComentarioComplemento = idComentarioComplemento;
    }
    
    public PghComentarioComplemento(Long idComentarioComplemento,Long idComplemento,String etiquetaComentario) {
        this.idComentarioComplemento = idComentarioComplemento;
        PghComplemento complemento=new PghComplemento();
        complemento.setIdComplemento(idComplemento);
        complemento.setEtiquetaComentario(etiquetaComentario);
        this.idComplemento=complemento;
        
    }

    public PghComentarioComplemento(Long idComentarioComplemento,Long idComplemento,String descripcion,String etiqueta,String etiquetaComentario,
            String validacion,Long idMaestroColumnaCT,String descripcionCT,String codigoCT,String formato,Long longitud,Float valorMaximo,Float valorMinimo,
            Long idMaestroColumnaOD,String descripcionOD,String codigoOD,String dominioMaestroColumna,String aplicacionMaestroColumna,String vistaNombre,
            String vistaCampoId,String vistaCampoDesc,Long idMaestroColumnaVCF,String descripcionVCF,String codigoVCF,String aplicacionVCF) {
        this.idComentarioComplemento=idComentarioComplemento;
        PghComplemento complemento=new PghComplemento();
        complemento.setIdComplemento(idComplemento);
        complemento.setDescripcion(descripcion);
        complemento.setEtiqueta(etiqueta);
        complemento.setEtiquetaComentario(etiquetaComentario);
        complemento.setValidacion(validacion);
        complemento.setCodTipo(new MdiMaestroColumna(idMaestroColumnaCT, descripcionCT, codigoCT));
        complemento.setFormato(formato);
        complemento.setLongitud(longitud);
        complemento.setValorMaximo(valorMaximo);
        complemento.setValorMinimo(valorMinimo);
        complemento.setOrigenDatos(new MdiMaestroColumna(idMaestroColumnaOD, descripcionOD, codigoOD));
        complemento.setDominioMaestroColumna(dominioMaestroColumna);
        complemento.setAplicacionMaestroColumna(aplicacionMaestroColumna);
        complemento.setVistaNombre(vistaNombre);
        complemento.setVistaCampoId(vistaCampoId);
        complemento.setVistaCampoDesc(vistaCampoDesc);
        complemento.setVistaCampoFiltro(new MdiMaestroColumna(idMaestroColumnaVCF, descripcionVCF, codigoVCF, aplicacionVCF));
        this.idComplemento=complemento;
    }
    
    public Long getIdComentarioComplemento() {
        return idComentarioComplemento;
    }

    public void setIdComentarioComplemento(Long idComentarioComplemento) {
        this.idComentarioComplemento = idComentarioComplemento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PghComplemento getIdComplemento() {
        return idComplemento;
    }

    public void setIdComplemento(PghComplemento idComplemento) {
        this.idComplemento = idComplemento;
    }

    public PghComentarioIncumplimiento getIdComentarioIncumplimiento() {
        return idComentarioIncumplimiento;
    }

    public void setIdComentarioIncumplimiento(PghComentarioIncumplimiento idComentarioIncumplimiento) {
        this.idComentarioIncumplimiento = idComentarioIncumplimiento;
    }

    @XmlTransient
    public List<PghComplementoDetsupervision> getPghComplementoDetsupervisionList() {
        return pghComplementoDetsupervisionList;
    }

    public void setPghComplementoDetsupervisionList(List<PghComplementoDetsupervision> pghComplementoDetsupervisionList) {
        this.pghComplementoDetsupervisionList = pghComplementoDetsupervisionList;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComentarioComplemento != null ? idComentarioComplemento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghComentarioComplemento)) {
            return false;
        }
        PghComentarioComplemento other = (PghComentarioComplemento) object;
        if ((this.idComentarioComplemento == null && other.idComentarioComplemento != null) || (this.idComentarioComplemento != null && !this.idComentarioComplemento.equals(other.idComentarioComplemento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghComentarioComplemento[ idComentarioComplemento=" + idComentarioComplemento + " ]";
    }
    
}
