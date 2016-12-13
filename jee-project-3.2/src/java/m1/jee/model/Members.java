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

@Entity
@Table(name = "MEMBERS")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Members.findAll", query = "SELECT m FROM Members m"),
  @NamedQuery(name = "Members.findInList", query = "SELECT m FROM Members m WHERE m.id IN :list")})
public class Members implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "ID")
  private Integer id;
  
  @Size(max = 255)
  @Column(name = "ADRESS")
  private String address;
  
  @Size(max = 255)
  @Column(name = "CITY")
  private String city;
  
  @Size(max = 255)
  @Column(name = "EMAIL")
  private String email;
  
  @Size(max = 255)
  @Column(name = "FIRSTNAME")
  private String firstName;
  
  @Size(max = 255)
  @Column(name = "NAME")
  private String name;
  
  @Size(max = 255)
  @Column(name = "POSTALCODE")
  private String postalCode;
  
  @Size(max = 255)
  @Column(name = "TELHOME")
  private String telHome;
  
  @Size(max = 255)
  @Column(name = "TELMOB")
  private String telMob;
  
  @Size(max = 255)
  @Column(name = "TELPRO")
  private String telPro;
  
  /**
   * Default constructor
   */
  public Members(){
    
  }

  /**
   * Constructor of a new members
   * @param name
   * @param firstName
   * @param telHome
   * @param telMob
   * @param telPro
   * @param address
   * @param postalCode
   * @param city
   * @param email 
   */
  public Members(String name, String firstName, String telHome, String telMob, String telPro, String address, String postalCode, String city, String email) {
    this.name = name;
    this.firstName = firstName;
    this.telHome = telHome;
    this.telMob = telMob;
    this.telPro = telPro;
    this.address = address;
    this.postalCode = postalCode;
    this.city = city;
    this.email = email;    
  }

  /**
   * Constructor with only primary key parameter
   * @param id Integer
   */
  public Members(Integer id) {
    this.id = id;
  }

  /**
   * Get primary key
   * @return Integer
   */
  public Integer getId() {
    return id;
  }

  /**
   * Set primary key
   * @param id Integer
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * Get address
   * @return String
   */
  public String getAddress() {
    return address;
  }

  /**
   * Set address
   * @param adress String
   */
  public void setAddress(String adress) {
    this.address = adress;
  }

  /**
   * Get city
   * @return String
   */
  public String getCity() {
    return city;
  }

  /**
   * Set city
   * @param city String
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * Get email
   * @return String
   */
  public String getEmail() {
    return email;
  }

  /**
   * Set email
   * @param email String
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Get firstname
   * @return String
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Set firstname
   * @param firstname 
   */
  public void setFirstname(String firstname) {
    this.firstName = firstname;
  }

  /**
   * Get name
   * @return String
   */
  public String getName() {
    return name;
  }

  /**
   * Set name
   * @param name String
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get postal code
   * @return String
   */
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * Set postal code
   * @param postalcode String
   */
  public void setPostalCode(String postalcode) {
    this.postalCode = postalcode;
  }

  /**
   * Get telephone home
   * @return String
   */
  public String getTelHome() {
    return telHome;
  }

  /**
   * Set telephone home
   * @param telhome 
   */
  public void setTelHome(String telhome) {
    this.telHome = telhome;
  }

  /**
   * Get mobile phone
   * @return String
   */
  public String getTelMob() {
    return telMob;
  }

  /**
   * Set mobile phone
   * @param telmob String
   */
  public void setTelMob(String telmob) {
    this.telMob = telmob;
  }

  /**
   * Get professional phone
   * @return String
   */
  public String getTelPro() {
    return telPro;
  }

  /**
   * set professional phone
   * @param telpro 
   */
  public void setTelPro(String telpro) {
    this.telPro = telpro;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Members)) {
      return false;
    }
    
    Members other = (Members) object;
    return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
  }

  @Override
  public String toString() {
    return "m1.jee.model.Members[ id=" + id + " ]";
  }
  
}
