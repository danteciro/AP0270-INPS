/**
* Resumen		
* Objeto		: PghCorreo.java
* Descripción		: Clase del modelo de dominio PghCorreo
* Fecha de Creación	: 12/05/2016  
* PR de Creación	: OSINE_SFS-480
* Autor			: Luis García Reyna
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* 
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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "PGH_CORREO")
@XmlRootElement
@NamedQueries({    
    @NamedQuery(name = "PghCorreo.findByCodigoFuncionalidad", query = "SELECT c FROM PghCorreo c where c.estado=1 and c.codigoFuncionalidad.codigoFuncionalidad=:codigoFuncionalidad"),
    @NamedQuery(name = "PghCorreo.findAll", query = "SELECT c FROM PghCorreo c")
})
public class PghCorreo extends Auditoria {

    @Id
    @Basic(optional = false)
    @Column(name = "ID_CORREO")
    private Long idCorreo;
    @Basic(optional = false)
    @Column(name = "CODIGO_FUNCIONALIDAD")
    private String codigoFuncionalidad;
    @Basic(optional = false)
    @Column(name = "ASUNTO")
    private String asunto;
    @Basic(optional = false)
    @Column(name = "MENSAJE")
    private String mensaje;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(mappedBy = "idCorreo", fetch = FetchType.LAZY)
    private List<PghDestinatarioCorreo> pghDestinatarioCorreoList;
    @Size(max = 200)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumns({
        @JoinColumn(name = "ID_OBLIGACION_TIPO", referencedColumnName = "ID_OBLIGACION_TIPO"),
        @JoinColumn(name = "ID_ACTIVIDAD", referencedColumnName = "ID_ACTIVIDAD"),
        @JoinColumn(name = "ID_PROCESO", referencedColumnName = "ID_PROCESO"),
        @JoinColumn(name = "ID_PRO_OBL_TIP", referencedColumnName = "ID_PRO_OBL_TIP")})
    @ManyToOne(fetch = FetchType.LAZY)
    private PghProcesoObligacionTipo pghProcesoObligacionTipo;
    
    public PghCorreo() {
    }

    public PghCorreo(Long idCorreo) {
        this.idCorreo = idCorreo;
    }

    public PghCorreo(Long idCorreo, String codigoFuncionalidad, String asunto, String mensaje, String estado, String usuarioCreacion, Date fechaCreacion, String terminalCreacion) {
        this.idCorreo = idCorreo;
        this.codigoFuncionalidad = codigoFuncionalidad;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.estado = estado;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
    }

    public Long getIdCorreo() {
        return idCorreo;
    }

    public void setIdCorreo(Long idCorreo) {
        this.idCorreo = idCorreo;
    }

    public String getCodigoFuncionalidad() {
        return codigoFuncionalidad;
    }

    public void setCodigoFuncionalidad(String codigoFuncionalidad) {
        this.codigoFuncionalidad = codigoFuncionalidad;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<PghDestinatarioCorreo> getPghDestinatarioCorreoList() {
        return pghDestinatarioCorreoList;
    }

    public void setPghDestinatarioCorreoList(List<PghDestinatarioCorreo> pghDestinatarioCorreoList) {
        this.pghDestinatarioCorreoList = pghDestinatarioCorreoList;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public PghProcesoObligacionTipo getPghProcesoObligacionTipo() {
        return pghProcesoObligacionTipo;
    }

    public void setPghProcesoObligacionTipo(PghProcesoObligacionTipo pghProcesoObligacionTipo) {
        this.pghProcesoObligacionTipo = pghProcesoObligacionTipo;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCorreo != null ? idCorreo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghCorreo)) {
            return false;
        }
        PghCorreo other = (PghCorreo) object;
        if ((this.idCorreo == null && other.idCorreo != null) || (this.idCorreo != null && !this.idCorreo.equals(other.idCorreo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghCorreo[ idCorreo=" + idCorreo + " ]";
    }
    
}