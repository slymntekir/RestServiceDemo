package pojos;

public class musteri 
{
    private int musteri_id;
    private String tc_no;
    private String ad;
    private String soyad;
    private String e_mail;
    private String adres;
    private String kullanici_adi;
    private String sifre;
    private String arac_plaka;
    private int bakiye;
    private String tel_no;

    public musteri() {
    }

    public musteri(String tc_no, String ad, String soyad, String e_mail, String adres, String kullanici_adi,
            String sifre, String arac_plaka, int bakiye, String tel_no) {
        this.tc_no = tc_no;
        this.ad = ad;
        this.soyad = soyad;
        this.e_mail = e_mail;
        this.adres = adres;
        this.kullanici_adi = kullanici_adi;
        this.sifre = sifre;
        this.arac_plaka = arac_plaka;
        this.bakiye = bakiye;
        this.tel_no = tel_no;
    }

    public int getMusteri_id() {
        return musteri_id;
    }

    public void setMusteri_id(int musteri_id) {
        this.musteri_id = musteri_id;
    }

    public String getTc_no() {
        return tc_no;
    }

    public void setTc_no(String tc_no) {
        this.tc_no = tc_no;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getKullanici_adi() {
        return kullanici_adi;
    }

    public void setKullanici_adi(String kullanici_adi) {
        this.kullanici_adi = kullanici_adi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getArac_plaka() {
        return arac_plaka;
    }

    public void setArac_plaka(String arac_plaka) {
        this.arac_plaka = arac_plaka;
    }

    public int getBakiye() {
        return bakiye;
    }

    public void setBakiye(int bakiye) {
        this.bakiye = bakiye;
    }

    public String getTel_no() {
        return tel_no;
    }

    public void setTel_no(String tel_no) {
        this.tel_no = tel_no;
    } 
}