/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m1.jee.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author FABIEN
 */
@Entity
@Table(name = "MEMBERS")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Members.findAll", query = "SELECT m FROM Members m")
  , @NamedQuery(name = "Members.findById", query = "SELECT m FROM Members m WHERE m.id = :id")
  , @NamedQuery(name = "Members.findByAdress", query = "SELECT m FROM Members m WHERE m.adress = :adress")
  , @NamedQuery(name = "Members.findByCity", query = "SELECT m FROM Members m WHERE m.city = :city")
  , @NamedQuery(name = "Members.findByEmail", query = "SELECT m FROM Members m WHERE m.email = :email")
  , @NamedQuery(name = "Members.findByFirstname", query = "SELECT m FROM Members m WHERE m.firstname = :firstname")
  , @NamedQuery(name = "Members.findByName", query = "SELECT m FROM Members m WHERE m.name = :name")
  , @NamedQuery(name = "Members.findByPostalcode", query = "SELECT m FROM Members m WHERE m.postalcode = :postalcode")
  , @NamedQuery(name = "Members.findByTelhome", query = "SELECT m FROM Members m WHERE m.telhome = :telhome")
  , @NamedQuery(name = "Members.findByTelmob", query = "SELECT m FROM Members m WHERE m.telmob = :telmob")
  , @NamedQuery(name = "Members.findByTelpro", query = "SELECT m FROM Members m WHERE m.telpro = :telpro")})
public class Members implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "ID")
  private Integer id;
  @Size(max = 255)
  @Column(name = "ADRESS")
  private String adress;
  @Size(max = 255)
  @Column(name = "CITY")
  private String city;
  // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
  @Size(max = 255)
  @Column(name = "EMAIL")
  private String email;
  @Size(max = 255)
  @Column(name = "FIRSTNAME")
  private String firstname;
  @Size(max = 255)
  @Column(name = "NAME")
  private String name;
  @Size(max = 255)
  @Column(name = "POSTALCODE")
  private String postalcode;
  @Size(max = 255)
  @Column(name = "TELHOME")
  private String telhome;
  @Size(max = 255)
  @Column(name = "TELMOB")
  private String telmob;
  @Size(max = 255)
  @Column(name = "TELPRO")
  private String telpro;

  public Members() {
  }

  public Members(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getAdress() {
    return adress;
  }

  public void setAdress(String adress) {
    this.adress = adress;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPostalcode() {
    return postalcode;
  }

  public void setPostalcode(String postalcode) {
    this.postalcode = postalcode;
  }

  public String getTelhome() {
    return telhome;
  }

  public void setTelhome(String telhome) {
    this.telhome = telhome;
  }

  public String getTelmob() {
    return telmob;
  }

  public void setTelmob(String telmob) {
    this.telmob = telmob;
  }

  public String getTelpro() {
    return telpro;
  }

  public void setTelpro(String telpro) {
    this.telpro = telpro;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Members)) {
      return false;
    }
    Members other = (Members) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "m1.jee.model.Members[ id=" + id + " ]";
  }
  
}
