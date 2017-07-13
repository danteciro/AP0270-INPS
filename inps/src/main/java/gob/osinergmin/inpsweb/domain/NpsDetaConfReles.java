/**
* Resumen		
* Objeto			: NpsDetaConfReles.java
* Descripción		: Clase del modelo de dominio NpsDetaConfReles.
* Fecha de Creación	: 05/10/2016.
* PR de Creación	: OSINE_SFS-1063.
* Autor				: Hernan Torres.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
*
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author htorress
 */
@Entity
@Table(name = "NPS_DETA_CONF_RELES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NpsDetaConfReles.findAll", query = "SELECT n FROM NpsDetaConfReles n"),
    @NamedQuery(name = "NpsDetaConfReles.findByIdDetaConfReles", query = "SELECT n FROM NpsDetaConfReles n WHERE n.idDetaConfReles = :idDetaConfReles"),
    @NamedQuery(name = "NpsDetaConfReles.findByIdEtapa", query = "SELECT n FROM NpsDetaConfReles n WHERE n.idEtapa = :idEtapa"),
    @NamedQuery(name = "NpsDetaConfReles.findByEstado", query = "SELECT n FROM NpsDetaConfReles n WHERE n.estado = :estado"),
    @NamedQuery(name = "NpsDetaConfReles.findByUsuarioCreacion", query = "SELECT n FROM NpsDetaConfReles n WHERE n.usuarioCreacion = :usuarioCreacion"),
    @NamedQuery(name = "NpsDetaConfReles.findByFechaCreacion", query = "SELECT n FROM NpsDetaConfReles n WHERE n.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "NpsDetaConfReles.findByTerminalCreacion", query = "SELECT n FROM NpsDetaConfReles n WHERE n.terminalCreacion = :terminalCreacion"),
    @NamedQuery(name = "NpsDetaConfReles.findByUsuarioActualizacion", query = "SELECT n FROM NpsDetaConfReles n WHERE n.usuarioActualizacion = :usuarioActualizacion"),
    @NamedQuery(name = "NpsDetaConfReles.findByFechaActualizacion", query = "SELECT n FROM NpsDetaConfReles n WHERE n.fechaActualizacion = :fechaActualizacion"),
    @NamedQuery(name = "NpsDetaConfReles.findByTerminalActualizacion", query = "SELECT n FROM NpsDetaConfReles n WHERE n.terminalActualizacion = :terminalActualizacion")})
public class NpsDetaConfReles extends Auditoria {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DETA_CONF_RELES")
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "NPS_DETA_CONF_RELES_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idDetaConfReles;
    @Column(name = "ID_ETAPA")
    private Long idEtapa;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_CONFIGURACION_RELES", referencedColumnName = "ID_CONFIGURACION_RELES")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private NpsConfiguracionReles idConfiguracionReles;

    public NpsDetaConfReles() {
    }

    public NpsDetaConfReles(Long idDetaConfReles) {
        this.idDetaConfReles = idDetaConfReles;
    }

    public NpsDetaConfReles(Long idDetaConfReles, String estado) {
        this.idDetaConfReles = idDetaConfReles;
        this.estado = estado;
    }

    public Long getIdDetaConfReles() {
        return idDetaConfReles;
    }

    public void setIdDetaConfReles(Long idDetaConfReles) {
        this.idDetaConfReles = idDetaConfReles;
    }

    public Long getIdEtapa() {
        return idEtapa;
    }

    public void setIdEtapa(Long idEtapa) {
        this.idEtapa = idEtapa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public NpsConfiguracionReles getIdConfiguracionReles() {
        return idConfiguracionReles;
    }

    public void setIdConfiguracionReles(NpsConfiguracionReles idConfiguracionReles) {
        this.idConfiguracionReles = idConfiguracionReles;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetaConfReles != null ? idDetaConfReles.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NpsDetaConfReles)) {
            return false;
        }
        NpsDetaConfReles other = (NpsDetaConfReles) object;
        if ((this.idDetaConfReles == null && other.idDetaConfReles != null) || (this.idDetaConfReles != null && !this.idDetaConfReles.equals(other.idDetaConfReles))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.NpsDetaConfReles[ idDetaConfReles=" + idDetaConfReles + " ]";
    }
    
}