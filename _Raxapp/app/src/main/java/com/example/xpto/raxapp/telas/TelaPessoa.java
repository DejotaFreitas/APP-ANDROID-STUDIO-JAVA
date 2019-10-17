package com.example.xpto.raxapp.telas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.xpto.raxapp.R;
import com.example.xpto.raxapp.adapter.PessoaAdapter;
import com.example.xpto.raxapp.entityDAO.JogadorDAO;
import com.example.xpto.raxapp.entityDAO.Pessoa;
import com.example.xpto.raxapp.entityDAO.PessoaDAO;
import com.example.xpto.raxapp.global.Global;

import java.util.Collections;
import java.util.Comparator;


public class TelaPessoa extends Fragment {

    private GridView listaPessoa;
    private PessoaAdapter pessoaAdapter;
    private EditText input_nome;
    private ImageButton button_add_pessoa;
    private PessoaDAO pdao;

    public TelaPessoa() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_tela_pessoa, container, false);
        listaPessoa = view.findViewById(R.id.tela_pessoa_lista_pessoas);
        input_nome = view.findViewById(R.id.tela_pessoa_input_nome);
        button_add_pessoa = view.findViewById(R.id.tela_pessoa_button_add_pessoa);
        pdao = new PessoaDAO(getActivity());
        pessoaAdapter = new PessoaAdapter(getActivity());
        listaPessoa.setAdapter(pessoaAdapter);
        atualizar_lista_pessoas();



        button_add_pessoa.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    button_add_pessoa.setPadding(10, 10, 10, 10);
                    add_pessoa();
                } else {
                    button_add_pessoa.setPadding(0, 0, 0, 0);
                }
            }
        });

        button_add_pessoa.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction()==MotionEvent.ACTION_DOWN){
                    button_add_pessoa.setPadding(10, 10, 10, 10);
                } else {
                    button_add_pessoa.setPadding(0, 0, 0, 0);
                }
                if (arg1.getAction()==MotionEvent.ACTION_UP){
                    add_pessoa();
                }
                return true;
            }
        });


        input_nome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String nome = charSequence.toString();
                Global.pessoas = pdao.buscarPorNome(nome);
                pessoaAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view;
    }

    public void add_pessoa(){
        String nome = input_nome.getText().toString();
        if (!nome.isEmpty()) {
            pdao.insert(new Pessoa(nome));
            atualizar_lista_pessoas();
            input_nome.setText("");
            input_nome.requestFocus();
        }
    }


    public void atualizar_lista_pessoas(){
        Global.pessoas = pdao.selectAll();
        pessoaAdapter.notifyDataSetChanged();
    }

//    public void ordenar_por_nome(){
//        Collections.sort (Global.pessoas, new Comparator() {
//            public int compare(Object o1, Object o2) {
//                Pessoa c1 = (Pessoa) o1;
//                Pessoa c2 = (Pessoa) o2;
//                return c1.getNome().compareToIgnoreCase(c2.getNome());
//            }
//        });
//    }




}
