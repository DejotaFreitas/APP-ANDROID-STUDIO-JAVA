package com.dejota.petadote.util;

import android.graphics.Bitmap;
import android.widget.ListView;

import com.dejota.petadote.adapter.MeuPetAdapter;
import com.dejota.petadote.entity.MeuPet;
import com.dejota.petadote.entity.Pet;
import com.dejota.petadote.entity.Relacao;
import com.dejota.petadote.entity.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Global {


    public static Usuario usuario;

    public static Map<Integer, Bitmap> usuarioImg = new HashMap<>();
    public static Map<Integer, Bitmap> petImg = new HashMap<>();

    public static ArrayList<Pet> pets = new ArrayList<>();
    public static int offset_pesquisa = 0;

    public static ArrayList<MeuPet> meuspets = new ArrayList<>();

    public static ArrayList<Relacao> listadocao = new ArrayList<>();



}
