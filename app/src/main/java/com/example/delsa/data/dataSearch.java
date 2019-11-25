package com.example.delsa.data;

import com.example.delsa.R;

import java.util.ArrayList;

public class dataSearch {
    private static String[] searchJudul = {
            "Bantu saudara kita terkena bencana Longsor Bandang",
            "Sahabat kita sedang kesusahan nih mulai dari sekarang",
            "Bantu saudara kita terkena bencana Longsor Bandang",
            "Sahabat kita sedang kesusahan nih mulai dari sekarang",
            "Bantu saudara kita terkena bencana Longsor Bandang",
            "Sahabat kita sedang kesusahan nih mulai dari sekarang"
    };

    private static String[] searchLink = {
            "kitamampu.com",
            "kamimampu.com",
            "kitamampu.com",
            "kamimampu.com",
            "kitamampu.com",
            "kamimampu.com"

    };

    private static String[] searchHari = {
            "15 hari lagi",
            "3 hari lagi",
            "15 hari lagi",
            "3 hari lagi",
            "15 hari lagi",
            "3 hari lagi"
    };

    private static String[] searchDeskripsi = {
            "Ayo bantu saudara kita yang sedang tertimpa musibah.",
            "Ayo bantu saudara kita yang sedang tertimpa musibah.",
            "Ayo bantu saudara kita yang sedang tertimpa musibah.",
            "Ayo bantu saudara kita yang sedang tertimpa musibah.",
            "Ayo bantu saudara kita yang sedang tertimpa musibah.",
            "Ayo bantu saudara kita yang sedang tertimpa musibah."
    };

    private static String[] searchPhoto = {

    };

    static ArrayList<dataSearch> getListData() {
        ArrayList<dataSearch> list = new ArrayList<>();
        for (int position = 0; position < searchJudul.length; position++) {
            dataSearch search = new dataSearch();
            search.setJudul(searchJudul[position]);
            search.setLink(searchLink[position]);
            search.setHari(searchHari[position]);
            search.setDeskripsi(searchDeskripsi[position]);
            search.setPhoto(searchPhoto[position]);
            list.add(search);
        }
        return list;
    }

}