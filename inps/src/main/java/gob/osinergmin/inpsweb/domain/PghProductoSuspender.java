/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "PGH_PRODUCTO_SUSPENDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghProductoSuspender.findAll", query = "SELECT p FROM PghProductoSuspender p")})
public class PghProductoSuspender extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PRODUCTO_SUSPENDER")
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "PGH_PRODUCTO_SUSPENDER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idProductoSuspender;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_DETALLE_SUPERVISION", referencedColumnName = "ID_DETALLE_SUPERVISION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghDetalleSupervision idDetalleSupervision;
    @JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiProducto idProducto;

    public PghProductoSuspender() {
    }

    public PghProductoSuspender(Long idProductoSuspender) {
        this.idProductoSuspender = idProductoSuspender;
    }
    public PghProductoSuspender(Long idProducto, String nombrlargo) {
       this.idProducto = new MdiProducto(idProducto,nombrlargo);
    }

    public PghProductoSuspender(Long idProductoSuspender, String estado, String usuarioCreacion, Date fechaCreacion, String terminalCreacion) {
        this.idProductoSuspender = idProductoSuspender;
        this.estado = estado;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
    }

    public Long getIdProductoSuspender() {
        return idProductoSuspender;
    }

    public void setIdProductoSuspender(Long idProductoSuspender) {
        this.idProductoSuspender = idProductoSuspender;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PghDetalleSupervision getIdDetalleSupervision() {
        return idDetalleSupervision;
    }

    public void setIdDetalleSupervision(PghDetalleSupervision idDetalleSupervision) {
        this.idDetalleSupervision = idDetalleSupervision;
    }

    public MdiProducto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(MdiProducto idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProductoSuspender != null ? idProductoSuspender.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghProductoSuspender)) {
            return false;
        }
        PghProductoSuspender other = (PghProductoSuspender) object;
        if ((this.idProductoSuspender == null && other.idProductoSuspender != null) || (this.idProductoSuspender != null && !this.idProductoSuspender.equals(other.idProductoSuspender))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghProductoSuspender[ idProductoSuspender=" + idProductoSuspender + " ]";
    }
    
}
