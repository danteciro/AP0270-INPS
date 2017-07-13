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
 * @author jcsar
 */
@Entity
@Table(name = "MDI_PRODUCTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdiProducto.findAll", query = "SELECT m FROM MdiProducto m")
})
public class MdiProducto extends Auditoria {
    @Column(name = "DEDICADO")
    private String dedicado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "NOMBRE_LARGO")
    private String nombreLargo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "NOMBRE_CORTO")
    private String nombreCorto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "CODIGO")
    private String codigo;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PRODUCTO")
    private Long idProducto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NIVEL")
    private int nivel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @Size(max = 200)
    @Column(name = "IDENTIFICADOR")
    private String identificador;
    @OneToMany(mappedBy = "idProducto", fetch = FetchType.LAZY)
    private List<PghProductoSuspender> pghProductoSuspenderList;
    @OneToMany(mappedBy = "idProductoPadre", fetch = FetchType.LAZY)
    private List<MdiProducto> mdiProductoList;
    @JoinColumn(name = "ID_PRODUCTO_PADRE", referencedColumnName = "ID_PRODUCTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiProducto idProductoPadre;
    @Column(name = "TIPO_PRODUCTO")
    private Long idTipoProducto;

    public MdiProducto() {
    }

    public MdiProducto(Long idProducto) {
        this.idProducto = idProducto;
    }
    public MdiProducto(Long idProducto,String nombreLargo) {
        this.idProducto = idProducto;
        this.nombreLargo = nombreLargo;
    }

    public MdiProducto(Long idProducto, String nombreLargo, String nombreCorto, String codigo, Date fechaCreacion, String usuarioCreacion, String terminalCreacion, int nivel, String estado) {
        this.idProducto = idProducto;
        this.nombreLargo = nombreLargo;
        this.nombreCorto = nombreCorto;
        this.codigo = codigo;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.terminalCreacion = terminalCreacion;
        this.nivel = nivel;
        this.estado = estado;
    }

    public String getDedicado() {
        return dedicado;
    }

    public void setDedicado(String dedicado) {
        this.dedicado = dedicado;
    }

    public String getNombreLargo() {
        return nombreLargo;
    }

    public void setNombreLargo(String nombreLargo) {
        this.nombreLargo = nombreLargo;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    @XmlTransient
    public List<PghProductoSuspender> getPghProductoSuspenderList() {
        return pghProductoSuspenderList;
    }

    public void setPghProductoSuspenderList(List<PghProductoSuspender> pghProductoSuspenderList) {
        this.pghProductoSuspenderList = pghProductoSuspenderList;
    }

    @XmlTransient
    public List<MdiProducto> getMdiProductoList() {
        return mdiProductoList;
    }

    public void setMdiProductoList(List<MdiProducto> mdiProductoList) {
        this.mdiProductoList = mdiProductoList;
    }

    public MdiProducto getIdProductoPadre() {
        return idProductoPadre;
    }

    public void setIdProductoPadre(MdiProducto idProductoPadre) {
        this.idProductoPadre = idProductoPadre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdiProducto)) {
            return false;
        }
        MdiProducto other = (MdiProducto) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domain.MdiProducto[ idProducto=" + idProducto + " ]";
    }

    /**
     * @return the idTipoProducto
     */
    public Long getIdTipoProducto() {
        return idTipoProducto;
    }

    /**
     * @param idTipoProducto the idTipoProducto to set
     */
    public void setIdTipoProducto(Long idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }
    
}
