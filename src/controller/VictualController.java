package controller;

import model.classes.Victual;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VictualController {
    ConnectionHandler conn = new ConnectionHandler();

    public List<Victual> getVictual() {
        conn.connect();
        List<Victual> victuals = new ArrayList<>();
        String query = "SELECT * FROM victual";

//        try {
//            PreparedStatement statement = conn.con.prepareStatement(query);
//            statement.setString(1, nik);
//
//            ResultSet result = statement.executeQuery();
//
//            while (result.next()) {
//                penduduk.setId(result.getInt("id"));
//                penduduk.setNIK(result.getString("NIK"));
//                penduduk.setNama(result.getString("nama"));
//                penduduk.setTempatLahir(result.getString("tempatLahir"));
//                penduduk.setTanggalLahir(result.getDate("tanggalLahir"));
//                penduduk.setJenisKelamin(convertJenisKelamin(result.getString("jenisKelamin")));
//                penduduk.setGolonganDarah(convertGolonganDarah(result.getString("golonganDarah")));
//                penduduk.setAlamat(result.getString("alamat"));
//                penduduk.setRtRw(result.getString("rtRw"));
//                penduduk.setKelDesa(result.getString("kelDesa"));
//                penduduk.setKecamatan(result.getString("kecamatan"));
//                penduduk.setAgama(convertAgama(result.getString("agama")));
//                penduduk.setStatusPerkawinan(convertStatusPerkawinan(result.getString("statusPerkawinan")));
//                penduduk.setPekerjaan(result.getString("pekerjaan"));
//                penduduk.setKewarganegaraan(result.getString("kewarganegaraan"));
//                penduduk.setFoto(result.getString("foto"));
//                penduduk.setTandaTangan(result.getString("tandaTangan"));
//                penduduk.setBerlakuHingga(result.getString("berlakuHingga"));
//                penduduk.setKotaPembuatan(result.getString("kotaPembuatan"));
//                penduduk.setTanggalPembuatan(result.getDate("tanggalPembuatan"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            conn.disconnect();
//        }
//
        return victuals;
    }

    public Victual getVictuals(int id) {
        Victual victual = null;



        return victual;
    }
}
