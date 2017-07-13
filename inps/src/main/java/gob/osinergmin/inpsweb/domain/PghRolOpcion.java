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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "PGH_ROL_OPCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghRolOpcion.findAll", query = "SELECT p FROM PghRolOpcion p where p.estado='1'"),
    // htorress - RSIS 1, 2 y 3 - Inicio
    //@NamedQuery(name = "PghRolOpcion.findByIdentificadorRol", query = "SELECT p FROM PghRolOpcion p where p.estado='1' and p.idRol.identificadorRol=:identificadorRol")
    @NamedQuery(name = "PghRolOpcion.findByIdentificadorRol", query = "SELECT p FROM PghRolOpcion p where p.estado='1' and p.idRol.identificadorRol=:identificadorRol order by p.idRolOpcion asc")
    // htorress - RSIS 1, 2 y 3 - Fin
})
public class PghRolOpcion extends Auditoria {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ROL_OPCION")
    private Long idRolOpcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghRol idRol;
    @JoinColumn(name = "ID_OPCION", referencedColumnName = "ID_OPCION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghOpcion idOpcion;

    public PghRolOpcion() {
    }

    public PghRolOpcion(Long idRolOpcion) {
        this.idRolOpcion = idRolOpcion;
    }

    public PghRolOpcion(Long idRolOpcion, String estado, String usuarioCreacion, Date fechaCreacion, String terminalCreacion) {
        this.idRolOpcion = idRolOpcion;
        this.estado = estado;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
    }

    public Long getIdRolOpcion() {
        return idRolOpcion;
    }

    public void setIdRolOpcion(Long idRolOpcion) {
        this.idRolOpcion = idRolOpcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PghRol getIdRol() {
        return idRol;
    }

    public void setIdRol(PghRol idRol) {
        this.idRol = idRol;
    }

    public PghOpcion getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(PghOpcion idOpcion) {
        this.idOpcion = idOpcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRolOpcion != null ? idRolOpcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghRolOpcion)) {
            return false;
        }
        PghRolOpcion other = (PghRolOpcion) object;
        if ((this.idRolOpcion == null && other.idRolOpcion != null) || (this.idRolOpcion != null && !this.idRolOpcion.equals(other.idRolOpcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.descargagmd.PghRolOpcion[ idRolOpcion=" + idRolOpcion + " ]";
    }
    
}
