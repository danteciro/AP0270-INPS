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

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "PGH_OPCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghOpcion.findAll", query = "SELECT p FROM PghOpcion p")
})
public class PghOpcion extends Auditoria {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_OPCION")
    private Long idOpcion;
    @Size(max = 50)
    @Column(name = "NOMBRE_OPCION")
    private String nombreOpcion;
    @Size(max = 250)
    @Column(name = "DESCRIPCION_OPCION")
    private String descripcionOpcion;
    @Column(name = "ESTADO")
    private String estado;
    @Size(max = 40)
    @Column(name = "IDENTIFICADOR_OPCION")
    private String identificadorOpcion;
    @Size(max = 500)
    @Column(name = "PAGE_OPCION")
    private String pageOpcion;
    @OneToMany(mappedBy = "idOpcion", fetch = FetchType.LAZY)
    private List<PghRolOpcion> pghRolOpcionList;

    public PghOpcion() {
    }

    public PghOpcion(Long idOpcion) {
        this.idOpcion = idOpcion;
    }

    public PghOpcion(Long idOpcion, String usuarioCreacion, Date fechaCreacion, String terminalCreacion) {
        this.idOpcion = idOpcion;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
    }

    public Long getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(Long idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getNombreOpcion() {
        return nombreOpcion;
    }

    public void setNombreOpcion(String nombreOpcion) {
        this.nombreOpcion = nombreOpcion;
    }

    public String getDescripcionOpcion() {
        return descripcionOpcion;
    }

    public void setDescripcionOpcion(String descripcionOpcion) {
        this.descripcionOpcion = descripcionOpcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdentificadorOpcion() {
        return identificadorOpcion;
    }

    public void setIdentificadorOpcion(String identificadorOpcion) {
        this.identificadorOpcion = identificadorOpcion;
    }

    public String getPageOpcion() {
        return pageOpcion;
    }

    public void setPageOpcion(String pageOpcion) {
        this.pageOpcion = pageOpcion;
    }

    @XmlTransient
    public List<PghRolOpcion> getPghRolOpcionList() {
        return pghRolOpcionList;
    }

    public void setPghRolOpcionList(List<PghRolOpcion> pghRolOpcionList) {
        this.pghRolOpcionList = pghRolOpcionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOpcion != null ? idOpcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghOpcion)) {
            return false;
        }
        PghOpcion other = (PghOpcion) object;
        if ((this.idOpcion == null && other.idOpcion != null) || (this.idOpcion != null && !this.idOpcion.equals(other.idOpcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.descargagmd.PghOpcion[ idOpcion=" + idOpcion + " ]";
    }
    
}
