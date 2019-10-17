package com.example.xpto.raxapp.global;

import android.app.Activity;

import com.example.xpto.raxapp.entityDAO.Pessoa;
import com.example.xpto.raxapp.entityDAO.PessoaDAO;

import java.util.ArrayList;
import java.util.List;

public class Global extends Activity{

    public static ArrayList<Pessoa> pessoas = new ArrayList<>();
    public static ArrayList<Pessoa> jogadores = new ArrayList<>();

    public static String times_string_global = "TIMES....";

    public static String times_fora_string_global = "";
    public static List<Integer> times_fora_nunmeros_saidos =  new ArrayList();

}
