package services;

import databasesprocess.inserts;
import databasesprocess.selects;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import pojos.Kiralanmis;
import pojos.Koordinat;
import pojos.lokasyon;
import pojos.musteri;

@Path("/databases")
public class resteasyone {

    inserts ins = new inserts();
    selects sel = new selects();

    // kullanici bilgileri ekleyen GET servis isteği
    // bilgileri url üzerinden çekiyor.ekleme başarılı ise
    // 'tamam' stringi return ediliyor.
    // guncellendi 5/2/2017 21:38
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/musteriekle")
    public String musteriEkle(@QueryParam("tc_no") String tc_no,
            @QueryParam("ad") String ad,
            @QueryParam("soyad") String soyad,
            @QueryParam("e_mail") String e_mail,
            @QueryParam("adres") String adres,
            @QueryParam("kullanici_adi") String kullanici_adi,
            @QueryParam("sifre") String sifre,
            @QueryParam("arac_plaka") String arac_plaka,
            @QueryParam("tel_no") String tel_no) {
        musteri m = new musteri();
        m.setTc_no(tc_no);
        m.setAd(ad);
        m.setSoyad(soyad);
        m.setE_mail(e_mail);
        m.setAdres(adres);
        m.setKullanici_adi(kullanici_adi);
        m.setSifre(sifre);
        m.setBakiye(0);
        m.setArac_plaka(arac_plaka);
        m.setTel_no(tel_no);
        String sonuc = ins.musteriEkle(m);
        return sonuc;
    }

    // kullanıcı adi ve şifreye göre
    // kullanıcı id'si dönen method
    // guncellendi 5/2/2017 21:42
    // çalışıyor
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/idsorgula")
    public int idSorgula(@QueryParam("kullanici_adi") String kullanici_adi,
            @QueryParam("sifre") String sifre) {
        return sel.getMusteriId(kullanici_adi, sifre);
    }

    // güncel bakiye sorgusu
    // guncellendi 5/2/2017 21:44 çalışıyor
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/bakiyeSorgula")
    public int bakiyeSorgula(@QueryParam("id") int id) {
        return sel.bakiyeSorgu(id);
    }

    // kullanıcı id'sine göre
    // bakiye yükleyen GET metodu
    // guncellendi 5/2/2017 21:47 çalışıyor.
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/tlYukle")
    public String tlYukle(@QueryParam("id") int id,
            @QueryParam("miktar") int miktar) {
        return ins.tlYukle(miktar, id);
    }

    // tüm lokasyonları döndüren
    // metod
    // guncellendi 5/2/2017 21:53 çalışıyor
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/lokasyonYukle")
    public List<lokasyon> lokasyonYukle() {
        return sel.lokasyonDon();
    }

    // lokasyon adina göre
    // lokasyon id'si döndüren metod
    // guncellendi 5/2/2017 21:55
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/lokasyonIdDon")
    public int lokasyonIdDon(@QueryParam("lokasyon_adi") String lokasyon_adi) {
        return sel.lokasyonIdDon(lokasyon_adi);
    }

    // kullanıcının isteğine göre uygun
    // kordinatları dönen metod
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/koordinatDon")
    public List<Koordinat> koordinatDon(@QueryParam("lokasyon_id") int id,
            @QueryParam("tarih") Date tarih,
            @QueryParam("bas_saat") Time bas_saat,
            @QueryParam("bit_saat") Time bit_saat) {
        return sel.koordinatDon(id, tarih, bas_saat, bit_saat);
    }

    // araç plaka sorgusu yapan method
    // guncellendi 5/2/2017 22:00 çalışıyor
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/plakaSorgu")
    public boolean plakaVarMi(@QueryParam("plaka") String plaka) {
        return sel.plakaVarMi(plaka);
    }

    // enlem ve boylama göre konum_id'sini veritabanından çeken method
    // guncellendi 5/2/2017 22:18 çalışıyor. 
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/konumIdDon")
    public int konumIdDon(@QueryParam("enlem") String enlem,
            @QueryParam("boylam") String boylam) {
        return sel.konumIdDon(enlem, boylam);
    }

    // daha önce aynı koşullarda kiralama yapılmış mı yoksa yapılmamış mı
    // guncellendi 5/5/2017 20:11
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/kiralamaKontrol")
    public int kiralamaKontrol(@QueryParam("tarih") Date date,
            @QueryParam("musteri_id") int musteri_id,
            @QueryParam("bas_saat") Time bas_saat,
            @QueryParam("bit_saat") Time bit_saat) {
        return sel.kiralamaKontrol(date, musteri_id, bas_saat, bit_saat);
    }

    // kiralama işlemini yapan web servis methodu
    // guncellendi 5/2/2017 22:39 çalışıyor ancak android tarafı eksik
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/kirala")
    public String kirala(@QueryParam("musteri_id") int m_id,
            @QueryParam("konum_id") int konum_id,
            @QueryParam("lokasyon_id") int lokasyon_id,
            @QueryParam("tarih") Date tarih,
            @QueryParam("bas_saat") Time bas_saat,
            @QueryParam("bit_saat") Time bit_saat,
            @QueryParam("fiyat") int fiyat) {
        try {
            return ins.kirala(m_id, konum_id, lokasyon_id, tarih, bas_saat, bit_saat, fiyat);
        } catch (Exception e) {
            return "2. " + e.getMessage();
        }
    }

    // guncellendi 5/2/2017 22:19
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("bakiyeAzalt")
    public boolean bakiyeAzalt(@QueryParam("musteri_id") int musteri_id,
            @QueryParam("bakiye") int bakiye) {
        return sel.bakiyeAzalt(musteri_id, bakiye);
    }

    // kullanici bilgileri guncelleyen method
    @GET
    @Produces("text/plain")
    @Path("/kullaniciGuncelle")
    public String kullaniciGuncelle(@QueryParam("kullanici_id") int kullanici_id,
            @QueryParam("tc_no") String tc_no,
            @QueryParam("adi") String adi,
            @QueryParam("soyadi") String soyadi,
            @QueryParam("arac_plaka") String plaka,
            @QueryParam("kul_adi") String kullanici_adi,
            @QueryParam("sifre") String sifre,
            @QueryParam("email") String email,
            @QueryParam("tel_no") String tel_no,
            @QueryParam("adres") String adres) {
        try {
            return ins.kullaniciGuncelle(kullanici_id, tc_no, adi, soyadi, plaka,
                    kullanici_adi, sifre, email, tel_no, adres);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/kiralanmaBilgileri")
    public List<Kiralanmis> kiralanmaBilgileriGetir(
            @QueryParam("kullanici_id")int kullanici_id)
    {
        return sel.kiralanmisAlanlariGetir(kullanici_id);
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/TlYukle")
    public String tlYukle1(@QueryParam("tl_kodu")String tl_kodu,
            @QueryParam("kullanici_id")int kullanici_id)
    {
        return ins.tlYukle(tl_kodu, kullanici_id);
    }
}
