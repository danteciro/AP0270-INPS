/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.osinergmin.inpsweb.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rsantosv
 */
@Entity
@Table(name = "PGH_PERSONA_GENERAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghPersonaGeneral.findAll", query = "SELECT p FROM PghPersonaGeneral p"),
    @NamedQuery(name = "PghPersonaGeneral.findByIdPersonaGeneral", query = "SELECT p FROM PghPersonaGeneral p WHERE p.idPersonaGeneral = :idPersonaGeneral")})
public class PghPersonaGeneral extends Auditoria {
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @Column(name = "ID_PERSONA_GENERAL")
	@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "PGH_PERSONA_GENERAL_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idPersonaGeneral;
    @Column(name = "NOMBRES_PERSONA")
    private String nombresPersona;
    @Column(name = "APELLIDO_PATERNO_PERSONA")
    private String apellidoPaternoPersona;
    @Column(name = "APELLIDO_MATERNO_PERSONA")
    private String apellidoMaternoPersona;
    @Basic(optional = false)
    @Column(name = "ID_TIPO_DOCUMENTO")
    private Long idTipoDocumento;
    @Basic(optional = false)
    @Column(name = "NUMERO_DOCUMENTO")
    private String numeroDocumento;
    @Column(name = "FLAG_ULTIMO")
    private String flagUltimo;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pghPersonaGeneral")
    private List<PghSupervisionPersonaGral> pghSupervisionPersonaGralList;

    public PghPersonaGeneral() {
    }

    public PghPersonaGeneral(Long idPersonaGeneral) {
        this.idPersonaGeneral = idPersonaGeneral;
    }

    public PghPersonaGeneral(Long idPersonaGeneral, Long idTipoDocumento, String numeroDocumento, String estado, Date fechaCreacion, String usuarioCreacion, String terminalCreacion) {
        this.idPersonaGeneral = idPersonaGeneral;
        this.idTipoDocumento = idTipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.terminalCreacion = terminalCreacion;
    }

    public Long getIdPersonaGeneral() {
        return idPersonaGeneral;
    }

    public void setIdPersonaGeneral(Long idPersonaGeneral) {
        this.idPersonaGeneral = idPersonaGeneral;
    }

    public String getNombresPersona() {
        return nombresPersona;
    }

    public void setNombresPersona(String nombresPersona) {
        this.nombresPersona = nombresPersona;
    }

    public String getApellidoPaternoPersona() {
        return apellidoPaternoPersona;
    }

    public void setApellidoPaternoPersona(String apellidoPaternoPersona) {
        this.apellidoPaternoPersona = apellidoPaternoPersona;
    }

    public String getApellidoMaternoPersona() {
        return apellidoMaternoPersona;
    }

    public void setApellidoMaternoPersona(String apellidoMaternoPersona) {
        this.apellidoMaternoPersona = apellidoMaternoPersona;
    }

    public Long getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Long idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getFlagUltimo() {
        return flagUltimo;
    }

    public void setFlagUltimo(String flagUltimo) {
        this.flagUltimo = flagUltimo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    @XmlTransient
    public List<PghSupervisionPersonaGral> getPghSupervisionPersonaGralList() {
        return pghSupervisionPersonaGralList;
    }

    public void setPghSupervisionPersonaGralList(List<PghSupervisionPersonaGral> pghSupervisionPersonaGralList) {
        this.pghSupervisionPersonaGralList = pghSupervisionPersonaGralList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersonaGeneral != null ? idPersonaGeneral.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghPersonaGeneral)) {
            return false;
        }
        PghPersonaGeneral other = (PghPersonaGeneral) object;
        if ((this.idPersonaGeneral == null && other.idPersonaGeneral != null) || (this.idPersonaGeneral != null && !this.idPersonaGeneral.equals(other.idPersonaGeneral))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inps.domain.PghPersonaGeneral[ idPersonaGeneral=" + idPersonaGeneral + " ]";
    }
    
}
