package databasesprocess;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import pojos.Kiralanmis;
import pojos.Koordinat;
import pojos.lokasyon;

public class selects {

    baglanti b;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public selects() {
        b = new baglanti();
        b.baglan();
    }

    // değeri kalmadı
    public int getMaxMusteriId() {
        int sonuc;
        try {
            ps = b.con.prepareStatement("select max(kullanici_id) from Kullanici");
            rs = ps.executeQuery();
            rs.next();
            sonuc = rs.getInt(1);
            if (sonuc > 0) {
                return sonuc;
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            return -1;
        }
    }

    public int getMaxKiralananId() {
        int id;
        try {
            ps = b.con.prepareStatement("select max(kiralanan_id) from Kiralanan");
            rs = ps.executeQuery();
            rs.next();
            id = rs.getInt(1);
            if (id > 0) {
                return id;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return -1;
        }
    }

    // guncellendi 5/2/2017 21:41
    // çalışıyor
    public int getMusteriId(String kul_adi, String sifre) {
        try {
            ps = b.con.prepareStatement("select kullanici_id from Kullanici "
                    + "where kullanici_adi = ? and kullanici_sifre = ?");
            ps.setString(1, kul_adi);
            ps.setString(2, sifre);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            return -1;
        }
    }

    // guncellendi 5/2/2017 21:44 çalışıyor
    public int bakiyeSorgu(int musteri_id) {
        try {
            ps = b.con.prepareStatement("select bakiye from Kullanici where kullanici_id=?");
            ps.setInt(1, musteri_id);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt("bakiye");
        } catch (Exception e) {
            return -1;
        }
    }

    // guncellendi 5/2/2017 21:53 çalışıyor
    public List<lokasyon> lokasyonDon() {
        List<lokasyon> lokasyons = new ArrayList<>();
        lokasyons.clear();
        try {
            ps = b.con.prepareStatement("select lokasyon_adi from Lokasyon group by lokasyon_adi");
            rs = ps.executeQuery();
            while (rs.next()) {
                lokasyons.add(new lokasyon(rs.getString(1)));
            }
            return lokasyons;
        } catch (SQLException e) {
            List<lokasyon> emptyList = new ArrayList<>();
            emptyList.add(new lokasyon("bos"));
            emptyList.add(new lokasyon("bos1"));
            return emptyList;
        }
    }

    // guncellendi 5/2/2017 21:55
    public int lokasyonIdDon(String lokasyonAdi) {
        try {
            ps = b.con.prepareStatement("select lokasyon_id from Lokasyon where lokasyon_adi=?");
            ps.setString(1, lokasyonAdi);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            return -1;
        }
    }

    // guncellendi 5/2/2017 21:59
    public boolean plakaVarMi(String plaka) {
        try {
            ps = b.con.prepareStatement("select arac_plaka from Kullanici where arac_plaka=?");
            ps.setString(1, plaka);
            rs = ps.executeQuery();
            rs.next();
            if (!rs.getString(1).equals("")) {
                return true;
            } else if (rs.getString(1).equals("")) {
                return false;
            }
        } catch (Exception e) {
        }
        return false;
    }

    // en önemli veritabanı sorgularından biri
    // istenilen özelliklere göre uygun park alanı varsa
    // kullanıcıya gösterilmek üzere 
    // geri dönüş yapılıyor.
    // guncellendi 5/5/2017 20:08
    public List<Koordinat> koordinatDon(int lokasyon_id, Date tarih, Time bas_saat, Time bit_saat) 
    {
        String sorgu = "select konum_id,enlem,boylam,zoom,park_kodu from Konum where konum_id not in"
                + "(select konum_id from Kiralanan where tarih=? and"
                + "( ( (? >= baslangic_saat and ? <= bitis_saat) and (? >= baslangic_saat and ? <= bitis_saat) )"
                + " or "
                + "( (? <= baslangic_saat) and (? >= baslangic_saat and ? <= bitis_saat) )"
                + " or "
                + "( (? >= baslangic_saat and ? <= bitis_saat) and (? >= bitis_saat) )"
                + " or "
                + "( (? <= baslangic_saat) and (? >= bitis_saat) )"
                + " )"
                + " ) and lokasyon_id = ?";
        List<Koordinat> list = new ArrayList<>();
        list.clear();
        try {
            ps = b.con.prepareStatement(sorgu);
            ps.setDate(1, tarih);

            ps.setTime(2, bas_saat);
            ps.setTime(3, bas_saat);
            ps.setTime(4, bit_saat);
            ps.setTime(5, bit_saat);

            ps.setTime(6, bas_saat);
            ps.setTime(7, bit_saat);
            ps.setTime(8, bit_saat);

            ps.setTime(9, bas_saat);
            ps.setTime(10, bas_saat);
            ps.setTime(11, bit_saat);

            ps.setTime(12, bas_saat);
            ps.setTime(13, bit_saat);

            ps.setInt(14, lokasyon_id);

            rs = ps.executeQuery();
            while (rs.next()) {
                Koordinat k = new Koordinat();
                k.setKonum_id(rs.getInt(1));
                k.setEnlem(rs.getString(2));
                k.setBoylam(rs.getString(3));
                k.setZoom(rs.getInt(4));
                k.setParkKodu(rs.getString(5));

                list.add(k);
            }
        } catch (Exception e) {
            Koordinat k = new Koordinat(-1, e.getMessage(), e.getMessage(), 0, "-100");
            list.add(k);
        }
        return list;
    }

    // guncellendi 5/2/2017 22:18 çalışıyor.
    public int konumIdDon(String enlem, String boylam) {
        int konum_id;
        try {
            ps = b.con.prepareStatement("select konum_id from Konum "
                    + "where enlem=? and boylam=?");
            ps.setString(1, enlem);
            ps.setString(2, boylam);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            return -1;
        }
    }

    // kullanıcı aynı lokasyonda aynı tarih,saatte
    // birden fazla kiralama yapmaya çalıştığında
    // kontrol edilecek method
    // park alanı mevcut değilse 0 yani kiralanabilir.
    // guncellendi 5/5/2017 20:05
    public int kiralamaKontrol(Date tarih, int musteri_id, Time bas_saat, Time bit_saat) {
        int id;
        String sorgu_kiralama = "select count(*) from Kiralanan where kullanici_id=?"
                + " and "
                + " tarih=? "
                + " and "
                + " kiralanan_id not in "
                + "("
                + "select kiralanan_id from Kiralanan where "
                + "(? < baslangic_saat and ? < baslangic_saat) "
                + " or "
                + "(? > bitis_saat and ? > bitis_saat)"
                + ")";
        try {
            ps = b.con.prepareStatement(sorgu_kiralama);
            ps.setInt(1,musteri_id);
            ps.setDate(2,tarih);
            ps.setTime(3, bas_saat);
            ps.setTime(4, bit_saat);
            ps.setTime(5, bas_saat);
            ps.setTime(6, bit_saat);
            rs = ps.executeQuery();
            rs.next();
            id = rs.getInt(1);
            return id;
        }catch(SQLException e) {
            return -1;
        }
    }

    // guncellendi 5/2/2017 22:22 çalışıyor
    public boolean bakiyeAzalt(int musteri_id, int bakiye) {
        try {
            ps = b.con.prepareStatement("update Kullanici set bakiye=bakiye-? "
                    + "where kullanici_id=?");
            ps.setInt(1, bakiye);
            ps.setInt(2, musteri_id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    // kullanici idsine göre kiralanmış bilgilerini getiren method
    public List<Kiralanmis> kiralanmisAlanlariGetir(int kullanici_id)
    {
        List<Kiralanmis> kira_liste = new ArrayList<>();
        kira_liste.clear();
        String sorgu = "select ko.enlem,ko.boylam,ko.park_kodu,lo.sehir,"
                + "lo.lokasyon_adi,ki.tarih,"
                + "ki.baslangic_saat,ki.bitis_saat,ki.fiyat "
                + "from Kiralanan ki inner join Konum ko "
                + "on ki.konum_id=ko.konum_id inner join Lokasyon lo "
                + "on ko.lokasyon_id=lo.lokasyon_id where kullanici_id=?";
        try
        {
            ps = b.con.prepareStatement(sorgu);
            ps.setInt(1, kullanici_id);
            rs = ps.executeQuery();
            while(rs.next())
            {
                Kiralanmis k = new Kiralanmis(rs.getFloat(1), 
                        rs.getFloat(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getTime(7),
                        rs.getTime(8),
                        rs.getInt(9));
                kira_liste.add(k);
            }
            return kira_liste;
        }catch(SQLException e)
        {
            return new ArrayList<>();
        }
    }
}