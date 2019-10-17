package com.dejota.cameraougaleria;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class Lista extends AppCompatActivity {

    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        listview = (ListView) findViewById(R.id.lista);
        registerForContextMenu(listview);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Contato contaoSelecionado = (Contato) listview.getAdapter().getItem(info.position);

        final MenuItem editar = menu.add("Editar");
        editar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(Lista.this, MainActivity.class);
                intent.putExtra("itemSelecionado", contaoSelecionado);
                startActivity(intent);
                return false;
            }
        });

        final MenuItem menuApagarContato = menu.add("Apagar contato.");
        menuApagarContato.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                new AlertDialog.Builder(Lista.this)
                        .setIcon(android.R.drawable.ic_dialog_dialer)
                        .setTitle("Apagar contato ?")
                        .setMessage("Deseja realmente apagar esse contato ?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ContatoDAO cdao = new ContatoDAO(Lista.this);
                                cdao.removerContato(contaoSelecionado);
                                cdao.close();
                                carregaLista();
                            }
                        })
                        .setNegativeButton("Não", null).show();
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    private void carregaLista() {
        ContatoDAO cdao = new ContatoDAO(this);
        List<Contato> contatos = cdao.carregarContato();
        ListaContatoAdapter lca = new ListaContatoAdapter(this, contatos);
        listview.setAdapter(lca);
    }



}
