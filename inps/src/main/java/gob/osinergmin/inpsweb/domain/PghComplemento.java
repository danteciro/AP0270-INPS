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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "PGH_COMPLEMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghComplemento.findAll", query = "SELECT p FROM PghComplemento p")
})
public class PghComplemento extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_COMPLEMENTO")
    private Long idComplemento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 150)
    @Column(name = "ETIQUETA")
    private String etiqueta;
    @Size(max = 40)
    @Column(name = "ETIQUETA_COMENTARIO")
    private String etiquetaComentario;
    @Size(max = 20)
    @Column(name = "VALIDACION")
    private String validacion;
    @JoinColumn(name = "COD_TIPO")
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private MdiMaestroColumna codTipo;
    @Size(max = 20)
    @Column(name = "FORMATO")
    private String formato;
    @Column(name = "LONGITUD")
    private Long longitud;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR_MAXIMO")
    private Float valorMaximo;
    @Column(name = "VALOR_MINIMO")
    private Float valorMinimo;
    @JoinColumn(name = "ORIGEN_DATOS")
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private MdiMaestroColumna origenDatos;
    @Size(max = 20)
    @Column(name = "DOMINIO_MAESTRO_COLUMNA")
    private String dominioMaestroColumna;
    @Size(max = 25)
    @Column(name = "APLICACION_MAESTRO_COLUMNA")
    private String aplicacionMaestroColumna;
    @Size(max = 50)
    @Column(name = "VISTA_NOMBRE")
    private String vistaNombre;
    @Size(max = 50)
    @Column(name = "VISTA_CAMPO_ID")
    private String vistaCampoId;
    @Size(max = 50)
    @Column(name = "VISTA_CAMPO_DESC")
    private String vistaCampoDesc;
    @JoinColumn(name = "VISTA_CAMPO_FILTRO")
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private MdiMaestroColumna vistaCampoFiltro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(mappedBy = "idComplemento", fetch = FetchType.LAZY)
    private List<PghComentarioComplemento> pghComentarioComplementoList;

    public PghComplemento() {
    }

    public PghComplemento(Long idComplemento) {
        this.idComplemento = idComplemento;
    }

    public Long getIdComplemento() {
        return idComplemento;
    }

    public void setIdComplemento(Long idComplemento) {
        this.idComplemento = idComplemento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiquetaComentario() {
        return etiquetaComentario;
    }

    public void setEtiquetaComentario(String etiquetaComentario) {
        this.etiquetaComentario = etiquetaComentario;
    }

    public String getValidacion() {
        return validacion;
    }

    public void setValidacion(String validacion) {
        this.validacion = validacion;
    }

    public MdiMaestroColumna getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(MdiMaestroColumna codTipo) {
        this.codTipo = codTipo;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public Long getLongitud() {
        return longitud;
    }

    public void setLongitud(Long longitud) {
        this.longitud = longitud;
    }

    public Float getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(Float valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public Float getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(Float valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public MdiMaestroColumna getOrigenDatos() {
        return origenDatos;
    }

    public void setOrigenDatos(MdiMaestroColumna origenDatos) {
        this.origenDatos = origenDatos;
    }

    public String getDominioMaestroColumna() {
        return dominioMaestroColumna;
    }

    public void setDominioMaestroColumna(String dominioMaestroColumna) {
        this.dominioMaestroColumna = dominioMaestroColumna;
    }

    public String getAplicacionMaestroColumna() {
        return aplicacionMaestroColumna;
    }

    public void setAplicacionMaestroColumna(String aplicacionMaestroColumna) {
        this.aplicacionMaestroColumna = aplicacionMaestroColumna;
    }

    public String getVistaNombre() {
        return vistaNombre;
    }

    public void setVistaNombre(String vistaNombre) {
        this.vistaNombre = vistaNombre;
    }

    public String getVistaCampoId() {
        return vistaCampoId;
    }

    public void setVistaCampoId(String vistaCampoId) {
        this.vistaCampoId = vistaCampoId;
    }

    public String getVistaCampoDesc() {
        return vistaCampoDesc;
    }

    public void setVistaCampoDesc(String vistaCampoDesc) {
        this.vistaCampoDesc = vistaCampoDesc;
    }

    public MdiMaestroColumna getVistaCampoFiltro() {
        return vistaCampoFiltro;
    }

    public void setVistaCampoFiltro(MdiMaestroColumna vistaCampoFiltro) {
        this.vistaCampoFiltro = vistaCampoFiltro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<PghComentarioComplemento> getPghComentarioComplementoList() {
        return pghComentarioComplementoList;
    }

    public void setPghComentarioComplementoList(List<PghComentarioComplemento> pghComentarioComplementoList) {
        this.pghComentarioComplementoList = pghComentarioComplementoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComplemento != null ? idComplemento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghComplemento)) {
            return false;
        }
        PghComplemento other = (PghComplemento) object;
        if ((this.idComplemento == null && other.idComplemento != null) || (this.idComplemento != null && !this.idComplemento.equals(other.idComplemento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghComplemento[ idComplemento=" + idComplemento + " ]";
    }
    
}
